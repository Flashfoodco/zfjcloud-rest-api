package com.thed.zephyr.cloud.rest.client.async;

import com.atlassian.httpclient.api.ResponsePromise;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.thed.zephyr.cloud.rest.client.CycleRestClient;
import com.thed.zephyr.cloud.rest.client.constant.ApplicationConstants;
import com.thed.zephyr.cloud.rest.client.model.Cycle;
import com.thed.zephyr.cloud.rest.client.model.GenericEntityUtil;
import com.thed.zephyr.cloud.rest.client.util.json.CycleJsonParser;
import org.apache.http.HttpException;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.core.UriBuilder;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
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
    private JsonObjectParser<Cycle> cycleJsonObjectParser;

    public AsynchronousCycleRestClient(URI baseUri, DisposableHttpClient httpClient) {
        this.httpClient = httpClient;
        this.baseUri = baseUri;
        this.cycleJsonObjectParser = new CycleJsonParser();
    }

    @Override
    public Cycle createCycle(Cycle cycle) throws JSONException, HttpException {
        return createCycle(cycle, cycleJsonObjectParser);
    }

    @Override
    public <T> T createCycle(Cycle cycle, JsonObjectParser<T> parser) throws JSONException, HttpException {
        try {
            final URI createCycleUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_CYCLE).build();
            ResponsePromise responsePromise = httpClient.newRequest(createCycleUri).setEntity(GenericEntityUtil.toEntity(cycle)).setAccept("application/json").post();
            JSONObject jsonResponse = GenericEntityUtil.parseJsonResponse(responsePromise);
            return parser.parse(jsonResponse);
        } catch (JSONException e) {
            log.log(Level.SEVERE, "Error in parsing the response");
            throw e;
        } catch (HttpException e) {
            throw e;
        }
    }

    @Override
    public Cycle cloneCycle(String clonedCycleId, Cycle cycle) throws JSONException, HttpException {
        return cloneCycle(clonedCycleId, cycle, cycleJsonObjectParser);
    }

    @Override
    public <T> T cloneCycle(String clonedCycleId, Cycle cycle, JsonObjectParser<T> parser) throws JSONException, HttpException {
        try {
            final URI createCycleUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_CYCLE).queryParam("clonedCycleId", clonedCycleId).build();
            ResponsePromise responsePromise = httpClient.newRequest(createCycleUri).setEntity(GenericEntityUtil.toEntity(cycle)).setAccept("application/json").post();
            JSONObject jsonObejct = GenericEntityUtil.parseJsonResponse(responsePromise);
            return parser.parse(jsonObejct);
        } catch (JSONException e) {
            log.log(Level.SEVERE, "Error in parsing the response");
            throw e;
        } catch (HttpException e) {
            throw e;
        }
    }

    @Override
    public Cycle getCycle(Long projectId, Long versionId, String cycleId) throws JSONException, HttpException {
        return getCycle(projectId, versionId, cycleId, cycleJsonObjectParser);
    }

    @Override
    public <T> T getCycle(Long projectId, Long versionId, String cycleId, JsonObjectParser<T> parser) throws JSONException, HttpException {
        try {
            final URI getCycleUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_CYCLE).path(cycleId).queryParam(ApplicationConstants.QUERY_PARAM_PROJECT_ID, projectId).queryParam(ApplicationConstants.QUERY_PARAM_VERSION_ID, versionId).build();
            ResponsePromise responsePromise = httpClient.newRequest(getCycleUri).setAccept("application/json").get();
            JSONObject jsonObejct = GenericEntityUtil.parseJsonResponse(responsePromise);
            return parser.parse(jsonObejct);
        } catch (JSONException e) {
            log.log(Level.SEVERE, "Error in parsing the response");
            throw e;
        } catch (HttpException e) {
            throw e;
        }
    }

    @Override
    public Cycle updateCycle(String cycleId, Cycle newCycle) throws JSONException, HttpException {
        return updateCycle(cycleId, newCycle, cycleJsonObjectParser);
    }

    @Override
    public <T> T updateCycle(String cycleId, Cycle newCycle, JsonObjectParser<T> parser) throws JSONException, HttpException {
        try {
            final URI updateCycleUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_CYCLE).path(cycleId).build();
            ResponsePromise responsePromise = httpClient.newRequest(updateCycleUri).setEntity(GenericEntityUtil.toEntity(newCycle)).setAccept("application/json").put();
            JSONObject jsonObejct = GenericEntityUtil.parseJsonResponse(responsePromise);
            return parser.parse(jsonObejct);
        } catch (JSONException e) {
            log.log(Level.SEVERE, "Error in parsing the response");
            throw e;
        } catch (HttpException e) {
            throw e;
        }
    }

    @Override
    public Boolean deleteCycle(Long projectId, Long versionId, String cycleId) throws JSONException, HttpException {
        try {
            final URI deleteCycleUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_CYCLE).path(cycleId).queryParam(ApplicationConstants.QUERY_PARAM_PROJECT_ID, projectId).queryParam(ApplicationConstants.QUERY_PARAM_VERSION_ID, versionId).build();
            ResponsePromise responsePromise = httpClient.newRequest(deleteCycleUri).setAccept("application/json").delete();
            return GenericEntityUtil.parseBooleanResponse(responsePromise);
        } catch (JSONException e) {
            log.log(Level.SEVERE, "Error in parsing the response");
            throw e;
        } catch (HttpException e) {
            throw e;
        }
    }

    @Override
    public List<Cycle> getCycles(Long projectId, Long versionId) throws JSONException, HttpException {
        return getCycles(projectId, versionId, cycleJsonObjectParser);
    }

    @Override
    public <T> List<T> getCycles(Long projectId, Long versionId, JsonObjectParser<T> parser) throws JSONException, HttpException {
        try {
            final URI getCyclesUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_CYCLES).path(ApplicationConstants.URL_PATH_SEARCH).queryParam(ApplicationConstants.QUERY_PARAM_PROJECT_ID, projectId).queryParam(ApplicationConstants.QUERY_PARAM_VERSION_ID, versionId).build();
            ResponsePromise responsePromise = httpClient.newRequest(getCyclesUri).setAccept("application/json").get();
            JSONArray jsonArray = GenericEntityUtil.parseJsonArrayResponse(responsePromise);
            return parseCycleArray(jsonArray, parser);
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

    private <T> List<T> parseCycleArray(JSONArray jsonArray, JsonObjectParser<T> parser) throws JSONException{
        List<T> result = new ArrayList<T>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.optJSONObject(i);
            if (json != null){
                result.add(parser.parse(json));
            }
        }
        return result;
    }
}
