package com.thed.zephyr.cloud.rest.client;

import com.thed.zephyr.cloud.rest.exception.BadRequestParamException;
import org.apache.http.HttpException;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Created by Kavya on 21-06-2016.
 */
public interface ChartRestClient {

    public abstract JSONObject getTestsCreated(Long projectId, Long daysPrevious, String periodName, Integer maxResults) throws JSONException, HttpException, BadRequestParamException;
    public abstract JSONObject getExecutionCreated(Long projectId, Long daysPrevious, String periodName) throws JSONException, HttpException, BadRequestParamException;
}
