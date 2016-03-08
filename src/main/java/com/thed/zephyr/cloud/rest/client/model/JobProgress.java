package com.thed.zephyr.cloud.rest.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;

/**
 * Created by Kavya on 01-03-2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobProgress {
    public String id;
    public String tenantId;
    public Integer status;
    public String name;
    public Integer totalSteps;
    public Integer completedSteps;
    public Date startTime;
    public Date endTime;
    public Boolean canceledJob;

    public JobProgress(String id, String tenantId, Integer status, String name, Integer totalSteps, Integer completedSteps, Date startTime, Date endTime, Boolean canceledJob, String stepMessage, List<String> stepMessages, String message, String summaryMessage, String stepLabel, String errorMessage) {
        this.id = id;
        this.tenantId = tenantId;
        this.status = status;
        this.name = name;
        this.totalSteps = totalSteps;
        this.completedSteps = completedSteps;
        this.startTime = startTime;
        this.endTime = endTime;
        this.canceledJob = canceledJob;
        this.stepMessage = stepMessage;
        this.stepMessages = stepMessages;
        this.message = message;
        this.summaryMessage = summaryMessage;
        this.stepLabel = stepLabel;
        this.errorMessage = errorMessage;
    }

    public JobProgress() {

    }

    /**
     * Contains information about current processing step.
     */
    public String stepMessage;
    /**
     * Contains all stepMessages
     */
    public List<String> stepMessages;
    /**
     * Contains detailed message of all the processed steps.
     */
    public String message;
    /**
     * Contains the final summary of job.
     */
    public String summaryMessage;
    /**
     * Contains the entity name of job.
     */
    public String stepLabel;
    /**
     * Contains the error message.
     */
    public String errorMessage;


}
