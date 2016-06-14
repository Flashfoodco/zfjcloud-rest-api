package com.thed.zephyr.cloud.rest.client.impl;

import com.atlassian.httpclient.api.Response;
import com.atlassian.httpclient.api.ResponsePromise;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.thed.zephyr.cloud.rest.client.JobProgressRestClient;
import com.thed.zephyr.cloud.rest.client.TeststepRestClient;
import com.thed.zephyr.cloud.rest.client.async.AsyncTeststepRestClient;
import com.thed.zephyr.cloud.rest.client.async.impl.AsyncTeststepRestClientImpl;
import com.thed.zephyr.cloud.rest.exception.BadRequestParamException;
import com.thed.zephyr.cloud.rest.model.Teststep;
import com.thed.zephyr.cloud.rest.util.HttpResponseParser;
import com.thed.zephyr.cloud.rest.util.ValidateUtil;
import com.thed.zephyr.cloud.rest.util.ZFJConnectResults;
import com.thed.zephyr.cloud.rest.util.json.TeststepJsonParser;
import org.apache.http.HttpException;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kavya on 13-06-2016.
 */
public class TeststepRestClientImpl implements TeststepRestClient {

    private AsyncTeststepRestClient asyncTeststepRestClient;

    private JsonObjectParser<Teststep> teststepJsonObjectParser;

    private HttpResponseParser httpResponseParser;

    private JobProgressRestClient jobProgressRestClient;

    Logger log = LoggerFactory.getLogger(TeststepRestClientImpl.class);

    public TeststepRestClientImpl(URI baseUri, DisposableHttpClient httpClient) {
        httpResponseParser = new HttpResponseParser();
        this.teststepJsonObjectParser = new TeststepJsonParser();
        this.jobProgressRestClient = new JobProgressRestClientImpl(baseUri, httpClient);
        asyncTeststepRestClient = new AsyncTeststepRestClientImpl(baseUri, httpClient);
    }

    @Override
    public Teststep createTeststep(Long projectId, Long issueId, Teststep teststep) throws JSONException, HttpException, BadRequestParamException {
        return createTeststep(projectId, issueId, teststep, teststepJsonObjectParser);
    }

