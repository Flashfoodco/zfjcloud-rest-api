package com.thed.zephyr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.apache.http.HttpException;
import org.codehaus.jettison.json.JSONException;
import org.junit.BeforeClass;
import com.thed.zephyr.cloud.rest.client.CycleRestClient;
import com.thed.zephyr.cloud.rest.client.model.Cycle;
import com.thed.zephyr.util.AbstractTest;


/**
 * Created by aliakseimatsarski on 3/15/16.
 */
public class CycleUnitTest extends AbstractTest {

    private static CycleRestClient cycleRestClient;
    final private Long projectId = 10000l;
    final private Long versionId = -1l;
    final private Long issueId = 10001l;

    @BeforeClass
    public static void setUp() throws Exception{
    	cycleRestClient = client.getCycleRestClient();
    }

  //  @Test
    public void testCreateCycle() throws JSONException, HttpException{
    	Cycle cycle = new Cycle();
    	cycle.projectId = projectId;
    	cycle.versionId = versionId;
    	cycle.name = "Create cycle";
					
		Cycle responseCycle = cycleRestClient.createCycle(cycle);
		assertNotNull(responseCycle);
    }

 //   @Test
    public void testGetCycle() throws JSONException, HttpException{
    	Cycle cycle = new Cycle();
    	cycle.projectId = projectId;
    	cycle.versionId = versionId;
    	cycle.name = "Create cycle";
					
    	Cycle responseCycle = cycleRestClient.createCycle(cycle);
		assertNotNull(responseCycle);
		
		
		Cycle responseCycleAfterGetting = cycleRestClient.getCycle(projectId, versionId, responseCycle.id);
		assertNotNull(responseCycleAfterGetting);
		assertEquals(responseCycle.id, responseCycleAfterGetting.id);
        
    }

 //   @Test
    public void testUpdateCycle() throws JSONException, HttpException{
    	Cycle cycle = new Cycle();
    	cycle.projectId = projectId;
    	cycle.versionId = versionId;
    	cycle.name = "Create cycle";
					
    	Cycle responseCycle = cycleRestClient.createCycle(cycle);
		assertNotNull(responseCycle);
		
		responseCycle.name = "Cycle edited";
		
		Cycle responseCycleAfterGetting = cycleRestClient.updateCycle(responseCycle.id, responseCycle);
		assertNotNull(responseCycleAfterGetting);
		assertEquals(responseCycle.id, responseCycleAfterGetting.id);
    }
//  @Test
   public void testDeleteCycle() throws JSONException, HttpException{
	   	Cycle cycle = new Cycle();
	   	cycle.projectId = projectId;
	   	cycle.versionId = versionId;
	   	cycle.name = "Create cycle";
						
	   	Cycle responseCycle = cycleRestClient.createCycle(cycle);
		assertNotNull(responseCycle);
		
		boolean flag = cycleRestClient.deleteCycle(responseCycle.projectId, responseCycle.versionId, responseCycle.id);
		assertTrue(flag);
   }


}
