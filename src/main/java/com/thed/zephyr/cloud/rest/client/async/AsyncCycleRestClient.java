package com.thed.zephyr.cloud.rest.client.async;

import com.atlassian.httpclient.api.ResponsePromise;
import com.thed.zephyr.cloud.rest.model.Cycle;
import org.apache.http.HttpException;
import org.codehaus.jettison.json.JSONException;

import java.util.List;

/**
 * Created by aliakseimatsarski on 3/27/16.
 */
public interface AsyncCycleRestClient {

    ResponsePromise createCycle(Cycle cycle);

    ResponsePromise cloneCycle(String clonedCycleId, Cycle cycle);

    ResponsePromise getCycle(Long projectId, Long versionId, String cycleId);

    ResponsePromise updateCycle(String cycleId, Cycle newCycle);

    ResponsePromise getCycles(Long projectId, Long versionId);

    ResponsePromise deleteCycle(Long projectId, Long versionId, String cycleId);

    ResponsePromise exportCycle(Long projectId, Long versionId, String cycleId, String exportType);

    ResponsePromise downloadExportedFile(String fileName);

    ResponsePromise moveExecutionsToCycle(String cycleId, Long projectId, Long versionId, List<String> executionIds, Boolean clearDefectMappingFlag, Boolean clearStatusFlag);

    ResponsePromise copyExecutionsToCycle(String cycleId, Long projectId, Long versionId, List<String> executionIds, Boolean clearDefectMappingFlag, Boolean clearStatusFlag);
}
