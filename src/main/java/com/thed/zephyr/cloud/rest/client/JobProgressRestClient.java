package com.thed.zephyr.cloud.rest.client;

import com.google.common.util.concurrent.FutureCallback;
import com.thed.zephyr.cloud.rest.exception.JobProgressException;
import com.thed.zephyr.cloud.rest.model.JobProgress;
import org.apache.http.HttpException;
import org.codehaus.jettison.json.JSONObject;

import java.util.function.Function;

/**
 * Created by Kavya on 01-03-2016.
 */
public interface JobProgressRestClient {
    JobProgress getJobProgress(String jobProgressToken) throws JobProgressException;
}
