package com.thed.zephyr.cloud.rest.model;

import java.util.Collection;
import java.util.Date;

/**
 * Created by Kavya on 16-06-2016.
 */
public class StepResult {

    public String id;
    public Date executedOn;
    public String comment;
    public String executedBy;
    public String executionId;
    public String stepId;
    public ExecutionStatus status;
    public Collection<Defect> defects;
    public Long issueId;
    public String createdBy;
    public String modifiedBy;
    public Collection<Attachment> attachments;

    public StepResult() {
    }

    public StepResult(String id, Date executedOn, String comment, String executedBy, String executionId, String stepId, ExecutionStatus status, Collection<Defect> defects, Long issueId, String createdBy, String modifiedBy, Collection<Attachment> attachments) {
        this.id = id;
        this.executedOn = executedOn;
        this.comment = comment;
        this.executedBy = executedBy;
        this.executionId = executionId;
        this.stepId = stepId;
        this.status = status;
        this.defects = defects;
        this.issueId = issueId;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.attachments = attachments;
    }

    @Override
    public String toString() {
        return "{" +
                "id : \"" + id + "\"" +
                ", executedOn : " + executedOn +
                ", comment : \"" + comment + "\"" +
                ", executedBy : \"" + executedBy + "\"" +
                ", executionId : \"" + executionId + "\"" +
                ", stepId : \"" + stepId + "\"" +
                ", status : " + status +
                ", defects : " + defects +
                ", issueId : " + issueId +
                ", createdBy : \"" + createdBy + "\"" +
                ", modifiedBy : \"" + modifiedBy + "\"" +
                ", attachments : " + attachments +
                "}";
    }
}
