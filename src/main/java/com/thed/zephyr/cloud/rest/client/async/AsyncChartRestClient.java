package com.thed.zephyr.cloud.rest.client.async;

import com.atlassian.httpclient.api.ResponsePromise;

/**
 * Created by Kavya on 21-06-2016.
 */
public interface AsyncChartRestClient {

    ResponsePromise getTestsCreated(Long projectId, Long daysPrevious, String periodName, Integer maxResults);

    ResponsePromise getExecutionCreated(Long projectId, Long daysPrevious, String periodName);
}
