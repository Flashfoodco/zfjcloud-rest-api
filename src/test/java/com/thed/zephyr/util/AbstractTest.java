package com.thed.zephyr.util;

import com.thed.zephyr.cloud.rest.ZFJCloudRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by aliakseimatsarski on 3/17/16.
 */
public class AbstractTest {

    final static String accessKey = "amlyYTo0OTJlOTlmYS00ZGRlLTRlNDQtODk0NC1iNDkzODVmZjVmNjcgYWRtaW4";/*replace with you credentials */
    final static String secretKey = "tl4XmBh2ibvLVI9B-jruy-JdkYCHrPRd3W4Cc342m_c";/*replace with you credentials */
    final static String userName = "admin";
    final static String zephyrBaseUrl = "http://127.0.0.1:9000";
    public static ZFJCloudRestClient client;

    public Logger log = LoggerFactory.getLogger(AbstractTest.class);

    static {
        client = ZFJCloudRestClient.restBuilder(zephyrBaseUrl, accessKey, secretKey, userName).build();
    }
}
