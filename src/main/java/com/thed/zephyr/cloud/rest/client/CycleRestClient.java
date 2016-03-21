package com.thed.zephyr.cloud.rest.client;

import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.thed.zephyr.cloud.rest.client.model.Cycle;
import org.apache.http.HttpException;
import org.codehaus.jettison.json.JSONException;

import java.io.File;
import java.util.List;

/**
 * Created by Kavya on 18-02-2016.
 */
public interface CycleRestClient {

    public abstract Cycle createCycle(Cycle cycle) throws JSONException, HttpException;
    public abstract <T> T createCycle(Cycle cycle, JsonObjectParser<T> parser) throws JSONException, HttpException;

    public abstract Cycle cloneCycle(String clonedCycleId, Cycle cycle) throws JSONException, HttpException;
    public abstract <T> T cloneCycle(String clonedCycleId, Cycle cycle, JsonObjectParser<T> parser) throws JSONException, HttpException;

    public abstract Cycle getCycle(Long projectId, Long versionId, String cycleId) throws JSONException, HttpException;
    public abstract <T> T getCycle(Long projectId, Long versionId, String cycleId, JsonObjectParser<T> parser) throws JSONException, HttpException;

    public abstract Cycle updateCycle(String cycleId, Cycle newCycle) throws JSONException, HttpException;
    public abstract <T> T updateCycle(String cycleId, Cycle newCycle, JsonObjectParser<T> parser) throws JSONException, HttpException;

    public abstract List<Cycle> getCycles(Long projectId, Long versionId) throws JSONException, HttpException;
    public abstract <T> List<T> getCycles(Long projectId, Long versionId, JsonObjectParser<T> parser) throws JSONException, HttpException;

    public abstract Boolean deleteCycle(Long projectId, Long versionId, String cycleId) throws JSONException, HttpException;
    public abstract String exportCycle(Long projectId, Long versionId, String cycleId, String exportType) throws JSONException, HttpException;
    public abstract File downloadExportedFile(String fileName) throws JSONException, HttpException;
    public abstract String moveExecutionsToCycle(String cycleId, Long projectId, Long versionId, List<String> executionIds, Boolean clearDefectMappingFlag, Boolean clearStatusFlag) throws JSONException, HttpException;
    public abstract String copyExecutionsToCycle(String cycleId, Long projectId, Long versionId, List<String> executionIds, Boolean clearDefectMappingFlag, Boolean clearStatusFlag) throws JSONException, HttpException;
}
