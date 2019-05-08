package com.berkethetechnerd.surveypwp.helper;

/**
 * All URLs are stored in this class.
 */
public class ApiURL {

    private static final String API_BASE_URL = "http://45.76.39.46:5000/";

    public static String API_ALL_QUESTIONNAIRE() {
        return API_BASE_URL + "api/questionnaires/";
    }

    public static String API_POST_QUESTIONNAIRE() {
        return API_BASE_URL + "api/questionnaires/";
    }

    public static String API_GET_ONE_QUESTIONNAIRE(int questionnaire_id) {
        return API_BASE_URL + "api/questionnaires/" + questionnaire_id + "/";
    }

    public static String API_GET_QUESTIONS(int questionnaire_id) {
        return API_BASE_URL + "api/questionnaires/" + questionnaire_id + "/" + "questions/";
    }

    public static String API_POST_QUESTION(int questionnaire_id) {
        return API_BASE_URL + "api/questionnaires/" + questionnaire_id + "/" + "questions/";
    }

    public static String API_DELETE_QUESTIONNAIRE(int questionnaire_id) {
        return API_BASE_URL + "api/questionnaires/" + questionnaire_id + "/";
    }

    public static String API_EDIT_QUESTION(int questionnaire_id, int question_id) {
        return API_BASE_URL + "api/questionnaires/" + questionnaire_id + "/" + "questions/" + question_id + "/";
    }

    public static String API_DELETE_QUESTION(int questionnaire_id, int question_id) {
        return API_BASE_URL + "api/questionnaires/" + questionnaire_id + "/" + "questions/" + question_id + "/";
    }

    public static String API_GET_USER_ANSWERS(int questionnaire_id, String username) {
        return API_BASE_URL + "api/questionnaires/" + questionnaire_id + "/" + "answers/" + username + "/";
    }

    public static String API_POST_ANSWER(int questionnaire_id, int question_id) {
        return API_BASE_URL + "api/questionnaires/" + questionnaire_id + "/" + "questions/" + question_id + "/answers/";
    }
}