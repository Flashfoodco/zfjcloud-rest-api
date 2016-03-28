package com.thed.zephyr.cloud.rest.client.async;

import com.atlassian.httpclient.api.ResponsePromise;
import com.thed.zephyr.cloud.rest.model.Execution;
import com.thed.zephyr.cloud.rest.model.enam.SortOrder;

import java.util.List;

/**
 * Created by aliakseimatsarski on 3/25/16.
 */
public interface AsyncExecutionRestClient {

    ResponsePromise createExecution(Execution execution);

    ResponsePromise getExecution(Long projectId, Long issueId, String executionId);

    ResponsePromise updateExecution(Execution execution);

    ResponsePromise deleteExecution(Long projectId, Long issueId, String executionId);

    ResponsePromise getExecutions(Long projectId, Long issueId, int offset, int size);

    ResponsePromise getExecutionsByCycle(Long projectId, Long versionId, String cycleId, int offset, int size, String sortBy, SortOrder sortOrder);

    ResponsePromise addTestsToCycle(Long projectId, Long versionId, String cycleId, List<Long> issueIds);

    ResponsePromise exportExecution(String exportType, List<String> executionIds, String zqlQuery);

    ResponsePromise downloadExportedFile(String fileName);

    ResponsePromise bulkUpdateStatus(List<String> executionIds, Integer statusId, Integer stepStatusId, Boolean testStepStatusChangeFlag, Boolean clearDefectMappingFlag);

    ResponsePromise bulkDeleteExecutions(List<String> executionIds);
}
