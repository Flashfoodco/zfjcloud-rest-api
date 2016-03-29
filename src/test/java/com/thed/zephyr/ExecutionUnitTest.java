package com.thed.zephyr;

import com.thed.zephyr.cloud.rest.client.ExecutionRestClient;
import com.thed.zephyr.cloud.rest.exception.JobProgressException;
import com.thed.zephyr.cloud.rest.model.Execution;
import com.thed.zephyr.cloud.rest.model.JobProgress;
import com.thed.zephyr.cloud.rest.model.enam.ExecutionFieldId;
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
import java.util.ArrayList;
import java.util.List;

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

 //   @Test
    public void testUpdateExecution() throws JSONException, HttpException{
        Execution execution = new Execution();
        execution.id = "0001458978644495-d6eb16e347d4-0001";
        execution.projectId = projectId;
        execution.issueId = issueId;
        execution.comment = "New Comment";

        Execution responseExecution = executionRestClient.updateExecution(execution);

        log.info(responseExecution.toString());
        assertNotNull(responseExecution);
        assertEquals(execution.comment, responseExecution.comment);
    }

 //   @Test
    public void testDeleteExecution() throws JSONException, HttpException{
        String executionId = "0001458978644495-d6eb16e347d4-0001";
        Boolean response = executionRestClient.deleteExecution(projectId, issueId, executionId);

        log.info("Deleted execution: {}", response);
        assertTrue(response);
    }

 //   @Test
    public void testGetExecutions() throws JSONException, HttpException{
        ZFJConnectResults<Execution> executions = executionRestClient.getExecutions(projectId, issueId, 0, 5);

        assertTrue(executions.totalCount == 10);
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

  //  @Test
    public void testAddTestsToCycle()  throws JobProgressException, HttpException{
        List<Long>  issuesId = new ArrayList<>();
        issuesId.add(issueId);
        JobProgress jobProgress = executionRestClient.addTestsToCycle(projectId, 10000l, "0001458068629621-de15b8674876-0001", issuesId);

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
}
