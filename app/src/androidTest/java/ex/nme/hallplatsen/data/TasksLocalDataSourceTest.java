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

package ex.nme.hallplatsen.data;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import ex.nme.hallplatsen.data.source.local.TasksDataSource;
import ex.nme.hallplatsen.data.source.local.TasksDbHelper;
import ex.nme.hallplatsen.data.source.local.TasksLocalDataSource;
import ex.nme.hallplatsen.tasks.domain.model.Task;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Integration test for the {@link TasksDataSource}, which uses the {@link TasksDbHelper}.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TasksLocalDataSourceTest {

    private TasksLocalDataSource mLocalDataSource;

    @Before
    public void setup() {
         mLocalDataSource = TasksLocalDataSource.getInstance(
                 InstrumentationRegistry.getTargetContext());
    }

    @After
    public void cleanUp() {
        mLocalDataSource.deleteAllTasks();
    }

    @Test
    public void testPreConditions() {
        assertNotNull(mLocalDataSource);
    }

    @Test
    public void saveTask_retrievesTask() {
        // Given a new task
        final Task newTask = new Task("FROMNAME1","FROMID1","TONAME2","TOID2");

        // When saved into the persistent repository
        mLocalDataSource.saveTask(newTask);

        // Then the task can be retrieved from the persistent repository
        mLocalDataSource.getTask(newTask.getId(), new TasksDataSource.GetTaskCallback() {
            @Override
            public void onTaskLoaded(Task task) {
                assertThat(task, is(newTask));
            }

            @Override
            public void onDataNotAvailable() {
                fail("Callback error");
            }
        });
    }

    @Test
    public void deleteTask_addTwoAndDeleteTheSecond() {
        // Add two tasks
        final Task first = new Task("A1","A2","B1","B2");
        final Task second = new Task("A","B","C","D");
        mLocalDataSource.saveTask(first);
        mLocalDataSource.saveTask(second);

        // delete the second
        mLocalDataSource.deleteTask(second.getId());

        // The first should exists
        mLocalDataSource.getTask(first.getId(), new TasksDataSource.GetTaskCallback() {
            @Override
            public void onTaskLoaded(Task task) {
                assertThat(task, is(first));
            }

            @Override
            public void onDataNotAvailable() {
                fail("Callback error");
            }
        });

        // The second should not
        mLocalDataSource.getTask(second.getId(), new TasksDataSource.GetTaskCallback() {
            @Override
            public void onTaskLoaded(Task task) {
                fail("on data should not be returned");
            }

            @Override
            public void onDataNotAvailable() {
                // this should be invoked
            }
        });
    }

    @Test
    public void getTasks_addTwoAndReturnTwo() {
        // Add two tasks
        final Task first = new Task("A1","A2","B1","B2");
        final Task second = new Task("A","B","C","D");
        mLocalDataSource.saveTask(first);
        mLocalDataSource.saveTask(second);

        // Get all tasks
        mLocalDataSource.getTasks(new TasksDataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                assertNotNull(tasks);
                assertTrue(tasks.size() == 2);
            }

            @Override
            public void onDataNotAvailable() {
                fail("Some error");
            }
        });
    }

    @Test
    public void reverseTask_fromAndToStationShouldChangePlaces() {
        // Add and reverse task
        final Task before = new Task("A1","A2","B1","B2");
        mLocalDataSource.saveTask(before);
        mLocalDataSource.reverseTask(before.getId());

        //inspect result
        mLocalDataSource.getTask(before.getId(), new TasksDataSource.GetTaskCallback() {
            @Override
            public void onTaskLoaded(Task after) {
                assertEquals(before.getId(), after.getId());
                assertEquals(before.getStationIdFrom(), after.getStationIdTo());
                assertEquals(before.getStationNameFrom(), after.getStationNameTo());
            }

            @Override
            public void onDataNotAvailable() {
                fail("Callback error");
            }
        });
    }

    @Test
    public void reverseTask_invalidIdShouldReturnFalse() {
        // Add and reverse task
        final Task before = new Task("A1","A2","B1","B2");
        mLocalDataSource.saveTask(before);
        boolean result = mLocalDataSource.reverseTask("invalid-id");

        assertEquals(false, result);

        //inspect result, should be unmodified
        mLocalDataSource.getTask(before.getId(), new TasksDataSource.GetTaskCallback() {
            @Override
            public void onTaskLoaded(Task after) {
                assertEquals(before.getId(), after.getId());
                assertEquals(before.getStationIdFrom(), after.getStationIdFrom());
                assertEquals(before.getStationNameFrom(), after.getStationNameFrom());
            }

            @Override
            public void onDataNotAvailable() {
                fail("Callback error");
            }
        });
    }
}