package com.berkethetechnerd.surveypwp.ws;

import com.android.volley.Request;
import com.android.volley.Response;
import com.berkethetechnerd.surveypwp.ApplicationClass;
import com.berkethetechnerd.surveypwp.helper.ApiURL;
import com.berkethetechnerd.surveypwp.model.ApiResultAllQuestionnaire;

import java.util.HashMap;
import java.util.Map;

public class SurveyAPI {

    // The coreApi instance for the application.
    private static CoreApi coreApi = ApplicationClass.getCoreApi();

    public static void getQuestionnaire(Response.Listener<ApiResultAllQuestionnaire> successListener,
                                        Response.ErrorListener errorListener) {
        String URL = ApiURL.API_ALL_QUESTIONNAIRE;

        Map<String, String> headers = new HashMap<>();
        Map<String, String> params = new HashMap<>();

        GsonRequest<ApiResultAllQuestionnaire> request = new GsonRequest<>(Request.Method.GET, URL,
                ApiResultAllQuestionnaire.class, headers, params, successListener, errorListener);

        coreApi.getRequestQueue().getCache().clear();
        coreApi.addToRequestQueue(request);
    }
}
