package com.thed.zephyr.cloud.rest.client.impl;

import com.atlassian.httpclient.api.Response;
import com.atlassian.httpclient.api.ResponsePromise;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import com.thed.zephyr.cloud.rest.client.JiraReportRestClient;
import com.thed.zephyr.cloud.rest.client.async.AsyncJiraReportRestClient;
import com.thed.zephyr.cloud.rest.client.async.impl.AsyncJiraReportRestClientImpl;
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
 * Created by Kavya on 23-06-2016.
 */

public class JiraReportRestClientImpl implements JiraReportRestClient {

    Logger log = LoggerFactory.getLogger(JiraReportRestClientImpl.class);

    AsyncJiraReportRestClient asyncJiraReportRestClient;

    HttpResponseParser httpResponseParser;

    public JiraReportRestClientImpl(URI baseUri, DisposableHttpClient httpClient) {
        asyncJiraReportRestClient = new AsyncJiraReportRestClientImpl(baseUri, httpClient);
        httpResponseParser = new HttpResponseParser();
    }

    @Override
    public JSONObject getExecutionCount(Long projectId, String groupFld, String periodName, Long versionId, String cycleName) throws JSONException, HttpException, BadRequestParamException {
        try {
            ValidateUtil.validate(projectId, groupFld, versionId);
            ResponsePromise responsePromise = asyncJiraReportRestClient.getExecutionCount(projectId, groupFld, periodName, versionId, cycleName);
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
    public JSONObject getTopDefects(Long projectId, Long versionId, String issueStatuses, Integer howMany) throws JSONException, HttpException, BadRequestParamException {
        try {
            ValidateUtil.validate(projectId, versionId, issueStatuses, howMany);
            ResponsePromise responsePromise = asyncJiraReportRestClient.getTopDefects(projectId, versionId, issueStatuses, howMany);
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
    public JSONObject getTestDistributionCount(Long projectId, Long versionId, String groupFld) throws JSONException, HttpException, BadRequestParamException {
        try {
            ValidateUtil.validate(projectId, versionId, groupFld);
            ResponsePromise responsePromise = asyncJiraReportRestClient.getTestDistributionCount(projectId, versionId, groupFld);
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
