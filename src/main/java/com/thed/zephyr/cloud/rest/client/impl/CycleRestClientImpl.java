package com.thed.zephyr.cloud.rest.client.impl;

import com.atlassian.httpclient.api.Response;
import com.atlassian.httpclient.api.ResponsePromise;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.thed.zephyr.cloud.rest.client.CycleRestClient;
import com.thed.zephyr.cloud.rest.client.JobProgressRestClient;
import com.thed.zephyr.cloud.rest.client.async.AsyncCycleRestClient;
import com.thed.zephyr.cloud.rest.client.async.impl.AsyncCycleRestClientImpl;
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
    public Cycle createCycle(Cycle cycle) throws JSONException, HttpException {
        return createCycle(cycle, cycleJsonObjectParser);
    }

    @Override
    public <T> T createCycle(Cycle cycle, JsonObjectParser<T> jsonParser) throws JSONException, HttpException {
        try {
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
        }
    }

    @Override
    public Cycle cloneCycle(String clonedCycleId, Cycle cycle) throws JSONException, HttpException {
        return cloneCycle(clonedCycleId, cycle, cycleJsonObjectParser);
    }

    @Override
    public <T> T cloneCycle(String clonedCycleId, Cycle cycle, JsonObjectParser<T> jsonParser) throws JSONException, HttpException {
        try {
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
        }
    }

    @Override
    public Cycle getCycle(Long projectId, Long versionId, String cycleId) throws JSONException, HttpException {
        return getCycle(projectId, versionId, cycleId, cycleJsonObjectParser);
    }

    @Override
    public <T> T getCycle(Long projectId, Long versionId, String cycleId, JsonObjectParser<T> jsonParser) throws JSONException, HttpException {
        try {
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
        }
    }

    @Override
    public Cycle updateCycle(String cycleId, Cycle newCycle) throws JSONException, HttpException {
        return updateCycle(cycleId, newCycle, cycleJsonObjectParser);
    }

    @Override
    public <T> T updateCycle(String cycleId, Cycle newCycle, JsonObjectParser<T> jsonParser) throws JSONException, HttpException {
        try {
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
        }
    }

    @Override
    public Boolean deleteCycle(Long projectId, Long versionId, String cycleId) throws  HttpException {
        try {
            ResponsePromise responsePromise = asyncCycleRestClient.deleteCycle(projectId, versionId, cycleId);
            Response response = responsePromise.claim();
            return httpResponseParser.parseBooleanResponse(response);
        } catch (HttpException exception) {
            log.error("Http error from server.", exception);
            throw exception;
        }
    }

    @Override
    public List<Cycle> getCycles(Long projectId, Long versionId) throws JSONException, HttpException {
        return getCycles(projectId, versionId, cycleJsonObjectParser);
    }

    @Override
    public <T> List<T> getCycles(Long projectId, Long versionId, JsonObjectParser<T> parser) throws JSONException, HttpException {
        /*try {
            ResponsePromise responsePromise = asyncCycleRestClient.getCycles(projectId, versionId);
            Response response = responsePromise.claim();
            JSONObject jsonResponse = httpResponseParser.parseJsonResponse(response);
            return parseCycleArray(jsonResponse, parser);
        } catch (JSONException exception) {
            log.error("Error during parse response from server.", exception);
            throw exception;
        } catch (HttpException exception) {
            log.error("Http error from server.", exception);
            throw exception;
        }*/
        return null;
    }

    @Override
    public InputStream exportCycle(Long projectId, Long versionId, String cycleId, String exportType) throws JobProgressException, HttpException {
        try {
            ResponsePromise responsePromise = asyncCycleRestClient.exportCycle(projectId, versionId, cycleId, exportType);
            Response response = responsePromise.claim();
            String jobProgressTicket = httpResponseParser.parseStringResponse(response);
            JobProgress jobProgress = jobProgressRestClient.getJobProgress(jobProgressTicket);

            return downloadExportedFile(jobProgress.summaryMessage);
        } catch (HttpException exception) {
            log.error("Http error from server.", exception);
            throw exception;
        }
    }

    @Override
    public InputStream downloadExportedFile(String fileName) throws HttpException {
        ResponsePromise responsePromise = asyncCycleRestClient.downloadExportedFile(fileName);
        Response response = responsePromise.claim();
        InputStream inputStream = httpResponseParser.parseInputStrem(response);

        return inputStream;
    }

    @Override
    public JobProgress moveExecutionsToCycle(String cycleId, Long projectId, Long versionId, List<String> executionIds, Boolean clearDefectMappingFlag, Boolean clearStatusFlag) throws JobProgressException, HttpException {
        try {
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
        }
    }

    @Override
    public JobProgress copyExecutionsToCycle(String cycleId, Long projectId, Long versionId, List<String> executionIds, Boolean clearDefectMappingFlag, Boolean clearStatusFlag) throws JobProgressException, HttpException {
        try {
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
