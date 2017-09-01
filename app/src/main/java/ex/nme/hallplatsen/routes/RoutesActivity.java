package ex.nme.hallplatsen.routes;

import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import ex.nme.hallplatsen.Injection;
import ex.nme.hallplatsen.R;
import ex.nme.hallplatsen.util.ActivityUtils;
import ex.nme.hallplatsen.util.EspressoIdlingResource;

/**
 * Created by nm2 on 2017-09-02
 */

public class RoutesActivity extends AppCompatActivity {
    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";

    private RoutesPresenter mRoutesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks_act);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        RoutesFragment routesFragment =
                (RoutesFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (routesFragment == null) {
            // Create the fragment
            routesFragment = RoutesFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), routesFragment, R.id.contentFrame);
        }

        // Create the presenter
        mRoutesPresenter = new RoutesPresenter(
                Injection.provideUseCaseHandler(),
                routesFragment,
                Injection.provideGetTasks(getApplicationContext())
        );

        // Load previously saved state, if available.
        if (savedInstanceState != null) {
            // do something?
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open ... when the home icon is selected from the toolbar.
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
