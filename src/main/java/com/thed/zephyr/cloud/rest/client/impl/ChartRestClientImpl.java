package com.thed.zephyr.cloud.rest.client.impl;

import com.atlassian.httpclient.api.Response;
import com.atlassian.httpclient.api.ResponsePromise;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import com.thed.zephyr.cloud.rest.client.ChartRestClient;
import com.thed.zephyr.cloud.rest.client.async.AsyncChartRestClient;
import com.thed.zephyr.cloud.rest.client.async.impl.AsyncChartRestClientImpl;
import com.thed.zephyr.cloud.rest.exception.BadRequestParamException;
import com.thed.zephyr.cloud.rest.util.HttpResponseParser;
import com.thed.zephyr.cloud.rest.util.ValidateUtil;
import org.apache.http.HttpException;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

/**
 * Created by Kavya on 21-06-2016.
 */
public class ChartRestClientImpl implements ChartRestClient {

    AsyncChartRestClient asyncChartRestClient;

    HttpResponseParser httpResponseParser;

    Logger log = LoggerFactory.getLogger(ChartRestClientImpl.class);

    public ChartRestClientImpl(URI baseUri, DisposableHttpClient httpClient) {
        httpResponseParser = new HttpResponseParser();
        asyncChartRestClient = new AsyncChartRestClientImpl(baseUri, httpClient);
    }

    @Override
    public JSONObject getTestsCreated(Long projectId, Long daysPrevious, String periodName, Integer maxResults) throws JSONException, HttpException, BadRequestParamException {
        try {
            ValidateUtil.validate(projectId, daysPrevious, periodName, maxResults);
            ResponsePromise responsePromise = asyncChartRestClient.getTestsCreated(projectId, daysPrevious, periodName, maxResults);
            Response response = responsePromise.claim();
            return httpResponseParser.parseJsonResponse(response);
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
    public JSONObject getExecutionCreated(Long projectId, Long daysPrevious, String periodName) throws JSONException, HttpException, BadRequestParamException {
        try {
            ValidateUtil.validate(projectId, daysPrevious, periodName);
            ResponsePromise responsePromise = asyncChartRestClient.getExecutionCreated(projectId, daysPrevious, periodName);
            Response response = responsePromise.claim();
            return httpResponseParser.parseJsonResponse(response);
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
}
