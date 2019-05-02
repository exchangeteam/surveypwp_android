package com.berkethetechnerd.surveypwp.helper;

/**
 * All URLs are stored in this class.
 */
public class ApiURL {

    private static final String API_BASE_URL = "http://45.76.39.46:5000/";
    public static final String API_ALL_QUESTIONNAIRE = API_BASE_URL + "api/questionnaires/";

    public static String API_GET_QUESTIONS(int questionnaire_id) {
        return API_ALL_QUESTIONNAIRE + questionnaire_id + "/questions/";
    }
}