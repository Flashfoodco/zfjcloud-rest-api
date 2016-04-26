package com.thed.zephyr.cloud.rest.client.impl;

import com.atlassian.httpclient.api.Response;
import com.atlassian.httpclient.api.ResponsePromise;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.thed.zephyr.cloud.rest.client.ExecutionRestClient;
import com.thed.zephyr.cloud.rest.client.JobProgressRestClient;
import com.thed.zephyr.cloud.rest.client.async.AsyncExecutionRestClient;
import com.thed.zephyr.cloud.rest.client.async.impl.AsyncExecutionRestClientImpl;
import com.thed.zephyr.cloud.rest.exception.BadRequestParamException;
import com.thed.zephyr.cloud.rest.exception.JobProgressException;
import com.thed.zephyr.cloud.rest.model.Execution;
import com.thed.zephyr.cloud.rest.model.JobProgress;
import com.thed.zephyr.cloud.rest.model.enam.FromCycleFilter;
import com.thed.zephyr.cloud.rest.model.enam.SortOrder;
import com.thed.zephyr.cloud.rest.util.HttpResponseParser;
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
import java.util.Map;

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
    public Execution createExecution(Execution execution) throws JSONException, HttpException, BadRequestParamException {
        return createExecution(execution, executionJsonObjectParser);
    }

    @Override
    public <T> T createExecution(Execution execution, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException {
        try {
            validateExecution(execution);

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
        } catch (BadRequestParamException e) {
            log.error("Error in request", e);
            throw e;
        }
    }

    @Override
    public Execution getExecution(Long projectId, Long issueId, String executionId) throws JSONException, HttpException, BadRequestParamException {
        return getExecution(projectId, issueId, executionId, executionJsonObjectParser);
    }

    @Override
    public <T> T getExecution(Long projectId, Long issueId, String executionId, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException {
        try {
            validateInput(projectId);
            validateInput(issueId);
            validateInput(executionId);

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
        } catch (BadRequestParamException e) {
            log.error("Error in request", e);
            throw e;
        }
    }

    @Override
    public Execution updateExecution(Execution execution) throws JSONException, HttpException, BadRequestParamException {
        return updateExecution(execution, executionJsonObjectParser);
    }

    @Override
    public <T> T updateExecution(Execution execution, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException {
        try {
            validateInput(execution.id);
            validateExecution(execution);

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
        } catch (BadRequestParamException e) {
            log.error("Error in request", e);
            throw e;
        }
    }

    @Override
    public Boolean deleteExecution(Long projectId, Long issueId, String executionId) throws HttpException, BadRequestParamException {
        try {
            validateInput(projectId);
            validateInput(issueId);
            validateInput(executionId);

            ResponsePromise responsePromise = asyncExecutionRestClient.deleteExecution(projectId, issueId, executionId);
            Response response = responsePromise.claim();
            Boolean booleanResponse = httpResponseParser.parseBooleanResponse(response);

            return booleanResponse;
        } catch (HttpException exception) {
            log.error("Http error from server.", exception);
            throw exception;
        } catch (BadRequestParamException e) {
            log.error("Error in request", e);
            throw e;
        }
    }

    @Override
    public ZFJConnectResults<Execution> getExecutions(Long projectId, Long issueId, int offset, int size) throws JSONException, HttpException, BadRequestParamException {
        return getExecutions(projectId, issueId, offset, size, executionJsonObjectParser);
    }

    @Override
    public <T> ZFJConnectResults<T> getExecutions(Long projectId, Long issueId, int offset, int size, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException {
        try {
            validateInput(projectId);
            validateInput(issueId);

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
        } catch (BadRequestParamException e) {
            log.error("Error in request", e);
            throw e;
        }
    }

    @Override
    public ZFJConnectResults<Execution> getExecutionsByCycle(Long projectId, Long versionId, String cycleId, int offset, int size, String sortBy, SortOrder sortOrder) throws JSONException, HttpException, BadRequestParamException {
        return getExecutionsByCycle(projectId, versionId, cycleId, offset, size, sortBy, sortOrder, executionJsonObjectParser);
    }

    @Override
    public <T> ZFJConnectResults<T> getExecutionsByCycle(Long projectId, Long versionId, String cycleId, int offset, int size, String sortBy, SortOrder sortOrder, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException {
        try {
            validateInput(projectId);
            validateInput(versionId);
            validateInput(cycleId);

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
        } catch (BadRequestParamException e) {
            log.error("Error in request", e);
            throw e;
        }
    }

    @Override
    public ZFJConnectResults<Execution> getLinkedExecutions(String issueIdorKey, int offset, int size) throws JSONException, HttpException, BadRequestParamException {
        return getLinkedExecutions(issueIdorKey, offset, size, executionJsonObjectParser);
    }

    @Override
    public <T> ZFJConnectResults<T> getLinkedExecutions(String issueIdorKey, int offset, int size, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException {
        try {
            validateInput(issueIdorKey);

            ResponsePromise responsePromise = asyncExecutionRestClient.getLinkedExecutions(issueIdorKey, offset, size);
            Response response = responsePromise.claim();
            JSONObject jsonResponse = httpResponseParser.parseJsonResponse(response);
            List<T> resultList = parseExecutionArray(jsonResponse.getJSONArray("searchObjectList"), "execution", jsonParser);
            ZFJConnectResults<T> results = new ZFJConnectResults<T>(resultList, offset, jsonResponse.getInt("totalCount"), jsonResponse.optInt("maxAllowed", 0));
            return results;
        } catch (JSONException exception) {
            log.error("Error during parse response from server.", exception);
            throw exception;
        } catch (HttpException exception) {
            log.error("Http error from server.", exception);
            throw exception;
        } catch (BadRequestParamException e) {
            log.error("Error in request", e);
            throw e;
        }
    }

    @Override
    public JobProgress addTestsToCycle(Long projectId, Long versionId, String cycleId, List<Long> issueIds) throws HttpException, JobProgressException, BadRequestParamException {
        try {
            validateInput(projectId);
            validateInput(versionId);
            validateArrayInput(issueIds);

            ResponsePromise responsePromise = asyncExecutionRestClient.addTestsToCycle(projectId, versionId, cycleId, issueIds, null, null, 1, null, null);
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
        } catch (BadRequestParamException e) {
            log.error("Error in request", e);
            throw e;
        }
    }

    @Override
    public JobProgress addTestsToCycleFromCycle(Long projectId, Long toVersionId, String toCycleId, String fromCycleId, Long fromVersionId, Map<FromCycleFilter, List<String>> filter) throws HttpException, JobProgressException, BadRequestParamException {
        try {
            validateInput(projectId);
            validateInput(toVersionId);
            validateInput(toCycleId);
            validateInput(fromVersionId);
            validateInput(fromCycleId);

            ResponsePromise responsePromise = asyncExecutionRestClient.addTestsToCycle(projectId, toVersionId, toCycleId, null, fromCycleId, fromVersionId, 3, filter, null);
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
        } catch (BadRequestParamException e) {
            log.error("Error in request", e);
            throw e;
        }
    }

    @Override
    public JobProgress addTestsToCycleByJQL(Long projectId, Long versionId, String cycleId, String zql) throws HttpException, JobProgressException, BadRequestParamException {
        try {
            validateInput(projectId);
            validateInput(versionId);
            validateInput(cycleId);
            validateInput(zql);

            ResponsePromise responsePromise = asyncExecutionRestClient.addTestsToCycle(projectId, versionId, cycleId, null, null, null, 2, null, zql);
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
        } catch (BadRequestParamException e) {
            log.error("Error in request", e);
            throw e;
        }
    }

    @Override
    public InputStream exportExecutions(String exportType, List<String> executionIds, String zqlQuery) throws JobProgressException, HttpException, BadRequestParamException {
        try {
            validateInput(exportType);

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
        } catch (BadRequestParamException e) {
            log.error("Error in request", e);
            throw e;
        }
    }

    @Override
    public InputStream downloadExportedFile(String fileName) throws HttpException, BadRequestParamException {
        validateInput(fileName);

        ResponsePromise responsePromise = asyncExecutionRestClient.downloadExportedFile(fileName);
        Response response = responsePromise.claim();
        InputStream inputStream = httpResponseParser.parseInputStrem(response);

        return inputStream;
    }

    @Override
    public JobProgress bulkUpdateStatus(List<String> executionIds, Integer statusId, Integer stepStatusId, Boolean testStepStatusChangeFlag, Boolean clearDefectMappingFlag) throws JobProgressException, HttpException, BadRequestParamException {
        try {
            validateArrayInput(executionIds);
            validateInput(statusId);
            validateInput(clearDefectMappingFlag);

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
        } catch (BadRequestParamException e) {
            log.error("Error in request", e);
            throw e;
        }
    }

    @Override
    public JobProgress bulkDeleteExecutions(List<String> executionIds) throws JobProgressException, HttpException, BadRequestParamException {
        try {
            validateArrayInput(executionIds);

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
        } catch (BadRequestParamException exception) {
            log.error("Error in request", exception);
            throw exception;
        }
    }

    @Override
    public JSONObject getExecutionSummaryBySprint(Long sprintId, List<Long> issueIds) throws JSONException, HttpException, BadRequestParamException {
        try {
            validateInput(sprintId);
            validateArrayInput(issueIds);

            ResponsePromise responsePromise = asyncExecutionRestClient.getExecutionSummaryBySprint(sprintId.toString(), issueIds);
            Response response = responsePromise.claim();
            JSONObject jsonResponse = httpResponseParser.parseJsonResponse(response);

            return jsonResponse;
        } catch (JSONException exception) {
            log.error("Error during parse response from server.", exception);
            throw exception;
        } catch (HttpException exception) {
            log.error("Http error from server.", exception);
            throw exception;
        } catch (BadRequestParamException e) {
            log.error("Error in request", e);
            throw e;
        }
    }

    private <T> void validateInput(T input) throws BadRequestParamException {
        if(null == input)
            throw new BadRequestParamException("Required request parameter is missing", new NullPointerException());
    }

    private void validateInputMaxLength(String param, int maxLength) throws BadRequestParamException {
        if(param.length() > maxLength)
            throw new BadRequestParamException("Request parameter is too long");
    }

    private void validateExecution(Execution execution) throws BadRequestParamException {
        validateInput(execution.projectId);
        validateInput(execution.issueId);
    }

    private <T> void validateArrayInput(List<T> elements) throws BadRequestParamException {
        if(null == elements) {
            throw new BadRequestParamException("Required request parameter is missing", new NullPointerException());
        }
        if(elements.isEmpty()) {
            throw new BadRequestParamException("Required request parameter is empty");
        }
    }

    private <T> List<T> parseExecutionArray(JSONArray jsonArray, String key, JsonObjectParser<T> parser) throws JSONException{
        List<T> result = new ArrayList<T>();
        T c;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.optJSONObject(i).optJSONObject(key);
            if (json != null){
                result.add(parser.parse(json));
            }
        }

        return result;
    }
}
