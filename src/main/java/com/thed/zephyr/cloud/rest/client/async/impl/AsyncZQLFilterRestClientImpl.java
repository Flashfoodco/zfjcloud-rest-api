package com.thed.zephyr.cloud.rest.client.async.impl;

import com.atlassian.httpclient.api.ResponsePromise;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import com.thed.zephyr.cloud.rest.client.async.AsyncZQLFilterRestClient;
import com.thed.zephyr.cloud.rest.client.async.GenericEntityBuilder;
import com.thed.zephyr.cloud.rest.constant.ApplicationConstants;
import com.thed.zephyr.cloud.rest.model.ZQLFilter;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * Created by Kavya on 28-06-2016.
 */
public class AsyncZQLFilterRestClientImpl implements AsyncZQLFilterRestClient {

    private final URI baseUri;

    private final DisposableHttpClient httpClient;

    public AsyncZQLFilterRestClientImpl(URI baseUri, DisposableHttpClient httpClient) {
        this.baseUri = baseUri;
        this.httpClient = httpClient;
    }

    @Override
    public ResponsePromise getFilter(String filterId) {
        URI uri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_FILTER).path(filterId).build();
        return httpClient.newRequest(uri).get();
    }

    @Override
    public ResponsePromise getFilters(boolean byUser, boolean selectFavourite, int offset, int maxRecords) {
        URI uri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_FILTERS)
                .queryParam("byUser", byUser)
                .queryParam("fav", selectFavourite)
                .queryParam(ApplicationConstants.QUERY_PARAM_OFFSET, offset)
                .queryParam(ApplicationConstants.QUERY_PARAM_MAX_RECORDS, maxRecords).build();
        return httpClient.newRequest(uri).get();
    }

    @Override
    public ResponsePromise quickSearch(String query) {
        URI uri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_FILTERS)
                .path(ApplicationConstants.URL_PATH_QUICKSEARCH)
                .queryParam(ApplicationConstants.QUERY_PARAM_QUERY, query).build();
        return httpClient.newRequest(uri).get();
    }

    @Override
    public ResponsePromise search(String name, String owner, String sharePerm) {
        URI uri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_FILTERS)
                .path(ApplicationConstants.URL_PATH_SEARCH).build();
        return httpClient.newRequest(uri).get();
    }

    @Override
    public ResponsePromise deleteFilter(String filterId) {
        URI uri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_FILTER).path(filterId).build();
        return httpClient.newRequest(uri).delete();
    }

    @Override
    public ResponsePromise saveFilter(ZQLFilter zqlFilter) {
        URI uri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_FILTER).build();
        return httpClient.newRequest(uri).setEntity(new GenericEntityBuilder(zqlFilter)).setAccept("application/json").post();
    }

    @Override
    public ResponsePromise updateFilter(String filterId, ZQLFilter zqlFilter) {
        URI uri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_FILTER).path(filterId).build();
        return httpClient.newRequest(uri).setEntity(new GenericEntityBuilder(zqlFilter)).setAccept("application/json").put();
    }

    @Override
    public ResponsePromise copyFilter(ZQLFilter zqlFilter) {
        URI uri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_FILTER).path(ApplicationConstants.URL_PATH_COPY).build();
        return httpClient.newRequest(uri).setEntity(new GenericEntityBuilder(zqlFilter)).setAccept("application/json").put();
    }

    @Override
    public ResponsePromise toggleFavorite(String filterId) {
        URI uri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_FILTER).path(filterId).path(ApplicationConstants.URL_PATH_FAVORITE).build();
        return httpClient.newRequest(uri).put();
    }
}
