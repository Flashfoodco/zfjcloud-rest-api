package com.thed.zephyr.cloud.rest.client.async;

import com.atlassian.httpclient.api.ResponsePromise;

/**
 * Created by Kavya on 23-06-2016.
 */
public interface AsyncJiraReportRestClient {

    ResponsePromise getExecutionCount(Long projectId, String groupFld, String periodName, Long versionId, String cycleName);
    ResponsePromise getTopDefects(Long projectId, Long versionId, String issueStatuses, Integer howMany);
    ResponsePromise getTestDistributionCount(Long projectId, Long versionId, String groupFld);
}
