package com.thed.zephyr;

import com.thed.zephyr.cloud.rest.client.ExecutionRestClient;
import com.thed.zephyr.cloud.rest.exception.BadRequestParamException;
import com.thed.zephyr.cloud.rest.exception.JobProgressException;
import com.thed.zephyr.cloud.rest.model.Defect;
import com.thed.zephyr.cloud.rest.model.Execution;
import com.thed.zephyr.cloud.rest.model.ExecutionStatus;
import com.thed.zephyr.cloud.rest.model.JobProgress;
import com.thed.zephyr.cloud.rest.model.enam.ExecutionFieldId;
import com.thed.zephyr.cloud.rest.model.enam.FromCycleFilter;
import com.thed.zephyr.cloud.rest.model.enam.SortOrder;
import com.thed.zephyr.cloud.rest.util.ZFJConnectResults;
import com.thed.zephyr.util.AbstractTest;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpException;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * Created by aliakseimatsarski on 3/15/16.
 */
public class ExecutionUnitTest extends AbstractTest {

    private static ExecutionRestClient executionRestClient;
    final private Long projectId = 10000l;
    final private Long versionId = -1l;
    final private Long issueId = 10000l;
    final private String cycleId = "0001464160040530-b82a729d7df-0001";

    @BeforeClass
    public static void setUp() throws Exception{
        executionRestClient = client.getExecutionRestClient();
    }

    @Test
    public void testCreateExecution() throws JSONException, HttpException, BadRequestParamException {
        Execution execution = new Execution();
        execution.projectId = projectId;
        execution.issueId = issueId;
        execution.versionId = versionId;
        execution.cycleId = cycleId;

        Execution responseExecution = executionRestClient.createExecution(execution);
        Execution responseExecution1 = executionRestClient.createExecution(execution);

        assertEquals(responseExecution.id, responseExecution1.id);
    }

 //   @Test
    public void testGetExecution() throws JSONException, HttpException, BadRequestParamException {
        String executionId = "0001458978644495-d6eb16e347d4-0001";

        Execution responseExecution = executionRestClient.getExecution(projectId, issueId, executionId);

        log.info(responseExecution.toString());
        assertNotNull(responseExecution);
    }

    //@Test
    public void testUpdateExecution() throws JSONException, HttpException, BadRequestParamException {
        Execution execution = new Execution();
        execution.id = "0001461619207057-32cd60effffff460-0001+1";
        execution.projectId = 10000l;
        execution.cycleId = cycleId;
        execution.issueId = 10000l;
        Defect defect = new Defect();
        defect.id = 10300l;
     //   execution.defects = Arrays.<Defect>asList(defect);
        //execution.comment = "New Comment";

        ExecutionStatus executionStatus = new ExecutionStatus();
        executionStatus.id = 2;

        execution.status = executionStatus;

        Execution responseExecution = executionRestClient.updateExecution(execution);

        log.info(responseExecution.toString());
        assertNotNull(responseExecution);
     //   assertEquals(execution.comment, responseExecution.comment);
    }

    @Test
    public void testDeleteExecution() throws JSONException, HttpException, BadRequestParamException {
        String executionId = "0001458978644495-d6eb16e347d4-0001";
        Long issueId = 10302l;
        Boolean response = executionRestClient.deleteExecution(issueId, executionId);

        log.info("Deleted execution: {}", response);
        assertTrue(response);
    }

    @Test
    public void testGetExecutions() throws JSONException, HttpException, BadRequestParamException {
        int offset = 0;
        int size = 50;
        Long projectId = 10000l;
        Long issueId = 10000l;
        ZFJConnectResults<Execution> executionResults;
        List<String> ids = new ArrayList<String>();
        do{
            executionResults = executionRestClient.getExecutions(projectId, issueId, 2, 1);
            for (Execution execution:executionResults.resultList){
                ids.add(execution.id);
            }
            offset = offset + size;
        }while(executionResults.getTotalCount() > offset);
        log.info("Number founded executions:{}", ids.size());

        assertTrue(executionResults.totalCount > 0);
    }

  //  @Test
    public void testGetExecutionsByCycle() throws JSONException, HttpException, BadRequestParamException {
        int offset = 0;
        int size = 10;
        String sortBy = ExecutionFieldId.STATUS.id;
        SortOrder sortOrder = SortOrder.ASC;
        ZFJConnectResults<Execution> searchResult = executionRestClient.getExecutionsByCycle(projectId, versionId, cycleId, offset, size, sortBy, sortOrder);

        List<Execution> executionList = searchResult.getResultList();
        for (Execution execution:executionList){
    //        log.info(execution.toString());
        }

        assertTrue(executionList.size() > 0);
    }

    @Test
    public void testGetLinkedExecutions() throws JSONException, HttpException, BadRequestParamException {
        String issueIdorKey = "10200";
        int offset = 0;
        int size = 50;

        ZFJConnectResults<Execution> searchResult = executionRestClient.getLinkedExecutions(issueIdorKey, offset, size);
        log.info("searchResult:{}", searchResult.getMaxAllowed());
        List<Execution> executionList = searchResult.getResultList();
        for (Execution execution:executionList){
            log.info(execution.toString());
        }

        assertTrue(executionList.size() > 0);

    }

