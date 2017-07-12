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
import android.widget.Toast;

import ex.nme.hallplatsen.models.responses.TripResponse;
import ex.nme.hallplatsen.services.ReseplanerarenService;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView timeLastUpdated;
    private TextView fromText;
    private TextView toText;

    private DepartureListAdapter adapter;
    private TripCardModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set up model
        TripCardModel model = TripCardModel.getInstance();

        // Set up layout
        timeLastUpdated = (TextView) findViewById(R.id.time_last_updated);
        fromText = (TextView) findViewById(R.id.from_value);
        toText = (TextView) findViewById(R.id.to_value);
        ListView listView = (ListView)findViewById(R.id.departure_list);
        adapter = new DepartureListAdapter(getApplicationContext(), model.getTripList());
        listView.setAdapter(adapter);
        initButtons();
    }

    @Override
    public void onResume(){
        super.onResume();
        if(model == null) {
            model = TripCardModel.getInstance();
        }

        updateTextViews();

        if (model.isLocationsSelected()) {
            requestTrip(model.getFromId(), model.getToId());
        }
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
                    updateTextViews();
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
        String currentTime = Utils.time();
        model.setTimeLastUpdated(currentTime);
        timeLastUpdated.setText(currentTime);

        Call<TripResponse> call = ReseplanerarenService.getService().getTrip(originId, destId,
                Utils.date(), currentTime, "json");
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

    private void updateTextViews() {
        timeLastUpdated.setText(model.getTimeLastUpdated());
        fromText.setText(model.getFromName());
        toText.setText(model.getToName());
    }
}
