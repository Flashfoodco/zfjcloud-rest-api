package com.thed.zephyr.cloud.rest.util.json;

import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.thed.zephyr.cloud.rest.model.Teststep;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static com.thed.zephyr.cloud.rest.model.enam.TeststepFieldId.*;

/**
 * Created by Kavya on 13-06-2016.
 */
public class TeststepJsonParser implements JsonObjectParser<Teststep> {
    Logger log = LoggerFactory.getLogger(TeststepJsonParser.class);

    @Override
    public Teststep parse(JSONObject json) throws JSONException {
        String id = json.optString(ID.id);
        Long orderId = json.optLong(ORDER_ID.id);
        Long issueId = json.optLong(ISSUE_ID.id);
        String step = json.optString(STEP.id);
        String data = json.optString(DATA.id);
        String result = json.optString(RESULT.id);
        String createdBy = json.optString(CREATED_BY.id);
        String modifiedBy = json.optString(MODIFIED_BY.id);
        Date createdOn = json.has(CREATED_ON.id) ? new Date(json.getLong(CREATED_ON.id)) : null;
        Date lastModifiedOn = json.has(LAST_MODIFIED_ON.id) ? new Date(json.getLong(LAST_MODIFIED_ON.id)) : null;

        Teststep teststep = new Teststep(id, orderId, issueId, step, data, result, createdBy, modifiedBy, createdOn, lastModifiedOn);

        return teststep;
    }
}
