package com.thed.zephyr.cloud.rest.client;

import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.thed.zephyr.cloud.rest.exception.JobProgressException;
import com.thed.zephyr.cloud.rest.model.Execution;
import com.thed.zephyr.cloud.rest.model.JobProgress;
import com.thed.zephyr.cloud.rest.model.enam.SortOrder;
import com.thed.zephyr.cloud.rest.util.ZFJConnectResults;
import org.apache.http.HttpException;
import org.codehaus.jettison.json.JSONException;

import java.io.InputStream;
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

    ZFJConnectResults<Execution> getExecutionsByCycle(Long projectId, Long versionId, String cycleId, int offset, int size, String sortBy, SortOrder sortOrder) throws JSONException, HttpException;
    <T> ZFJConnectResults<T> getExecutionsByCycle(Long projectId, Long versionId, String cycleId, int offset, int size, String sortBy, SortOrder sortOrder, JsonObjectParser<T> jsonParser) throws JSONException, HttpException;

    /*
    ZFJConnectResults<Execution> getExecutionsOfLinkedIssue(Long issueId, int offset, int size) throws  JSONException, HttpException;
    <T> ZFJConnectResults<T> getExecutionsOfLinkedIssue(Long issueId, int offset, int size, JsonObjectParser<T> jsonParse) throws  JSONException, HttpException;
*/
    JobProgress addTestsToCycle(Long projectId, Long versionId, String cycleId, List<Long> issueIds) throws JobProgressException, HttpException;

    InputStream exportExecutions(String exportType, List<String> executionIds, String zqlQuery) throws JobProgressException, HttpException;

    InputStream downloadExportedFile(String fileName) throws HttpException;

    JobProgress bulkUpdateStatus(List<String> executionIds, Integer statusId, Integer stepStatusId, Boolean testStepStatusChangeFlag, Boolean clearDefectMappingFlag) throws JobProgressException, HttpException;

    /*JobProgress bulkDeleteExecutions(List<String> executionIds) throws JobProgressException, HttpException;*/
    /*public abstract JSONObject getExecutionSummaryOfIssuesBySprint(Long sprintId, List<Long> issueIds) throws JSONException, HttpException;*/
    /*public abstract JSONObject getExecutionsByIssue(Long issueId, Integer offset, Integer maxRecords) throws JSONException, HttpException;*/
}
