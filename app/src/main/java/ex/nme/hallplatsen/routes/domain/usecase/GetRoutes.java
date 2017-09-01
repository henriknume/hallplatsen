package ex.nme.hallplatsen.routes.domain.usecase;

import static com.google.common.base.Preconditions.checkNotNull;

import android.support.annotation.NonNull;

import java.util.List;

import ex.nme.hallplatsen.UseCase;
import ex.nme.hallplatsen.data.source.local.RoutesDataSource;
import ex.nme.hallplatsen.routes.domain.model.Route;

/**
 * Fetches the list of Routes.
 */
public class GetRoutes extends UseCase<GetRoutes.RequestValues, GetRoutes.ResponseValue> {

    private final RoutesDataSource mRoutesDataSource;

    public GetRoutes(@NonNull RoutesDataSource routesRepository) {
        mRoutesDataSource = checkNotNull(routesRepository, "routesRepository cannot be null!");
    }

    @Override
    protected void executeUseCase(final RequestValues values) {
        mRoutesDataSource.getRoutes(new RoutesDataSource.LoadRoutesCallback() {
            @Override
            public void onRoutesLoaded(List<Route> routes) {
                ResponseValue responseValue = new ResponseValue(routes);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });

    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final boolean mForceUpdate;

        public RequestValues(boolean forceUpdate) {
            mForceUpdate = forceUpdate;
        }

        public boolean isForceUpdate() {
            return mForceUpdate;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final List<Route> mRoutes;

        public ResponseValue(@NonNull List<Route> routes) {
            mRoutes = checkNotNull(routes, "routes cannot be null!");
        }

        public List<Route> getRoutes() {
            return mRoutes;
        }
    }
}
