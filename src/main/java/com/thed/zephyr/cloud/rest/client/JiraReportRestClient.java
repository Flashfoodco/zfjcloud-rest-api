package com.thed.zephyr.cloud.rest.client;

import com.thed.zephyr.cloud.rest.exception.BadRequestParamException;
import org.apache.http.HttpException;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Created by Kavya on 23-06-2016.
 */
public interface JiraReportRestClient {

    JSONObject getExecutionCount(Long projectId, String groupFld, String periodName, Long versionId, String cycleName) throws JSONException, HttpException, BadRequestParamException;
    JSONObject getTopDefects(Long projectId, Long versionId, String issueStatuses, Integer howMany) throws JSONException, HttpException, BadRequestParamException;
    JSONObject getTestDistributionCount(Long projectId, Long versionId, String groupFld) throws JSONException, HttpException, BadRequestParamException;
}
