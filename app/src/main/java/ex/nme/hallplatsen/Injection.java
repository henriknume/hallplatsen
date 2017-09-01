package ex.nme.hallplatsen;

import static com.google.common.base.Preconditions.checkNotNull;

import android.content.Context;
import android.support.annotation.NonNull;

import ex.nme.hallplatsen.data.source.local.RoutesDataSource;
import ex.nme.hallplatsen.data.source.local.RoutesLocalDataSource;
import ex.nme.hallplatsen.routes.domain.usecase.GetRoutes;

/**
 * Enables injection of production implementations for
 * {@link RoutesDataSource} at compile time.
 */
public class Injection {

    public static RoutesDataSource provideRoutesRepository(@NonNull Context context) {
        checkNotNull(context);
        return RoutesLocalDataSource.getInstance(context);
    }

    public static GetRoutes provideGetTasks(@NonNull Context context) {
        return new GetRoutes(provideRoutesRepository(context));
    }

    public static UseCaseHandler provideUseCaseHandler() {
        return UseCaseHandler.getInstance();
    }
}
