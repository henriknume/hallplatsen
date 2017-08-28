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

package ex.nme.hallplatsen.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

import ex.nme.hallplatsen.data.source.RoutesDataSource;
import ex.nme.hallplatsen.trips.domain.model.Route;

import ex.nme.hallplatsen.data.source.local.RoutesPersistenceContract.RouteEntry;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation of a data source as a db.
 */
public class RoutesLocalDataSource implements RoutesDataSource {

    private static RoutesLocalDataSource INSTANCE;

    private RoutesDbHelper mDbHelper;

    // Prevent direct instantiation.
    private RoutesLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
        mDbHelper = new RoutesDbHelper(context);
    }

    public static RoutesLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new RoutesLocalDataSource(context);
        }
        return INSTANCE;
    }


    @Override
    public void getRoutes(@NonNull LoadRoutesCallback callback) {
        List<Route> routes = new ArrayList<Route>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                RouteEntry.COLUMN_NAME_ENTRY_ID,
                RouteEntry.COLUMN_NAME_TITLE,
                RouteEntry.COLUMN_NAME_DESCRIPTION,
                RouteEntry.COLUMN_NAME_COMPLETED
        };

        Cursor c = db.query(
                RouteEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String itemId = c.getString(c.getColumnIndexOrThrow(RouteEntry.COLUMN_NAME_ENTRY_ID));
                String title = c.getString(c.getColumnIndexOrThrow(RouteEntry.COLUMN_NAME_TITLE));
                String description =
                        c.getString(c.getColumnIndexOrThrow(RouteEntry.COLUMN_NAME_DESCRIPTION));
                boolean completed =
                        c.getInt(c.getColumnIndexOrThrow(RouteEntry.COLUMN_NAME_COMPLETED)) == 1;
                Route route = new Route(title, description, itemId, completed);
                routes.add(route);
            }
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (routes.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable();
        } else {
            callback.onRoutesLoaded(routes);
        }

    }


    @Override
    public void getRoute(@NonNull String routeId, @NonNull GetRouteCallback callback) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                RouteEntry.COLUMN_NAME_ENTRY_ID,
                RouteEntry.COLUMN_NAME_TITLE,
                RouteEntry.COLUMN_NAME_DESCRIPTION,
                RouteEntry.COLUMN_NAME_COMPLETED
        };

        String selection = RouteEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = {routeId};

        Cursor c = db.query(
                RouteEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        Route route = null;

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            String itemId = c.getString(c.getColumnIndexOrThrow(RouteEntry.COLUMN_NAME_ENTRY_ID));
            String title = c.getString(c.getColumnIndexOrThrow(RouteEntry.COLUMN_NAME_TITLE));
            String description =
                    c.getString(c.getColumnIndexOrThrow(RouteEntry.COLUMN_NAME_DESCRIPTION));
            boolean completed =
                    c.getInt(c.getColumnIndexOrThrow(RouteEntry.COLUMN_NAME_COMPLETED)) == 1;
            route = new Route(title, description, itemId, completed);
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (route != null) {
            callback.onRouteLoaded(route);
        } else {
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void saveRoute(@NonNull Route route) {
        checkNotNull(route);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RouteEntry.COLUMN_NAME_ENTRY_ID, route.getId());
        values.put(RouteEntry.COLUMN_NAME_TITLE, route.getTitle());
        values.put(RouteEntry.COLUMN_NAME_DESCRIPTION, route.getDescription());
        values.put(RouteEntry.COLUMN_NAME_COMPLETED, route.isCompleted());

        db.insert(RouteEntry.TABLE_NAME, null, values);

        db.close();
    }

/*
    @Override
    public void refreshTasks() {
        // Not required because the {@link RoutesRepository} handles the logic of refreshing the
        // tasks from all the available data sources.
    }
*/
/*
    @Override
    public void deleteAllRoutes() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        db.delete(RouteEntry.TABLE_NAME, null, null);

        db.close();
    }
*/
/*
    @Override
    public void deleteRoute(@NonNull String routeId) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String selection = RouteEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = {routeId};

        db.delete(RouteEntry.TABLE_NAME, selection, selectionArgs);

        db.close();
    }
*/
}
