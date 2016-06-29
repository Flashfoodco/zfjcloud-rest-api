package com.thed.zephyr;

import com.thed.zephyr.cloud.rest.client.ChartRestClient;
import com.thed.zephyr.cloud.rest.exception.BadRequestParamException;
import com.thed.zephyr.cloud.rest.model.enam.Period;
import com.thed.zephyr.util.AbstractTest;
import org.apache.http.HttpException;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Kavya on 21-06-2016.
 */
public class ChartUnitTest extends AbstractTest {

    static ChartRestClient chartRestClient;
    private Long projectId = 10000L;
    private Long daysPrevious = 30L;
    private String periodName = Period.DAILY.period;
    private int maxResults = 10;

    @BeforeClass
    public static void setup() {
         chartRestClient = client.getChartRestClient();
    }

    @Test
    public void testGetTestsCreated() throws BadRequestParamException, JSONException, HttpException {
        JSONObject response = chartRestClient.getTestsCreated(projectId, daysPrevious, periodName, maxResults);
        assertNotNull(response);
    }

    @Test
    public void testGetExecutionsCreated() throws BadRequestParamException, JSONException, HttpException {
        JSONObject response = chartRestClient.getExecutionCreated(projectId, daysPrevious, periodName);
        assertNotNull(response);
    }
}
