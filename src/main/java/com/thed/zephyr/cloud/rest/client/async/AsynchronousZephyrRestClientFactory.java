package com.thed.zephyr.cloud.rest.client.async;

import com.atlassian.jira.rest.client.api.AuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousHttpClientFactory;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import com.thed.zephyr.cloud.rest.client.ZephyrRestClient;
import com.thed.zephyr.cloud.rest.client.ZephyrRestClientFactory;

import java.net.URI;

/**
 * Created by Kavya on 18-02-2016.
 */
public class AsynchronousZephyrRestClientFactory implements ZephyrRestClientFactory {
    @Override
    public ZephyrRestClient create(URI serverUri, final AuthenticationHandler authenticationHandler) {
        final DisposableHttpClient httpClient = new AsynchronousHttpClientFactory()
                .createClient(serverUri, authenticationHandler);
        return new AsynchronousZephyrRestClient(serverUri, httpClient);
    }
}
