package com.thed.zephyr.cloud.rest.client.async;

import org.apache.http.client.HttpClient;

/**
 * Created by Kavya on 04-03-2016.
 */
public interface DisposableHttpClient extends HttpClient {
    void destroy() throws Exception;
}
