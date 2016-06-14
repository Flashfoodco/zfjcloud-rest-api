package com.thed.zephyr;

import com.thed.zephyr.cloud.rest.client.TeststepRestClient;
import com.thed.zephyr.cloud.rest.exception.BadRequestParamException;
import com.thed.zephyr.cloud.rest.model.Teststep;
import com.thed.zephyr.cloud.rest.util.ZFJConnectResults;
import com.thed.zephyr.util.AbstractTest;
import org.apache.http.HttpException;
import org.codehaus.jettison.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Kavya on 14-06-2016.
 */
public class TeststepUnitTest extends AbstractTest {

    private static TeststepRestClient teststepRestClient;
    private Long projectId = 10000L;
    private Long issueId = 10000L;

    @BeforeClass
    public static void setUp() {
        teststepRestClient = client.getTeststepRestClient();
    }

    @Test
    public void testCreateTeststep() throws BadRequestParamException, JSONException, HttpException {
        Teststep teststep = new Teststep();
        teststep.step = "step1";
        teststep.data = "data1";
        teststep.result = "result1";
        Teststep response = teststepRestClient.createTeststep(projectId, issueId, teststep);
        assertNotNull(response);
    }

    @Test
    public void testCloneTeststep() throws BadRequestParamException, JSONException, HttpException {
        Teststep teststep = new Teststep();
        teststep.step = "step1";
        teststep.data = "data1";
        teststep.result = "result1";
        Teststep createdTest = teststepRestClient.createTeststep(projectId, issueId, teststep);

        ZFJConnectResults<Teststep> responseAfterCloning = teststepRestClient.cloneTeststep(projectId, issueId, createdTest.id, 2, teststep);
        assertNotNull(responseAfterCloning.getResultList());
    }

    @Test
    public void testGetTeststep() throws BadRequestParamException, JSONException, HttpException {
        Teststep teststep = new Teststep();
        teststep.step = "step1";
        teststep.data = "data1";
        teststep.result = "result1";
        Teststep createdTest = teststepRestClient.createTeststep(projectId, issueId, teststep);

        Teststep responseAfterGetCall = teststepRestClient.getTeststep(projectId, issueId, createdTest.id);
        assertNotNull(responseAfterGetCall);
    }

    @Test
    public void testGetTeststeps() throws BadRequestParamException, JSONException, HttpException {
        ZFJConnectResults<Teststep> responseAfterGetCall = teststepRestClient.getTeststeps(projectId, issueId);
        assertNotNull(responseAfterGetCall.getResultList());
    }

    @Test
    public void testUpdateTeststep() throws BadRequestParamException, JSONException, HttpException {
        Teststep teststep = new Teststep();
        teststep.step = "step1";
        teststep.data = "data1";
        teststep.result = "result1";
        Teststep createdTest = teststepRestClient.createTeststep(projectId, issueId, teststep);

        teststep.step = "update step1";
        teststep.data = "update data1";
        teststep.result = "update result1";

        teststepRestClient.updateTeststep(projectId, issueId, createdTest.id, teststep);
    }

    @Test
    public void testDeleteTeststep() throws BadRequestParamException, JSONException, HttpException {
        Teststep teststep = new Teststep();
        teststep.step = "step1";
        teststep.data = "data1";
        teststep.result = "result1";
        Teststep createdTest = teststepRestClient.createTeststep(projectId, issueId, teststep);

        ZFJConnectResults<Teststep> responseAfterDelete = teststepRestClient.deleteTeststep(projectId, issueId, createdTest.id);
        assertFalse(responseAfterDelete.getResultList().contains(createdTest));
    }

//    @Test
    public void testMoveTeststep() {

    }
}
