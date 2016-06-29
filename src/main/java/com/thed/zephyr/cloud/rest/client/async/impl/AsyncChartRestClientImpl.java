package com.thed.zephyr.cloud.rest.client.async.impl;

import com.atlassian.httpclient.api.ResponsePromise;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import com.thed.zephyr.cloud.rest.client.async.AsyncChartRestClient;
import com.thed.zephyr.cloud.rest.constant.ApplicationConstants;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * Created by Kavya on 21-06-2016.
 */
public class AsyncChartRestClientImpl implements AsyncChartRestClient {

    private final URI baseUri;

    private final DisposableHttpClient httpClient;

    public AsyncChartRestClientImpl(URI baseUri, DisposableHttpClient httpClient) {
        this.baseUri = baseUri;
        this.httpClient = httpClient;
    }

    @Override
    public ResponsePromise getTestsCreated(Long projectId, Long daysPrevious, String periodName, Integer maxResults) {
        final URI getTestsCreatedUri = UriBuilder.fromUri(baseUri)
                .path(ApplicationConstants.URL_PATH_CHART)
                .path(ApplicationConstants.URL_PATH_TESTS)
                .path(ApplicationConstants.URL_PATH_CREATED)
                .queryParam(ApplicationConstants.QUERY_PARAM_PROJECT_ID, projectId)
                .queryParam("daysPrevious", daysPrevious)
                .queryParam("periodName", periodName)
                .queryParam(ApplicationConstants.QUERY_PARAM_MAX_RESULTS).build();
        return httpClient.newRequest(getTestsCreatedUri).setAccept("application/json").get();
    }


    @Override
    public ResponsePromise getExecutionCreated(Long projectId, Long daysPrevious, String periodName) {
        final URI getExecutionsCreatedUri = UriBuilder.fromUri(baseUri)
                .path(ApplicationConstants.URL_PATH_CHART)
                .path(ApplicationConstants.URL_PATH_EXECUTIONS)
                .path(ApplicationConstants.URL_PATH_CREATED)
                .queryParam(ApplicationConstants.QUERY_PARAM_PROJECT_ID, projectId)
                .queryParam("daysPrevious", daysPrevious)
                .queryParam("periodName", periodName).build();
        return httpClient.newRequest(getExecutionsCreatedUri).setAccept("application/json").get();
    }
}
