package com.thed.zephyr.cloud.rest.client;

import com.thed.zephyr.cloud.rest.client.model.Cycle;
import org.apache.http.HttpException;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.File;
import java.util.List;

/**
 * Created by Kavya on 18-02-2016.
 */
public interface CycleRestClient {

    public abstract JSONObject createCycle(Cycle cycle) throws JSONException, HttpException;
    public abstract JSONObject cloneCycle(String clonedCycleId, Cycle cycle) throws JSONException, HttpException;
    public abstract JSONObject getCycle(Long projectId, Long versionId, String cycleId) throws JSONException, HttpException;
    public abstract JSONObject updateCycle(String cycleId, Cycle newCycle) throws JSONException, HttpException;
    public abstract JSONObject deleteCycle(Long projectId, Long versionId, String cycleId) throws JSONException, HttpException;
    public abstract JSONArray getCycles(Long projectId, Long versionId) throws JSONException, HttpException;
    public abstract String exportCycle(Long projectId, Long versionId, String cycleId, String exportType) throws JSONException, HttpException;
    public abstract File downloadExportedFile(String fileName) throws JSONException, HttpException;
    public abstract String moveExecutionsToCycle(String cycleId, Long projectId, Long versionId, List<String> executionIds, Boolean clearDefectMappingFlag, Boolean clearStatusFlag) throws JSONException, HttpException;
    public abstract String copyExecutionsToCycle(String cycleId, Long projectId, Long versionId, List<String> executionIds, Boolean clearDefectMappingFlag, Boolean clearStatusFlag) throws JSONException, HttpException;
}
