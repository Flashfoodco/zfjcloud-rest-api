package com.thed.zephyr.util;

import com.thed.zephyr.cloud.rest.ZFJCloudRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by aliakseimatsarski on 3/17/16.
 */
public class AbstractTest {

    final static String accessKey = "amlyYTo4MmQzYzk1YS02MTMwLTRjNjctYWMwOS04ZWU2NjUwNDgwNjMgYWRtaW4";/*replace with you credentials */
    final static String secretKey = "21D6uyAihOxOlxhkWI6yjJcCKGWpuImFfZOt2j71EhI";/*replace with you credentials */
    final static String userName = "admin";
    final static String zephyrBaseUrl = "http://127.0.0.1:9000";
    public static ZFJCloudRestClient client;

    public Logger log = LoggerFactory.getLogger(AbstractTest.class);

    static {
        client = ZFJCloudRestClient.restBuilder(zephyrBaseUrl, accessKey, secretKey, userName).build();
    }
}
