package com.berkethetechnerd.surveypwp;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.berkethetechnerd.surveypwp.fragment.FragmentAnswerQuestionnaire;
import com.berkethetechnerd.surveypwp.fragment.FragmentLoginToAnswer;
import com.berkethetechnerd.surveypwp.fragment.FragmentLogin;
import com.berkethetechnerd.surveypwp.fragment.FragmentLoginToEdit;
import com.berkethetechnerd.surveypwp.model.ApiResultAllAnswers;
import com.berkethetechnerd.surveypwp.model.ApiResultOneQuestionnaire;
import com.berkethetechnerd.surveypwp.ws.SurveyAPI;

public class PlatformLoginActivity extends AppCompatActivity
    implements FragmentLogin.OnFragmentInteractionListener,
        FragmentLoginToEdit.OnFragmentInteractionListener,
        FragmentLoginToAnswer.OnFragmentInteractionListener {

    private final String TAG_LOGIN = "fragment_login";
    private final String TAG_LOGIN_EDIT = "fragment_login_edit";
    private final String TAG_LOGIN_ANSWER = "fragment_login_answer";

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
        setContentView(R.layout.activity_platform_login);

        FragmentLogin fragmentLogin = FragmentLogin.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.login_content,
                fragmentLogin, TAG_LOGIN).commit();
    }

    @Override
    public void createQuestionnaire(String title, String description) {
        Qtitle = title;
        Qdescription = description;

        Intent createIntent = new Intent(PlatformLoginActivity.this, QuestionnaireActivity.class);
        createIntent.putExtra(INTENT_QUESTIONNAIRE_TAG, INTENT_CREATE_QUESTIONNAIRE);
        createIntent.putExtra(INTENT_QUESTIONNAIRE_EXTRA_TITLE, title);
        createIntent.putExtra(INTENT_QUESTIONNAIRE_EXTRA_DESCRIPTION, description);

        startActivity(createIntent);
        finish();
    }

    @Override
    public void editQuestionnairePlatform() {
        FragmentLoginToEdit fragmentLoginToEdit = FragmentLoginToEdit.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.login_content, fragmentLoginToEdit, TAG_LOGIN_EDIT);
        fragmentTransaction.addToBackStack(TAG_LOGIN_EDIT);
        fragmentTransaction.commit();
    }

    @Override
    public void editQuestionnaire(int id) {
        Qid = id;
        SurveyAPI.getOneQuestionnaire(id, editSuccessListener, editErrorListener);
    }

    @Override
    public void answerPlatform() {
        FragmentLoginToAnswer fragmentLoginToAnswer = FragmentLoginToAnswer.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.login_content, fragmentLoginToAnswer, TAG_LOGIN_ANSWER);
        fragmentTransaction.addToBackStack(TAG_LOGIN_ANSWER);
        fragmentTransaction.commit();
    }

    @Override
    public void answerQuestionnaire(String username, int id) {
        Qusername = username;
        Qid = id;

        SurveyAPI.getOneQuestionnaire(id, answerSuccessListener, answerErrorListener);
    }

    @Override
    public void seeAnswerOfUser(String username, int id) {
        Qusername = username;
        Qid = id;

        SurveyAPI.getOneQuestionnaire(id, answerUserSuccessListener, answerUserErrorListener);
    }

    private Response.Listener<ApiResultOneQuestionnaire> editSuccessListener = new Response.Listener<ApiResultOneQuestionnaire>() {
        @Override
        public void onResponse(ApiResultOneQuestionnaire response) {
            Intent editIntent = new Intent(PlatformLoginActivity.this, QuestionnaireActivity.class);
            editIntent.putExtra(INTENT_QUESTIONNAIRE_TAG, INTENT_EDIT_QUESTIONNAIRE);
            editIntent.putExtra(INTENT_QUESTIONNAIRE_EXTRA_ID, Qid);

            startActivity(editIntent);
            finish();
        }
    };

    private Response.ErrorListener editErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            String msg = "The questionnaire does not exist!";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    };

    private Response.Listener<ApiResultOneQuestionnaire> answerSuccessListener = new Response.Listener<ApiResultOneQuestionnaire>() {
        @Override
        public void onResponse(ApiResultOneQuestionnaire response) {
            Intent answerIntent = new Intent(PlatformLoginActivity.this, QuestionnaireActivity.class);
            answerIntent.putExtra(INTENT_QUESTIONNAIRE_TAG, INTENT_ANSWER_QUESTIONNAIRE);
            answerIntent.putExtra(INTENT_QUESTIONNAIRE_EXTRA_USERNAME, Qusername);
            answerIntent.putExtra(INTENT_QUESTIONNAIRE_EXTRA_ID, Qid);

            startActivity(answerIntent);
            finish();
        }
    };

    private Response.ErrorListener answerErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            String msg = "The questionnaire does not exist!";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    };

    private Response.Listener<ApiResultOneQuestionnaire> answerUserSuccessListener = new Response.Listener<ApiResultOneQuestionnaire>() {
        @Override
        public void onResponse(ApiResultOneQuestionnaire response) {
            SurveyAPI.getUserAnswers(Qid, Qusername, getUserAnswersSuccessListener, getUserAnswersErrorListener);
        }
    };

    private Response.ErrorListener answerUserErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            String msg = "The questionnaire does not exist!";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    };

    private Response.Listener<ApiResultAllAnswers> getUserAnswersSuccessListener = new Response.Listener<ApiResultAllAnswers>() {
        @Override
        public void onResponse(ApiResultAllAnswers response) {
            Intent answerOfUserIntent = new Intent(PlatformLoginActivity.this, QuestionnaireActivity.class);
            answerOfUserIntent.putExtra(INTENT_QUESTIONNAIRE_TAG, INTENT_SEE_ANSWER_OF_USER);
            answerOfUserIntent.putExtra(INTENT_QUESTIONNAIRE_EXTRA_USERNAME, Qusername);
            answerOfUserIntent.putExtra(INTENT_QUESTIONNAIRE_EXTRA_ID, Qid);

            startActivity(answerOfUserIntent);
            finish();
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
}