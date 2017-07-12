package ex.nme.hallplatsen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import ex.nme.hallplatsen.models.reseplaneraren.StopLocation;
import ex.nme.hallplatsen.models.responses.LocationNameResponse;
import ex.nme.hallplatsen.services.ReseplanerarenRestApi;
import ex.nme.hallplatsen.services.ReseplanerarenService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseLocationActivity extends AppCompatActivity {

    private static final String TAG = "ChooseLocationActivity";

    private EditText searchInput;
    private ListView locationResults;
    TripCard model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        model = TripCard.getInstance();

        searchInput = (EditText) findViewById(R.id.location_name_edittext);
        locationResults = (ListView) findViewById(R.id.location_results_list);

        searchInput.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged()");
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged()");

            }

            public void afterTextChanged(Editable s) {
                // you can call or do what you want with your EditText here
                Log.d(TAG, "afterTextChanged()");
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        if(model == null) {
            model = TripCard.getInstance();
        }

    }

    private void requestLocations(String name){
        ReseplanerarenRestApi service = ReseplanerarenService.getService();
        Call<LocationNameResponse> call = service.getLocationsByName(name, "json");
        call.enqueue(new Callback<LocationNameResponse>() {
            @Override
            public void onResponse(Call<LocationNameResponse> call, Response<LocationNameResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse() successful");

                    List<StopLocation> locations = response.body().getLocationList().getStopLocation();
                    if (locations != null){
                        StopLocation sl = locations.get(0);
                        //fromText.setText(sl.getName());
                        Log.d(TAG, sl.toString());
                    } else{
                        //fromText.setText("no results");
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

    private void goToChooseLocActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
