package com.thed.zephyr.cloud.rest.client.util.json;

import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.thed.zephyr.cloud.rest.client.model.Cycle;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.Date;

import static com.thed.zephyr.cloud.rest.client.model.enam.CycleFieldId.*;

/**
 * Created by Kavya on 21-03-2016.
 */
public class CycleJsonParser implements JsonObjectParser<Cycle> {
    @Override
    public Cycle parse(JSONObject jsonObject) throws JSONException {
        String id = jsonObject.optString(ID.id);
        String name = jsonObject.optString(NAME.id);
        String environment = jsonObject.optString(ENVIRONMENT.id);
        String build = jsonObject.optString(BUILD.id);
        Long versionId = jsonObject.optLong(VERSION_ID.id) != 0L ? jsonObject.optLong(VERSION_ID.id) : null;
        Long projectId = jsonObject.optLong(PROJECT_ID.id) != 0L ? jsonObject.optLong(PROJECT_ID.id) : null;
        Date startDate = jsonObject.optLong(START_DATE.id) != 0L ? new Date(jsonObject.optLong(START_DATE.id)) : null;
        Date endDate = jsonObject.optLong(END_DATE.id) != 0L ? new Date(jsonObject.optLong(END_DATE.id)) : null;
        String description = jsonObject.optString(DESCRIPTION.id);

        Cycle cycle = new Cycle(id, name, environment, build, versionId, projectId, startDate, endDate, description);

        return cycle;
    }
}
