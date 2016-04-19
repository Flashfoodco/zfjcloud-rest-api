package com.thed.zephyr.cloud.rest.client.impl;

import com.atlassian.httpclient.api.Response;
import com.atlassian.httpclient.api.ResponsePromise;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.thed.zephyr.cloud.rest.client.CycleRestClient;
import com.thed.zephyr.cloud.rest.client.JobProgressRestClient;
import com.thed.zephyr.cloud.rest.client.async.AsyncCycleRestClient;
import com.thed.zephyr.cloud.rest.client.async.impl.AsyncCycleRestClientImpl;
import com.thed.zephyr.cloud.rest.constant.ApplicationConstants;
import com.thed.zephyr.cloud.rest.exception.BadRequestParamException;
import com.thed.zephyr.cloud.rest.exception.JobProgressException;
import com.thed.zephyr.cloud.rest.model.Cycle;
import com.thed.zephyr.cloud.rest.model.JobProgress;
import com.thed.zephyr.cloud.rest.util.HttpResponseParser;
import com.thed.zephyr.cloud.rest.util.json.CycleJsonParser;
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
public class CycleRestClientImpl implements CycleRestClient {

    private AsyncCycleRestClient asyncCycleRestClient;

    private JsonObjectParser<Cycle> cycleJsonObjectParser;

    private HttpResponseParser httpResponseParser;

    private JobProgressRestClient jobProgressRestClient;

    Logger log = LoggerFactory.getLogger(CycleRestClientImpl.class);


    public CycleRestClientImpl(URI baseUri, DisposableHttpClient httpClient) {
        httpResponseParser = new HttpResponseParser();
        this.cycleJsonObjectParser = new CycleJsonParser();
        this.jobProgressRestClient = new JobProgressRestClientImpl(baseUri, httpClient);
        asyncCycleRestClient = new AsyncCycleRestClientImpl(baseUri, httpClient);
    }

    @Override
    public Cycle createCycle(Cycle cycle) throws JSONException, HttpException, BadRequestParamException {
        return createCycle(cycle, cycleJsonObjectParser);
    }

