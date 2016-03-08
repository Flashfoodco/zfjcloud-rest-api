package com.thed.zephyr.cloud.rest.client.model;

import java.util.List;

/**
 * Created by Kavya on 18-02-2016.
 */
public class Execution {

    public String id;
    public Long issueId;
    public Long versionId;
    public Long projectId;
    public String cycleId;
    public String comment;
    public String cycleName;
    public ExecutionStatus status;
    public List<Long> defects;
    //public String defects;
    public Execution () {}

}
