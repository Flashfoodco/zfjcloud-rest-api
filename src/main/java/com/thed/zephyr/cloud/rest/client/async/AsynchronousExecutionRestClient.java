package com.thed.zephyr.cloud.rest.client.async;

import com.atlassian.httpclient.api.ResponsePromise;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.thed.zephyr.cloud.rest.client.ExecutionRestClient;
import com.thed.zephyr.cloud.rest.client.constant.ApplicationConstants;
import com.thed.zephyr.cloud.rest.client.model.Execution;
import com.thed.zephyr.cloud.rest.client.model.GenericEntityUtil;
import com.thed.zephyr.cloud.rest.client.util.json.ExecutionJsonParser;
import org.codehaus.jettison.json.JSONArray;
import org.apache.http.HttpException;
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
public class AsynchronousExecutionRestClient implements ExecutionRestClient {

    private final DisposableHttpClient httpClient;
    private final URI baseUri;
    private Logger log = Logger.getLogger("ExecutionRestClient");
    private JsonObjectParser<Execution> executionJsonObjectParser;

    public AsynchronousExecutionRestClient(URI baseUri, DisposableHttpClient httpClient) {
        this.httpClient = httpClient;
        this.baseUri = baseUri;
        this.executionJsonObjectParser = new ExecutionJsonParser();
    }

    @Override
    public Execution createExecution(Execution execution) throws JSONException, HttpException {
        return createExecution(execution, executionJsonObjectParser);
    }

