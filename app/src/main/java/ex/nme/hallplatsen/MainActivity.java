package ex.nme.hallplatsen;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ex.nme.hallplatsen.models.Departure;
import ex.nme.hallplatsen.models.reseplaneraren.StopLocation;
import ex.nme.hallplatsen.models.responses.LocationNameResponse;
import ex.nme.hallplatsen.services.ReseplanerarenRestService;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView tempTextView;
    private Button updateBtn;
    private Button switchBtn;
    private ArrayList<Departure> departures = new ArrayList<>();

    private ReseplanerarenRestService service;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up layout
        tempTextView = (TextView) findViewById(R.id.to_value);
        updateBtn = (Button) findViewById(R.id.update_button);
        switchBtn = (Button) findViewById(R.id.switch_button);

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
                // Reset
                tempTextView.setText("cleared.");
            }
        });

        // setup service
        OkHttpClient client = new OkHttpClient();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build();
        service = retrofit.create(ReseplanerarenRestService.class);

/*
        departures.add(new Departure("GUL", "Torslanda", "1"));
        departures.add(new Departure("16", "Eketrägatan", "3"));
        departures.add(new Departure("16", "Eketrägatan", "7"));
        departures.add(new Departure("GUL", "Torslanda", "16"));

        ListView listView=(ListView)findViewById(R.id.departure_list);

        DepartureListAdapter adapter = new DepartureListAdapter(getApplicationContext(), departures);
        listView.setAdapter(adapter);
*/

    }

    private void requestLocation(String name){
        Call<LocationNameResponse> call = service.getLocationsByName(name, "json");

        call.enqueue(new Callback<LocationNameResponse>() {
            @Override
            public void onResponse(Call<LocationNameResponse> call, Response<LocationNameResponse> response) {
                if(response.isSuccessful()){
                    StopLocation location = response.body().getLocationList().getStopLocation().get(0);
                    tempTextView.setText(location.toString());
                } else {

                }
            }

            @Override
            public void onFailure(Call<LocationNameResponse> call, Throwable t) {

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
