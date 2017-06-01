package ex.nme.hallplatsen;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ex.nme.hallplatsen.models.Departure;

public class MainActivity extends AppCompatActivity {

    ArrayList<Departure> departures = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        departures.add(new Departure("GUL", "Torslanda", "1"));
        departures.add(new Departure("16", "Eketrägatan", "3"));
        departures.add(new Departure("16", "Eketrägatan", "7"));
        departures.add(new Departure("GUL", "Torslanda", "16"));
        departures.add(new Departure("16", "Eketrägatan", "33"));
        departures.add(new Departure("16", "Eketrägatan", "33"));
        departures.add(new Departure("16", "Eketrägatan", "33"));
        departures.add(new Departure("16", "Eketrägatan", "33"));
        departures.add(new Departure("16", "Eketrägatan", "33"));
        departures.add(new Departure("16", "Eketrägatan", "33"));
        departures.add(new Departure("16", "Eketrägatan", "33"));
        departures.add(new Departure("16", "Eketrägatan", "33"));
        departures.add(new Departure("16", "Eketrägatan", "33"));
        departures.add(new Departure("16", "Eketrägatan", "33"));
        departures.add(new Departure("16", "Eketrägatan", "33"));

        ListView listView=(ListView)findViewById(R.id.departure_list);

        DepartureListAdapter adapter = new DepartureListAdapter(getApplicationContext(), departures);
        listView.setAdapter(adapter);

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
