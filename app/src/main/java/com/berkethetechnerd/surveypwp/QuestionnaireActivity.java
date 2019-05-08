package com.berkethetechnerd.surveypwp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.berkethetechnerd.surveypwp.fragment.FragmentAnswerQuestionnaire;
import com.berkethetechnerd.surveypwp.fragment.FragmentCreateQuestionnaire;
import com.berkethetechnerd.surveypwp.fragment.FragmentEditQuestionnaire;
import com.berkethetechnerd.surveypwp.helper.SharedPrefHelper;
import com.berkethetechnerd.surveypwp.model.ApiResultAllAnswers;
import com.berkethetechnerd.surveypwp.model.ApiResultAllQuestions;
import com.berkethetechnerd.surveypwp.model.ApiResultNoData;
import com.berkethetechnerd.surveypwp.model.ApiResultOneQuestionnaire;
import com.berkethetechnerd.surveypwp.model.ModelAnswer;
import com.berkethetechnerd.surveypwp.model.ModelQuestion;
import com.berkethetechnerd.surveypwp.ws.SurveyAPI;

import org.json.JSONObject;

import java.util.ArrayList;

public class QuestionnaireActivity extends AppCompatActivity
    implements FragmentCreateQuestionnaire.OnFragmentInteractionListener,
    FragmentEditQuestionnaire.OnFragmentInteractionListener,
    FragmentAnswerQuestionnaire.OnFragmentInteractionListener {

    private final String TAG_CREATE_QUESTIONNAIRE = "create_questionnaire";
    private final String TAG_EDIT_QUESTIONNAIRE = "edit_questionnaire";
    private final String TAG_ANSWER_QUESTIONNAIRE = "answer_questionnaire";

    private final String INTENT_QUESTIONNAIRE_TAG = "fragment_type";
    private final int INTENT_CREATE_QUESTIONNAIRE = 0;
    private final int INTENT_EDIT_QUESTIONNAIRE = 1;
    private final int INTENT_ANSWER_QUESTIONNAIRE = 2;
    private final int INTENT_SEE_ANSWER_OF_USER = 3;

    private final String INTENT_QUESTIONNAIRE_EXTRA_TITLE = "questionnaire_title";
    private final String INTENT_QUESTIONNAIRE_EXTRA_DESCRIPTION = "questionnaire_description";
    private final String INTENT_QUESTIONNAIRE_EXTRA_USERNAME = "questionnaire_username";
    private final String INTENT_QUESTIONNAIRE_EXTRA_ID = "questionnaire_id";

    private String Qtitle;
    private String Qdescription;
    private String Qusername;
    private int Qid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        Intent callerIntent = getIntent();
        int typeOfCall = callerIntent.getIntExtra(INTENT_QUESTIONNAIRE_TAG, 0);

        switch (typeOfCall) {
            case INTENT_CREATE_QUESTIONNAIRE:
                Qtitle = callerIntent.getStringExtra(INTENT_QUESTIONNAIRE_EXTRA_TITLE);
                Qdescription = callerIntent.getStringExtra(INTENT_QUESTIONNAIRE_EXTRA_DESCRIPTION);
                SurveyAPI.postQuestionnaire(Qtitle, Qdescription, postQuestionnaireSuccessListener, postQuestionnaireErrorListener);

                break;

            case INTENT_EDIT_QUESTIONNAIRE:
                Qid = callerIntent.getIntExtra(INTENT_QUESTIONNAIRE_EXTRA_ID, 0);
                SurveyAPI.getOneQuestionnaire(Qid, getOneQuestionnaireSuccessListener, getOneQuestionnaireErrorListener);

                break;

            case INTENT_ANSWER_QUESTIONNAIRE:
                Qusername = callerIntent.getStringExtra(INTENT_QUESTIONNAIRE_EXTRA_USERNAME);
                Qid = callerIntent.getIntExtra(INTENT_QUESTIONNAIRE_EXTRA_ID, 0);
                SurveyAPI.getOneQuestionnaire(Qid, getOneQuestionnaireAnswerSuccessListener, getOneQuestionnaireAnswerErrorListener);

                break;

            case INTENT_SEE_ANSWER_OF_USER:
                Qusername = callerIntent.getStringExtra(INTENT_QUESTIONNAIRE_EXTRA_USERNAME);
                Qid = callerIntent.getIntExtra(INTENT_QUESTIONNAIRE_EXTRA_ID, 0);
                SurveyAPI.getOneQuestionnaire(Qid, getOneQuestionnaireUserSuccessListener, getOneQuestionnaireUserErrorListener);

                break;
        }
    }

    @Override
    public void submitQuestionnaire(ArrayList<ModelQuestion> listOfQuestions, int questionnaireID) {
        for(ModelQuestion question: listOfQuestions) {
            String title = question.getTitle();
            String description = question.getDescription();

            SurveyAPI.addQuestion(questionnaireID, title, description, addQuestionToQuestionnaireSuccessListener, addQuestionToQuestionnaireErrorListener);
        }

        Toast.makeText(getApplicationContext(), "Received the submit", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void deleteQuestionnaire(final int id) {
        SurveyAPI.deleteQuestionnaire(id, deleteQuestionnaireSuccessListener, deleteQuestionnaireErrorListener);
    }

    @Override
    public void submitEditQuestionnaire(ArrayList<ModelQuestion> listOfQuestions, int questionnaireID) {
        for(ModelQuestion question: listOfQuestions) {
            String title = question.getTitle();
            String description = question.getDescription();
            int question_id = question.getId();

            if(question.isDeleted()) {
                SurveyAPI.deleteQuestion(questionnaireID, question_id, deleteQuestionSuccessListener, deleteQuestionErrorListener);
            } else {
                if(question_id == 0) {
                    SurveyAPI.addQuestion(questionnaireID, title, description, addQuestionToQuestionnaireSuccessListener, addQuestionToQuestionnaireErrorListener);
                } else {
                    SurveyAPI.editQuestion(questionnaireID, question_id, title, description, editQuestionnaireSuccessListener, editQuestionnaireErrorListener);
                }
            }
        }

        Toast.makeText(getApplicationContext(), "Received the submit", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void submitAnswerQuestionnaire(ArrayList<ModelQuestion> listOfAnswers, String username, int questionnaireID) {
        for(ModelQuestion answer: listOfAnswers) {
            SurveyAPI.addUserAnswer(questionnaireID, answer.getId(), username, answer.getContent(),
                    addAnswerSuccessListener, addAnswerErrorListener);
        }

        Toast.makeText(getApplicationContext(), "Received the submit", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void returnHomePage() {
        finish();
    }

    private Response.Listener<JSONObject> postQuestionnaireSuccessListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            // The server does not return JSONObject as response...
        }
    };

    private Response.ErrorListener postQuestionnaireErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            int numOfQuestionnaire = SharedPrefHelper.getNumQuestionnaire(getApplicationContext());
            SharedPrefHelper.setNumQuestionnaire(getApplicationContext(), ++numOfQuestionnaire);

            FragmentCreateQuestionnaire fragmentCreateQuestionnaire = FragmentCreateQuestionnaire.newInstance(
                    Qtitle, Qdescription, numOfQuestionnaire
            );

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.questionnaire_content, fragmentCreateQuestionnaire, TAG_CREATE_QUESTIONNAIRE)
                    .commit();
        }
    };

    private Response.Listener<ApiResultOneQuestionnaire> getOneQuestionnaireSuccessListener = new Response.Listener<ApiResultOneQuestionnaire>() {
        @Override
        public void onResponse(ApiResultOneQuestionnaire response) {
            Qtitle = response.getTitle();
            Qdescription = response.getDescription();

            SurveyAPI.getQuestions(Qid, getOneQuestionnaireQuestionsSuccessListener, getOneQuestionnaireQuestionsErrorListener);
        }
    };

    private Response.ErrorListener getOneQuestionnaireErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // No error handling...
        }
    };

    private Response.Listener<ApiResultAllQuestions> getOneQuestionnaireQuestionsSuccessListener = new Response.Listener<ApiResultAllQuestions>() {
        @Override
        public void onResponse(ApiResultAllQuestions response) {
            FragmentEditQuestionnaire fragmentEditQuestionnaire = FragmentEditQuestionnaire.newInstance(
                    Qtitle, Qdescription, Qid
            );

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.questionnaire_content, fragmentEditQuestionnaire, TAG_EDIT_QUESTIONNAIRE)
                    .commit();

            fragmentEditQuestionnaire.setQuestionData(response.getItems());
        }
    };

    private Response.ErrorListener getOneQuestionnaireQuestionsErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // No error handling...
        }
    };

    private Response.Listener<ApiResultOneQuestionnaire> getOneQuestionnaireAnswerSuccessListener = new Response.Listener<ApiResultOneQuestionnaire>() {
        @Override
        public void onResponse(ApiResultOneQuestionnaire response) {
            Qtitle = response.getTitle();
            Qdescription = response.getDescription();

            FragmentAnswerQuestionnaire fragmentAnswerQuestionnaire = FragmentAnswerQuestionnaire.newInstance(
                    Qtitle, Qdescription, Qusername, Qid
            );

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.questionnaire_content, fragmentAnswerQuestionnaire, TAG_ANSWER_QUESTIONNAIRE)
                    .commit();

            SurveyAPI.getQuestions(Qid, getOneQuestionnaireQuestionsAnswerSuccessListener, getOneQuestionnaireQuestionsAnswerErrorListener);
        }
    };

    private Response.ErrorListener getOneQuestionnaireAnswerErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // No error handling...
        }
    };

    private Response.Listener<ApiResultAllQuestions> getOneQuestionnaireQuestionsAnswerSuccessListener = new Response.Listener<ApiResultAllQuestions>() {
        @Override
        public void onResponse(ApiResultAllQuestions response) {
            FragmentAnswerQuestionnaire fragment = (FragmentAnswerQuestionnaire) getSupportFragmentManager().findFragmentByTag(TAG_ANSWER_QUESTIONNAIRE);

            if(fragment != null) {
                fragment.setQuestionData(response.getItems());
            }
        }
    };

    private Response.ErrorListener getOneQuestionnaireQuestionsAnswerErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // No error handling...
        }
    };

    private Response.Listener<ApiResultOneQuestionnaire> getOneQuestionnaireUserSuccessListener = new Response.Listener<ApiResultOneQuestionnaire>() {
        @Override
        public void onResponse(ApiResultOneQuestionnaire response) {
            Qtitle = response.getTitle();
            Qdescription = response.getDescription();

            FragmentAnswerQuestionnaire fragmentAnswerQuestionnaire = FragmentAnswerQuestionnaire.newInstance(
                    Qtitle, Qdescription, Qusername, Qid
            );

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.questionnaire_content, fragmentAnswerQuestionnaire, TAG_ANSWER_QUESTIONNAIRE)
                    .commit();

            SurveyAPI.getQuestions(Qid, getOneQuestionnaireQuestionsUserSuccessListener, getOneQuestionnaireQuestionsUserErrorListener);
        }
    };

    private Response.ErrorListener getOneQuestionnaireUserErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            int errorCode = error.networkResponse.statusCode;

            if(errorCode == 404) {
                String msg = "The questionnaire does not exist!";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }

            finish();
        }
    };

    private Response.Listener<ApiResultAllQuestions> getOneQuestionnaireQuestionsUserSuccessListener = new Response.Listener<ApiResultAllQuestions>() {
        @Override
        public void onResponse(ApiResultAllQuestions response) {
            FragmentAnswerQuestionnaire fragment = (FragmentAnswerQuestionnaire) getSupportFragmentManager().findFragmentByTag(TAG_ANSWER_QUESTIONNAIRE);

            if(fragment != null) {
                fragment.setQuestionData(response.getItems());
                SurveyAPI.getUserAnswers(Qid, Qusername, getUserAnswersSuccessListener, getUserAnswersErrorListener);
            }
        }
    };

    private Response.ErrorListener getOneQuestionnaireQuestionsUserErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // No error handling...
        }
    };

    private Response.Listener<ApiResultAllAnswers> getUserAnswersSuccessListener = new Response.Listener<ApiResultAllAnswers>() {
        @Override
        public void onResponse(ApiResultAllAnswers response) {
            FragmentAnswerQuestionnaire fragment = (FragmentAnswerQuestionnaire) getSupportFragmentManager().findFragmentByTag(TAG_ANSWER_QUESTIONNAIRE);
            Log.v("GOT THE ANSWER:", String.valueOf(response.getItems().length));

            if(fragment != null) {
                fragment.setAnswerContent(response.getItems());
            }
        }
    };

    private Response.ErrorListener getUserAnswersErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            int errorCode = error.networkResponse.statusCode;

            if(errorCode == 405) {
                String msg = "The user does not exist for this questionnaire!";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            } else if(errorCode == 404) {
                String msg = "The questionnaire does not exist!";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }

            finish();
        }
    };

    private Response.Listener<JSONObject> addQuestionToQuestionnaireSuccessListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            // The server does not return JSONObject as response...
        }
    };

    private Response.ErrorListener addQuestionToQuestionnaireErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // No need of action...
        }
    };

    private Response.Listener<ApiResultNoData> deleteQuestionnaireSuccessListener = new Response.Listener<ApiResultNoData>() {
        @Override
        public void onResponse(ApiResultNoData response) {
            Toast.makeText(getApplicationContext(), "Questionnaire deleted", Toast.LENGTH_SHORT).show();
            finish();
        }
    };

    private Response.ErrorListener deleteQuestionnaireErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // No error handling...
        }
    };

    private Response.Listener<JSONObject> editQuestionnaireSuccessListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            // The server does not return JSONObject as response...
        }
    };

    private Response.ErrorListener editQuestionnaireErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // No error handling...
        }
    };

    private Response.Listener<ApiResultNoData> deleteQuestionSuccessListener = new Response.Listener<ApiResultNoData>() {
        @Override
        public void onResponse(ApiResultNoData response) {
            // No need of action...
        }
    };

    private Response.ErrorListener deleteQuestionErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // No error handling...
        }
    };

    private Response.Listener<JSONObject> addAnswerSuccessListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            // The server does not return JSONObject as response...
        }
    };

    private Response.ErrorListener addAnswerErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // No need of action...
        }
    };
}
