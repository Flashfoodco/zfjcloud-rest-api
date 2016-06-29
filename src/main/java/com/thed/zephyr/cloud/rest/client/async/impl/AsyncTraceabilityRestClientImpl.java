package com.thed.zephyr.cloud.rest.client.async.impl;

import com.atlassian.httpclient.api.ResponsePromise;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import com.thed.zephyr.cloud.rest.client.async.AsyncTraceabilityRestClient;
import com.thed.zephyr.cloud.rest.client.async.GenericEntityBuilder;
import com.thed.zephyr.cloud.rest.constant.ApplicationConstants;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Map;

/**
 * Created by Kavya on 29-06-2016.
 */
public class AsyncTraceabilityRestClientImpl implements AsyncTraceabilityRestClient {

    private final URI baseUri;

    private final DisposableHttpClient httpClient;

    public AsyncTraceabilityRestClientImpl(URI baseUri, DisposableHttpClient httpClient) {
        this.baseUri = baseUri;
        this.httpClient = httpClient;
    }

    @Override
    public ResponsePromise searchTestsByRequirement(String requirementIdOrKeyList, Long versionId) {
        final URI uri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_TRACEABILITY)
                .path(ApplicationConstants.URL_PATH_TESTSBYREQUIREMENT)
                .queryParam("requirementIdOrKeyList",requirementIdOrKeyList)
                .queryParam(ApplicationConstants.QUERY_PARAM_VERSION_ID, versionId).build();
        return httpClient.newRequest(uri).get();
    }

    @Override
    public ResponsePromise searchExecutionsByTest(Long testId, Long versionId, Integer maxResult, Integer offset) {
        final URI uri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_TRACEABILITY)
                .path(ApplicationConstants.URL_PATH_EXECUTIONSBYTEST)
                .queryParam(ApplicationConstants.QUERY_PARAM_TEST_ID, testId)
                .queryParam(ApplicationConstants.QUERY_PARAM_VERSION_ID, versionId)
                .queryParam(ApplicationConstants.QUERY_PARAM_MAX_RESULT, maxResult)
                .queryParam(ApplicationConstants.QUERY_PARAM_OFFSET, offset).build();
        return httpClient.newRequest(uri).get();
    }

    @Override
    public ResponsePromise searchDefectStatistics(String defectIdList, Long versionId) {
        final URI uri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_TRACEABILITY)
                .path(ApplicationConstants.URL_PATH_DEFECTSTATS)
                .queryParam(ApplicationConstants.QUERY_PARAM_DEFECT_ID_LIST,defectIdList)
                .queryParam(ApplicationConstants.QUERY_PARAM_VERSION_ID, versionId).build();
        return httpClient.newRequest(uri).get();
    }

    @Override
    public ResponsePromise searchExecutionsByDefect(Long defectId, Long versionId, Integer maxResult, Integer offset) {
        final URI uri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_TRACEABILITY)
                .path(ApplicationConstants.URL_PATH_EXECUTIONSBYDEFECT)
                .queryParam(ApplicationConstants.QUERY_PARAM_DEFECT_ID, defectId)
                .queryParam(ApplicationConstants.QUERY_PARAM_VERSION_ID, versionId)
                .queryParam(ApplicationConstants.QUERY_PARAM_MAX_RESULT, maxResult)
                .queryParam(ApplicationConstants.QUERY_PARAM_OFFSET, offset).build();
        return httpClient.newRequest(uri).get();
    }

    @Override
    public ResponsePromise exportTraceabilityReport(Long versionId, Map dataMap) {
        final URI uri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_TRACEABILITY)
                .path(ApplicationConstants.URL_PATH_EXPORT)
                .queryParam(ApplicationConstants.QUERY_PARAM_VERSION_ID, versionId).build();
        return httpClient.newRequest(uri).setEntity(new GenericEntityBuilder(dataMap)).setAccept("application/json").post();
    }

    @Override
    public ResponsePromise downloadReport(String fileName) {
        final URI uri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_TRACEABILITY)
                .path(ApplicationConstants.URL_PATH_DOWNLOAD)
                .path(fileName).build();
        return httpClient.newRequest(uri).get();
    }
}
