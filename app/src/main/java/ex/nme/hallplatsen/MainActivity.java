package ex.nme.hallplatsen;

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
import ex.nme.hallplatsen.models.responses.TokenResponse;
import ex.nme.hallplatsen.models.responses.TripResponse;
import ex.nme.hallplatsen.services.ReseplanerarenRestService;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String AUTH = "Basic VEh4dTNZMkZXVm9sTnJCM3JaQXo3TVgzSDdZYTp0dGJYM2pOQmFTMkxzNzJUczNUTnFJbDZ4bzRh";
    private String mToken;
    private ReseplanerarenRestService mService;
    private Retrofit mRetrofit;
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

        // setup service
        OkHttpClient client = new OkHttpClient();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        mService = mRetrofit.create(ReseplanerarenRestService.class);

        // Set up layout
        mTempTextView = (TextView) findViewById(R.id.to_value);
        initButtons();

        mListView = (ListView)findViewById(R.id.departure_list);
        mTripList = new ArrayList<>();
        mAdapter = new DepartureListAdapter(getApplicationContext(), mTripList);
        mListView.setAdapter(mAdapter);
        requestToken();
    }

    private void initButtons(){
        Button updateBtn = (Button) findViewById(R.id.update_button);
        Button switchBtn = (Button) findViewById(R.id.switch_button);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do the request
                requestLocation("kviberg");
            }
        });

        switchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTempTextView.setText("");
                requestTrip(LOCATION_ID_KVIBERG, LOCATION_ID_SKF);
            }
        });
    }

    private void requestToken(){
        Call<TokenResponse> call = mService.generateToken(AUTH, "client_credentials", Constants.DEVICE_ID);

        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    mToken = "Bearer " + response.body().getAccessToken();
                    Log.d(TAG, "token generated...");
                    Log.d(TAG, "token:" + mToken );
                } else {
                    Log.d(TAG, "token error...");
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.d(TAG, "token onFailure...");
            }
        });

    }

    private void requestLocation(String name){
        Call<LocationNameResponse> call = mService.getLocationsByName(mToken, name, "json");
        call.enqueue(new Callback<LocationNameResponse>() {
            @Override
            public void onResponse(Call<LocationNameResponse> call, Response<LocationNameResponse> response) {
                if(response.isSuccessful()){
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
        Call<TripResponse> call = mService.getTrip(mToken, originId, destId, Utils.date(), Utils.time(), "json");
        call.enqueue(new Callback<TripResponse>() {
            @Override
            public void onResponse(Call<TripResponse> call, Response<TripResponse> response) {
                if(response.isSuccessful()){
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
}