package com.thed.zephyr;

import com.thed.zephyr.cloud.rest.client.StepResultRestClient;
import com.thed.zephyr.cloud.rest.exception.BadRequestParamException;
import com.thed.zephyr.cloud.rest.model.ExecutionStatus;
import com.thed.zephyr.cloud.rest.model.StepResult;
import com.thed.zephyr.util.AbstractTest;
import org.apache.http.HttpException;
import org.codehaus.jettison.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Kavya on 20-06-2016.
 */
public class StepResultUnitTest extends AbstractTest{

    private static StepResultRestClient stepResultRestClient;
    private Long projectId = 10000L;
    private Long issueId = 10100L;
    private String executionId = "0001466423473550-b82a729d7df-0001";
    private String stepResultId = "0001466423474592-b82a729d7df-0001";

    @BeforeClass
    public static void setUp() {
        stepResultRestClient = client.getStepResultRestClient();
    }

    @Test
    public void testGetStepResults() throws BadRequestParamException, JSONException, HttpException {
        List<StepResult> stepResults = stepResultRestClient.getStepResults(projectId, issueId, executionId);
        assertNotNull(stepResults);
    }

    @Test
    public void testGetStepResult() throws BadRequestParamException, JSONException, HttpException {
        StepResult stepResult = stepResultRestClient.getStepResult(projectId, executionId, stepResultId);
        assertNotNull(stepResult);
    }

    @Test
    public void testUpdateStepResult() throws BadRequestParamException, JSONException, HttpException {
        StepResult stepResult = stepResultRestClient.getStepResult(projectId, executionId, stepResultId);
        ExecutionStatus status = new ExecutionStatus();
        status.id = 2;
        stepResult.status = status;
        StepResult updatedStepResult = stepResultRestClient.updateStepResult(projectId, stepResultId, stepResult);
        assertTrue(updatedStepResult.status.id == 2);
    }

    @Test
    public void testGetStepDefectsByExecution() throws BadRequestParamException, JSONException, HttpException {
        stepResultRestClient.getStepDefectsCountByExecutionId(projectId, executionId);
    }
}
