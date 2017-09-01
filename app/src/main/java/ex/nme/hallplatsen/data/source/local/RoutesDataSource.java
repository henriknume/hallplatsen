package ex.nme.hallplatsen.data.source.local;

import android.support.annotation.NonNull;

import ex.nme.hallplatsen.routes.domain.model.Route;

import java.util.List;

public interface RoutesDataSource {

    interface LoadRoutesCallback {

        void onRoutesLoaded(List<Route> routes);

        void onDataNotAvailable();
    }

    interface GetRouteCallback {

        void onRouteLoaded(Route route);

        void onDataNotAvailable();
    }

    void getRoutes(@NonNull LoadRoutesCallback callback);

    void getRoute(@NonNull String routeId, @NonNull GetRouteCallback callback);

    void saveRoute(@NonNull Route route);

    boolean reverseRoute(@NonNull String routeId);

    void deleteAllRoutes();

    void deleteRoute(@NonNull String routeId);
}
