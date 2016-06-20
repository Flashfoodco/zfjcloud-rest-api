package com.thed.zephyr.cloud.rest.client.async;

import com.atlassian.httpclient.api.ResponsePromise;
import com.thed.zephyr.cloud.rest.model.StepResult;

/**
 * Created by Kavya on 20-06-2016.
 */
public interface AsyncStepResultRestClient {

    ResponsePromise getStepResults(Long projectId, Long issueId, String executionId);
    ResponsePromise getStepResult(Long projectId, String executionId, String stepResultId);
    ResponsePromise updateStepResult(Long projectId, String stepResultId, StepResult stepResult);
    ResponsePromise getStepDefectsCountByExecutionId(Long projectId, String executionId);
}
