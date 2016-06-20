package com.thed.zephyr.cloud.rest.client.impl;

import com.atlassian.httpclient.api.Response;
import com.atlassian.httpclient.api.ResponsePromise;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thed.zephyr.cloud.rest.client.StepResultRestClient;
import com.thed.zephyr.cloud.rest.client.async.AsyncStepResultRestClient;
import com.thed.zephyr.cloud.rest.client.async.impl.AsyncStepResultRestClientImpl;
import com.thed.zephyr.cloud.rest.exception.BadRequestParamException;
import com.thed.zephyr.cloud.rest.model.StepResult;
import com.thed.zephyr.cloud.rest.util.HttpResponseParser;
import com.thed.zephyr.cloud.rest.util.ValidateUtil;
import com.thed.zephyr.cloud.rest.util.json.StepResultJsonParser;
import org.apache.http.HttpException;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Kavya on 20-06-2016.
 */
public class StepResultRestClientImpl implements StepResultRestClient {

    private JsonObjectParser<StepResult> stepResultJsonParser;

    private AsyncStepResultRestClient asyncStepResultRestClient;

    private HttpResponseParser httpResponseParser;

    Logger log = LoggerFactory.getLogger(StepResultRestClientImpl.class);

    public StepResultRestClientImpl(URI baseUri, DisposableHttpClient httpClient) {
        httpResponseParser = new HttpResponseParser();
        asyncStepResultRestClient = new AsyncStepResultRestClientImpl(baseUri, httpClient);
        stepResultJsonParser = new StepResultJsonParser();
    }

    @Override
    public List<StepResult> getStepResults(Long projectId, Long issueId, String executionId) throws BadRequestParamException, JSONException, HttpException {
        return getSteResults(projectId, issueId, executionId, stepResultJsonParser);
    }

    @Override
    public <T> List<T> getSteResults(Long projectId, Long issueId, String executionId, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException {
        try {
            ValidateUtil.validate(projectId, issueId, executionId);
            ResponsePromise responsePromise = asyncStepResultRestClient.getStepResults(projectId, issueId, executionId);
            Response response = responsePromise.claim();
            JSONObject jsonResponse = httpResponseParser.parseJsonResponse(response);
            return parseStepResultArray(jsonResponse.getJSONArray("stepResults"), jsonParser);
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
    public StepResult getStepResult(Long projectId, String executionId, String stepResultId) throws BadRequestParamException, JSONException, HttpException {
        return getStepResult(projectId, executionId, stepResultId, stepResultJsonParser);
    }

    @Override
    public <T> T getStepResult(Long projectId, String executionId, String stepResultId, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException {
        try {
            ValidateUtil.validate(projectId, executionId, stepResultId);
            ResponsePromise responsePromise = asyncStepResultRestClient.getStepResult(projectId, executionId, stepResultId);
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
    public StepResult updateStepResult(Long projectId, String stepResultId, StepResult stepResult) throws BadRequestParamException, JSONException, HttpException {
        return updateStepResult(projectId, stepResultId, stepResult, stepResultJsonParser);
    }

    @Override
    public <T> T updateStepResult(Long projectId, String stepResultId, StepResult stepResult, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException {
        try {
            ValidateUtil.validate(projectId, stepResultId);
            ResponsePromise responsePromise = asyncStepResultRestClient.updateStepResult(projectId, stepResultId, stepResult);
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
    public Map getStepDefectsCountByExecutionId(Long projectId, String executionId) throws JSONException, HttpException, BadRequestParamException {
        try {
            ValidateUtil.validate(projectId, executionId);
            ResponsePromise responsePromise = asyncStepResultRestClient.getStepDefectsCountByExecutionId(projectId, executionId);
            Response response = responsePromise.claim();
            JSONObject jsonResponse = httpResponseParser.parseJsonResponse(response);
            return new ObjectMapper().convertValue(jsonResponse, Map.class);
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

    private <T> List<T> parseStepResultArray(JSONArray jsonArray, JsonObjectParser<T> parser) throws JSONException{
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
