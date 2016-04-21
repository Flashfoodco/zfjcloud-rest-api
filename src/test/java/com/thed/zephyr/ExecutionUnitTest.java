package com.thed.zephyr;

import com.thed.zephyr.cloud.rest.client.ExecutionRestClient;
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
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.junit.Assert.*;


/**
 * Created by aliakseimatsarski on 3/15/16.
 */
public class ExecutionUnitTest extends AbstractTest {

    private static ExecutionRestClient executionRestClient;
    final private Long projectId = 10000l;
    final private Long versionId = -1l;
    final private Long issueId = 10001l;
    final private String cycleId = "-1";

    @BeforeClass
    public static void setUp() throws Exception{
        executionRestClient = client.getExecutionRestClient();
    }

  //  @Test
    public void testCreateExecution() throws JSONException, HttpException{
        Execution execution = new Execution();
        execution.projectId = projectId;
        execution.issueId = issueId;
        execution.versionId = versionId;
        execution.cycleId = cycleId;

        Execution responseExecution = executionRestClient.createExecution(execution);

        assertNotNull(responseExecution);
    }

 //   @Test
    public void testGetExecution() throws JSONException, HttpException{
        String executionId = "0001458978644495-d6eb16e347d4-0001";

        Execution responseExecution = executionRestClient.getExecution(projectId, issueId, executionId);

        log.info(responseExecution.toString());
        assertNotNull(responseExecution);
    }

    //@Test
    public void testUpdateExecution() throws JSONException, HttpException{
        Execution execution = new Execution();
        //execution.id = "0001459058677020-56459c344cdf-0001";
        execution.projectId = projectId;
        execution.issueId = issueId;
        Defect defect = new Defect();
        defect.id = 10101l;
        execution.defects = Arrays.<Defect>asList(defect);
        //execution.comment = "New Comment";

        ExecutionStatus executionStatus = new ExecutionStatus();
        executionStatus.id = 2;

        execution.status = executionStatus;

        Execution responseExecution = executionRestClient.updateExecution(execution);

        log.info(responseExecution.toString());
        assertNotNull(responseExecution);
     //   assertEquals(execution.comment, responseExecution.comment);
    }

 //   @Test
    public void testDeleteExecution() throws JSONException, HttpException{
        String executionId = "0001458978644495-d6eb16e347d4-0001";
        Boolean response = executionRestClient.deleteExecution(projectId, issueId, executionId);

        log.info("Deleted execution: {}", response);
        assertTrue(response);
    }

    @Test
    public void testGetExecutions() throws JSONException, HttpException{
        ZFJConnectResults<Execution> executionResults = executionRestClient.getExecutions(projectId, issueId, 0, 5);

        for (Execution execution:executionResults.resultList){
            log.info(execution.toString());
        }

        assertTrue(executionResults.totalCount > 0);
    }

  //  @Test
    public void testGetExecutionsByCycle()  throws JSONException, HttpException{
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
    public void testGetLinkedExecutions()  throws JSONException, HttpException{
        String issueIdorKey = "TP-49";
        int offset = 0;
        int size = 10;

        ZFJConnectResults<Execution> searchResult = executionRestClient.getLinkedExecutions(issueIdorKey, offset, size);
        List<Execution> executionList = searchResult.getResultList();
        for (Execution execution:executionList){
            log.info(execution.toString());
        }

        assertTrue(executionList.size() > 0);

    }

  //  @Test
    public void testAddTestsToCycle()  throws JobProgressException, HttpException {
        List<Long>  issuesId = new ArrayList<>();
        issuesId.add(issueId);
        JobProgress jobProgress = executionRestClient.addTestsToCycle(projectId, 10000l, "0001458068629621-de15b8674876-0001", issuesId);

        assertNotNull(jobProgress);
    }

  //  @Test
    public void testAddTestsToCycleFromCycle()  throws JobProgressException, HttpException {
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
    public void testAddTestsToCycleByJQL()  throws JobProgressException, HttpException {
        String toCycleId = "0001459303961836-56459c344cdf-0001";
        long toVersionId = -1l;
        String zql = "project = TP";

        JobProgress jobProgress = executionRestClient.addTestsToCycleByJQL(projectId, toVersionId, toCycleId, zql);
        log.info(jobProgress.toString());
        assertNotNull(jobProgress);
    }

  //  @Test
    public void testExportExecutions() throws JobProgressException, HttpException, IOException {
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
    public void testBulkUpdateStatus() throws JobProgressException, HttpException{
        List<String> executionIds = new ArrayList();
        executionIds.add("0001459059333955-56459c344cdf-0001");
        Integer statusId = 2;
        Integer stepStatusId = 2;
        Boolean testStepStatusChangeFlag = true;
        Boolean clearDefectMappingFlag = false;

        JobProgress jobProgress = executionRestClient.bulkUpdateStatus(executionIds, statusId, stepStatusId, testStepStatusChangeFlag, clearDefectMappingFlag);
        log.info(jobProgress.toString());
        assertNotNull(jobProgress);
    }

  //  @Test
    public void testsomething() throws HttpException, JSONException {
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
    public void testGetExecutionSummary() throws HttpException, JSONException {
        Long sprintId = 1L;
        List<Long> issueIds = new ArrayList();
        JSONObject result = executionRestClient.getExecutionSummary(sprintId, issueIds);
        log.info(result.toString());
    }
}
