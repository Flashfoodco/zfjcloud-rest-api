package com.thed.zephyr.cloud.rest.model;

import java.util.Date;

/**
 * Created by Kavya on 16-06-2016.
 */
public class Attachment {
    public String id;
    public String name;
    public String fileExtension;
    public Date createDate;
    public String createdBy;
    public Long size;
    public String comment;
    public String entityId;
    public String entityType;
    public String cycleId;
    public Long versionId;

    public Attachment() {
    }

    public Attachment(String id, String name, String fileExtension, Date createDate, String createdBy, Long size, String comment, String entityId, String entityType, String cycleId, Long versionId) {
        this.id = id;
        this.name = name;
        this.fileExtension = fileExtension;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.size = size;
        this.comment = comment;
        this.entityId = entityId;
        this.entityType = entityType;
        this.cycleId = cycleId;
        this.versionId = versionId;
    }

    @Override
    public String toString() {
        return "{" +
                "id : \"" + id + "\"" +
                ", name : \"" + name + "\"" +
                ", fileExtension : \"" + fileExtension + "\"" +
                ", createDate : " + createDate +
                ", createdBy : \"" + createdBy + "\"" +
                ", size : " + size +
                ", comment : \"" + comment + "\"" +
                ", entityId : \"" + entityId + "\"" +
                ", entityType : \"" + entityType + "\"" +
                ", cycleId : \"" + cycleId + "\"" +
                ", versionId : " + versionId +
                "}";
    }
}
