package ex.nme.hallplatsen.routes;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ex.nme.hallplatsen.R;
import ex.nme.hallplatsen.routes.domain.model.Route;
import ex.nme.hallplatsen.util.EspressoIdlingResource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by nm2 on 2017-09-02
 */

public class RoutesFragment extends Fragment implements RoutesContract.View {

    private RoutesContract.Presenter mPresenter;

    private CardAdapter mListAdapter;

    public RoutesFragment() {
        // Requires empty public constructor
    }

    public static RoutesFragment newInstance() {
        return new RoutesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new CardAdapter(getActivity().getApplicationContext(), new ArrayList<Route>(0));

        // Set up layout and adapter
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.routes_list);
        CardAdapter adapter = new CardAdapter(getActivity(), new ArrayList<Route>(0));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.routes_frag, container, false);

        // Set up routes view
        RecyclerView recView = (RecyclerView) root.findViewById(R.id.routes_list);
        recView.setAdapter(mListAdapter);
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void setPresenter(@NonNull RoutesContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }


    @Override
    public void showAddRoute() {
        //Intent intent = new Intent(getContext(), AddEditRouteActivity.class);
        //startActivityForResult(intent, AddEditRouteActivity.REQUEST_ADD_TASK);
    }

    @Override
    public void showNoRoutes() {

    }

    @Override
    public void showRoutes(List<Route> routes) {
        mListAdapter.replaceData(routes);
    }

    @Override
    public void showSuccessfullySavedMessage() {
        showMessage("Successfully Saved Message");
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
        switch (item.getItemId()) {

            case R.id.menu_clear:
                break;
            case R.id.menu_filter:
                break;
            case R.id.menu_refresh:
                mPresenter.loadRoutes(true);
                break;

        }
        return true;
    */
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
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

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
