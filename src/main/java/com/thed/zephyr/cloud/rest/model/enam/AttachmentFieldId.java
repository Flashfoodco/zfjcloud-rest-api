package com.thed.zephyr.cloud.rest.model.enam;

/**
 * Created by Kavya on 20-06-2016.
 */
public enum AttachmentFieldId {

    ID("id"),
    NAME("name"),
    FILE_EXTENSION("fileExtension"),
    CREATE_DATE("createDate"),
    CREATED_BY("createdBy"),
    SIZE("size"),
    COMMENT("comment"),
    ENTITY_ID("entityId"),
    ENTITY_TYPE("entityType"),
    CYCLE_ID("cycleId"),
    VERSION_ID("versionId");

    public final String id;

    AttachmentFieldId(String id) {
        this.id = id;
    }
}
