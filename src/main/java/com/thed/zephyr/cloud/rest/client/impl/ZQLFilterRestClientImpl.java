package com.thed.zephyr.cloud.rest.client.impl;

import com.atlassian.httpclient.api.Response;
import com.atlassian.httpclient.api.ResponsePromise;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.thed.zephyr.cloud.rest.client.ZQLFiletrRestClient;
import com.thed.zephyr.cloud.rest.client.async.AsyncZQLFilterRestClient;
import com.thed.zephyr.cloud.rest.client.async.impl.AsyncZQLFilterRestClientImpl;
import com.thed.zephyr.cloud.rest.exception.BadRequestParamException;
import com.thed.zephyr.cloud.rest.model.ZQLFilter;
import com.thed.zephyr.cloud.rest.util.HttpResponseParser;
import com.thed.zephyr.cloud.rest.util.ValidateUtil;
import com.thed.zephyr.cloud.rest.util.json.ZQLFilterJsonParser;
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
 * Created by Kavya on 28-06-2016.
 */
public class ZQLFilterRestClientImpl implements ZQLFiletrRestClient {

    Logger log = LoggerFactory.getLogger(ZQLFilterRestClientImpl.class);

    AsyncZQLFilterRestClient asyncZQLFilterRestClient;

    HttpResponseParser httpResponseParser;

    JsonObjectParser<ZQLFilter> zqlFilterJsonObjectParser;

    public ZQLFilterRestClientImpl(URI baseUri, DisposableHttpClient httpClient) {
        httpResponseParser = new HttpResponseParser();
        asyncZQLFilterRestClient = new AsyncZQLFilterRestClientImpl(baseUri, httpClient);
        zqlFilterJsonObjectParser = new ZQLFilterJsonParser();
    }

    @Override
    public ZQLFilter getFilter(String filterId) throws BadRequestParamException, JSONException, HttpException {
        return getFilter(filterId, zqlFilterJsonObjectParser);
    }

    @Override
    public <T> T getFilter(String filterId, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException {
        try {
            ValidateUtil.validate(filterId);
            ResponsePromise responsePromise = asyncZQLFilterRestClient.getFilter(filterId);
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
    public List<ZQLFilter> getFilters(boolean byUser, boolean fav, int offset, int maxRecords) throws HttpException, JSONException {
        return getFilters(byUser, fav, offset, maxRecords, zqlFilterJsonObjectParser);
    }

    @Override
    public <T> List<T> getFilters(boolean byUser, boolean fav, int offset, int maxRecords, JsonObjectParser<T> jsonParser) throws HttpException, JSONException {
        try {
            ResponsePromise responsePromise = asyncZQLFilterRestClient.getFilters(byUser, fav, offset, maxRecords);
            Response response = responsePromise.claim();
            JSONArray jsonArrayResponse = httpResponseParser.parseJsonArrayResponse(response);
            return parseZQLFilterArrayResponse(jsonArrayResponse, jsonParser);
        } catch (HttpException exception) {
            log.error("Http error from server.", exception);
            throw exception;
        } catch (JSONException exception) {
            log.error("Error during parse response from server.", exception);
            throw exception;
        }
    }

    @Override
    public List<ZQLFilter> quickSearch(String query) throws HttpException, JSONException {
        return quickSearch(query, zqlFilterJsonObjectParser);
    }

    @Override
    public <T> List<T> quickSearch(String query, JsonObjectParser<T> jsonParser) throws HttpException, JSONException {
        try {
            ResponsePromise responsePromise = asyncZQLFilterRestClient.quickSearch(query);
            Response response = responsePromise.claim();
            JSONArray jsonArrayResponse = httpResponseParser.parseJsonArrayResponse(response);
            return parseZQLFilterArrayResponse(jsonArrayResponse, jsonParser);
        } catch (HttpException exception) {
            log.error("Http error from server.", exception);
            throw exception;
        } catch (JSONException exception) {
            log.error("Error during parse response from server.", exception);
            throw exception;
        }
    }

    @Override
    public List<ZQLFilter> search(String name, String owner, String sharePerm) throws HttpException, JSONException {
        return search(name, owner, sharePerm, zqlFilterJsonObjectParser);
    }

    @Override
    public <T> List<T> search(String name, String owner, String sharePerm, JsonObjectParser<T> jsonParser) throws HttpException, JSONException {
        try {
            ResponsePromise responsePromise = asyncZQLFilterRestClient.search(name, owner, sharePerm);
            Response response = responsePromise.claim();
            JSONArray jsonArrayResponse = httpResponseParser.parseJsonArrayResponse(response);
            return parseZQLFilterArrayResponse(jsonArrayResponse, jsonParser);
        } catch (HttpException exception) {
            log.error("Http error from server.", exception);
            throw exception;
        } catch (JSONException exception) {
            log.error("Error during parse response from server.", exception);
            throw exception;
        }
    }

    @Override
    public boolean deleteFilter(String filterId) throws HttpException, BadRequestParamException {
        try {
            ValidateUtil.validate(filterId);
            ResponsePromise responsePromise = asyncZQLFilterRestClient.deleteFilter(filterId);
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
    public ZQLFilter saveFilter(ZQLFilter zqlFilter) throws BadRequestParamException, JSONException, HttpException {
        return saveFilter(zqlFilter, zqlFilterJsonObjectParser);
    }

    @Override
    public <T> T saveFilter(ZQLFilter zqlFilter, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException {
        try {
            ValidateUtil.validate(zqlFilter.name, zqlFilter.zql);
            ResponsePromise responsePromise = asyncZQLFilterRestClient.saveFilter(zqlFilter);
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
    public ZQLFilter updateFilter(String filterId, ZQLFilter zqlFilter) throws BadRequestParamException, JSONException, HttpException {
        return updateFilter(filterId, zqlFilter, zqlFilterJsonObjectParser);
    }

    @Override
    public <T> T updateFilter(String filterId, ZQLFilter zqlFilter, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException {
        try {
            ValidateUtil.validate(filterId, zqlFilter.name, zqlFilter.zql);
            ResponsePromise responsePromise = asyncZQLFilterRestClient.updateFilter(filterId, zqlFilter);
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
    public ZQLFilter copyFilter(ZQLFilter zqlFilter) throws BadRequestParamException, JSONException, HttpException {
        return copyFilter(zqlFilter, zqlFilterJsonObjectParser);
    }

    @Override
    public <T> T copyFilter(ZQLFilter zqlFilter, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException {
        try {
            ValidateUtil.validate(zqlFilter.id, zqlFilter.name, zqlFilter.zql);
            ResponsePromise responsePromise = asyncZQLFilterRestClient.copyFilter(zqlFilter);
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
    public ZQLFilter toggleFavorite(String filterId) throws BadRequestParamException, JSONException, HttpException {
        return toggleFavorite(filterId, zqlFilterJsonObjectParser);
    }

    @Override
    public <T> T toggleFavorite(String filterId, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException {
        try {
            ValidateUtil.validate(filterId);
            ResponsePromise responsePromise = asyncZQLFilterRestClient.toggleFavorite(filterId);
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

    private  <T> List<T> parseZQLFilterArrayResponse(JSONArray jsonArray, JsonObjectParser<T> parser) throws JSONException {
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
