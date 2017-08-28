package ex.nme.hallplatsen.data.source;

import android.support.annotation.NonNull;

import java.util.List;

import ex.nme.hallplatsen.trips.domain.model.Route;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation to load tasks from the data sources into a cache.
 * <p>
 * For simplicity, this implements a dumb synchronisation between locally persisted data and data
 * obtained from the server, by using the remote data source only if the local database doesn't
 * exist or is empty.
 */
public class RoutesRepository implements RoutesDataSource {

    private static RoutesRepository INSTANCE = null;
    private final RoutesDataSource mRoutesLocalDataSource;

    // Prevent direct instantiation.
    private RoutesRepository(@NonNull RoutesDataSource tasksLocalDataSource) {
        mRoutesLocalDataSource = checkNotNull(tasksLocalDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param tasksLocalDataSource  the device storage data source
     * @return the {@link RoutesRepository} instance
     */
    public static RoutesRepository getInstance(RoutesDataSource tasksLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new RoutesRepository(tasksLocalDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(RoutesDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }


    @Override
    public void getRoutes(@NonNull final LoadRoutesCallback callback) {
        checkNotNull(callback);

            mRoutesLocalDataSource.getRoutes(new LoadRoutesCallback() {
                @Override
                public void onRoutesLoaded(List<Route> routes) {
                    callback.onRoutesLoaded(routes);
                }

                @Override
                public void onDataNotAvailable() {
                    callback.onDataNotAvailable();
                }
            });
    }


    @Override
    public void saveRoute(@NonNull Route route) {
        checkNotNull(route);
        mRoutesLocalDataSource.saveRoute(route);
    }

    /**
     * Gets tasks from local data source (sqlite)
     */
    @Override
    public void getRoute(@NonNull final String routeId, @NonNull final GetRouteCallback callback) {
        checkNotNull(routeId);
        checkNotNull(callback);

        // Is the task in the local data source? If not, query the network.
        mRoutesLocalDataSource.getRoute(routeId, new GetRouteCallback() {
            @Override
            public void onRouteLoaded(Route route) {
                callback.onRouteLoaded(route);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }
/*
    @Override
    public void deleteAllRoutes() {
        mTasksRemoteDataSource.deleteAllRoutes();
        mRoutesLocalDataSource.deleteAllRoutes();

        if (mCachedTasks == null) {
            mCachedTasks = new LinkedHashMap<>();
        }
        mCachedTasks.clear();
    }
*/
/*
    @Override
    public void deleteRoute(@NonNull String routeId) {
        mTasksRemoteDataSource.deleteRoute(checkNotNull(routeId));
        mRoutesLocalDataSource.deleteRoute(checkNotNull(routeId));

        mCachedTasks.remove(routeId);
    }
*/
}
