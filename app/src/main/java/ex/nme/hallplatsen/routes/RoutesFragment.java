package ex.nme.hallplatsen.routes;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ex.nme.hallplatsen.R;
import ex.nme.hallplatsen.routes.domain.model.Route;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by nm2 on 2017-09-02
 */

public class RoutesFragment extends Fragment implements RoutesContract.View {

    private RoutesContract.Presenter mPresenter;

    private RoutesAdapter mListAdapter;

    private LinearLayout mRoutesView;

    public RoutesFragment() {
        // Requires empty public constructor
    }

    public static RoutesFragment newInstance() {
        return new RoutesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new RoutesAdapter(new ArrayList<Route>(0), mItemListener);
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
        ListView listView = (ListView) root.findViewById(R.id.routes_list);
        listView.setAdapter(mListAdapter);
        mRoutesView = (LinearLayout) root.findViewById(R.id.routesLL);

        // Set the scrolling view in the custom SwipeRefreshLayout.
        swipeRefreshLayout.setScrollUpChild(listView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadRoutes(false);
            }
        });

        setHasOptionsMenu(true);

        return root;
    }


    //#############################################################################

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
        mRoutesView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSuccessfullySavedMessage() {
        showMessage("Successfully Saved Message");
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
        inflater.inflate(R.menu.routes_fragment_menu, menu);
    }


    /**
     * Listener for clicks on routes in the ListView.
     */
    RouteItemListener mItemListener = new RouteItemListener() {
        @Override
        public void onRouteClick(Route clickedRoute) {
            mPresenter.switchRouteDirection(clickedRoute);
        }
    };


    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    private static class RoutesAdapter extends BaseAdapter {

        private List<Route> mRoutes;
        private RouteItemListener mItemListener;

        public RoutesAdapter(List<Route> routes, RouteItemListener itemListener) {
            setList(routes);
            mItemListener = itemListener;
        }

        public void replaceData(List<Route> routes) {
            setList(routes);
            notifyDataSetChanged();
        }

        private void setList(List<Route> routes) {
            mRoutes = checkNotNull(routes);
        }

        @Override
        public int getCount() {
            return mRoutes.size();
        }

        @Override
        public Route getItem(int i) {
            return mRoutes.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View rowView = view;
            if (rowView == null) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                rowView = inflater.inflate(R.layout.route_item, viewGroup, false);
            }

            final Route route = getItem(i);

            TextView titleTV = (TextView) rowView.findViewById(R.id.title);
            titleTV.setText(route.getTitleForList());

            // Active/completed route UI

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemListener.onRouteClick(route);
                }
            });

            return rowView;
        }
    }

    public interface RouteItemListener {
        void onRouteClick(Route clickedRoute);
    }


}
