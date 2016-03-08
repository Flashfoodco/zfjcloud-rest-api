package com.thed.zephyr.cloud.rest.client.model;

import com.atlassian.jira.rest.client.api.domain.Resolution;
import com.atlassian.jira.rest.client.api.domain.Status;

/**
 * Created by Kavya on 29-02-2016.
 */
public class Defect {
    public Long id;
    public String key;
    public String summary;
    public Status status;
    public Resolution resolution;
}
