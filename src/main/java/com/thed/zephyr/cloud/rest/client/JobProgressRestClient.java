package com.thed.zephyr.cloud.rest.client;

import org.apache.http.HttpException;
import org.codehaus.jettison.json.JSONObject;

import java.util.function.Function;

/**
 * Created by Kavya on 01-03-2016.
 */
public interface JobProgressRestClient {
    JSONObject getJobProgress(String jobProgressToken) throws HttpException;
    JSONObject getJobProgressAsync(String jobProgressToken, Function onRedeemFunction, Function onErrorFunction);
}
