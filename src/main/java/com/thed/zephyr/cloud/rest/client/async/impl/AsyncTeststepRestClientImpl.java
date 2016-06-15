package com.thed.zephyr.cloud.rest.client.async.impl;

import com.atlassian.httpclient.api.ResponsePromise;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thed.zephyr.cloud.rest.client.async.AsyncTeststepRestClient;
import com.thed.zephyr.cloud.rest.client.async.GenericEntityBuilder;
import com.thed.zephyr.cloud.rest.constant.ApplicationConstants;
import com.thed.zephyr.cloud.rest.model.Teststep;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Map;

/**
 * Created by Kavya on 13-06-2016.
 */
public class AsyncTeststepRestClientImpl implements AsyncTeststepRestClient {

    private final URI baseUri;

    private final DisposableHttpClient httpClient;

    public AsyncTeststepRestClientImpl(URI baseUri, DisposableHttpClient httpClient) {
        this.baseUri = baseUri;
        this.httpClient = httpClient;
    }

    @Override
    public ResponsePromise createTeststep(Long projectId, Long issueId, Teststep teststep) {
        final URI createTeststepUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_TESTSTEP).path(String.valueOf(issueId)).queryParam(ApplicationConstants.QUERY_PARAM_PROJECT_ID,String.valueOf(projectId)).build();
        return httpClient.newRequest(createTeststepUri).setEntity(new GenericEntityBuilder(teststep)).setAccept("application/json").post();
    }

    @Override
    public ResponsePromise cloneTeststep(Long projectId, Long issueId, String teststepId, int position, Teststep teststep) {
        Map teststepMap = new ObjectMapper().convertValue(teststep, Map.class);
        if (position != 0) teststepMap.put("position", position);
        final URI cloneTeststepUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_TESTSTEP).path(String.valueOf(issueId)).path(ApplicationConstants.URL_PATH_CLONE).path(teststepId).queryParam(ApplicationConstants.QUERY_PARAM_PROJECT_ID,String.valueOf(projectId)).build();
        return httpClient.newRequest(cloneTeststepUri).setEntity(new GenericEntityBuilder(teststepMap)).setAccept("application/json").post();
    }

    @Override
    public ResponsePromise getTeststep(Long projectId, Long issueId, String teststepId) {
        final URI getTeststepUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_TESTSTEP).path(String.valueOf(issueId)).path(teststepId).queryParam(ApplicationConstants.QUERY_PARAM_PROJECT_ID,String.valueOf(projectId)).build();
        return httpClient.newRequest(getTeststepUri).get();
    }

    @Override
    public ResponsePromise getTeststeps(Long projectId, Long issueId) {
        final URI getTeststepsUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_TESTSTEP).path(String.valueOf(issueId)).queryParam(ApplicationConstants.QUERY_PARAM_PROJECT_ID,String.valueOf(projectId)).build();
        return httpClient.newRequest(getTeststepsUri).get();
    }

    @Override
    public ResponsePromise updateTeststep(Long projectId, Long issueId, String teststepId, Teststep teststep) {
        final URI updateTeststepUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_TESTSTEP).path(String.valueOf(issueId)).path(teststepId).queryParam(ApplicationConstants.QUERY_PARAM_PROJECT_ID,String.valueOf(projectId)).build();
        return httpClient.newRequest(updateTeststepUri).setEntity(new GenericEntityBuilder(teststep)).setAccept("application/json").put();
    }

    @Override
    public ResponsePromise deleteTeststep(Long projectId, Long issueId, String teststepId) {
        final URI deleteTeststepUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_TESTSTEP).path(String.valueOf(issueId)).path(teststepId).queryParam(ApplicationConstants.QUERY_PARAM_PROJECT_ID,String.valueOf(projectId)).build();
        return httpClient.newRequest(deleteTeststepUri).delete();
    }
}
