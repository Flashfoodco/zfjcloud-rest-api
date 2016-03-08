package com.thed.zephyr.cloud.rest.client;

import com.thed.zephyr.cloud.rest.client.model.Execution;
import org.apache.http.HttpException;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.File;
import java.util.List;

/**
 * Created by Kavya on 18-02-2016.
 */
public interface ExecutionRestClient {

    public abstract JSONObject createExecution(Execution execution) throws JSONException, HttpException;
    public abstract JSONObject getExecution(Long projectId, Long issueId, String executionId) throws JSONException, HttpException;
    public abstract JSONObject updateExecution(String executionId, Execution execution) throws JSONException, HttpException;
    public abstract Boolean deleteExecution(Long projectId, Long issueId, String executionId) throws JSONException, HttpException;
    public abstract JSONObject getExecutions(Long projectId, Long issueId) throws JSONException, HttpException;
    public abstract JSONObject getExecutions(Long projectId, Long versionId, String cycleId) throws JSONException, HttpException;
    public abstract String addTestsToCycle(Long projectId, String cycleId, List<Long> issueIds) throws JSONException, HttpException;
    public abstract File exportExecution(String exportType, List<String> executionIds, String zqlQuery) throws JSONException, HttpException;
    public abstract File downloadExportedFile(String fileName) throws JSONException, HttpException;
    public abstract String bulkUpdateStatus(List<String> executionIds, Integer statusId, Integer stepStatusId, Boolean testStepStatusChangeFlag, Boolean clearDefectMappingFlag) throws JSONException, HttpException;
    public abstract String bulkDeleteExecutions(List<String> executionIds) throws JSONException, HttpException;
    //public abstract JSONObject getExecutionSummaryOfIssuesBySprint(Long sprintId, List<Long> issueIds) throws JSONException, HttpException;
    //public abstract JSONObject getExecutionsByIssue(Long issueId, Integer offset, Integer maxRecords) throws JSONException, HttpException;

}
