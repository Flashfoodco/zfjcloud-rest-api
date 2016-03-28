package com.thed.zephyr.cloud.rest.client.impl;

import com.atlassian.httpclient.api.Response;
import com.atlassian.httpclient.api.ResponsePromise;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.thed.zephyr.cloud.rest.client.ExecutionRestClient;
import com.thed.zephyr.cloud.rest.client.JobProgressRestClient;
import com.thed.zephyr.cloud.rest.client.async.AsyncExecutionRestClient;
import com.thed.zephyr.cloud.rest.client.async.impl.AsyncExecutionRestClientImpl;
import com.thed.zephyr.cloud.rest.exception.JobProgressException;
import com.thed.zephyr.cloud.rest.model.JobProgress;
import com.thed.zephyr.cloud.rest.model.enam.SortOrder;
import com.thed.zephyr.cloud.rest.util.HttpResponseParser;
import com.thed.zephyr.cloud.rest.model.Execution;
import com.thed.zephyr.cloud.rest.util.ZFJConnectResults;
import com.thed.zephyr.cloud.rest.util.json.ExecutionJsonParser;
import org.apache.http.HttpException;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kavya on 18-02-2016.
 */
public class ExecutionRestClientImpl implements ExecutionRestClient {

    private final AsyncExecutionRestClient asyncExecutionRestClient;

    private JsonObjectParser<Execution> executionJsonObjectParser;

    private HttpResponseParser httpResponseParser;

    private JobProgressRestClient jobProgressRestClient;

    private Logger log = LoggerFactory.getLogger(ExecutionRestClientImpl.class);


    public ExecutionRestClientImpl(URI baseUri, DisposableHttpClient httpClient) {
        this.asyncExecutionRestClient = new AsyncExecutionRestClientImpl(baseUri, httpClient);
        this.executionJsonObjectParser = new ExecutionJsonParser();
        this.httpResponseParser = new HttpResponseParser();
        this.jobProgressRestClient = new JobProgressRestClientImpl(baseUri, httpClient);
    }

    @Override
    public Execution createExecution(Execution execution) throws JSONException, HttpException {
        return createExecution(execution, executionJsonObjectParser);
    }

    @Override
    public <T> T createExecution(Execution execution, JsonObjectParser<T> jsonParser) throws JSONException, HttpException {
        try {
            ResponsePromise responsePromise = asyncExecutionRestClient.createExecution(execution);
            Response response = responsePromise.claim();
            JSONObject jsonResponse = httpResponseParser.parseJsonResponse(response);

            return jsonParser.parse(jsonResponse.getJSONObject("execution"));
        } catch (JSONException exception) {
            log.error("Error during parse response from server.", exception);
            throw exception;
        } catch (HttpException exception) {
            log.error("Http error from server.", exception);
            throw exception;
        }
    }

    @Override
    public Execution getExecution(Long projectId, Long issueId, String executionId) throws JSONException, HttpException{
        return getExecution(projectId, issueId, executionId, executionJsonObjectParser);
    }

    @Override
    public <T> T getExecution(Long projectId, Long issueId, String executionId, JsonObjectParser<T> jsonParser) throws JSONException, HttpException {
        try {
            ResponsePromise responsePromise = asyncExecutionRestClient.getExecution(projectId, issueId, executionId);
            Response response = responsePromise.claim();
            JSONObject jsonResponse = httpResponseParser.parseJsonResponse(response);
            return jsonParser.parse(jsonResponse.getJSONObject("execution").getJSONObject("execution"));

        } catch (JSONException exception) {
            log.error("Error during parse response from server.", exception);
            throw exception;
        } catch (HttpException exception) {
            log.error("Http error from server.", exception);
            throw exception;
        }
    }

    @Override
    public Execution updateExecution(Execution execution) throws JSONException, HttpException{
        return updateExecution(execution, executionJsonObjectParser);
    }

    @Override
    public <T> T updateExecution(Execution execution, JsonObjectParser<T> jsonParser) throws JSONException, HttpException {
        try {
            ResponsePromise responsePromise = asyncExecutionRestClient.updateExecution(execution);
            Response response = responsePromise.claim();
            JSONObject jsonResponse = httpResponseParser.parseJsonResponse(response);

            return jsonParser.parse(jsonResponse.getJSONObject("execution"));
        } catch (JSONException exception) {
            log.error("Error during parse response from server.", exception);
            throw exception;
        } catch (HttpException exception) {
            log.error("Http error from server.", exception);
            throw exception;
        }
    }

    @Override
    public Boolean deleteExecution(Long projectId, Long issueId, String executionId) throws HttpException {
        try {
            ResponsePromise responsePromise = asyncExecutionRestClient.deleteExecution(projectId, issueId, executionId);
            Response response = responsePromise.claim();
            Boolean booleanResponse = httpResponseParser.parseBooleanResponse(response);

            return booleanResponse;
        } catch (HttpException exception) {
            log.error("Http error from server.", exception);
            throw exception;
        }
    }

    @Override
    public ZFJConnectResults<Execution> getExecutions(Long projectId, Long issueId, int offset, int size) throws JSONException, HttpException{
        return getExecutions(projectId, issueId, offset, size, executionJsonObjectParser);
    }

