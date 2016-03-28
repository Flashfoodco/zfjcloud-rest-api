package com.thed.zephyr.cloud.rest;

import com.atlassian.jira.rest.client.internal.async.AsynchronousHttpClientFactory;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import com.thed.zephyr.cloud.rest.client.CycleRestClient;
import com.thed.zephyr.cloud.rest.client.ExecutionRestClient;
import com.thed.zephyr.cloud.rest.client.impl.CycleRestClientImpl;
import com.thed.zephyr.cloud.rest.client.impl.ExecutionRestClientImpl;
import com.thed.zephyr.cloud.rest.model.ZConfig;
import com.thed.zephyr.cloud.rest.util.json.ZephyrAuthenticationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * Created by aliakseimatsarski on 3/25/16.
 */
public class ZFJCloudRestClient {

    private ExecutionRestClient executionRestClient;

    private CycleRestClient cycleRestClient;

    private Logger log = LoggerFactory.getLogger(ZFJCloudRestClient.class);

    private ZFJCloudRestClient() {
    }

    public ExecutionRestClient getExecutionRestClient() {
        return executionRestClient;
    }

    public static Builder  restBuilder(String zephyrBaseUrl, String accessKey, String secretKey, String userName){
        return new ZFJCloudRestClient().new Builder(zephyrBaseUrl, accessKey, secretKey, userName);
    }

    public CycleRestClient getCycleRestClient() {
        return cycleRestClient;
    }

    public class Builder {

        private String accessKey;
        private String secretKey;
        private String userName;
        private String zephyrBaseUrl;
        private String apiVersion = "1.0";

        private Builder(String zephyrBaseUrl, String accessKey, String secretKey, String userName) {
            this.zephyrBaseUrl = zephyrBaseUrl;
            this.accessKey = accessKey;
            this.secretKey = secretKey;
            this.userName = userName;
        }

        public ZFJCloudRestClient build(){
            ZConfig zConfig= new ZConfig(accessKey, secretKey, userName, zephyrBaseUrl);
            URI serverUri = UriBuilder.fromUri(zConfig.ZEPHYR_BASE_URL).path("/public/rest/api/" + apiVersion).build();
            DisposableHttpClient httpClient = new AsynchronousHttpClientFactory().createClient(serverUri, new ZephyrAuthenticationHandler(zConfig));

            executionRestClient = new ExecutionRestClientImpl(serverUri, httpClient);
            cycleRestClient = new CycleRestClientImpl(serverUri, httpClient);

            log.info("ZFJCloudRestClient was successfully created with url:{}", serverUri.toString());

            return ZFJCloudRestClient.this;
        }

        public void setApiVersion(String apiVersion) {
            this.apiVersion = apiVersion;
        }
    }

}