    @Override
    public <T> T createTeststep(Long projectId, Long issueId, Teststep teststep, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException {
        try {
            ValidateUtil.validate(projectId, issueId);
            ResponsePromise responsePromise = asyncTeststepRestClient.createTeststep(projectId, issueId, teststep);
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
            log.error("Error in request", exception);
            throw exception;
        }
    }

    @Override
    public ZFJConnectResults<Teststep> cloneTeststep(Long projectId, Long issueId, String teststepId, int position, Teststep teststep) throws BadRequestParamException, JSONException, HttpException {
        return cloneTeststep(projectId, issueId, teststepId, position, teststep, teststepJsonObjectParser);
    }

    @Override
    public <T> ZFJConnectResults<T> cloneTeststep(Long projectId, Long issueId, String teststepId, int position, Teststep teststep, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException {
        try {
            ValidateUtil.validate(projectId, issueId, teststepId, position);
            ResponsePromise responsePromise = asyncTeststepRestClient.cloneTeststep(projectId, issueId, teststepId, position, teststep);
            Response response = responsePromise.claim();
            JSONArray jsonResponse = httpResponseParser.parseJsonArrayResponse(response);
            List<T> resultList = parseTeststepArray(jsonResponse, jsonParser);
            ZFJConnectResults<T> results = new ZFJConnectResults<T>(resultList, 0, resultList.size(), resultList.size());
            return results;
        } catch (JSONException exception) {
            log.error("Error during parse response from server.", exception);
            throw exception;
        } catch (HttpException exception) {
            log.error("Http error from server.", exception);
            throw exception;
        } catch (BadRequestParamException exception) {
            log.error("Error in request", exception);
            throw exception;
        }
    }

    @Override
    public Teststep getTeststep(Long projectId, Long issueId, String teststepId) throws BadRequestParamException, JSONException, HttpException {
        return getTeststep(projectId, issueId, teststepId, teststepJsonObjectParser);
    }

    @Override
    public <T> T getTeststep(Long projectId, Long issueId, String teststepId, JsonObjectParser<T> jsonParser) throws BadRequestParamException, JSONException, HttpException {
       try {
           ValidateUtil.validate(projectId, issueId, teststepId);
           ResponsePromise responsePromise = asyncTeststepRestClient.getTeststep(projectId, issueId, teststepId);
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
           log.error("Error in request", exception);
           throw exception;
       }
    }

    @Override
    public ZFJConnectResults<Teststep> getTeststeps(Long projectId, Long issueId) throws BadRequestParamException, JSONException, HttpException {
        return getTeststeps(projectId, issueId, teststepJsonObjectParser);
    }

    @Override
    public <T> ZFJConnectResults<T> getTeststeps(Long projectId, Long issueId, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException {
        try {
            ValidateUtil.validate(projectId, issueId);
            ResponsePromise responsePromise = asyncTeststepRestClient.getTeststeps(projectId, issueId);
            Response response = responsePromise.claim();
            JSONArray jsonResponse = httpResponseParser.parseJsonArrayResponse(response);
            List<T> resultList = parseTeststepArray(jsonResponse, jsonParser);
            ZFJConnectResults<T> results = new ZFJConnectResults<T>(resultList, 0, resultList.size(), resultList.size());
            return results;
        } catch (JSONException exception) {
            log.error("Error during parse response from server.", exception);
            throw exception;
        } catch (HttpException exception) {
            log.error("Http error from server.", exception);
            throw exception;
        } catch (BadRequestParamException exception) {
            log.error("Error in request", exception);
            throw exception;
        }
    }

    @Override
    public Teststep updateTeststep(Long projectId, Long issueId, String teststepId, Teststep teststep) throws BadRequestParamException, JSONException, HttpException {
        return updateTeststep(projectId, issueId, teststepId, teststep, teststepJsonObjectParser);
    }

    @Override
    public <T> T updateTeststep(Long projectId, Long issueId, String teststepId, Teststep teststep, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException {
        try {
            ValidateUtil.validate(projectId, issueId, teststepId);
            ResponsePromise responsePromise = asyncTeststepRestClient.updateTeststep(projectId, issueId, teststepId, teststep);
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
            log.error("Error in request", exception);
            throw exception;
        }
    }

    @Override
    public ZFJConnectResults<Teststep> deleteTeststep(Long projectId, Long issueId, String teststepId) throws BadRequestParamException, JSONException, HttpException {
        return deleteTeststep(projectId, issueId, teststepId, teststepJsonObjectParser);
    }

    @Override
    public <T> ZFJConnectResults<T> deleteTeststep(Long projectId, Long issueId, String teststepId, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException {
        try {
            ValidateUtil.validate(projectId, issueId, teststepId);
            ResponsePromise responsePromise = asyncTeststepRestClient.deleteTeststep(projectId, issueId, teststepId);
            Response response = responsePromise.claim();
            JSONArray jsonResponse = httpResponseParser.parseJsonArrayResponse(response);
            List<T> resultList = parseTeststepArray(jsonResponse, jsonParser);
            ZFJConnectResults<T> results = new ZFJConnectResults<T>(resultList, 0, resultList.size(), resultList.size());
            return results;
        } catch (JSONException exception) {
            log.error("Error during parse response from server.", exception);
            throw exception;
        } catch (HttpException exception) {
            log.error("Http error from server.", exception);
            throw exception;
        } catch (BadRequestParamException exception) {
            log.error("Error in request", exception);
            throw exception;
        }
    }

    @Override
    public ZFJConnectResults<Teststep> moveTeststep(Long projectId, Long issueId, String teststepId) throws BadRequestParamException, JSONException, HttpException {
        return moveTeststep(projectId, issueId, teststepId, teststepJsonObjectParser);
    }

    @Override
    public <T> ZFJConnectResults<T> moveTeststep(Long projectId, Long issueId, String teststepId, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException {
        try {
            ValidateUtil.validate(projectId, issueId, teststepId);
            ValidateUtil.validate(projectId, issueId);
            ResponsePromise responsePromise = asyncTeststepRestClient.getTeststeps(projectId, issueId);
            Response response = responsePromise.claim();
            JSONArray jsonResponse = httpResponseParser.parseJsonArrayResponse(response);
            List<T> resultList = parseTeststepArray(jsonResponse, jsonParser);
            ZFJConnectResults<T> results = new ZFJConnectResults<T>(resultList, 0, resultList.size(), resultList.size());
            return results;
        } catch (JSONException exception) {
            log.error("Error during parse response from server.", exception);
            throw exception;
        } catch (HttpException exception) {
            log.error("Http error from server.", exception);
            throw exception;
        } catch (BadRequestParamException exception) {
            log.error("Error in request", exception);
            throw exception;
        }
    }


    private <T> List<T> parseTeststepArray(JSONArray jsonArray, JsonObjectParser<T> parser) throws JSONException{
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