    @Test
    public void testAddTestsToCycle() throws JobProgressException, HttpException, BadRequestParamException {
        Long issueId = 987l;
        Long projectId = 10000l;
        Long versionId = -1l;
        String cycleId = "yedhbkdn";

        List<Long>  issuesId = new ArrayList<>();
        issuesId.add(issueId);
        JobProgress jobProgress = executionRestClient.addTestsToCycle(projectId, versionId, cycleId, issuesId);
        log.info(jobProgress.toString());
        assertNotNull(jobProgress);
    }

    @Test
    public void testAddTestsToCycleFromCycle() throws JobProgressException, HttpException, BadRequestParamException {
        String toCycleId = "0001459303961836-56459c344cdf-0001";
        long toVersionId = -1l;
        String fromCycleId = "0001459199696405-56459c344cdf-0001";
        long fromVersionId = -1l;
        Map<FromCycleFilter, List<String>> filter = new HashMap<FromCycleFilter, List<String>>();
        filter.put(FromCycleFilter.COMPONENTS, Arrays.<String>asList("10000", "10001"));

        JobProgress jobProgress = executionRestClient.addTestsToCycleFromCycle(projectId, toVersionId, toCycleId, fromCycleId, fromVersionId, filter);

        log.info(jobProgress.toString());
        assertNotNull(jobProgress);
    }

   // @Test
    public void testAddTestsToCycleByJQL() throws JobProgressException, HttpException, BadRequestParamException {
        String toCycleId = "0001459303961836-56459c344cdf-0001";
        long toVersionId = -1l;
        String zql = "project = TP";

        JobProgress jobProgress = executionRestClient.addTestsToCycleByJQL(projectId, toVersionId, toCycleId, zql);
        log.info(jobProgress.toString());
        assertNotNull(jobProgress);
    }

  //  @Test
    public void testExportExecutions() throws JobProgressException, HttpException, IOException, BadRequestParamException {
        String exportType = "HTML";
        List<String> executionIds = new ArrayList();
        executionIds.add("0001459059333955-56459c344cdf-0001");
        String zqlQuery = "";

        InputStream inputStream = executionRestClient.exportExecutions(exportType, executionIds, zqlQuery);
        String theString = IOUtils.toString(inputStream, "UTF-8");
        IOUtils.closeQuietly(inputStream);
        log.info(theString);
    }

 //   @Test
    public void testBulkUpdateStatus() throws JobProgressException, HttpException, BadRequestParamException {
        List<String> executionIds = new ArrayList();
        executionIds.add("0001459059333955-56459c344cdf-0001");
        Integer statusId = 2;
        Integer stepStatusId = 2;
        Boolean testStepStatusChangeFlag = true;
        Boolean clearDefectMappingFlag = false;

        JobProgress jobProgress = executionRestClient.bulkUpdateStatus(executionIds, statusId, stepStatusId, testStepStatusChangeFlag);
        log.info(jobProgress.toString());
        assertNotNull(jobProgress);
    }

  //  @Test
    public void testsomething() throws HttpException, JSONException, BadRequestParamException {
        int offset = 0;
        int maxsize = 50;
        List<Defect> defects = new ArrayList<Defect>();
        Defect d1 =  new Defect();
        d1.id = 10701L;
        Defect d2 = new Defect();
        d2.id = 10700L;
        defects.add(d1);
        defects.add(d2);
        for(;offset<700;offset+=maxsize) {
            ZFJConnectResults<Execution> executions = executionRestClient.getExecutionsByCycle(10000L, -1L, "0001459313722161-b82a729d7df-0001", offset, maxsize, "", SortOrder.ASC);
            for (Execution execution : executions.getResultList()) {
                execution.defects = defects;
                Execution updatedExecution = executionRestClient.updateExecution(execution);
                assertNotNull(updatedExecution);
            }
            executions = null;
        }
    }

    @Test
    public void testGetExecutionSummaryBySprint() throws HttpException, JSONException, BadRequestParamException {
        Long sprintId = 1L;
        List<Long> issueIds = new ArrayList();
        issueIds.add(10200L);
        JSONObject result = executionRestClient.getExecutionSummaryBySprint(sprintId, issueIds);
        log.info(result.toString());
    }

    //@Test
    public void testBulkDeleteExecutions() throws HttpException, JobProgressException, BadRequestParamException {
        List<String> executionIds = new ArrayList<>();
        executionIds.add("0001461623027292-32cd60effffff460-0001");
        executionIds.add("0001461623027247-32cd60effffff460-0001");
        executionIds.add("0001461623027156-32cd60effffff460-0001");
        JobProgress jobProgress = executionRestClient.bulkDeleteExecutions(executionIds);

        log.info(jobProgress.toString());
    }
}
