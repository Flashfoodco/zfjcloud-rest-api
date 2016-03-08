package com.thed.zephyr.cloud.rest.client.async;

import com.atlassian.httpclient.api.ResponsePromise;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import com.thed.zephyr.cloud.rest.client.CycleRestClient;
import com.thed.zephyr.cloud.rest.client.constant.ApplicationConstants;
import com.thed.zephyr.cloud.rest.client.model.Cycle;
import com.thed.zephyr.cloud.rest.client.model.GenericEntityUtil;
import org.apache.http.HttpException;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.core.UriBuilder;
import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Kavya on 18-02-2016.
 */
public class AsynchronousCycleRestClient implements CycleRestClient {

    private final DisposableHttpClient httpClient;
    private final URI baseUri;
    Logger log = Logger.getLogger("CycleRestClient");

    public AsynchronousCycleRestClient(URI baseUri, DisposableHttpClient httpClient) {
        this.httpClient = httpClient;
        this.baseUri = baseUri;
    }

    @Override
    public JSONObject createCycle(Cycle cycle) throws JSONException, HttpException {
        try {
            final URI createCycleUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_CYCLE).build();
            ResponsePromise responsePromise = httpClient.newRequest(createCycleUri).setEntity(GenericEntityUtil.toEntity(cycle)).setAccept("application/json").post();
            return GenericEntityUtil.parseJsonResponse(responsePromise);
        } catch (JSONException e) {
            log.log(Level.SEVERE, "Error in parsing the response");
            throw e;
        } catch (HttpException e) {
            throw e;
        }
    }

    @Override
    public JSONObject cloneCycle(String clonedCycleId, Cycle cycle) throws JSONException, HttpException {
        try {
            final URI createCycleUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_CYCLE).queryParam("clonedCycleId", clonedCycleId).build();
            ResponsePromise responsePromise = httpClient.newRequest(createCycleUri).setEntity(GenericEntityUtil.toEntity(cycle)).setAccept("application/json").post();
            return GenericEntityUtil.parseJsonResponse(responsePromise);
        } catch (JSONException e) {
            log.log(Level.SEVERE, "Error in parsing the response");
            throw e;
        } catch (HttpException e) {
            throw e;
        }
    }

    @Override
    public JSONObject getCycle(Long projectId, Long versionId, String cycleId) throws JSONException, HttpException {
        try {
            final URI getCycleUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_CYCLE).path(cycleId).queryParam(ApplicationConstants.QUERY_PARAM_PROJECT_ID, projectId).queryParam(ApplicationConstants.QUERY_PARAM_VERSION_ID, versionId).build();
            ResponsePromise responsePromise = httpClient.newRequest(getCycleUri).setAccept("application/json").get();
            return GenericEntityUtil.parseJsonResponse(responsePromise);
        } catch (JSONException e) {
            log.log(Level.SEVERE, "Error in parsing the response");
            throw e;
        } catch (HttpException e) {
            throw e;
        }
    }

    @Override
    public JSONObject updateCycle(String cycleId, Cycle newCycle) throws JSONException, HttpException {
        try {
            final URI updateCycleUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_CYCLE).path(cycleId).build();
            ResponsePromise responsePromise = httpClient.newRequest(updateCycleUri).setEntity(GenericEntityUtil.toEntity(newCycle)).setAccept("application/json").put();
            return GenericEntityUtil.parseJsonResponse(responsePromise);
        } catch (JSONException e) {
            log.log(Level.SEVERE, "Error in parsing the response");
            throw e;
        } catch (HttpException e) {
            throw e;
        }
    }

    @Override
    public JSONObject deleteCycle(Long projectId, Long versionId, String cycleId) throws JSONException, HttpException {
        try {
            final URI deleteCycleUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_CYCLE).path(cycleId).queryParam(ApplicationConstants.QUERY_PARAM_PROJECT_ID, projectId).queryParam(ApplicationConstants.QUERY_PARAM_VERSION_ID, versionId).build();
            ResponsePromise responsePromise = httpClient.newRequest(deleteCycleUri).setAccept("application/json").delete();
            return GenericEntityUtil.parseJsonResponse(responsePromise);
        } catch (JSONException e) {
            log.log(Level.SEVERE, "Error in parsing the response");
            throw e;
        } catch (HttpException e) {
            throw e;
        }
    }

    @Override
    public JSONArray getCycles(Long projectId, Long versionId) throws JSONException, HttpException {
        try {
            final URI getCyclesUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_CYCLES).path(ApplicationConstants.URL_PATH_SEARCH).queryParam(ApplicationConstants.QUERY_PARAM_PROJECT_ID, projectId).queryParam(ApplicationConstants.QUERY_PARAM_VERSION_ID, versionId).build();
            ResponsePromise responsePromise = httpClient.newRequest(getCyclesUri).setAccept("application/json").get();
            return GenericEntityUtil.parseJsonArrayResponse(responsePromise);
        } catch (JSONException e) {
            log.log(Level.SEVERE, "Error in parsing the response");
            throw e;
        } catch (HttpException e) {
            throw e;
        }
    }

    @Override
    public String exportCycle(Long projectId, Long versionId, String cycleId, String exportType) throws JSONException, HttpException {
        try {
            final URI exportCycleUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_CYCLE).path(cycleId).path(ApplicationConstants.URL_PATH_EXPORT).queryParam(ApplicationConstants.QUERY_PARAM_PROJECT_ID, projectId).queryParam(ApplicationConstants.QUERY_PARAM_VERSION_ID, versionId).queryParam(ApplicationConstants.QUERY_PARAM_EXPORT_TYPE, exportType).build();
            ResponsePromise responsePromise = httpClient.newRequest(exportCycleUri).setAccept("application/json").get();
            return GenericEntityUtil.parseJobProgressResponse(responsePromise);
        } catch (JSONException e) {
            log.log(Level.SEVERE, "Error in parsing the response");
            throw e;
        } catch (HttpException e) {
            throw e;
        }
    }

    @Override
    public File downloadExportedFile(String fileName) throws JSONException, HttpException {
        final URI exportCycleUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_CYCLE).path(ApplicationConstants.URL_PATH_EXPORT).path(ApplicationConstants.URL_PATH_DOWNLOAD).path(fileName).build();
        ResponsePromise responsePromise = httpClient.newRequest(exportCycleUri).setAccept("application/json").get();
        return GenericEntityUtil.parseFileResponse(responsePromise, fileName);
    }

    @Override
    public String moveExecutionsToCycle(String cycleId, Long projectId, Long versionId, List<String> executionIds, Boolean clearDefectMappingFlag, Boolean clearStatusFlag) throws JSONException, HttpException {
        try {
            Map<String, Object> entityMap = new HashMap<String, Object>();
            entityMap.put("executionIds", executionIds);
            entityMap.put("cycleId", cycleId);
            entityMap.put("projectId", projectId);
            entityMap.put("versionId", versionId);
            entityMap.put("clearDefectMappingFlag", clearDefectMappingFlag);
            entityMap.put("clearStatusFlag", clearStatusFlag);
            final URI getExecutionsUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_EXECUTIONS).path(ApplicationConstants.URL_PATH_EXPORT).build();
            ResponsePromise responsePromise = httpClient.newRequest(getExecutionsUri).setEntity(GenericEntityUtil.toEntity(entityMap)).setAccept("application/json").post();
            return GenericEntityUtil.parseJobProgressResponse(responsePromise);
        } catch (JSONException e) {
            log.log(Level.SEVERE, "Error in parsing the response");
            throw e;
        } catch (HttpException e) {
            throw e;
        }
    }

    @Override
    public String copyExecutionsToCycle(String cycleId, Long projectId, Long versionId, List<String> executionIds, Boolean clearDefectMappingFlag, Boolean clearStatusFlag) throws JSONException, HttpException {
        try {
            Map<String, Object> entityMap = new HashMap<String, Object>();
            entityMap.put("executionIds", executionIds);
            entityMap.put("cycleId", cycleId);
            entityMap.put("projectId", projectId);
            entityMap.put("versionId", versionId);
            entityMap.put("clearDefectMappingFlag", clearDefectMappingFlag);
            entityMap.put("clearStatusFlag", clearStatusFlag);
            final URI getExecutionsUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_EXECUTIONS).path(ApplicationConstants.URL_PATH_EXPORT).build();
            ResponsePromise responsePromise = httpClient.newRequest(getExecutionsUri).setEntity(GenericEntityUtil.toEntity(entityMap)).setAccept("application/json").post();
            return GenericEntityUtil.parseJobProgressResponse(responsePromise);
        } catch (JSONException e) {
            log.log(Level.SEVERE, "Error in parsing the response");
            throw e;
        } catch (HttpException e) {
            throw e;
        }
    }
}
