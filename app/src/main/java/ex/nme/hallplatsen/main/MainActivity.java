package ex.nme.hallplatsen.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import ex.nme.hallplatsen.Utils;
import ex.nme.hallplatsen.R;
import ex.nme.hallplatsen.createcard.CreateActivity;
import ex.nme.hallplatsen.models.CardStorage;
import ex.nme.hallplatsen.models.responses.TripResponse;
import ex.nme.hallplatsen.services.ReseplanerarenService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //private TextView timeLastUpdated;
    //private TextView fromText;
    //private TextView toText;

    private CardAdapter adapter;
    private CardStorage model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set up model
        CardStorage model = CardStorage.getInstance();

        // Set up layout

        //timeLastUpdated = (TextView) findViewById(R.id.time_last_updated);
        //fromText = (TextView) findViewById(R.id.from_value);
        //toText = (TextView) findViewById(R.id.to_value);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new CardAdapter(getApplicationContext(), model.getCards());
        recyclerView.setAdapter(adapter);
        initButtons();

    }

    @Override
    public void onResume(){
        super.onResume();
        if(model == null) {
            model = CardStorage.getInstance();
        }

        updateTextViews();

    }

    private void initButtons(){
        /*
        Button updateBtn = (Button) findViewById(R.id.update_button);
        Button switchBtn = (Button) findViewById(R.id.switch_button);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.isLocationsSelected()) {
                    requestTrip(model.getFromId(), model.getToId());
                }
            }
        });

        switchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.isLocationsSelected()) {
                    model.switchToAndFromLocations();
                    //Update
                    updateTextViews();
                    requestTrip(model.getFromId(), model.getToId());
                }
            }
        });


        */
    }

    private void requestTrip(String originId, String destId){
        String currentTime = Utils.time();

        Call<TripResponse> call = ReseplanerarenService.getService().getTrip(originId, destId,
                Utils.date(), currentTime, "json");
        call.enqueue(new Callback<TripResponse>() {
            @Override
            public void onResponse(Call<TripResponse> call, Response<TripResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse() successful");
                    model.setTripList(0, response.body().getTripList().getTrip());
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d(TAG, "requestTrip() - not successful");
                }
            }

            @Override
            public void onFailure(Call<TripResponse> call, Throwable t) {
                Log.d(TAG, "requestTrip() - on Failure");
                Log.d(TAG, t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_add_card:
                goToCreateActivity();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void updateTextViews() {
        //timeLastUpdated.setText(model.getTimeLastUpdated());
        //fromText.setText(model.getFromName());
        //toText.setText(model.getToName());
    }

    private void goToCreateActivity() {
        Intent intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
    }
}
