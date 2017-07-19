package ex.nme.hallplatsen.chooseloc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ex.nme.hallplatsen.R;
import ex.nme.hallplatsen.createcard.CreateActivity;
import ex.nme.hallplatsen.main.MainActivity;
import ex.nme.hallplatsen.models.CardStorage;
import ex.nme.hallplatsen.models.TripCard;
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

    private LocationListAdapter adapter;
    private CardStorage model;
    private String calledBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        model = CardStorage.getInstance();

        EditText searchInput = (EditText) findViewById(R.id.location_name_edittext);
        ListView listView = (ListView) findViewById(R.id.location_results_list);
        List<StopLocation> locationList = new ArrayList<>();
        adapter = new LocationListAdapter(getApplicationContext(), locationList);
        listView.setAdapter(adapter);

        searchInput.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                requestLocations(s.toString());
            }

            public void afterTextChanged(Editable s) {
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StopLocation selected = (StopLocation) parent.getItemAtPosition(position);
                TripCard card = model.getCreation();
                if(calledBy.equals(EXTRA_VALUE_FROM)){
                    card.setFromLocation(selected);
                } else if (calledBy.equals(EXTRA_VALUE_TO)) {
                    card.setToLocation(selected);
                } else {
                    Log.e(TAG, "ERROR - onItemClick run with faulty calledBy value");
                }
                goToCreateActivity();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(model == null) {
            model = CardStorage.getInstance();
        }
        calledBy = getIntent().getExtras().getString(EXTRA_LABEL_SOURCE);
        if(calledBy.equals(EXTRA_VALUE_FROM)){
            setTitle(getString(R.string.title_activity_choose_location_from));
        } else if (calledBy.equals(EXTRA_VALUE_TO)) {
            setTitle(getString(R.string.title_activity_choose_location_to));
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
                    } else {
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

    private void goToCreateActivity() {
        Intent intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
        finish();
    }
}
