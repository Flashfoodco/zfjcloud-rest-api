package com.thed.zephyr.cloud.rest.util.json;

import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.thed.zephyr.cloud.rest.model.Attachment;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.thed.zephyr.cloud.rest.model.enam.AttachmentFieldId.*;

/**
 * Created by Kavya on 20-06-2016.
 */
public class AttachmentJsonParser implements JsonObjectParser<Attachment> {
    Logger log = LoggerFactory.getLogger(AttachmentJsonParser.class);

    @Override
    public Attachment parse(JSONObject json) throws JSONException {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String id = json.optString(ID.id);
            String name = json.optString(NAME.id);
            String fileExtension = json.optString(FILE_EXTENSION.id);
            Date createDate = !json.optString(CREATE_DATE.id).equals(null) ? dateFormat.parse(json.optString(CREATE_DATE.id)) : null;
            String createdBy = json.optString(CREATED_BY.id);
            Long size = json.optLong(SIZE.id);
            String comment = json.optString(COMMENT.id);
            String entityId = json.optString(ENTITY_ID.id);
            String entityType = json.optString(ENTITY_TYPE.id);
            String cycleId = json.optString(CYCLE_ID.id);
            Long versionId = json.optLong(VERSION_ID.id);

            Attachment attachment = new Attachment(id, name, fileExtension, createDate, createdBy, size, comment, entityId, entityType, cycleId, versionId);
            return attachment;
        } catch (ParseException e) {
            log.error("Error in parsing the creation date of the attachment", e);
        }
        return null;
    }
}
