package ex.nme.hallplatsen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
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
    public static final String EXTRA_LABEL_SOURCE = "extra.label.source";
    public static final String EXTRA_VALUE_FROM = "extra.value.from";
    public static final String EXTRA_VALUE_TO = "extra.value.to";

    private EditText searchInput;
    private Button selectBtn;
    private ListView listView;
    private List<StopLocation> locationList;
    private LocationListAdapter adapter;
    private TripCard model;
    private String calledBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        model = TripCard.getInstance();

        searchInput = (EditText) findViewById(R.id.location_name_edittext);
        selectBtn = (Button) findViewById(R.id.select_button);
        listView = (ListView) findViewById(R.id.location_results_list);
        locationList = new ArrayList<>();
        adapter = new LocationListAdapter(getApplicationContext(), locationList);
        listView.setAdapter(adapter);

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

        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(searchInput.getText())){
                    // EditText was empty
                    searchInput.setError("Enter a location.");
                } else {
                    requestLocations(searchInput.getText().toString().trim());
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final StopLocation selected = (StopLocation) parent.getItemAtPosition(position);
                if(calledBy.equals(EXTRA_VALUE_FROM)){
                    model.setFromLocation(selected);
                } else if (calledBy.equals(EXTRA_VALUE_TO)) {
                    model.setToLocation(selected);
                } else {
                    Log.e(TAG, "ERROR - onItemClick run with faulty calledBy value");
                }
                goToMainActivity();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(model == null) {
            model = TripCard.getInstance();
        }
        calledBy = getIntent().getExtras().getString(EXTRA_LABEL_SOURCE);
        if(calledBy.equals(EXTRA_VALUE_FROM)){
            setTitle("Select From location");
        } else if (calledBy.equals(EXTRA_VALUE_TO)) {
            setTitle("Select To location");
        } else {
            //TODO check what happends then...
        }
    }

    private void requestLocations(String name) {
        ReseplanerarenRestApi service = ReseplanerarenService.getService();
        Call<LocationNameResponse> call = service.getLocationsByName(name, "json");
        call.enqueue(new Callback<LocationNameResponse>() {
            @Override
            public void onResponse(Call<LocationNameResponse> call, Response<LocationNameResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse() successful");

                    List<StopLocation> locations = response.body().getLocationList().getStopLocation();
                    if (locations != null) {
                        adapter.clear();
                        adapter.addAll(locations);
                        adapter.notifyDataSetChanged();
                        Log.d(TAG, "some results- size: " + locations.size());
                    } else {
                        Log.d(TAG, "no results");
                        Toast.makeText(getApplicationContext(), "No locations found", Toast.LENGTH_SHORT).show();
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

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
