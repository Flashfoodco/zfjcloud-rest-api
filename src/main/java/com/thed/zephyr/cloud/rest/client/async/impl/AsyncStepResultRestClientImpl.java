package com.thed.zephyr.cloud.rest.client.async.impl;

import com.atlassian.httpclient.api.ResponsePromise;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import com.thed.zephyr.cloud.rest.client.async.AsyncStepResultRestClient;
import com.thed.zephyr.cloud.rest.client.async.GenericEntityBuilder;
import com.thed.zephyr.cloud.rest.constant.ApplicationConstants;
import com.thed.zephyr.cloud.rest.model.StepResult;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * Created by Kavya on 20-06-2016.
 */
public class AsyncStepResultRestClientImpl implements AsyncStepResultRestClient {

    private final URI baseUri;

    private final DisposableHttpClient httpClient;


    public AsyncStepResultRestClientImpl(URI baseUri, DisposableHttpClient httpClient) {
        this.httpClient = httpClient;
        this.baseUri = baseUri;
    }

    @Override
    public ResponsePromise getStepResults(Long projectId, Long issueId, String executionId) {
        final URI getStepResultUri = UriBuilder.fromUri(baseUri)
                .path(ApplicationConstants.URL_PATH_STEP_RESULT)
                .path(ApplicationConstants.URL_PATH_SEARCH)
                .queryParam(ApplicationConstants.QUERY_PARAM_PROJECT_ID, projectId)
                .queryParam(ApplicationConstants.QUERY_PARAM_ISSUE_ID, issueId)
                .queryParam(ApplicationConstants.QUERY_PARAM_EXECUTION_ID, executionId).build();
        return httpClient.newRequest(getStepResultUri).get();
    }

    @Override
    public ResponsePromise getStepResult(Long projectId, String executionId, String stepResultId) {
        final URI getStepResultUri = UriBuilder.fromUri(baseUri)
                .path(ApplicationConstants.URL_PATH_STEP_RESULT)
                .path(stepResultId)
                .queryParam(ApplicationConstants.QUERY_PARAM_PROJECT_ID, projectId)
                .queryParam(ApplicationConstants.QUERY_PARAM_EXECUTION_ID, executionId).build();
        return httpClient.newRequest(getStepResultUri).get();
    }

    @Override
    public ResponsePromise updateStepResult(Long projectId, String stepResultId, StepResult stepResult) {
        final URI getStepResultUri = UriBuilder.fromUri(baseUri)
                .path(ApplicationConstants.URL_PATH_STEP_RESULT)
                .path(stepResultId)
                .queryParam(ApplicationConstants.QUERY_PARAM_PROJECT_ID, projectId).build();
        return httpClient.newRequest(getStepResultUri).setEntity(new GenericEntityBuilder(stepResult)).setAccept("application/json").put();
    }

    @Override
    public ResponsePromise getStepDefectsCountByExecutionId(Long projectId, String executionId) {
        final URI getStepResultUri = UriBuilder.fromUri(baseUri)
                .path(ApplicationConstants.URL_PATH_STEP_RESULT)
                .path(ApplicationConstants.URL_PATH_BY_EXECUTION)
                .queryParam(ApplicationConstants.QUERY_PARAM_PROJECT_ID, projectId)
                .queryParam(ApplicationConstants.QUERY_PARAM_EXECUTION_ID, executionId).build();
        return httpClient.newRequest(getStepResultUri).get();
    }
}
