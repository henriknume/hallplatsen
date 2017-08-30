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

import ex.nme.hallplatsen.tasks.domain.model.Task;
import ex.nme.hallplatsen.data.source.local.TasksPersistenceContract.TaskEntry;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation of a data source as a db.
 */
public class TasksLocalDataSource implements TasksDataSource {

    private static TasksLocalDataSource INSTANCE;

    private TasksDbHelper mDbHelper;

    // Prevent direct instantiation.
    private TasksLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
        mDbHelper = new TasksDbHelper(context);
    }

    public static TasksLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new TasksLocalDataSource(context);
        }
        return INSTANCE;
    }

    /**
     * Note: {@link GetTaskCallback#onDataNotAvailable()} is fired if the {@link Task} isn't
     * found.
     */
    @Override
    public void getTask(@NonNull String taskId, @NonNull GetTaskCallback callback) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                TaskEntry.COLUMN_NAME_ENTRY_ID,
                TaskEntry.COLUMN_NAME_STN_NAME_FROM,
                TaskEntry.COLUMN_NAME_STN_ID_FROM,
                TaskEntry.COLUMN_NAME_STN_NAME_TO,
                TaskEntry.COLUMN_NAME_STN_ID_TO
        };

        String selection = TaskEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = { taskId };

        Cursor cursor =  db.rawQuery("SELECT * FROM Tasks", null);

        Cursor c = db.query(TaskEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        Task task = null;

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            String eid = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_ENTRY_ID));
            String snf = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_STN_NAME_FROM));
            String sif = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_STN_ID_FROM));
            String snt = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_STN_NAME_TO));
            String sit = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_STN_ID_TO));
            task = new Task(snf, sif, snt, sit, eid);
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (task != null) {
            callback.onTaskLoaded(task);
        } else {
            callback.onDataNotAvailable();
        }

    }

    @Override
    public void saveTask(@NonNull Task task) {
        checkNotNull(task);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TaskEntry.COLUMN_NAME_STN_NAME_FROM, task.getStationNameFrom());
        values.put(TaskEntry.COLUMN_NAME_STN_ID_FROM, task.getStationIdFrom());
        values.put(TaskEntry.COLUMN_NAME_STN_NAME_TO, task.getStationNameTo());
        values.put(TaskEntry.COLUMN_NAME_STN_ID_TO, task.getStationIdTo());
        values.put(TaskEntry.COLUMN_NAME_ENTRY_ID, task.getId());

        db.insert(TaskEntry.TABLE_NAME, null, values);

        db.close();
    }

    @Override
    public void deleteAllTasks() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        db.delete(TaskEntry.TABLE_NAME, null, null);

        db.close();
    }

    @Override
    public void deleteTask(@NonNull String taskId) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String selection = TaskEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = { taskId };

        db.delete(TaskEntry.TABLE_NAME, selection, selectionArgs);

        db.close();
    }
}
