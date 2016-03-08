package com.thed.zephyr.cloud.rest.client.model;

import com.atlassian.httpclient.api.EntityBuilder;
import com.atlassian.httpclient.api.Response;
import com.atlassian.httpclient.api.ResponsePromise;
import com.atlassian.httpclient.api.ResponseTransformation;
import com.google.common.base.Function;
import com.thed.zephyr.cloud.rest.client.ZephyrRestClient;
import com.thed.zephyr.cloud.rest.client.ZephyrRestClientUtil;
import org.apache.http.HttpException;
import org.apache.http.HttpStatus;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.annotation.Nullable;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Kavya on 18-02-2016.
 */
public class GenericEntityUtil {

    private static JobProgress jobProgress;

    public static EntityBuilder toEntity(final Object o) {

        final EntityBuilder entityBuilder = new EntityBuilder() {
            @Override
            public Entity build() {
                Entity entity = new Entity() {
                    @Override
                    public Map<String, String> getHeaders() {
                        return Collections.singletonMap("Content-Type", "application/json");
                    }

                    @Override
                    public InputStream getInputStream() {
                        try {
                            return new ByteArrayInputStream(new ObjectMapper().writeValueAsString(o).toString().getBytes(Charset.forName("UTF-8")));
                        } catch (JsonGenerationException e) {
                            e.printStackTrace();
                        } catch (JsonMappingException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                };
                return entity;
            }
        };
        return entityBuilder;
    }

    public static JSONObject parseJsonResponse(ResponsePromise responsePromise) throws JSONException, HttpException {
        ResponseTransformation<Object> responseTransformation = responsePromise.transform().done(new Function<Response, Object>() {
            @Nullable
            @Override
            public Object apply(@Nullable Response input) {
                if (input.isOk() || input.getStatusCode() == HttpStatus.SC_ACCEPTED || input.isCreated() || input.getStatusCode() == HttpStatus.SC_CONTINUE) {
                    String entity = input.getEntity();
                    if (isValidJson(entity))
                        return new com.fasterxml.jackson.databind.ObjectMapper().convertValue(entity, JSONObject.class);
                    return entity;
                }
                Logger.getGlobal().log(Level.SEVERE, input.getStatusText());
                return input;
            }
        });

        Object response = responseTransformation.claim();

        if (response instanceof JSONObject) {
            return (JSONObject) response;
        }
        Response errorResponse = (Response) response;
        Logger.getGlobal().log(Level.SEVERE, "Error in request: " + errorResponse.getStatusCode() + " " + errorResponse.getStatusText());
        throw new HttpException(errorResponse.getStatusCode() + " " + errorResponse.getStatusText());
    }

    public static JSONArray parseJsonArrayResponse(ResponsePromise responsePromise) throws JSONException, HttpException {
        Object response = responsePromise.transform().done(new Function<Response, Object>() {

            @Nullable
            @Override
            public Object apply(@Nullable Response input) {
                if(input.isOk() || input.getStatusCode() == HttpStatus.SC_ACCEPTED ||  input.isCreated() || input.getStatusCode() == HttpStatus.SC_CONTINUE) {
                    String entity = input.getEntity();
                    if (isValidJsonArray(entity))
                        return new com.fasterxml.jackson.databind.ObjectMapper().convertValue(entity, JSONArray.class);
                    return entity;
                }
                Logger.getGlobal().log(Level.SEVERE, input.getStatusText());
                return input;
            }
        }).claim();
        if (response instanceof JSONArray) {
            return (JSONArray) response;
        }
        Response errorResponse = (Response) response;
        Logger.getGlobal().log(Level.SEVERE, "Error in request: " + errorResponse.getStatusCode() + " " + errorResponse.getStatusText());
        throw new HttpException(errorResponse.getStatusCode() + " " + errorResponse.getStatusText());
    }

    public static Boolean parseBooleanResponse(ResponsePromise responsePromise) throws JSONException, HttpException {
        Object response = responsePromise.transform().done(new Function<Response, Object>() {

            @Nullable
            @Override
            public Object apply(@Nullable Response input) {
                if(input.isOk() || input.getStatusCode() == HttpStatus.SC_ACCEPTED ||  input.isCreated() || input.getStatusCode() == HttpStatus.SC_CONTINUE) {
                    String entity = input.getEntity();
                    return Boolean.valueOf(entity);
                }
                Logger.getGlobal().log(Level.SEVERE, input.getStatusText());
                return input;
            }
        }).claim();
        if (response instanceof Boolean) {
            return (Boolean) response;
        }
        Response errorResponse = (Response) response;
        Logger.getGlobal().log(Level.SEVERE, "Error in request: " + errorResponse.getStatusCode() + " " + errorResponse.getStatusText());
        throw new HttpException(errorResponse.getStatusCode() + " " + errorResponse.getStatusText());
    }

    public static String parseJobProgressResponse(ResponsePromise responsePromise) throws JSONException, HttpException {
        Object response = responsePromise.transform().done(new Function<Response, Object>() {

            @Nullable
            @Override
            public Object apply(@Nullable Response input) {
                if (input.isOk() || input.getStatusCode() == HttpStatus.SC_ACCEPTED || input.isCreated() || input.getStatusCode() == HttpStatus.SC_CONTINUE) {
                    String entity = input.getEntity();
                    return entity;
                }
                Logger.getGlobal().log(Level.SEVERE, input.getStatusText());
                return input;
            }
        }).claim();
        if (response instanceof String) {
            while(jobProgress == null || jobProgress.summaryMessage == null) {
                ZephyrRestClient client = ZephyrRestClientUtil.getClient();
                try {
                    JSONObject job = client.getJobProgressRestClient().getJobProgress(String.valueOf(response));
                    if (job.getLong("progress") == 1) {
                        jobProgress = new JobProgress();
                        jobProgress.message = job.getString("message");
                        jobProgress.stepMessage = job.getString("stepMessage");
                        jobProgress.summaryMessage = job.getString("summaryMessage");
                    }
                    Thread.sleep(500L);
                } catch (HttpException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (jobProgress != null && jobProgress.summaryMessage != null) {
                    return jobProgress.summaryMessage;
                }
        }
        return null;
    }

    private static void callShutDown(ScheduledExecutorService pool) {
        if (jobProgress != null && jobProgress.summaryMessage != null) {
            pool.shutdown();
        }
    }


    public static JSONObject parseJobProgressPromiseResponse(ResponsePromise responsePromise) throws HttpException {
        Object response = responsePromise.transform().done(new Function<Response, Object>() {

            @Nullable
            @Override
            public Object apply(@Nullable Response input) {
                if(input.isOk() || input.getStatusCode() == HttpStatus.SC_ACCEPTED ||  input.isCreated() || input.getStatusCode() == HttpStatus.SC_CONTINUE) {
                    String entity = input.getEntity();
                    if (isValidJson(entity))
                        return new com.fasterxml.jackson.databind.ObjectMapper().convertValue(entity, JSONObject.class);
                    return entity;
                }
                Logger.getGlobal().log(Level.SEVERE, input.getStatusText());
                return input;
            }
        }).claim();
        if (response instanceof JSONObject) {
            return (JSONObject) response;
        }
        Response errorResponse = (Response) response;
        Logger.getGlobal().log(Level.SEVERE, "Error in request: " + errorResponse.getStatusCode() + " " + errorResponse.getStatusText());
        throw new HttpException(errorResponse.getStatusCode() + " " + errorResponse.getStatusText());
    }

    public static File parseFileResponse(ResponsePromise responsePromise, String fileName) throws HttpException {
        return (File)responsePromise.transform().done(new Function<Response, Object>() {

            @Nullable
            @Override
            public Object apply(@Nullable Response input) {
                if(input.isOk() || input.getStatusCode() == HttpStatus.SC_ACCEPTED ||  input.isCreated() || input.getStatusCode() == HttpStatus.SC_CONTINUE) {
                    String entity = input.getEntity();
                    if(input.getContentType().equals("application/x-download")) {
                        try {
                            File tempFile = new File(fileName);
                            BufferedWriter output = new BufferedWriter(new FileWriter(tempFile));
                            output.write(entity);
                            return tempFile;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return new File("");
                }
                Logger.getGlobal().log(Level.SEVERE, input.getStatusText());
                return input;
            }
        }).claim();
    }

    private static boolean isValidJson(String entity) {
        try {
            new JSONObject(entity);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }

    private static boolean isValidJsonArray(String entity) {
        try {
            new JSONArray(entity);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }

}