    @Override
    public <T> T createExecution(Execution execution, JsonObjectParser<T> parser) throws JSONException, HttpException {
        try {
            final URI createExecutionUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_EXECUTION).build();
            ResponsePromise responsePromise = httpClient.newRequest(createExecutionUri).setEntity(GenericEntityUtil.toEntity(execution)).setAccept("application/json").post();
            JSONObject jsonResponse = GenericEntityUtil.parseJsonResponse(responsePromise);
            return parser.parse(jsonResponse.getJSONObject("execution"));
        } catch (JSONException e) {
            log.log(Level.SEVERE, "Error in parsing the response");
            throw e;
        } catch (HttpException e) {
            throw e;
        }
    }

    @Override
    public Execution getExecution(Long projectId, Long issueId, String executionId) throws JSONException, HttpException{
        return getExecution(projectId, issueId, executionId, executionJsonObjectParser);
    }

    @Override
    public <T> T getExecution(Long projectId, Long issueId, String executionId, JsonObjectParser<T> parser) throws JSONException, HttpException {
        try {
            final URI createExecutionUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_EXECUTION).path(executionId).queryParam(ApplicationConstants.QUERY_PARAM_PROJECT_ID, projectId).queryParam(ApplicationConstants.QUERY_PARAM_ISSUE_ID, issueId).build();
            ResponsePromise responsePromise = httpClient.newRequest(createExecutionUri).get();
            JSONObject jsonResponse = GenericEntityUtil.parseJsonResponse(responsePromise);
            return parser.parse(jsonResponse.getJSONObject("execution").getJSONObject("execution"));
        } catch (JSONException e) {
            log.log(Level.SEVERE, "Error in parsing the response");
            throw e;
        } catch (HttpException e) {
            throw e;
        }
    }

    @Override
    public Execution updateExecution(Execution execution) throws JSONException, HttpException{
        return updateExecution(execution, executionJsonObjectParser);
    }

    @Override
    public <T> T updateExecution(Execution execution, JsonObjectParser<T> parser) throws JSONException, HttpException {
        try {
            final URI updateExecutionUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_EXECUTION).path(execution.id).build();
            ResponsePromise responsePromise = httpClient.newRequest(updateExecutionUri).setEntity(GenericEntityUtil.toEntity(execution)).setAccept("application/json").put();
            JSONObject jsonResponse = GenericEntityUtil.parseJsonResponse(responsePromise);
            return parser.parse(jsonResponse.getJSONObject("execution"));
        } catch (JSONException e) {
            log.log(Level.SEVERE, "Error in parsing the response");
            throw e;
        } catch (HttpException e) {
            throw e;
        }
    }

    @Override
    public Boolean deleteExecution(Long projectId, Long issueId, String executionId) throws JSONException, HttpException {
        try {
            final URI deleteExecutionUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_EXECUTION).path(executionId).queryParam(ApplicationConstants.QUERY_PARAM_PROJECT_ID, projectId).queryParam(ApplicationConstants.QUERY_PARAM_ISSUE_ID, issueId).build();
            ResponsePromise responsePromise = httpClient.newRequest(deleteExecutionUri).setAccept("application/json").delete();
            JSONObject jsonResponse = GenericEntityUtil.parseJsonResponse(responsePromise);
            return jsonResponse.optBoolean("response", false);
        } catch (JSONException e) {
            log.log(Level.SEVERE, "Error in parsing the response");
            throw e;
        } catch (HttpException e) {
            throw e;
        }
    }

    @Override
    public List<Execution> getExecutions(Long projectId, Long issueId) throws JSONException, HttpException{
        return getExecutions(projectId, issueId, executionJsonObjectParser);
    }

    @Override
    public <T> List<T> getExecutions(Long projectId, Long issueId, JsonObjectParser<T> parser) throws JSONException, HttpException {
        try {
            final URI deleteExecutionUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_EXECUTIONS).queryParam(ApplicationConstants.QUERY_PARAM_PROJECT_ID, projectId).queryParam(ApplicationConstants.QUERY_PARAM_ISSUE_ID, issueId).build();
            ResponsePromise responsePromise = httpClient.newRequest(deleteExecutionUri).setAccept("application/json").get();
            JSONArray jsonArray = GenericEntityUtil.parseJsonResponse(responsePromise).getJSONArray("executions");
            return parseExecutionArray(jsonArray, "execution", parser);
        } catch (JSONException e) {
            log.log(Level.SEVERE, "Error in parsing the response");
            throw e;
        } catch (HttpException e) {
            throw e;
        }
    }

    @Override
    public JSONObject getExecutionsByCycle(Long projectId, Long versionId, String cycleId) throws JSONException, HttpException {
        try {
            final URI getExecutionsUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_EXECUTIONS).path(ApplicationConstants.URL_PATH_SEARCH).path(ApplicationConstants.URL_PATH_CYCLE).path(cycleId).queryParam(ApplicationConstants.QUERY_PARAM_PROJECT_ID, projectId).queryParam(ApplicationConstants.QUERY_PARAM_VERSION_ID, versionId).build();
            ResponsePromise responsePromise = httpClient.newRequest(getExecutionsUri).setAccept("application/json").get();
            return GenericEntityUtil.parseJsonResponse(responsePromise);
        } catch (JSONException e) {
            log.log(Level.SEVERE, "Error in parsing the response");
            throw e;
        } catch (HttpException e) {
            throw e;
        }
    }

    @Override
    public String addTestsToCycle(Long projectId, String cycleId, List<Long> issueIds) throws JSONException, HttpException {
        try {
            Map<String, Object> entityMap = new HashMap<String, Object>();
            entityMap.put("issueIds", issueIds);
            final URI getExecutionsUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_EXECUTIONS).path(ApplicationConstants.URL_PATH_ADD).path(ApplicationConstants.URL_PATH_CYCLE).path(cycleId).build();
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
    public File exportExecution(String exportType, List<String> executionIds, String zqlQuery) throws JSONException, HttpException {
        try {
            Map<String, Object> entityMap = new HashMap<String, Object>();
            entityMap.put("executions", executionIds);
            entityMap.put("exportType", exportType);
            entityMap.put("zqlQuery", zqlQuery);
            final URI getExecutionsUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_EXECUTIONS).path(ApplicationConstants.URL_PATH_EXPORT).build();
            ResponsePromise responsePromise = httpClient.newRequest(getExecutionsUri).setEntity(GenericEntityUtil.toEntity(entityMap)).setAccept("application/json").post();
            String fileName = GenericEntityUtil.parseJobProgressResponse(responsePromise);
            log.log(Level.INFO, "Cycle export done and downloading the file " + fileName);
            return downloadExportedFile(fileName);
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
    public String bulkUpdateStatus(List<String> executionIds, Integer statusId, Integer stepStatusId, Boolean testStepStatusChangeFlag, Boolean clearDefectMappingFlag) throws JSONException, HttpException {
        try {
            Map<String, Object> entityMap = new HashMap<String, Object>();
            entityMap.put("executionIds", executionIds);
            entityMap.put("statusId", statusId);
            entityMap.put("stepStatusId", stepStatusId);
            entityMap.put("testStepStatusChangeFlag", testStepStatusChangeFlag);
            entityMap.put("clearDefectMappingFlag", clearDefectMappingFlag);
            final URI getExecutionsUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_EXECUTIONS).build();
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
    public String bulkDeleteExecutions(List<String> executionIds) throws JSONException, HttpException {
        try {
            Map<String, Object> entityMap = new HashMap<String, Object>();
            entityMap.put("executionIds", executionIds);
            final URI getExecutionsUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_EXECUTIONS).build();
            ResponsePromise responsePromise = httpClient.newRequest(getExecutionsUri).setEntity(GenericEntityUtil.toEntity(entityMap)).setAccept("application/json").delete();
            return GenericEntityUtil.parseJobProgressResponse(responsePromise);
        } catch (JSONException e) {
            log.log(Level.SEVERE, "Error in parsing the response");
            throw e;
        } catch (HttpException e) {
            throw e;
        }
    }

    /*@Override
    public JSONObject getExecutionSummaryOfIssuesBySprint(Long sprintId, List<Long> issueIds) throws JSONException, HttpException {
        try {
            final URI getExecutionsUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_EXECUTIONS).path(ApplicationConstants.URL_PATH_SEARCH).path(ApplicationConstants.URL_PATH_SPRINT).path(sprintId).path(ApplicationConstants.URL_PATH_ISSUES).build();
            ResponsePromise responsePromise = httpClient.newRequest(getExecutionsUri).setAccept("application/json").get();
            return GenericEntityUtil.parseJobProgressResponse(responsePromise);
        } catch (JSONException e) {
            log.log(Level.SEVERE, "Error in parsing the response");
            throw e;
        } catch (HttpException e) {
            throw e;
        }
    }

    @Override
    public JSONObject getExecutionsByIssue(Long issueId, Integer offset, Integer maxRecords) throws JSONException, HttpException {
        try {
            final URI getExecutionsUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_EXECUTIONS).path(ApplicationConstants.URL_PATH_SEARCH).path(ApplicationConstants.URL_PATH_CYCLE).path(cycleId).queryParam(ApplicationConstants.QUERY_PARAM_PROJECT_ID, projectId).queryParam(ApplicationConstants.QUERY_PARAM_VERSION_ID, versionId).build();
            ResponsePromise responsePromise = httpClient.newRequest(getExecutionsUri).setAccept("application/json").get();
            return GenericEntityUtil.parseJobProgressResponse(responsePromise);
        } catch (JSONException e) {
            log.log(Level.SEVERE, "Error in parsing the response");
            throw e;
        } catch (HttpException e) {
            throw e;
        }
    }*/

    private <T> List<T> parseExecutionArray(JSONArray jsonArray, String key, JsonObjectParser<T> parser) throws JSONException{
        List<T> result = new ArrayList<T>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.optJSONObject(i).optJSONObject(key);
            if (json != null){
                result.add(parser.parse(json));
            }
        }

        return result;
    }
}
