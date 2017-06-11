package ex.nme.hallplatsen.mock;

import android.app.Instrumentation;
import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by nume on 2017-06-11.
 */

public class MockedApiTest{
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getLocationByName() throws Exception {
        Context context = new Instrumentation().getContext();
        MockedApi api = new MockedApi(context);
        String res = api.getLocationByName("kviberg");
        assertTrue(res.contains("kviberg"));
    }

}