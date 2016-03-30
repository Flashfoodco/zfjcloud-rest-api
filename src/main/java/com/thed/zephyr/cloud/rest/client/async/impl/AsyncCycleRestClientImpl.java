package com.thed.zephyr.cloud.rest.client.async.impl;

import com.atlassian.httpclient.api.ResponsePromise;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import com.thed.zephyr.cloud.rest.client.async.AsyncCycleRestClient;
import com.thed.zephyr.cloud.rest.client.async.GenericEntityBuilder;
import com.thed.zephyr.cloud.rest.constant.ApplicationConstants;
import com.thed.zephyr.cloud.rest.model.Cycle;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by aliakseimatsarski on 3/27/16.
 */
public class AsyncCycleRestClientImpl implements AsyncCycleRestClient {

    private final URI baseUri;

    private final DisposableHttpClient httpClient;


    public AsyncCycleRestClientImpl(URI baseUri, DisposableHttpClient httpClient) {
        this.httpClient = httpClient;
        this.baseUri = baseUri;
    }

    @Override
    public ResponsePromise createCycle(Cycle cycle) {
        final URI createCycleUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_CYCLE).build();
        return httpClient.newRequest(createCycleUri).setEntity(new GenericEntityBuilder(cycle)).setAccept("application/json").post();
    }

    @Override
    public ResponsePromise cloneCycle(String clonedCycleId, Cycle cycle) {
        final URI createCycleUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_CYCLE).queryParam("clonedCycleId", clonedCycleId).build();
        return httpClient.newRequest(createCycleUri).setEntity(new GenericEntityBuilder(cycle)).setAccept("application/json").post();
    }

    @Override
    public ResponsePromise getCycle(Long projectId, Long versionId, String cycleId) {
        final URI getCycleUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_CYCLE).path(cycleId).queryParam(ApplicationConstants.QUERY_PARAM_PROJECT_ID, projectId).queryParam(ApplicationConstants.QUERY_PARAM_VERSION_ID, versionId).build();
        return httpClient.newRequest(getCycleUri).setAccept("application/json").get();
    }

    @Override
    public ResponsePromise updateCycle(String cycleId, Cycle newCycle) {
        final URI updateCycleUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_CYCLE).path(cycleId).build();
        return httpClient.newRequest(updateCycleUri).setEntity(new GenericEntityBuilder(newCycle)).setAccept("application/json").put();
    }

    @Override
    public ResponsePromise getCycles(Long projectId, Long versionId) {
        final URI getCyclesUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_CYCLES).path(ApplicationConstants.URL_PATH_SEARCH).queryParam(ApplicationConstants.QUERY_PARAM_PROJECT_ID, projectId).queryParam(ApplicationConstants.QUERY_PARAM_VERSION_ID, versionId).build();
        return httpClient.newRequest(getCyclesUri).setAccept("application/json").get();
    }

    @Override
    public ResponsePromise deleteCycle(Long projectId, Long versionId, String cycleId) {
        final URI deleteCycleUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_CYCLE).path(cycleId).queryParam(ApplicationConstants.QUERY_PARAM_PROJECT_ID, projectId).queryParam(ApplicationConstants.QUERY_PARAM_VERSION_ID, versionId).build();
        return httpClient.newRequest(deleteCycleUri).setAccept("application/json").delete();
    }

    @Override
    public ResponsePromise exportCycle(Long projectId, Long versionId, String cycleId, String exportType) {
        final URI exportCycleUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_CYCLE).path(cycleId).path(ApplicationConstants.URL_PATH_EXPORT).queryParam(ApplicationConstants.QUERY_PARAM_PROJECT_ID, projectId).queryParam(ApplicationConstants.QUERY_PARAM_VERSION_ID, versionId).queryParam(ApplicationConstants.QUERY_PARAM_EXPORT_TYPE, exportType).build();
        return httpClient.newRequest(exportCycleUri).setAccept("application/json").get();
    }

    @Override
    public ResponsePromise downloadExportedFile(String fileName) {
        final URI exportCycleUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_CYCLE).path(ApplicationConstants.URL_PATH_EXPORT).path(ApplicationConstants.URL_PATH_DOWNLOAD).path(fileName).build();
        return httpClient.newRequest(exportCycleUri).setAccept("application/json").get();
    }

    @Override
    public ResponsePromise moveExecutionsToCycle(String cycleId, Long projectId, Long versionId, List<String> executionIds, Boolean clearDefectMappingFlag, Boolean clearStatusFlag) {
        Map<String, Object> entityMap = new HashMap<String, Object>();
        entityMap.put("executions", executionIds);
        entityMap.put("projectId", projectId);
        entityMap.put("versionId", versionId);
        entityMap.put("clearDefectMappingFlag", clearDefectMappingFlag);
        entityMap.put("clearStatusFlag", clearStatusFlag);
        final URI getExecutionsUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_CYCLE).path(cycleId).path(ApplicationConstants.URL_PATH_MOVE).build();
        return httpClient.newRequest(getExecutionsUri).setEntity(new GenericEntityBuilder(entityMap)).setAccept("application/json").post();
    }

    @Override
    public ResponsePromise copyExecutionsToCycle(String cycleId, Long projectId, Long versionId, List<String> executionIds, Boolean clearDefectMappingFlag, Boolean clearStatusFlag) {
        Map<String, Object> entityMap = new HashMap<String, Object>();
        entityMap.put("executions", executionIds);
        entityMap.put("projectId", projectId);
        entityMap.put("versionId", versionId);
        entityMap.put("clearDefectMappingFlag", clearDefectMappingFlag);
        entityMap.put("clearStatusFlag", clearStatusFlag);
        final URI getExecutionsUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_CYCLE).path(cycleId).path(ApplicationConstants.URL_PATH_COPY).build();
        return httpClient.newRequest(getExecutionsUri).setEntity(new GenericEntityBuilder(entityMap)).setAccept("application/json").post();
    }
}
