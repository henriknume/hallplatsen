/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ex.nme.hallplatsen.data.source;

import android.support.annotation.NonNull;

import java.util.List;

import ex.nme.hallplatsen.trips.domain.model.Route;

/**
 * Main entry point for accessing routes data.
 * <p>
 * For simplicity, only getRoutes() and getRoute() have callbacks.
 * TODO: Consider adding callbacks to other methods to inform the user of network/database errors
 * or successful operations.
 * For example, when a new Route is created, it's synchronously stored in cache but usually every
 * operation on database or network should be executed in a different thread.
 */
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
/*
    TODO: See if this is needed.
    void refreshTasks();
*/
    //void deleteAllRoutes();

    //void deleteRoute(@NonNull String routeId);
}
