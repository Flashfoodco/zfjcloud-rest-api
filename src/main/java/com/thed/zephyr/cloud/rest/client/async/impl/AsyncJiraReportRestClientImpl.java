package com.thed.zephyr.cloud.rest.client.async.impl;

import com.atlassian.httpclient.api.ResponsePromise;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import com.thed.zephyr.cloud.rest.client.async.AsyncJiraReportRestClient;
import com.thed.zephyr.cloud.rest.constant.ApplicationConstants;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * Created by Kavya on 23-06-2016.
 */
public class AsyncJiraReportRestClientImpl implements AsyncJiraReportRestClient {

    private final URI baseUri;

    private final DisposableHttpClient httpClient;

    public AsyncJiraReportRestClientImpl(URI baseUri, DisposableHttpClient httpClient) {
        this.baseUri = baseUri;
        this.httpClient = httpClient;
    }

    @Override
    public ResponsePromise getExecutionCount(Long projectId, String groupFld, String periodName, Long versionId, String cycleName) {
        final URI getExecutionCountUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_CYCLE).build();
        return null;
    }

    @Override
    public ResponsePromise getTopDefects(Long projectId, Long versionId, String issueStatuses, Integer howMany) {
        return null;
    }

    @Override
    public ResponsePromise getTestDistributionCount(Long projectId, Long versionId, String groupFld) {
        return null;
    }
}
