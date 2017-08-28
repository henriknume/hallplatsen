package ex.nme.hallplatsen.data.source;

import android.content.Context;

import com.google.common.collect.Lists;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import ex.nme.hallplatsen.trips.domain.model.Route;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for the implementation of the in-memory repository with cache.
 */
public class RoutesRepositoryTest {

    private static final String TASK_TITLE = "title";
    private static final String TASK_TITLE2 = "title2";
    private static final String TASK_TITLE3 = "title3";

    private static List<Route> TASKS = Lists.newArrayList(new Route("Title1", "Description1"),
            new Route("Title2", "Description2"));

    private RoutesRepository mRoutesRepository;
    @Mock
    private RoutesDataSource mRoutesLocalDataSource;
    @Mock
    private Context mContext;
    @Mock
    private RoutesDataSource.GetRouteCallback mGetRouteCallback;
    @Mock
    private RoutesDataSource.LoadRoutesCallback mLoadRoutesCallback;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */

    @Captor
    private ArgumentCaptor<RoutesDataSource.LoadRoutesCallback> mRoutesCallbackCaptor;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<RoutesDataSource.GetRouteCallback> mRouteCallbackCaptor;

    @Before
    public void setupTasksRepository() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mRoutesRepository = RoutesRepository.getInstance(mRoutesLocalDataSource);
    }

    @After
    public void destroyRepositoryInstance() {
        RoutesRepository.destroyInstance();
    }



    @Test
    public void getTask_requestsSingleTaskFromLocalDataSource() {
        // When a task is requested from the tasks repository
        mRoutesRepository.getRoute(TASK_TITLE, mGetRouteCallback);

        // Then the task is loaded from the database
        verify(mRoutesLocalDataSource).getRoute(eq(TASK_TITLE), any(
                RoutesDataSource.GetRouteCallback.class));
    }
    


}