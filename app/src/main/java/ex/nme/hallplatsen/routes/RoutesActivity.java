package ex.nme.hallplatsen.routes;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import ex.nme.hallplatsen.Injection;
import ex.nme.hallplatsen.R;
import ex.nme.hallplatsen.createcard.CreateActivity;
import ex.nme.hallplatsen.routes.domain.model.Route;
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
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RoutesFragment routesFragment = (RoutesFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (routesFragment == null) {
            // Create the fragment
            routesFragment = RoutesFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), routesFragment, R.layout.routes_frag);
        }

        // Create the presenter
        mRoutesPresenter = new RoutesPresenter(
                Injection.provideUseCaseHandler(),
                routesFragment,
                Injection.provideGetTasks(getApplicationContext())
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //Change icon colors
        for(int i = 0; i < menu.size(); i++){
            Drawable drawable = menu.getItem(i).getIcon();
            if(drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(getResources().getColor(R.color.icons), PorterDuff.Mode.SRC_ATOP);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_add_card:
                goToCreateActivity();
                return true;

            case R.id.action_update_all_cards:
                //adapter.updateTripListAllCards();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void goToCreateActivity() {
        Intent intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
    }



}
