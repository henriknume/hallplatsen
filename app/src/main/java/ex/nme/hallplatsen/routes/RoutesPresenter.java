package ex.nme.hallplatsen.routes;

import android.support.annotation.NonNull;

import java.util.List;

import ex.nme.hallplatsen.UseCase;
import ex.nme.hallplatsen.UseCaseHandler;
import ex.nme.hallplatsen.routes.domain.model.Route;
import ex.nme.hallplatsen.routes.domain.usecase.GetRoutes;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by nm2 on 2017-09-02
 */

public class RoutesPresenter implements RoutesContract.Presenter {

    private final RoutesContract.View mRoutesView;
    private final GetRoutes mGetRoutes;
    private boolean mFirstLoad = true;
    private final UseCaseHandler mUseCaseHandler;

    public RoutesPresenter(@NonNull UseCaseHandler useCaseHandler,
                           @NonNull RoutesContract.View routesView,
                           @NonNull GetRoutes getRoutes) {

        mUseCaseHandler = checkNotNull(useCaseHandler, "usecaseHandler cannot be null");
        mRoutesView = checkNotNull(routesView, "routesView cannot be null!");
        mGetRoutes = checkNotNull(getRoutes, "getRoute cannot be null!");
        mRoutesView.setPresenter(this);
    }

    @Override
    public void start() {
        loadRoutes(false);
    }

    @Override
    public void addNewRoute() {
        mRoutesView.showAddRoute();
    }

    @Override
    public void switchRouteDirection(Route clickedRoute) {

    }

    @Override
    public void result(int requestCode, int resultCode) {
        // If a route was successfully added, show snackbar
    }

    @Override
    public void loadRoutes(boolean forceUpdate) {


        GetRoutes.RequestValues requestValue = new GetRoutes.RequestValues(forceUpdate);

        mUseCaseHandler.execute(mGetRoutes, requestValue,
                new UseCase.UseCaseCallback<GetRoutes.ResponseValue>() {
                    @Override
                    public void onSuccess(GetRoutes.ResponseValue response) {
                        List<Route> routes = response.getRoutes();
                        // The view may not be able to handle UI updates anymore
                        if (!mRoutesView.isActive()) {
                            return;
                        }
                        processRoutes(routes);
                    }

                    @Override
                    public void onError() {
                        // The view may not be able to handle UI updates anymore
                    }
                });
    }

    private void processRoutes(List<Route> routes) {
        // Show the list of routes
        mRoutesView.showRoutes(routes);

    }
}
