package ex.nme.hallplatsen.createcard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import ex.nme.hallplatsen.R;
import ex.nme.hallplatsen.chooseloc.ChooseLocationActivity;
import ex.nme.hallplatsen.models.CardStorage;
import ex.nme.hallplatsen.models.TripCardOld;

public class CreateActivity extends AppCompatActivity {

    private static final String TAG = "CreateActivity";

    private TextView fromInput;
    private TextView toInput;
    private CardStorage model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.title_activity_create));
        setSupportActionBar(toolbar);
        fromInput = (TextView) findViewById(R.id.location_input_from);
        toInput = (TextView) findViewById(R.id.location_input_to);
        model = CardStorage.getInstance();
        initButtons();
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume()");
        if(model == null) {
            model = CardStorage.getInstance();
        }
        if(model.getCreation() == null){
            model.setCreation(new TripCardOld());
        }

        updateTextViews();

        TripCardOld creationCard = model.getCreation();
        if(creationCard != null && creationCard.isLocationsSelected()) {
            // Add new card and clear creation
            TripCardOld newCard = model.getCreation();
            model.addCard(newCard);
            model.clearCreation();
            // navigate to main
            //goToMainActivity();
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

    private void updateTextViews() {
        TripCardOld creationCard = model.getCreation();
        if (creationCard != null) {
            fromInput.setText(creationCard.getFromName());
            toInput.setText(creationCard.getToName());
        }
    }
}
