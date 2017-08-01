package ex.nme.hallplatsen.main;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ex.nme.hallplatsen.R;
import ex.nme.hallplatsen.Utils;
import ex.nme.hallplatsen.createcard.CreateActivity;
import ex.nme.hallplatsen.models.CardStorage;
import ex.nme.hallplatsen.models.TripCard;
import ex.nme.hallplatsen.models.reseplaneraren.Trip;
import ex.nme.hallplatsen.models.responses.TripResponse;
import ex.nme.hallplatsen.services.ReseplanerarenService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private CardAdapter adapter;
    private CardStorage model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set up model
         model = CardStorage.getInstance();

        // Set up layout and adapter
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new CardAdapter(this, model.getCards());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume()");
        model = CardStorage.getInstance();
        updateAllCards();
    }

    private void requestTripAsync(String originId, String destId){
        String currentTime = Utils.time();

        Call<TripResponse> call = ReseplanerarenService.getService().getTrip(originId, destId,
                Utils.date(), currentTime, "json");
        call.enqueue(new Callback<TripResponse>() {
            @Override
            public void onResponse(Call<TripResponse> call, Response<TripResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse() successful");
                    model.setTripList(0, response.body().getTripList().getTrip());
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

    private List<Trip> requestTrip(TripCard card){
        String currentTime = Utils.time();
        Call<TripResponse> call = ReseplanerarenService.getService().getTrip(card.getFromId(), card.getToId(), Utils.date(), currentTime, "json");
        try {
            Response<TripResponse> response =  call.execute();
            if (response.isSuccessful()) {
                return response.body().getTripList().getTrip();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
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
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_add_card:
                goToCreateActivity();
                return true;

            case R.id.action_update_all_cards:
                updateAllCards();
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

    private void updateAllCards() {
        new DownloadTripsTask().execute();
    }

    private class DownloadTripsTask extends AsyncTask<Void, Integer, String> {

        @Override
        protected String doInBackground(Void... params) {
            for(TripCard card : model.getCards()){
                List<Trip> trips = requestTrip(card);
                card.setTripList(trips);
                publishProgress(1);
            }
            return "Updated";
        }

        protected void onProgressUpdate(Integer... progress) {
            adapter.notifyDataSetChanged();
        }

        protected void onPostExecute(String result) {
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
