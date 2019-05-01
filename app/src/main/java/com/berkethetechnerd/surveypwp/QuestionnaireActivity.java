package com.berkethetechnerd.surveypwp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.berkethetechnerd.surveypwp.fragment.FragmentCreateQuestionnaire;
import com.berkethetechnerd.surveypwp.helper.SharedPrefHelper;
import com.berkethetechnerd.surveypwp.model.ModelQuestion;
import com.berkethetechnerd.surveypwp.ws.SurveyAPI;

import org.json.JSONObject;

import java.util.ArrayList;

public class QuestionnaireActivity extends AppCompatActivity
    implements FragmentCreateQuestionnaire.OnFragmentInteractionListener {

    private final String TAG_CREATE_QUESTIONNAIRE = "create_questionnaire";

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
    private String Qid;

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
                break;

            case INTENT_ANSWER_QUESTIONNAIRE:
                break;

            case INTENT_SEE_ANSWER_OF_USER:
                break;
        }
    }

    @Override
    public void submitQuestionnaire(ArrayList<ModelQuestion> listOfQuestions, int questionnaireID) {
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
}
