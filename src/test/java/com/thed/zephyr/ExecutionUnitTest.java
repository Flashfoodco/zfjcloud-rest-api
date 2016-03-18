package com.thed.zephyr;

import com.thed.zephyr.cloud.rest.client.ExecutionRestClient;
import com.thed.zephyr.util.AbstractTest;
import org.apache.http.HttpException;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;


/**
 * Created by aliakseimatsarski on 3/15/16.
 */
public class ExecutionUnitTest extends AbstractTest {

    private static ExecutionRestClient executionRestClient;

    @BeforeClass
    public static void setUp() throws Exception{
        executionRestClient = client.getExecutionRestClient();
    }

    @Test
    public void testGetExecutions() throws JSONException, HttpException{
        Long projectId = 10000l;
        Long issueId = 10001l;

        JSONObject executions = executionRestClient.getExecutions(projectId, issueId);

        assertNotNull(executions);
    }

    @Test
    public void testGetExecutionsByCycle()  throws JSONException, HttpException{
        Long projectId = 10000l;
        Long versionId = 10000l;
        String cycleId = "0001458068629621-de15b8674876-0001";

        JSONObject executions = executionRestClient.getExecutions(projectId, versionId, cycleId);

        assertNotNull(executions);
    }

}
