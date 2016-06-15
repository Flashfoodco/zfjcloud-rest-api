package com.thed.zephyr.cloud.rest.client;

import com.atlassian.jira.rest.client.internal.json.JsonObjectParser;
import com.thed.zephyr.cloud.rest.exception.BadRequestParamException;
import com.thed.zephyr.cloud.rest.model.Teststep;
import com.thed.zephyr.cloud.rest.util.ZFJConnectResults;
import org.apache.http.HttpException;
import org.codehaus.jettison.json.JSONException;

/**
 * Created by Kavya on 13-06-2016.
 */
public interface TeststepRestClient {
    Teststep createTeststep(Long projectId, Long issueId, Teststep teststep) throws JSONException, HttpException, BadRequestParamException;
    <T> T createTeststep(Long projectId, Long issueId, Teststep teststep, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException;

    ZFJConnectResults<Teststep> cloneTeststep(Long projectId, Long issueId, String teststepId, int position, Teststep teststep) throws BadRequestParamException, JSONException, HttpException;
    <T> ZFJConnectResults<T> cloneTeststep(Long projectId, Long issueId, String teststepId, int position, Teststep teststep, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException;

    Teststep getTeststep(Long projectId, Long issueId, String teststepId) throws BadRequestParamException, JSONException, HttpException;
    <T> T getTeststep(Long projectId, Long issueId, String teststepId, JsonObjectParser<T> jsonParser) throws BadRequestParamException, JSONException, HttpException;

    ZFJConnectResults<Teststep> getTeststeps(Long projectId, Long issueId) throws BadRequestParamException, JSONException, HttpException;
    <T> ZFJConnectResults<T> getTeststeps(Long projectId, Long issueId, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException;

    Teststep updateTeststep(Long projectId, Long issueId, String teststepId, Teststep teststep) throws BadRequestParamException, JSONException, HttpException;
    <T> T updateTeststep(Long projectId, Long issueId, String teststepId, Teststep teststep, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException;

    ZFJConnectResults<Teststep> deleteTeststep(Long projectId, Long issueId, String teststepId) throws BadRequestParamException, JSONException, HttpException;
    <T> ZFJConnectResults<T> deleteTeststep(Long projectId, Long issueId, String teststepId, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException;

    ZFJConnectResults<Teststep> moveTeststep(Long projectId, Long issueId, String teststepId) throws BadRequestParamException, JSONException, HttpException;
    <T> ZFJConnectResults<T> moveTeststep(Long projectId, Long issueId, String teststepId, JsonObjectParser<T> jsonParser) throws JSONException, HttpException, BadRequestParamException;
}
