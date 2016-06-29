package com.thed.zephyr.cloud.rest.util.json;

import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.thed.zephyr.cloud.rest.model.ZQLFilter;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.thed.zephyr.cloud.rest.model.enam.ZQLFilterFieldId.*;

/**
 * Created by Kavya on 27-06-2016.
 */
public class ZQLFilterJsonParser implements JsonObjectParser<ZQLFilter> {
    Logger log = LoggerFactory.getLogger(ZQLFilterJsonParser.class);

    @Override
    public ZQLFilter parse(JSONObject json) throws JSONException {
        String id = json.optString(ID.id);
        String name = json.optString(NAME.id);
        String tenantId = json.optString(TENANT_ID.id);
        String createdBy = json.optString(CREATED_BY.id);
        Date createdOn = json.has(CREATED_ON.id) ? new Date(json.getLong(CREATED_ON.id)) : null;
        String zql = json.optString(ZQL.id);
        String sharePerm = json.optString(SHARE_PERM.id);
        ZQLFilter zqlFilter = new ZQLFilter(id, tenantId, name, createdBy, createdOn, zql, sharePerm);
        zqlFilter.currentIndex = json.optInt("currentIndex");
        zqlFilter.description = json.optString("description");
        zqlFilter.executionCount = json.optLong("executionCount");
        zqlFilter.favorite = json.getBoolean("favorite");
        if (json.has("linksNew")) {
            List<Integer> linksNew = new ArrayList<Integer>();
            for (int i = 0; i<json.optJSONArray("linksNew").length(); i++) {
                linksNew.add(json.getJSONArray("linksNew").getInt(i));
            }
            zqlFilter.linksNew = linksNew;
        }
        if (json.has("markedFavoriteBy")) {
            Set<String> markedFavoriteBy = new HashSet<String>();
            for(int i = 0; i<json.optJSONArray("markedFavoriteBy").length(); i++) {
                markedFavoriteBy.add(json.optJSONArray("markedFavoriteBy").optString(i));
            }
            zqlFilter.markedFavoriteBy = markedFavoriteBy;
        }
        zqlFilter.maxResultAllowed = json.optInt("maxResultAllowed");
        zqlFilter.popularity = json.optInt("popularity");
        zqlFilter.totalCount = json.optInt("totalCount");
        zqlFilter.updatedBy = json.optString("updatedBy");
        zqlFilter.updatedOn = json.has("updatedOn") ? new Date(json.getLong("updatedOn")) : null;

        return zqlFilter;
    }
}
