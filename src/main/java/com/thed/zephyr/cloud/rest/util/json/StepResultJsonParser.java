package com.thed.zephyr.cloud.rest.util.json;

import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.thed.zephyr.cloud.rest.model.Attachment;
import com.thed.zephyr.cloud.rest.model.Defect;
import com.thed.zephyr.cloud.rest.model.ExecutionStatus;
import com.thed.zephyr.cloud.rest.model.StepResult;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static com.thed.zephyr.cloud.rest.model.enam.StepResultFieldId.*;

/**
 * Created by Kavya on 20-06-2016.
 */
public class StepResultJsonParser implements JsonObjectParser<StepResult> {
    Logger log = LoggerFactory.getLogger(StepResultJsonParser.class);

    JsonObjectParser<ExecutionStatus> executionStatusJsonObjectParser = new ExecutionStatusJsonParser();
    JsonObjectParser<Defect> defectJsonParser = new DefectJsonParser();
    JsonObjectParser<Attachment> attachmentJsonParser = new AttachmentJsonParser();

    @Override
    public StepResult parse(JSONObject json) throws JSONException {
        String id = json.optString(ID.id);
        Date executedOn = json.has(EXECUTED_ON.id) ? new Date(json.optLong(EXECUTED_ON.id)) : null;
        String comment = json.optString(COMMENT.id);
        String executedBy = json.optString(EXECUTED_BY.id);
        String executionId = json.optString(EXECUTION_ID.id);
        String stepId = json.optString(STEP_ID.id);
        ExecutionStatus status = json.optJSONObject(STATUS.id) != null ? executionStatusJsonObjectParser.parse(json.optJSONObject(STATUS.id)) : null;
        Collection<Defect> defects = parseDefectsArray(json, DEFECTS.id);
        Long issueId = json.optLong(ISSUE_ID.id);
        String createdBy = json.optString(CREATED_BY.id);
        String modifiedBy = json.optString(MODIFIED_BY.id);
        Collection<Attachment> attachments = parseAttachmentsArray(json, ATTACHMENTS.id);

        StepResult stepResult = new StepResult(id, executedOn, comment, executedBy, executionId, stepId, status, defects, issueId, createdBy, modifiedBy, attachments);
        return stepResult;
    }

    private Collection<Defect> parseDefectsArray(JSONObject jsonObject, String key) throws JSONException{
        final JSONArray valueObject = jsonObject.optJSONArray(key);
        if (valueObject == null) {
            return new ArrayList<Defect>();
        }
        Collection<Defect> result = new ArrayList<Defect>(valueObject.length());
        for (int i = 0; i < valueObject.length(); i++) {
            JSONObject json = valueObject.optJSONObject(i);
            if (json != null){
                result.add(defectJsonParser.parse(json));
            }

        }
        return result;
    }

    private Collection<Attachment> parseAttachmentsArray(JSONObject jsonObject, String key) throws JSONException{
        final JSONArray valueObject = jsonObject.optJSONArray(key);
        if (valueObject == null) {
            return new ArrayList<Attachment>();
        }
        Collection<Attachment> result = new ArrayList<Attachment>(valueObject.length());
        for (int i = 0; i < valueObject.length(); i++) {
            JSONObject json = valueObject.optJSONObject(i);
            if (json != null){
                result.add(attachmentJsonParser.parse(json));
            }

        }
        return result;
    }
}
