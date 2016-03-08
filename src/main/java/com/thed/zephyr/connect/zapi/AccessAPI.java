package com.thed.zephyr.connect.zapi;

import com.thed.zephyr.cloud.rest.client.ZephyrRestClient;
import com.thed.zephyr.cloud.rest.client.ZephyrRestClientUtil;
import com.thed.zephyr.cloud.rest.client.model.Cycle;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Kavya on 19-02-2016.
 */
public class AccessAPI {
    public static void main(String[] args) {
        String accessKey = "amlyYToyOWE0MmY1MC0wYTJlLTQxMTUtOTM5OC1iZDY0MDI0MzAwNzIgYWRtaW4";
        String secretKey = "j8W_mEJTFZJMjh_e6kPbCFAHinc5ILJRz1TbRJNvy7A";
        String userName = "admin";
        String zephyrBaseUrl = "http://127.0.0.1:9000";
        try {
            /*Execution execution = new Execution();
            execution.projectId = 10000L;
            execution.versionId = -1L;
            execution.issueId = 10001L;*/

            List<String> executionIds = new ArrayList<String>();
             Cycle cycle = new Cycle();
            cycle.name = "Create Cycle";
            cycle.projectId = 10000L;
            cycle.versionId = -1L;
            ZephyrRestClient client = ZephyrRestClientUtil.createZephyrRestClient(accessKey, secretKey, userName, zephyrBaseUrl);
            JSONObject executionsJobProgress = client.getExecutionRestClient().getExecutions(10000L, -1L, "-1");
            //JSONArray executionsArray = executionsJobProgress.getJSONObject("exections").;
            //JSONObject jobp = client.getExecutionRestClient().exportExecution("csv", )
            /*Logger.getLogger("AccessAPI").log(Level.INFO, jobp.getString("name"));
            cycle.name = "clone cycle";
            jobp = client.getCycleRestClient().cloneCycle(jobp.getString("id"), cycle);*/
            JSONArray x = executionsJobProgress.getJSONArray("searchObjectList");
            for (int i=0; i< x.length(); i++) {
                executionIds.add(x.getJSONObject(i).getJSONObject("execution").getString("id"));
            }
            File ex = client.getExecutionRestClient().exportExecution("csv", executionIds, "project = API");
            Logger.getLogger("AccessAPI").log(Level.INFO, ex.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}















