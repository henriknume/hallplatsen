package ex.nme.hallplatsen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ex.nme.hallplatsen.models.reseplaneraren.StopLocation;
import ex.nme.hallplatsen.models.reseplaneraren.Trip;
import ex.nme.hallplatsen.models.responses.LocationNameResponse;
import ex.nme.hallplatsen.models.responses.TripResponse;
import ex.nme.hallplatsen.services.ReseplanerarenService;
import ex.nme.hallplatsen.services.ReseplanerarenRestApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView time;
    private TextView fromText;
    private TextView toText;
    private ListView listView;
    private DepartureListAdapter adapter;
    private TripCard model;

    // placeholders
    private String LOCATION_ID_KVIBERG = "9021014004140000";
    private String LOCATION_ID_SKF = "9021014005862000";
    private String LOCATION_ID_LINDHOLMEN = "9021014004490000";
    private String mLocationFrom;
    private String mLocationTo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set up model
        TripCard model = TripCard.getInstance();

        // Set up layout
        time = (TextView) findViewById(R.id.time_last_updated);
        fromText = (TextView) findViewById(R.id.to_value);
        toText = (TextView) findViewById(R.id.from_value);
        listView = (ListView)findViewById(R.id.departure_list);
        adapter = new DepartureListAdapter(getApplicationContext(), model.getTripList());
        listView.setAdapter(adapter);
        initButtons();

        //Init placeholderlocations
        mLocationFrom = LOCATION_ID_KVIBERG;
        mLocationTo = LOCATION_ID_SKF;
    }

    @Override
    public void onResume(){
        super.onResume();
        if(model == null) {
            model = TripCard.getInstance();
        }

        time.setText(model.getTimeLastUpdated());
        fromText.setText(model.getFromName());
        toText.setText(model.getToName());
    }

    private void initButtons(){
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
                    requestTrip(model.getFromId(), model.getToId());
                }
            }
        });

        fromText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToChooseLocActivity(ChooseLocationActivity.EXTRA_VALUE_FROM);
            }
        });

        toText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToChooseLocActivity(ChooseLocationActivity.EXTRA_VALUE_TO);
            }
        });
    }

    private void requestTrip(String originId, String destId){
        Call<TripResponse> call = ReseplanerarenService.getService().getTrip(originId, destId,
                Utils.date(), Utils.time(), "json");
        call.enqueue(new Callback<TripResponse>() {
            @Override
            public void onResponse(Call<TripResponse> call, Response<TripResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse() successful");

                    model.setTripList(response.body().getTripList().getTrip());
                    adapter.clear();
                    adapter.addAll(model.getTripList());
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToChooseLocActivity(String calledBy) {
        // if called by "from" argument should be ChooseLocationActivity.EXTRA_VALUE_FROM
        Intent intent = new Intent(this, ChooseLocationActivity.class);
        intent.putExtra(ChooseLocationActivity.EXTRA_LABEL_SOURCE, calledBy);
        startActivity(intent);
    }
}
