package com.thed.zephyr.cloud.rest.client;

import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.thed.zephyr.cloud.rest.exception.BadRequestParamException;
import com.thed.zephyr.cloud.rest.model.ZQLFilter;
import org.apache.http.HttpException;
import org.codehaus.jettison.json.JSONException;

import java.util.List;

/**
 * Created by Kavya on 27-06-2016.
 */
public interface ZQLFiletrRestClient {

    ZQLFilter getFilter(String filterId) throws BadRequestParamException, JSONException, HttpException;
    <T> T getFilter(String filterId, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException;

    List<ZQLFilter> getFilters(boolean byUser, boolean fav, int offset, int maxRecords) throws HttpException, JSONException;
    <T> List<T> getFilters(boolean byUser, boolean fav, int offset, int maxRecords, JsonObjectParser<T> jsonParser) throws HttpException, JSONException;

    List<ZQLFilter> quickSearch(String query) throws HttpException, JSONException;
    <T> List<T> quickSearch(String query, JsonObjectParser<T> jsonParser) throws HttpException, JSONException;

    List<ZQLFilter> search(String name, String owner, String sharePerm) throws HttpException, JSONException;
    <T> List<T> search(String name, String owner, String sharePerm, JsonObjectParser<T> jsonParser) throws HttpException, JSONException;

    boolean deleteFilter(String filterId) throws HttpException, BadRequestParamException;

    ZQLFilter saveFilter(ZQLFilter zqlFilter) throws BadRequestParamException, JSONException, HttpException;
    <T> T saveFilter(ZQLFilter zqlFilter, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException;

    ZQLFilter updateFilter(String filterId, ZQLFilter zqlFilter) throws BadRequestParamException, JSONException, HttpException;
    <T> T updateFilter(String filterId, ZQLFilter zqlFilter, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException;

    ZQLFilter copyFilter(ZQLFilter zqlFilter) throws BadRequestParamException, JSONException, HttpException;
    <T> T copyFilter(ZQLFilter zqlFilter, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException;

    ZQLFilter toggleFavorite(String filterId) throws BadRequestParamException, JSONException, HttpException;
    <T> T toggleFavorite(String filterId, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException;
}
