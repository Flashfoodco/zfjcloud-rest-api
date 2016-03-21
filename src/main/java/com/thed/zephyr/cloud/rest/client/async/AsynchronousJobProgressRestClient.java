package com.thed.zephyr.cloud.rest.client.async;

import com.atlassian.httpclient.api.ResponsePromise;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import com.thed.zephyr.cloud.rest.client.JobProgressRestClient;
import com.thed.zephyr.cloud.rest.client.constant.ApplicationConstants;
import com.thed.zephyr.cloud.rest.client.model.GenericEntityUtil;
import org.apache.http.HttpException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.function.Function;
import java.util.logging.Logger;

/**
 * Created by Kavya on 01-03-2016.
 */
public class AsynchronousJobProgressRestClient implements JobProgressRestClient {

    private final DisposableHttpClient httpClient;
    private final URI baseUri;
    private Logger log = Logger.getLogger("JobProgressRestClient");

    public AsynchronousJobProgressRestClient(URI baseUri, DisposableHttpClient httpClient) {
        this.httpClient = httpClient;
        this.baseUri = baseUri;
    }

    @Override
    public JSONObject getJobProgress(String jobProgressToken) throws HttpException {

        try {
            final URI getExecutionsUri = UriBuilder.fromUri(baseUri).path(ApplicationConstants.URL_PATH_JOB_PROGRESS).path(jobProgressToken).build();
            ResponsePromise responsePromise = httpClient.newRequest(getExecutionsUri).setAccept("application/json").get();
            return GenericEntityUtil.parseJobProgressPromiseResponse(responsePromise);
        } catch (HttpException e) {
            throw e;
        }
    }

    @Override
    public JSONObject getJobProgressAsync(String jobProgressToken, Function onRedeemFunction, Function onErrorFunction) {

        return null;
    }
}
