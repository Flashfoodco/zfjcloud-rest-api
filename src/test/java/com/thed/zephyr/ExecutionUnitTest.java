package com.thed.zephyr;

import com.thed.zephyr.cloud.rest.client.ExecutionRestClient;
import com.thed.zephyr.cloud.rest.client.model.Execution;
import com.thed.zephyr.util.AbstractTest;
import org.apache.http.HttpException;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * Created by aliakseimatsarski on 3/15/16.
 */
public class ExecutionUnitTest extends AbstractTest {

    private static ExecutionRestClient executionRestClient;
    final private Long projectId = 10000l;
    final private Long versionId = -1l;
    final private Long issueId = 10001l;

    @BeforeClass
    public static void setUp() throws Exception{
        executionRestClient = client.getExecutionRestClient();
    }

  //  @Test
    public void testCreateExecution() throws JSONException, HttpException{
        Execution execution = new Execution();
        execution.projectId = projectId;
        execution.issueId = issueId;
        execution.versionId = versionId;

        Execution responseExecution = executionRestClient.createExecution(execution);

        System.out.println("Execution:" + responseExecution.creationDate);
        assertNotNull(responseExecution);
    }

 //   @Test
    public void testGetExecution() throws JSONException, HttpException{
        String executionId = "0001458068644720-de15b8674876-0001";

        Execution responseExecution = executionRestClient.getExecution(projectId, issueId, executionId);

        System.out.println("Execution:" + responseExecution.creationDate);
        assertNotNull(responseExecution);
    }

 //   @Test
    public void testUpdateExecution() throws JSONException, HttpException{
        Execution execution = new Execution();
        execution.id = "0001458068644720-de15b8674876-0001";
        execution.projectId = projectId;
        execution.issueId = issueId;
        execution.comment = "New Comment";

        Execution responseExecution = executionRestClient.updateExecution(execution);

        assertNotNull(responseExecution);
        assertEquals(execution.comment, responseExecution.comment);
    }

 //   @Test
    public void testDeleteExecution() throws JSONException, HttpException{
        String executionId = "0001458451265957-ee569792189e-0001";
        Boolean response = executionRestClient.deleteExecution(projectId, issueId, executionId);

        assertTrue(response);
    }

  //  @Test
    public void testGetExecutions() throws JSONException, HttpException{
        List<Execution> executions = executionRestClient.getExecutions(projectId, issueId);

        assertTrue(executions.size() == 10);
    }

 //   @Test
    public void testGetExecutionsByCycle()  throws JSONException, HttpException{

    }

}
