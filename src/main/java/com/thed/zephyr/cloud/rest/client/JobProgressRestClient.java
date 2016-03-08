package com.thed.zephyr.cloud.rest.client;

import org.apache.http.HttpException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Created by Kavya on 01-03-2016.
 */
public interface JobProgressRestClient {
    JSONObject getJobProgress(String jobProgressToken) throws HttpException;
}
