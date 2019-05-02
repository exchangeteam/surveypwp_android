package com.berkethetechnerd.surveypwp.ws;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.StringRequest;
import com.berkethetechnerd.surveypwp.ApplicationClass;
import com.berkethetechnerd.surveypwp.helper.ApiURL;
import com.berkethetechnerd.surveypwp.model.ApiResultAllAnswers;
import com.berkethetechnerd.surveypwp.model.ApiResultAllQuestionnaire;
import com.berkethetechnerd.surveypwp.model.ApiResultAllQuestions;
import com.berkethetechnerd.surveypwp.model.ApiResultNoData;
import com.berkethetechnerd.surveypwp.model.ApiResultOneQuestionnaire;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SurveyAPI {

    // The coreApi instance for the application.
    private static CoreApi coreApi = ApplicationClass.getCoreApi();

    public static void getQuestionnaire(Response.Listener<ApiResultAllQuestionnaire> successListener,
                                        Response.ErrorListener errorListener) {
        String URL = ApiURL.API_ALL_QUESTIONNAIRE();

        Map<String, String> headers = new HashMap<>();
        Map<String, String> params = new HashMap<>();

        GsonRequest<ApiResultAllQuestionnaire> request = new GsonRequest<>(Request.Method.GET, URL,
                ApiResultAllQuestionnaire.class, headers, params, successListener, errorListener);

        coreApi.getRequestQueue().getCache().clear();
        coreApi.addToRequestQueue(request);
    }

    public static void addQuestionnaire(final String title, final String description,
                                        Response.Listener<JSONObject> successListener,
                                        Response.ErrorListener errorListener) {
        String URL = ApiURL.API_ALL_QUESTIONNAIRE();

        JSONObject JRequestObject = new JSONObject();
        try {
            JRequestObject.put("title", title);
            JRequestObject.put("description", description);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL,
                JRequestObject, successListener, errorListener);

        coreApi.getRequestQueue().getCache().clear();
        coreApi.addToRequestQueue(request);
    }

    public static void getOneQuestionnaire(final int id, Response.Listener<ApiResultOneQuestionnaire>
                                           successListener, Response.ErrorListener errorListener) {
        String URL = ApiURL.API_ONE_QUESTIONNAIRE(id);

        Map<String, String> headers = new HashMap<>();
        Map<String, String> params = new HashMap<>();

        GsonRequest<ApiResultOneQuestionnaire> request = new GsonRequest<>(Request.Method.GET, URL,
                ApiResultOneQuestionnaire.class, headers, params, successListener, errorListener);

        coreApi.getRequestQueue().getCache().clear();
        coreApi.addToRequestQueue(request);
    }

    public static void getQuestions(final int id, Response.Listener<ApiResultAllQuestions> successListener,
                                    Response.ErrorListener errorListener) {
        String URL = ApiURL.API_GET_QUESTIONS(id);

        Map<String, String> headers = new HashMap<>();
        Map<String, String> params = new HashMap<>();

        GsonRequest<ApiResultAllQuestions> request = new GsonRequest<>(Request.Method.GET, URL,
                ApiResultAllQuestions.class, headers, params, successListener, errorListener);

        coreApi.getRequestQueue().getCache().clear();
        coreApi.addToRequestQueue(request);
    }

    public static void addQuestion(final int id, final String title, final String description,
                                   Response.Listener<JSONObject> successListener,
                                   Response.ErrorListener errorListener) {
        String URL = ApiURL.API_POST_QUESTION(id);

        JSONObject JRequestObject = new JSONObject();
        try {
            JRequestObject.put("title", title);
            JRequestObject.put("description", description);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL,
                JRequestObject, successListener, errorListener);

        coreApi.getRequestQueue().getCache().clear();
        coreApi.addToRequestQueue(request);
    }

    public static void getAnswers(final int id, Response.Listener<ApiResultAllAnswers> successListener,
                                  Response.ErrorListener errorListener) {
        String URL = ApiURL.API_GET_QUESTIONS(id);

        Map<String, String> headers = new HashMap<>();
        Map<String, String> params = new HashMap<>();

        GsonRequest<ApiResultAllAnswers> request = new GsonRequest<>(Request.Method.GET, URL,
                ApiResultAllAnswers.class, headers, params, successListener, errorListener);

        coreApi.getRequestQueue().getCache().clear();
        coreApi.addToRequestQueue(request);
    }

    public static void deleteQuestionnaire(final int id, Response.Listener<ApiResultNoData> successListener,
                                           Response.ErrorListener errorListener) {
        String URL = ApiURL.API_DELETE_QUESTIONNAIRE(id);

        Map<String, String> headers = new HashMap<>();
        Map<String, String> params = new HashMap<>();

        GsonRequest<ApiResultNoData> request = new GsonRequest<>(Request.Method.DELETE, URL,
                ApiResultNoData.class, headers, params, successListener, errorListener);

        coreApi.getRequestQueue().getCache().clear();
        coreApi.addToRequestQueue(request);
    }

    public static void editQuestion(final int questionnaire_id, final int question_id,
                                    final String title, final String description,
                                    Response.Listener<JSONObject> successListener,
                                    Response.ErrorListener errorListener) {
        String URL = ApiURL.API_EDIT_QUESTION(questionnaire_id, question_id);

        JSONObject JRequestObject = new JSONObject();
        try {
            JRequestObject.put("title", title);
            JRequestObject.put("description", description);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, URL,
                JRequestObject, successListener, errorListener);

        coreApi.getRequestQueue().getCache().clear();
        coreApi.addToRequestQueue(request);
    }
}
