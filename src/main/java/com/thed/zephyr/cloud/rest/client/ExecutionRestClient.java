package com.thed.zephyr.cloud.rest.client;

import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.thed.zephyr.cloud.rest.client.model.Execution;
import com.thed.zephyr.cloud.rest.client.util.ZFJConnectResults;
import org.apache.http.HttpException;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.File;
import java.util.List;

/**
 * Created by Kavya on 18-02-2016.
 */
public interface ExecutionRestClient {

    Execution createExecution(Execution execution) throws JSONException, HttpException;
    <T> T createExecution(Execution execution, JsonObjectParser<T> parser) throws JSONException, HttpException;

    Execution getExecution(Long projectId, Long issueId, String executionId) throws JSONException, HttpException;
    <T> T getExecution(Long projectId, Long issueId, String executionId, JsonObjectParser<T> parser) throws JSONException, HttpException;

    Execution updateExecution(Execution execution) throws JSONException, HttpException;
    <T> T updateExecution(Execution execution, JsonObjectParser<T> parser) throws JSONException, HttpException;

    Boolean deleteExecution(Long projectId, Long issueId, String executionId) throws JSONException, HttpException;

    ZFJConnectResults<Execution> getExecutions(Long projectId, Long issueId, int offset, int size) throws JSONException, HttpException;
    <T> ZFJConnectResults<T> getExecutions(Long projectId, Long issueId, int offset, int size, JsonObjectParser<T> parser) throws JSONException, HttpException;



    public abstract JSONObject getExecutionsByCycle(Long projectId, Long versionId, String cycleId) throws JSONException, HttpException;
    public abstract String addTestsToCycle(Long projectId, Long versionId, String cycleId, List<Long> issueIds) throws JSONException, HttpException;
    public abstract File exportExecution(String exportType, List<String> executionIds, String zqlQuery) throws JSONException, HttpException;
    public abstract File downloadExportedFile(String fileName) throws JSONException, HttpException;
    public abstract String bulkUpdateStatus(List<String> executionIds, Integer statusId, Integer stepStatusId, Boolean testStepStatusChangeFlag, Boolean clearDefectMappingFlag) throws JSONException, HttpException;
    public abstract String bulkDeleteExecutions(List<String> executionIds) throws JSONException, HttpException;
    //public abstract JSONObject getExecutionSummaryOfIssuesBySprint(Long sprintId, List<Long> issueIds) throws JSONException, HttpException;
    //public abstract JSONObject getExecutionsByIssue(Long issueId, Integer offset, Integer maxRecords) throws JSONException, HttpException;

}