    @Override
    public <T> T createCycle(Cycle cycle, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException {
        try {
            validateCycle(cycle);
            ResponsePromise responsePromise = asyncCycleRestClient.createCycle(cycle);
            Response response = responsePromise.claim();
            JSONObject jsonResponse = httpResponseParser.parseJsonResponse(response);
            return jsonParser.parse(jsonResponse);
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
    public Cycle cloneCycle(String clonedCycleId, Cycle cycle) throws JSONException, HttpException, BadRequestParamException {
        return cloneCycle(clonedCycleId, cycle, cycleJsonObjectParser);
    }

    @Override
    public <T> T cloneCycle(String clonedCycleId, Cycle cycle, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException {
        try {
            validateInput(clonedCycleId);
            validateCycle(cycle);
            ResponsePromise responsePromise = asyncCycleRestClient.cloneCycle(clonedCycleId, cycle);
            Response response = responsePromise.claim();
            JSONObject jsonResponse = httpResponseParser.parseJsonResponse(response);
            return jsonParser.parse(jsonResponse);
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
    public Cycle getCycle(Long projectId, Long versionId, String cycleId) throws JSONException, HttpException, BadRequestParamException {
        return getCycle(projectId, versionId, cycleId, cycleJsonObjectParser);
    }

    @Override
    public <T> T getCycle(Long projectId, Long versionId, String cycleId, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException {
        try {
            validateInput(projectId);
            validateInput(versionId);
            validateInput(cycleId);
            ResponsePromise responsePromise = asyncCycleRestClient.getCycle(projectId, versionId, cycleId);
            Response response = responsePromise.claim();
            JSONObject jsonResponse = httpResponseParser.parseJsonResponse(response);
            return jsonParser.parse(jsonResponse);
        } catch (JSONException exception) {
            log.error("Error during parse response from server.", exception);
            throw exception;
        } catch (HttpException exception) {
            log.error("Http error from server.", exception);
            throw exception;
        } catch (BadRequestParamException exception) {
            log.error("Required parameter is missing", exception);
            throw  exception;
        }
    }

    @Override
    public Cycle updateCycle(String cycleId, Cycle newCycle) throws JSONException, HttpException, BadRequestParamException {
        return updateCycle(cycleId, newCycle, cycleJsonObjectParser);
    }

    @Override
    public <T> T updateCycle(String cycleId, Cycle newCycle, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException {
        try {
            validateInput(cycleId);
            validateCycle(newCycle);
            ResponsePromise responsePromise = asyncCycleRestClient.updateCycle(cycleId, newCycle);
            Response response = responsePromise.claim();
            JSONObject jsonResponse = httpResponseParser.parseJsonResponse(response);
            return jsonParser.parse(jsonResponse);
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
    public Boolean deleteCycle(Long projectId, Long versionId, String cycleId) throws HttpException, BadRequestParamException {
        try {
            validateInput(projectId);
            validateInput(versionId);
            validateInput(cycleId);
            ResponsePromise responsePromise = asyncCycleRestClient.deleteCycle(projectId, versionId, cycleId);
            Response response = responsePromise.claim();
            return httpResponseParser.parseBooleanResponse(response);
        } catch (HttpException exception) {
            log.error("Http error from server.", exception);
            throw exception;
        } catch (BadRequestParamException e) {
            log.error("Error in request", e);
            throw e;
        }
    }

    @Override
    public List<Cycle> getCycles(Long projectId, Long versionId) throws JSONException, HttpException, BadRequestParamException {
        return getCycles(projectId, versionId, cycleJsonObjectParser);
    }

    @Override
    public <T> List<T> getCycles(Long projectId, Long versionId, JsonObjectParser<T> parser) throws JSONException, HttpException, BadRequestParamException {
        try {
            validateInput(projectId);
            validateInput(versionId);
            ResponsePromise responsePromise = asyncCycleRestClient.getCycles(projectId, versionId);
            Response response = responsePromise.claim();
            JSONArray jsonResponse = httpResponseParser.parseJsonArrayResponse(response);
            return parseCycleArray(jsonResponse, parser);
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
    public InputStream exportCycle(Long projectId, Long versionId, String cycleId, String exportType) throws JobProgressException, HttpException, BadRequestParamException {
        try {
            validateInput(projectId);
            validateInput(versionId);
            validateInput(cycleId);
            validateInput(exportType);
            ResponsePromise responsePromise = asyncCycleRestClient.exportCycle(projectId, versionId, cycleId, exportType);
            Response response = responsePromise.claim();
            String jobProgressTicket = httpResponseParser.parseStringResponse(response);
            JobProgress jobProgress = jobProgressRestClient.getJobProgress(jobProgressTicket);
            return downloadExportedFile(jobProgress.summaryMessage);
        } catch (HttpException exception) {
            log.error("Http error from server.", exception);
            throw exception;
        } catch (BadRequestParamException e) {
            log.error("Error in request", e);
            throw e;
        }
    }

    @Override
    public InputStream downloadExportedFile(String fileName) throws HttpException, BadRequestParamException {
        validateInput(fileName);
        ResponsePromise responsePromise = asyncCycleRestClient.downloadExportedFile(fileName);
        Response response = responsePromise.claim();
        InputStream inputStream = httpResponseParser.parseInputStrem(response);
        return inputStream;
    }

    @Override
    public JobProgress moveExecutionsToCycle(String cycleId, Long projectId, Long versionId, List<String> executionIds, Boolean clearDefectMappingFlag, Boolean clearStatusFlag) throws JobProgressException, HttpException, BadRequestParamException {
        try {
            validateInput(cycleId);
            validateInput(projectId);
            validateInput(versionId);
            validateInput(clearDefectMappingFlag);
            validateInput(clearStatusFlag);
            ResponsePromise responsePromise = asyncCycleRestClient.moveExecutionsToCycle(cycleId, projectId, versionId, executionIds, clearDefectMappingFlag, clearStatusFlag);
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
    public JobProgress copyExecutionsToCycle(String cycleId, Long projectId, Long versionId, List<String> executionIds, Boolean clearDefectMappingFlag, Boolean clearStatusFlag) throws JobProgressException, HttpException, BadRequestParamException {
        try {
            validateInput(cycleId);
            validateInput(projectId);
            validateInput(versionId);
            validateInput(clearDefectMappingFlag);
            validateInput(clearStatusFlag);
            ResponsePromise responsePromise = asyncCycleRestClient.copyExecutionsToCycle(cycleId, projectId, versionId,executionIds, clearDefectMappingFlag, clearStatusFlag);
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

    private void validateInput(Object input) throws BadRequestParamException {
        if(null == input)
            throw new BadRequestParamException("Required request parameter is missing", new NullPointerException());
    }

    private void validateInputMaxLength(String param, int maxLength) throws BadRequestParamException {
        if(param.length() > maxLength)
            throw new BadRequestParamException("Request parameter is too long");
    }

    private void validateCycle(Cycle cycle) throws BadRequestParamException {
        validateInput(cycle.name);
        if(cycle.description != null) validateInputMaxLength(cycle.description, ApplicationConstants.CYCLE_DESC_MAX_LENGTH);
        if(cycle.startDate == null && cycle.endDate != null)
            throw new BadRequestParamException("End date can not be present without start date");
        if(cycle.startDate != null && cycle.endDate != null)
            if(cycle.startDate.after(cycle.endDate))
                throw new BadRequestParamException("End date of the cycle can not be before start date");

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
