package ex.nme.hallplatsen.data;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import ex.nme.hallplatsen.data.source.local.RoutesDataSource;
import ex.nme.hallplatsen.data.source.local.RoutesDbHelper;
import ex.nme.hallplatsen.data.source.local.RoutesLocalDataSource;
import ex.nme.hallplatsen.routes.domain.model.Route;

import org.junit.After;
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
 * Integration test for the {@link RoutesDataSource}, which uses the {@link RoutesDbHelper}.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class RoutesLocalDataSourceTest {

    private RoutesLocalDataSource mLocalDataSource;

    @Before
    public void setup() {
         mLocalDataSource = RoutesLocalDataSource.getInstance(
                 InstrumentationRegistry.getTargetContext());
    }

    @After
    public void cleanUp() {
        mLocalDataSource.deleteAllRoutes();
    }

    @Test
    public void testPreConditions() {
        assertNotNull(mLocalDataSource);
    }

    @Test
    public void saveRoute_retrievesRoute() {
        // Given a new route
        final Route newRoute = new Route("FROMNAME1","FROMID1","TONAME2","TOID2");

        // When saved into the persistent repository
        mLocalDataSource.saveRoute(newRoute);

        // Then the route can be retrieved from the persistent repository
        mLocalDataSource.getRoute(newRoute.getId(), new RoutesDataSource.GetRouteCallback() {
            @Override
            public void onRouteLoaded(Route route) {
                assertThat(route, is(newRoute));
            }

            @Override
            public void onDataNotAvailable() {
                fail("Callback error");
            }
        });
    }

    @Test
    public void deleteRoute_addTwoAndDeleteTheSecond() {
        // Add two routes
        final Route first = new Route("A1","A2","B1","B2");
        final Route second = new Route("A","B","C","D");
        mLocalDataSource.saveRoute(first);
        mLocalDataSource.saveRoute(second);

        // delete the second
        mLocalDataSource.deleteRoute(second.getId());

        // The first should exists
        mLocalDataSource.getRoute(first.getId(), new RoutesDataSource.GetRouteCallback() {
            @Override
            public void onRouteLoaded(Route route) {
                assertThat(route, is(first));
            }

            @Override
            public void onDataNotAvailable() {
                fail("Callback error");
            }
        });

        // The second should not
        mLocalDataSource.getRoute(second.getId(), new RoutesDataSource.GetRouteCallback() {
            @Override
            public void onRouteLoaded(Route route) {
                fail("on data should not be returned");
            }

            @Override
            public void onDataNotAvailable() {
                // this should be invoked
            }
        });
    }

    @Test
    public void getRoutes_addTwoAndReturnTwo() {
        // Add two routes
        final Route first = new Route("A1","A2","B1","B2");
        final Route second = new Route("A","B","C","D");
        mLocalDataSource.saveRoute(first);
        mLocalDataSource.saveRoute(second);

        // Get all routes
        mLocalDataSource.getRoutes(new RoutesDataSource.LoadRoutesCallback() {
            @Override
            public void onRoutesLoaded(List<Route> routes) {
                assertNotNull(routes);
                assertTrue(routes.size() == 2);
            }

            @Override
            public void onDataNotAvailable() {
                fail("Some error");
            }
        });
    }

    @Test
    public void reverseRoute_fromAndToStationShouldChangePlaces() {
        // Add and reverse route
        final Route before = new Route("A1","A2","B1","B2");
        mLocalDataSource.saveRoute(before);
        mLocalDataSource.reverseRoute(before.getId());

        //inspect result
        mLocalDataSource.getRoute(before.getId(), new RoutesDataSource.GetRouteCallback() {
            @Override
            public void onRouteLoaded(Route after) {
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
    public void reverseRoute_invalidIdShouldReturnFalse() {
        // Add and reverse route
        final Route before = new Route("A1","A2","B1","B2");
        mLocalDataSource.saveRoute(before);
        boolean result = mLocalDataSource.reverseRoute("invalid-id");

        assertEquals(false, result);

        //inspect result, should be unmodified
        mLocalDataSource.getRoute(before.getId(), new RoutesDataSource.GetRouteCallback() {
            @Override
            public void onRouteLoaded(Route after) {
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
