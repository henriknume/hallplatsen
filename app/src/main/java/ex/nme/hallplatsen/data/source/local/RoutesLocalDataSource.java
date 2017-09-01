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

import ex.nme.hallplatsen.routes.domain.model.Route;
import ex.nme.hallplatsen.data.source.local.TasksPersistenceContract.TaskEntry;

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
                TaskEntry.COLUMN_NAME_ENTRY_ID,
                TaskEntry.COLUMN_NAME_STN_NAME_FROM,
                TaskEntry.COLUMN_NAME_STN_ID_FROM,
                TaskEntry.COLUMN_NAME_STN_NAME_TO,
                TaskEntry.COLUMN_NAME_STN_ID_TO
        };

        Cursor c = db.query(
                TaskEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String eid = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_ENTRY_ID));
                String snf = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_STN_NAME_FROM));
                String sif = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_STN_ID_FROM));
                String snt = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_STN_NAME_TO));
                String sit = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_STN_ID_TO));
                Route route = new Route(snf, sif, snt, sit, eid);
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

    /**
     * Note: {@link GetRouteCallback#onDataNotAvailable()} is fired if the {@link Route} isn't
     * found.
     */
    @Override
    public void getRoute(@NonNull String routeId, @NonNull GetRouteCallback callback) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                TaskEntry.COLUMN_NAME_ENTRY_ID,
                TaskEntry.COLUMN_NAME_STN_NAME_FROM,
                TaskEntry.COLUMN_NAME_STN_ID_FROM,
                TaskEntry.COLUMN_NAME_STN_NAME_TO,
                TaskEntry.COLUMN_NAME_STN_ID_TO
        };

        String selection = TaskEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = {routeId};

        Cursor c = db.query(TaskEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        Route route = null;

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            String eid = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_ENTRY_ID));
            String snf = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_STN_NAME_FROM));
            String sif = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_STN_ID_FROM));
            String snt = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_STN_NAME_TO));
            String sit = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_STN_ID_TO));
            route = new Route(snf, sif, snt, sit, eid);
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
        values.put(TaskEntry.COLUMN_NAME_STN_NAME_FROM, route.getStationNameFrom());
        values.put(TaskEntry.COLUMN_NAME_STN_ID_FROM, route.getStationIdFrom());
        values.put(TaskEntry.COLUMN_NAME_STN_NAME_TO, route.getStationNameTo());
        values.put(TaskEntry.COLUMN_NAME_STN_ID_TO, route.getStationIdTo());
        values.put(TaskEntry.COLUMN_NAME_ENTRY_ID, route.getId());

        db.insert(TaskEntry.TABLE_NAME, null, values);

        db.close();
    }

    @Override
    public boolean reverseRoute(@NonNull String taskId) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String[] projection = {
                TaskEntry.COLUMN_NAME_ENTRY_ID,
                TaskEntry.COLUMN_NAME_STN_NAME_FROM,
                TaskEntry.COLUMN_NAME_STN_ID_FROM,
                TaskEntry.COLUMN_NAME_STN_NAME_TO,
                TaskEntry.COLUMN_NAME_STN_ID_TO
        };

        String selection1 = TaskEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs1 = { taskId };

        Cursor c = db.query(TaskEntry.TABLE_NAME, projection, selection1, selectionArgs1, null, null, null);

        if (c != null && c.getCount() > 0) {
            // get values
            c.moveToFirst();
            String id = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_ENTRY_ID));
            String sNameFrom = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_STN_NAME_FROM));
            String sIdFrom = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_STN_ID_FROM));
            String sNameTo = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_STN_NAME_TO));
            String sIdTo = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_STN_ID_TO));
            c.close();

            //switch and update
            ContentValues values = new ContentValues();
            values.put(TaskEntry.COLUMN_NAME_STN_NAME_FROM, sNameTo);
            values.put(TaskEntry.COLUMN_NAME_STN_ID_FROM, sIdTo);
            values.put(TaskEntry.COLUMN_NAME_STN_NAME_TO, sNameFrom);
            values.put(TaskEntry.COLUMN_NAME_STN_ID_TO, sIdFrom);

            String selection2 = TaskEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
            String[] selectionArgs2 = { id };

            db.update(TaskEntry.TABLE_NAME, values, selection2, selectionArgs2);
        } else {
            // nothing modified
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    @Override
    public void deleteAllRoutes() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        db.delete(TaskEntry.TABLE_NAME, null, null);

        db.close();
    }

    @Override
    public void deleteRoute(@NonNull String routeId) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String selection = TaskEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = {routeId};

        db.delete(TaskEntry.TABLE_NAME, selection, selectionArgs);

        db.close();
    }
}