    @Override
    public <T> ZFJConnectResults<T> getExecutions(Long projectId, Long issueId, int offset, int size, JsonObjectParser<T> jsonParser) throws JSONException, HttpException {
        try {
            ResponsePromise responsePromise = asyncExecutionRestClient.getExecutions(projectId, issueId, offset, size);
            Response response = responsePromise.claim();
            JSONObject jsonResponse = httpResponseParser.parseJsonResponse(response);
            List<T> resultList = parseExecutionArray(jsonResponse.getJSONArray("executions"), "execution", jsonParser);
            ZFJConnectResults<T> results = new ZFJConnectResults<T>(resultList, offset, jsonResponse.getInt("totalCount"), jsonResponse.getInt("maxAllowed"));

            return results;
        } catch (JSONException exception) {
            log.error("Error during parse response from server.", exception);
            throw exception;
        } catch (HttpException exception) {
            log.error("Http error from server.", exception);
            throw exception;
        }
    }

    @Override
    public ZFJConnectResults<Execution> getExecutionsByCycle(Long projectId, Long versionId, String cycleId, int offset, int size, String sortBy, SortOrder sortOrder) throws JSONException, HttpException{
        return getExecutionsByCycle(projectId, versionId, cycleId, offset, size, sortBy, sortOrder, executionJsonObjectParser);
    }

    @Override
    public <T> ZFJConnectResults<T> getExecutionsByCycle(Long projectId, Long versionId, String cycleId, int offset, int size, String sortBy, SortOrder sortOrder, JsonObjectParser<T> jsonParser) throws JSONException, HttpException {
        try {
            ResponsePromise responsePromise = asyncExecutionRestClient.getExecutionsByCycle(projectId, versionId, cycleId, offset, size, sortBy, sortOrder);
            Response response = responsePromise.claim();
            JSONObject jsonResponse = httpResponseParser.parseJsonResponse(response);
            List<T> resultList = parseExecutionArray(jsonResponse.getJSONArray("searchObjectList"), "execution", jsonParser);
            ZFJConnectResults<T> results = new ZFJConnectResults<T>(resultList, offset, jsonResponse.getInt("totalCount"), jsonResponse.getInt("maxAllowed"));

            return results;
        } catch (JSONException exception) {
            log.error("Error during parse response from server.", exception);
            throw exception;
        } catch (HttpException exception) {
            log.error("Http error from server.", exception);
            throw exception;
        }
    }

    @Override
    public JobProgress addTestsToCycle(Long projectId, Long versionId, String cycleId, List<Long> issueIds) throws HttpException, JobProgressException {
        try {
            ResponsePromise responsePromise = asyncExecutionRestClient.addTestsToCycle(projectId, versionId, cycleId, issueIds);
            Response response = responsePromise.claim();
            String jobProgressTicket = httpResponseParser.parseStringResponse(response);
            JobProgress jobProgress = jobProgressRestClient.getJobProgress(jobProgressTicket);

            return jobProgress;
        } catch (HttpException exception) {
            log.error("Http error from server.", exception);
            throw exception;
        } catch (JobProgressException exception){
            log.error("Error during proceed remote job in server.",exception);
            throw exception;
        }
    }

    @Override
    public InputStream exportExecutions(String exportType, List<String> executionIds, String zqlQuery) throws JobProgressException, HttpException {
        try {
            ResponsePromise responsePromise = asyncExecutionRestClient.exportExecution(exportType, executionIds, zqlQuery);
            Response response = responsePromise.claim();
            String jobProgressTicket = httpResponseParser.parseStringResponse(response);
            JobProgress jobProgress = jobProgressRestClient.getJobProgress(jobProgressTicket);

            return downloadExportedFile(jobProgress.summaryMessage);
        } catch (HttpException exception) {
            log.error("Http error from server.", exception);
            throw exception;
        } catch (JobProgressException exception){
            log.error("Error during proceed remote job in server.",exception);
            throw exception;
        }
    }

    @Override
    public InputStream downloadExportedFile(String fileName) throws HttpException {
        ResponsePromise responsePromise = asyncExecutionRestClient.downloadExportedFile(fileName);
        Response response = responsePromise.claim();
        InputStream inputStream = httpResponseParser.parseInputStrem(response);

        return inputStream;
    }

    @Override
    public JobProgress bulkUpdateStatus(List<String> executionIds, Integer statusId, Integer stepStatusId, Boolean testStepStatusChangeFlag, Boolean clearDefectMappingFlag) throws JobProgressException, HttpException {
        try {
            ResponsePromise responsePromise = asyncExecutionRestClient.bulkUpdateStatus(executionIds, statusId, stepStatusId, testStepStatusChangeFlag, clearDefectMappingFlag);
            Response response = responsePromise.claim();
            String jobProgressTicket = httpResponseParser.parseStringResponse(response);
            JobProgress jobProgress = jobProgressRestClient.getJobProgress(jobProgressTicket);

            return jobProgress;
        } catch (HttpException exception) {
            log.error("Http error from server.", exception);
            throw exception;
        }  catch (JobProgressException exception){
            log.error("Error during proceed remote job in server.",exception);
            throw exception;
        }
    }

    /*@Override
    public JobProgress bulkDeleteExecutions(List<String> executionIds) throws JobProgressException, HttpException {
        try {
            ResponsePromise responsePromise = asyncExecutionRestClient.bulkDeleteExecutions(executionIds);
            Response response = responsePromise.claim();
            String jobProgressTicket = httpResponseParser.parseStringResponse(response);
            JobProgress jobProgress = jobProgressRestClient.getJobProgress(jobProgressTicket);
            return jobProgress;
        } catch (HttpException exception) {
            log.error("Http error from server.", exception);
            throw exception;
        }  catch (JobProgressException exception){
            log.error("Error during proceed remote job in server.",exception);
            throw exception;
        }
    }*/

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
