package com.thed.zephyr.cloud.rest.client;

import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.thed.zephyr.cloud.rest.exception.BadRequestParamException;
import com.thed.zephyr.cloud.rest.model.StepResult;
import org.apache.http.HttpException;
import org.codehaus.jettison.json.JSONException;

import java.util.List;
import java.util.Map;

/**
 * Created by Kavya on 20-06-2016.
 */
public interface StepResultRestClient {

    public abstract List<StepResult> getStepResults(Long projectId, Long issueId, String executionId) throws BadRequestParamException, JSONException, HttpException;
    public abstract <T> List<T> getSteResults(Long projectId, Long issueId, String executionId, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException;

    public abstract StepResult getStepResult(Long projectId, String executionId, String stepResultId) throws BadRequestParamException, JSONException, HttpException;
    public abstract <T> T getStepResult(Long projectId, String executionId, String stepResultId, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException;

    public abstract StepResult updateStepResult(Long projectId, String stepResultId, StepResult stepResult) throws BadRequestParamException, JSONException, HttpException;
    public abstract <T> T updateStepResult(Long projectId, String stepResultId, StepResult stepResult, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException;

    public abstract Map getStepDefectsCountByExecutionId(Long projectId, String executionId) throws JSONException, HttpException, BadRequestParamException;
}
