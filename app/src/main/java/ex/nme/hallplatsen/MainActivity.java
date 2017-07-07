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
import android.widget.EditText;
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
    private ListView mListView;
    private List<Trip> mTripList;
    private DepartureListAdapter mAdapter;

    // placeholders
    private String LOCATION_ID_KVIBERG = "9021014004140000";
    private String LOCATION_ID_SKF = "9021014005862000";
    private String LOCATION_ID_LINDHOLMEN = "9021014004490000";
    private TextView mTempTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up layout
        mTempTextView = (TextView) findViewById(R.id.to_value);
        initButtons();

        mListView = (ListView)findViewById(R.id.departure_list);
        mTripList = new ArrayList<>();
        mAdapter = new DepartureListAdapter(getApplicationContext(), mTripList);
        mListView.setAdapter(mAdapter);
    }

    private void initButtons(){
        Button updateBtn = (Button) findViewById(R.id.update_button);
        Button switchBtn = (Button) findViewById(R.id.switch_button);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do the request
                requestTrip(LOCATION_ID_KVIBERG, LOCATION_ID_SKF);
            }
        });

        switchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToChooseLocActivity();
            }
        });
    }

    private void requestLocation(String name){
        ReseplanerarenRestApi service = ReseplanerarenService.getService();
        Call<LocationNameResponse> call = service.getLocationsByName(name, "json");
        call.enqueue(new Callback<LocationNameResponse>() {
            @Override
            public void onResponse(Call<LocationNameResponse> call, Response<LocationNameResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse() successful");

                    List<StopLocation> locations = response.body().getLocationList().getStopLocation();
                    if(locations != null){
                        StopLocation sl = locations.get(0);
                        mTempTextView.setText(sl.getName());
                        Log.d(TAG, sl.toString());
                    } else{
                        mTempTextView.setText("no results");
                        Log.d(TAG, "no results");
                    }
                } else {
                    Log.d(TAG, "onResponse() - not successful");
                }
            }

            @Override
            public void onFailure(Call<LocationNameResponse> call, Throwable t) {

            }
        });
    }

    private void requestTrip(String originId, String destId){
        ReseplanerarenRestApi service = ReseplanerarenService.getService();
        Call<TripResponse> call = service.getTrip(originId, destId, Utils.date(), Utils.time(), "json");
        call.enqueue(new Callback<TripResponse>() {
            @Override
            public void onResponse(Call<TripResponse> call, Response<TripResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse() successful");

                    mTripList = response.body().getTripList().getTrip();
                    mAdapter.clear();
                    mAdapter.addAll(mTripList);
                    mAdapter.notifyDataSetChanged();

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

    private void goToChooseLocActivity(){
        //TODO: Rename that class name!
        Intent intent = new Intent(this, CooseLocationActivity.class);
        startActivity(intent);
    }
}