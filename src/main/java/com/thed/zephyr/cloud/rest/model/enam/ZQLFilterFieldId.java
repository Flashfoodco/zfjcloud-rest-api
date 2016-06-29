package com.thed.zephyr.cloud.rest.model.enam;

/**
 * Created by Kavya on 27-06-2016.
 */
public enum ZQLFilterFieldId {
    ID("id"),
    TENANT_ID("tId"),
    NAME("name"),
    CREATED_BY("createdBy"),
    CREATED_ON("createdOn"),
    ZQL("zql"),
    SHARE_PERM("sharePerm");
    
    public final String id;

    ZQLFilterFieldId(String id) {
        this.id = id;
    }
}
