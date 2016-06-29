package com.thed.zephyr.cloud.rest.client;

import org.codehaus.jettison.json.JSONObject;

import java.io.InputStream;

/**
 * Created by Kavya on 21-06-2016.
 */
public interface TraceabilityRestClient {

    JSONObject searchTestsByRequirement(String requirementIdOrKeyList, Long versionId);
    JSONObject searchExecutionsByTest(Long testId, Long versionId, Integer maxResult, Integer offset);
    JSONObject searchDefectStatistics(String defectIdList, Long versionId);
    JSONObject searchExecutionsByDefect(Long defectId, Long versionId, Integer maxResult, Integer offset);
    InputStream exportTraceabilityReport(Long versionId);
    InputStream downloadReport(String fileName);
}
