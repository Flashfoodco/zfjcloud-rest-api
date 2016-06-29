package com.thed.zephyr.cloud.rest.client.async;

import com.atlassian.httpclient.api.ResponsePromise;

import java.util.Map;

/**
 * Created by Kavya on 29-06-2016.
 */
public interface AsyncTraceabilityRestClient {

    ResponsePromise searchTestsByRequirement(String requirementIdOrKeyList, Long versionId);
    ResponsePromise searchExecutionsByTest(Long testId, Long versionId, Integer maxResult, Integer offset);
    ResponsePromise searchDefectStatistics(String defectIdList, Long versionId);
    ResponsePromise searchExecutionsByDefect(Long defectId, Long versionId, Integer maxResult, Integer offset);
    ResponsePromise exportTraceabilityReport(Long versionId, Map dataMap);
    ResponsePromise downloadReport(String fileName);
}
