package com.thed.zephyr.cloud.rest.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

/**
 * Created by Kavya on 13-06-2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize
public class Teststep {

    public String id;
    public Long orderId;
    public Long issueId;
    public String step;
    public String data;
    public String result;
    public String createdBy;
    public String modifiedBy;
    public Date createdOn;
    public Date lastModifiedOn;

    public  Teststep() {

    }

    public Teststep(String id, Long orderId, Long issueId, String step, String data, String result, String createdBy, String modifiedBy, Date createdOn, Date lastModifiedOn) {
        this.id = id;
        this.orderId = orderId;
        this.issueId = issueId;
        this.step = step;
        this.data = data;
        this.result = result;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.createdOn = createdOn;
        this.lastModifiedOn = lastModifiedOn;
    }

    @Override
    public String toString() {
        return "{ " +
                "\"id\" : " + id +
                ", \"orderId\" : \"" + orderId + "\"" +
                ", \"issueId\" : \"" + issueId + "\"" +
                ", \"step\" : " + step +
                ", \"data\" : " + data +
                ", \"result\" : " + result +
                ", \"createdBy\" : " + createdBy +
                ", \"modifiedBy\" : " + modifiedBy +
                ", \"createdOn\" : \"" + createdOn + "\"" +
                ", \"lastModifiedOn\" : \"" + lastModifiedOn + "\" }";
    }
}
