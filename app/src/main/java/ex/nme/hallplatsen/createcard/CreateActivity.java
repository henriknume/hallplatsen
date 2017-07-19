package ex.nme.hallplatsen.createcard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import ex.nme.hallplatsen.R;
import ex.nme.hallplatsen.chooseloc.ChooseLocationActivity;
import ex.nme.hallplatsen.main.MainActivity;
import ex.nme.hallplatsen.models.CardStorage;
import ex.nme.hallplatsen.models.TripCard;

public class CreateActivity extends AppCompatActivity {

    private static final String TAG = "CreateActivity";

    private EditText fromInput;
    private EditText toInput;
    private CardStorage model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.title_activity_create));
        setSupportActionBar(toolbar);

        fromInput = (EditText) findViewById(R.id.location_input_from);
        toInput = (EditText) findViewById(R.id.location_input_to);
        model = CardStorage.getInstance();
    }

    @Override
    public void onResume(){
        super.onResume();
        if(model == null) {
            model = CardStorage.getInstance();
        }
        updateTextViews();

        TripCard creationCard = model.getCreation();

        if(creationCard != null && creationCard.isLocationsSelected()) {
            // Add new card and clear creation
            TripCard newCard = model.getCreation();
            model.addCard(newCard);
            model.clearCreation();
            // navigate to main
            goToMainActivity();
        }
    }

    private void initButtons(){
        // Set up listeners
        fromInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToChooseLocActivity(ChooseLocationActivity.EXTRA_VALUE_FROM);
            }
        });

        toInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToChooseLocActivity(ChooseLocationActivity.EXTRA_VALUE_TO);
            }
        });
    }

    private void goToChooseLocActivity(String calledBy) {
        // if called by "from" argument should be ChooseLocationActivity.EXTRA_VALUE_FROM
        Intent intent = new Intent(this, ChooseLocationActivity.class);
        intent.putExtra(ChooseLocationActivity.EXTRA_LABEL_SOURCE, calledBy);
        startActivity(intent);
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void updateTextViews() {
        TripCard creationCard = model.getCreation();
        if (creationCard != null) {
            fromInput.setText(creationCard.getFromName());
            toInput.setText(creationCard.getToName());
        }
    }
}
