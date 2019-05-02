package com.berkethetechnerd.surveypwp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
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

    // TODO: In edit, questionnaire cannot be deleted.
    // TODO: In edit, questionnaire title and desc cannot be changed.
    // TODO: In create, questionnaire title and desc cannot be changed.
    // TODO: In create, questionnaire cannot be deleted.
    // TODO: In create, submit does not work
    // TODO: In edit, submit does not work.
    // TODO: Add a label on top of page "Questionnaire", for both edit and create.
    // TODO: Error handling..

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
                SurveyAPI.addQuestionnaire(Qtitle, Qdescription, addQuestionnaireSuccessListener, addQuestionnaireErrorListener);

                break;

            case INTENT_EDIT_QUESTIONNAIRE:
                Qid = callerIntent.getIntExtra(INTENT_QUESTIONNAIRE_EXTRA_ID, 0);
                SurveyAPI.getOneQuestionnaire(Qid, getOneQuestionnaireSuccessListener, getOneQuestionnaireErrorListener);

                break;

            case INTENT_ANSWER_QUESTIONNAIRE:
                Qusername = callerIntent.getStringExtra(INTENT_QUESTIONNAIRE_EXTRA_USERNAME);
                Qid = callerIntent.getIntExtra(INTENT_QUESTIONNAIRE_EXTRA_ID, 0);
                SurveyAPI.getOneQuestionnaire(Qid, getOneQuestionnaireForAnswerSuccessListener, getOneQuestionnaireForAnswerErrorListener);

                break;

            case INTENT_SEE_ANSWER_OF_USER:
                break;
        }
    }

    @Override
    public void submitQuestionnaire(ArrayList<ModelQuestion> listOfQuestions, int questionnaireID) {
        for(ModelQuestion question: listOfQuestions) {
            String title = question.getTitle();
            String description = question.getDescription();

            SurveyAPI.addQuestion(questionnaireID, title, description, addQuestionSuccessListener, addQuestionErrorListener);
        }

        Toast.makeText(getApplicationContext(), "Received the submit", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void deleteQuestionnaire(final int id) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());
        alertDialog.setTitle(getResources().getString(R.string.dialog_deleteQuestionnaireConfirmationTitle));
        alertDialog.setMessage(getResources().getString(R.string.dialog_deleteQıestionnaireConfirmationMessage));

        alertDialog.setPositiveButton(getResources().getString(R.string.btn_delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SurveyAPI.deleteQuestionnaire(id, questionnaireDeleteSuccessListener, questionnaireDeleteErrorListener);
                dialog.dismiss();
            }
        });

        alertDialog.setNegativeButton(getResources().getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void submitEditQuestionnaire(ArrayList<ModelQuestion> listOfQuestions, int questionnaireID) {
        Toast.makeText(getApplicationContext(), "Received the submit", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void submitAnswerQuestionnaire(ArrayList<ModelAnswer> listOfAnswers, String username, int questionnaireID) {
        Toast.makeText(getApplicationContext(), "Received the submit", Toast.LENGTH_SHORT).show();
        finish();
    }

    private Response.Listener<JSONObject> addQuestionnaireSuccessListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            // The server does not return JSONObject as response...
        }
    };

    private Response.ErrorListener addQuestionnaireErrorListener = new Response.ErrorListener() {
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

            FragmentEditQuestionnaire fragmentEditQuestionnaire = FragmentEditQuestionnaire.newInstance(
                    Qtitle, Qdescription, Qid
            );

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.questionnaire_content, fragmentEditQuestionnaire, TAG_EDIT_QUESTIONNAIRE)
                    .commit();

            SurveyAPI.getQuestions(Qid, getQuestionsSuccessListener, getQuestionsErrorListener);
        }
    };

    private Response.ErrorListener getOneQuestionnaireErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // No error handling...
        }
    };

    private Response.Listener<ApiResultAllQuestions> getQuestionsSuccessListener = new Response.Listener<ApiResultAllQuestions>() {
        @Override
        public void onResponse(ApiResultAllQuestions response) {
            FragmentEditQuestionnaire fragment = (FragmentEditQuestionnaire) getSupportFragmentManager().findFragmentByTag(TAG_EDIT_QUESTIONNAIRE);

            if(fragment != null) {
                fragment.setQuestionData(response.getItems());
            }
        }
    };

    private Response.ErrorListener getQuestionsErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // No error handling...
        }
    };

    private Response.Listener<ApiResultOneQuestionnaire> getOneQuestionnaireForAnswerSuccessListener = new Response.Listener<ApiResultOneQuestionnaire>() {
        @Override
        public void onResponse(ApiResultOneQuestionnaire response) {
            Qtitle = response.getTitle();
            Qdescription = response.getDescription();

            FragmentAnswerQuestionnaire fragmentEditQuestionnaire = FragmentAnswerQuestionnaire.newInstance(
                    Qtitle, Qdescription, Qusername, Qid
            );

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.questionnaire_content, fragmentEditQuestionnaire, TAG_ANSWER_QUESTIONNAIRE)
                    .commit();

            SurveyAPI.getAnswers(Qid, getQuestionsForAnswerSuccessListener, getQuestionsForAnswerErrorListener);
        }
    };

    private Response.ErrorListener getOneQuestionnaireForAnswerErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // No error handling...
        }
    };

    private Response.Listener<ApiResultAllAnswers> getQuestionsForAnswerSuccessListener = new Response.Listener<ApiResultAllAnswers>() {
        @Override
        public void onResponse(ApiResultAllAnswers response) {
            FragmentAnswerQuestionnaire fragment = (FragmentAnswerQuestionnaire) getSupportFragmentManager().findFragmentByTag(TAG_ANSWER_QUESTIONNAIRE);

            if(fragment != null) {
                fragment.setAnswerData(response.getItems());
            }
        }
    };

    private Response.ErrorListener getQuestionsForAnswerErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // No error handling...
        }
    };

    private Response.Listener<JSONObject> addQuestionSuccessListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            // The server does not return JSONObject as response...
        }
    };

    private Response.ErrorListener addQuestionErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // No need of action...
        }
    };

    private Response.Listener<ApiResultNoData> questionnaireDeleteSuccessListener = new Response.Listener<ApiResultNoData>() {
        @Override
        public void onResponse(ApiResultNoData response) {
            Toast.makeText(getApplicationContext(), "Questionnaire deleted", Toast.LENGTH_SHORT).show();
            finish();
        }
    };

    private Response.ErrorListener questionnaireDeleteErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // No error handling...
        }
    };
}
