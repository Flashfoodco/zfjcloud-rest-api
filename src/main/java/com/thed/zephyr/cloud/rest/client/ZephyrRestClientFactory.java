package com.thed.zephyr.cloud.rest.client;

import com.atlassian.jira.rest.client.api.AuthenticationHandler;

import java.net.URI;

/**
 * Created by Kavya on 18-02-2016.
 */
public interface ZephyrRestClientFactory {

    public abstract ZephyrRestClient create(URI serverUri, final AuthenticationHandler authenticationHandler);
}
