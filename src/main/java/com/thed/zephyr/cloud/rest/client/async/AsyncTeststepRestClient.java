package com.thed.zephyr.cloud.rest.client.async;

import com.atlassian.httpclient.api.ResponsePromise;
import com.thed.zephyr.cloud.rest.model.Teststep;

/**
 * Created by Kavya on 13-06-2016.
 */
public interface AsyncTeststepRestClient {
    ResponsePromise createTeststep(Long projectId, Long issueId, Teststep teststep);
    ResponsePromise cloneTeststep(Long projectId, Long issueId, String teststepId, int position, Teststep teststep);
    ResponsePromise getTeststep(Long projectId, Long issueId, String teststepId);
    ResponsePromise getTeststeps(Long projectId, Long issueId);
    ResponsePromise updateTeststep(Long projectId, Long issueId, String teststepId, Teststep teststep);
    ResponsePromise deleteTeststep(Long projectId, Long issueId, String teststepId);
}
