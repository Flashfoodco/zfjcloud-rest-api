package com.thed.zephyr.cloud.rest.model.enam;

/**
 * Created by Kavya on 17-06-2016.
 */
public enum StepResultFieldId {

    ID("id"),
    EXECUTED_ON("executedOn"),
    COMMENT("comment"),
    EXECUTED_BY("executedBy"),
    EXECUTION_ID("executionId"),
    STEP_ID("stepId"),
    STATUS("status"),
    DEFECTS("defects"),
    ISSUE_ID("issueId"),
    CREATED_BY("createdBy"),
    MODIFIED_BY("modifiedBy"),
    ATTACHMENTS("attachments");

    public final String id;

    StepResultFieldId(String id) {
        this.id = id;
    }
}
