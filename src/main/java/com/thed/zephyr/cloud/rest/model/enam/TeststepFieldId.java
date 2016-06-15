package com.thed.zephyr.cloud.rest.model.enam;

/**
 * Created by Kavya on 13-06-2016.
 */
public enum TeststepFieldId {
    ID("id"),
    ORDER_ID("orderId"),
    ISSUE_ID("issueId"),
    STEP("step"),
    DATA("data"),
    RESULT("result"),
    CREATED_BY("createdBy"),
    MODIFIED_BY("modifiedBy"),
    CREATED_ON("createdOn"),
    LAST_MODIFIED_ON("lastModifiedOn");

    public final String id;

    TeststepFieldId(String id) {
        this.id = id;
    }
}
