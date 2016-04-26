package com.thed.zephyr.util;

import com.thed.zephyr.cloud.rest.ZFJCloudRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by aliakseimatsarski on 3/17/16.
 */
public class AbstractTest {

    final static String accessKey = "amlyYTo1ODI5YTUzNS0wMjkzLTQ1ODMtOGZkZS1iNTI4ZmYxOWVjNzMgYWRtaW4";/*replace with you credentials */
    final static String secretKey = "2Bf2ZWKjHyJC1Yj3TIYT6zOwBI6m_0vu5GA3pU_XOyM";/*replace with you credentials */
    final static String userName = "admin";
    final static String zephyrBaseUrl = "http://127.0.0.1:9000";
    public static ZFJCloudRestClient client;

    public Logger log = LoggerFactory.getLogger(AbstractTest.class);

    static {
        client = ZFJCloudRestClient.restBuilder(zephyrBaseUrl, accessKey, secretKey, userName).build();
    }
}
