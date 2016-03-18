package com.thed.zephyr.util;

import com.thed.zephyr.cloud.rest.client.ZephyrRestClient;
import com.thed.zephyr.cloud.rest.client.ZephyrRestClientUtil;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * Created by aliakseimatsarski on 3/17/16.
 */
public class AbstractTest {

    final static String accessKey = "amlyYTo0OTJlOTlmYS00ZGRlLTRlNDQtODk0NC1iNDkzODVmZjVmNjcgYWRtaW4";/*replace with you credentials */
    final static String secretKey = "ltDv5nZM5ri9tC9NcK5dp4SMdVe11FcYfOgLHs3N0qI";/*replace with you credentials */
    final static String userName = "admin";
    final static String zephyrBaseUrl = "http://127.0.0.1:9000";
    public static ZephyrRestClient client;

    static {
        try {
            client = ZephyrRestClientUtil.createZephyrRestClient(accessKey, secretKey, userName, zephyrBaseUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
