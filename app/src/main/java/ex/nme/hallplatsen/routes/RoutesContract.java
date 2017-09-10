package ex.nme.hallplatsen.routes;

import java.util.List;

import ex.nme.hallplatsen.BasePresenter;
import ex.nme.hallplatsen.BaseView;
import ex.nme.hallplatsen.routes.domain.model.Route;

/**
 * Created by nm2 on 2017-09-02
 */

public class RoutesContract {

    interface View extends BaseView<Presenter> {

        void showRoutes(List<Route> routes);

        void showAddRoute();

        void showNoRoutes();

        void showSuccessfullySavedMessage();

        boolean isActive();

    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadRoutes(boolean forceUpdate);

        void addNewRoute();

        void switchRouteDirection(Route clickedRoute);
    }
}
