package com.thed.zephyr.cloud.rest.client.async.impl;

import com.atlassian.httpclient.api.ResponsePromise;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import com.thed.zephyr.cloud.rest.client.async.AsyncExecutionRestClient;
import com.thed.zephyr.cloud.rest.client.async.GenericEntityBuilder;
import com.thed.zephyr.cloud.rest.constant.ApplicationConstants;
import com.thed.zephyr.cloud.rest.model.Execution;
import com.thed.zephyr.cloud.rest.model.enam.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by aliakseimatsarski on 3/25/16.
 */
public class AsyncExecutionRestClientImpl implements AsyncExecutionRestClient {

    private final DisposableHttpClient httpClient;

    private final URI baseUri;

    private Logger log = LoggerFactory.getLogger(AsyncExecutionRestClientImpl.class);


    public AsyncExecutionRestClientImpl(URI baseUri, DisposableHttpClient httpClient) {
        this.httpClient = httpClient;
        this.baseUri = baseUri;
    }

    @Override
    public ResponsePromise createExecution(Execution execution) {
        URI uri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_EXECUTION).build();
        log.debug("Sent request create execution path:{} execution:{}", uri.toString(), execution.toString());

        return httpClient.newRequest(uri).setEntity(new GenericEntityBuilder(execution)).setAccept("application/json").post();
    }

    @Override
    public ResponsePromise getExecution(Long projectId, Long issueId, String executionId) {
        URI uri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_EXECUTION).path(executionId).queryParam(ApplicationConstants.QUERY_PARAM_PROJECT_ID, projectId).queryParam(ApplicationConstants.QUERY_PARAM_ISSUE_ID, issueId).build();
        log.debug("Sent request get execution path:{} projectId:{} issueId:{} executionId:{}", uri.toString(), projectId, issueId, executionId);

        return httpClient.newRequest(uri).get();
    }

    @Override
    public ResponsePromise updateExecution(Execution execution) {
        URI uri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_EXECUTION).path(execution.id).build();
        log.debug("Sent request update execution path:{} execution:{}", uri.toString(), execution.toString());

        return httpClient.newRequest(uri).setEntity(new GenericEntityBuilder(execution)).setAccept("application/json").put();
    }

    @Override
    public ResponsePromise deleteExecution(Long projectId, Long issueId, String executionId) {
        URI uri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_EXECUTION).path(executionId).queryParam(ApplicationConstants.QUERY_PARAM_PROJECT_ID, projectId).queryParam(ApplicationConstants.QUERY_PARAM_ISSUE_ID, issueId).build();
        log.debug("Sent request delete execution path:{} projectId:{} issueId:{} executionId:{}", uri.toString(), projectId, issueId, executionId);

        return httpClient.newRequest(uri).setAccept("application/json").delete();
    }

    @Override
    public ResponsePromise getExecutions(Long projectId, Long issueId, int offset, int size) {
        URI uri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_EXECUTIONS)
                .queryParam(ApplicationConstants.QUERY_PARAM_PROJECT_ID, projectId)
                .queryParam(ApplicationConstants.QUERY_PARAM_ISSUE_ID, issueId)
                .queryParam(ApplicationConstants.QUERY_PARAM_OFFSET, offset)
                .queryParam(ApplicationConstants.QUERY_PARAM_SIZE, size)
                .build();
        log.debug("Sent request get executions path:{} projectId:{} issueId:{} offset:{} size:{}", uri.toString(), projectId, issueId, offset, size);

        return httpClient.newRequest(uri).setAccept("application/json").get();
    }

    @Override
    public ResponsePromise getExecutionsByCycle(Long projectId, Long versionId, String cycleId, int offset, int size, String sortBy, SortOrder sortOrder) {
        URI uri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_EXECUTIONS)
                .path(ApplicationConstants.URL_PATH_SEARCH)
                .path(ApplicationConstants.URL_PATH_CYCLE)
                .path(cycleId)
                .queryParam(ApplicationConstants.QUERY_PARAM_PROJECT_ID, projectId)
                .queryParam(ApplicationConstants.QUERY_PARAM_VERSION_ID, versionId)
                .queryParam(ApplicationConstants.QUERY_PARAM_OFFSET, offset)
                .queryParam(ApplicationConstants.QUERY_PARAM_SIZE, size)
                .queryParam(ApplicationConstants.QUERY_PARAM_SORT_BY, sortBy)
                .queryParam(ApplicationConstants.QUERY_PARAM_SORT_ORDER, sortOrder.order)
                .build();
        log.debug("Sent request get executions by cycle path:{} projectId:{} versionId:{} cycleId:{}", uri.toString(), projectId, versionId, cycleId);

        return httpClient.newRequest(uri).setAccept("application/json").get();
    }

    @Override
    public ResponsePromise addTestsToCycle(Long projectId, Long versionId, String cycleId, List<Long> issueIds) {
        Map<String, Object> entityMap = new HashMap<String, Object>();
        entityMap.put("projectId", projectId);
        entityMap.put("versionId", versionId);
        entityMap.put("issues", issueIds);
        entityMap.put("method", 1);
        URI uri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_EXECUTIONS).path(ApplicationConstants.URL_PATH_ADD).path(ApplicationConstants.URL_PATH_CYCLE).path(cycleId).build();
        log.debug("Sent request add tests to cycle path:{} projectId:{} versionId:{} cycleId:{} issueIds:{}", uri.toString(), projectId, versionId, cycleId, issueIds.toArray().toString());

        return httpClient.newRequest(uri).setEntity(new GenericEntityBuilder(entityMap)).setAccept("application/json").post();
    }

    // Have to review this api
    @Override
    public ResponsePromise exportExecution(String exportType, List<String> executionIds, String zqlQuery) {
        Map<String, Object> entityMap = new HashMap<String, Object>();
        entityMap.put("executions", executionIds);
        entityMap.put("exportType", exportType);
        entityMap.put("zqlQuery", zqlQuery);
        URI uri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_EXECUTIONS).path(ApplicationConstants.URL_PATH_EXPORT).build();
        log.debug("Sent request export executions  path:{} exportType:{} zqlQuery:{} executionIds:{}", uri.toString(), exportType, zqlQuery, executionIds.toArray().toString());

        return httpClient.newRequest(uri).setEntity(new GenericEntityBuilder(entityMap)).setAccept("application/json").post();
    }

    @Override
    public ResponsePromise downloadExportedFile(String fileName) {
        URI uri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_CYCLE).path(ApplicationConstants.URL_PATH_EXPORT).path(ApplicationConstants.URL_PATH_DOWNLOAD).path(fileName).build();
        log.debug("Sent request download exported file  path:{} fileName:{}", uri.toString(), fileName);

        return httpClient.newRequest(uri).setAccept("application/json").get();
    }

    @Override
    public ResponsePromise bulkUpdateStatus(List<String> executionIds, Integer statusId, Integer stepStatusId, Boolean testStepStatusChangeFlag, Boolean clearDefectMappingFlag) {
        Map<String, Object> entityMap = new HashMap<String, Object>();
        entityMap.put("executions", executionIds);
        entityMap.put("status", statusId);
        entityMap.put("stepStatus", stepStatusId);
        entityMap.put("testStepStatusChangeFlag", testStepStatusChangeFlag);
        entityMap.put("clearDefectMappingFlag", clearDefectMappingFlag);
        URI uri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_EXECUTIONS).build();
        log.debug("Sent request bulk update status  path:{} statusId:{} stepStatusId{} testStepStatusChangeFlag:{} clearDefectMappingFlag:{} executionIds:{}", uri.toString(), statusId, stepStatusId, testStepStatusChangeFlag, clearDefectMappingFlag, executionIds.toArray().toString());

        return httpClient.newRequest(uri).setEntity(new GenericEntityBuilder(entityMap)).setAccept("application/json").post();
    }

    @Override
    public ResponsePromise bulkDeleteExecutions(List<String> executionIds) {
        Map<String, Object> entityMap = new HashMap<String, Object>();
        entityMap.put("executions", executionIds);
        URI uri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_EXECUTIONS).build();
        log.debug("Sent request bulk delete executions  path:{} executionIds:{}", uri.toString(), executionIds.toArray().toString());

        return httpClient.newRequest(uri).setEntity(new GenericEntityBuilder(entityMap)).setAccept("application/json").delete();
    }
}
