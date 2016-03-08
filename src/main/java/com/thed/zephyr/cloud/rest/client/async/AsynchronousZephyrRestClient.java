package com.thed.zephyr.cloud.rest.client.async;

import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import com.thed.zephyr.cloud.rest.client.CycleRestClient;
import com.thed.zephyr.cloud.rest.client.ExecutionRestClient;
import com.thed.zephyr.cloud.rest.client.JobProgressRestClient;
import com.thed.zephyr.cloud.rest.client.ZephyrRestClient;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

/**
 * Created by Kavya on 18-02-2016.
 */
public class AsynchronousZephyrRestClient implements ZephyrRestClient {

    private final DisposableHttpClient httpClient;
    private final ExecutionRestClient executionRestClient;
    private final CycleRestClient cycleRestClient;
    private final JobProgressRestClient jobProgressRestClient;

    public AsynchronousZephyrRestClient(final URI serverUri, final DisposableHttpClient httpClient) {
        final URI baseUri = UriBuilder.fromUri(serverUri).path("/public/rest/api/1.0").build();
        this.httpClient = httpClient;
        this.executionRestClient = new AsynchronousExecutionRestClient(baseUri, httpClient);
        this.cycleRestClient = new AsynchronousCycleRestClient(baseUri, httpClient);
        this.jobProgressRestClient = new AsynchronousJobProgressRestClient(baseUri, httpClient);
    }

    @Override
    public ExecutionRestClient getExecutionRestClient() {
        return executionRestClient;
    }

    @Override
    public CycleRestClient getCycleRestClient() {
        return cycleRestClient;
    }

    @Override
    public JobProgressRestClient getJobProgressRestClient() {
        return jobProgressRestClient;
    }

    @Override
    public void close() throws IOException {

    }
}
