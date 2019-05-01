package com.berkethetechnerd.surveypwp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.berkethetechnerd.surveypwp.adapter.QuestionnaireAdapter;
import com.berkethetechnerd.surveypwp.helper.SharedPrefHelper;
import com.berkethetechnerd.surveypwp.model.ApiResultAllQuestionnaire;
import com.berkethetechnerd.surveypwp.model.ModelQuestionnaire;
import com.berkethetechnerd.surveypwp.ws.SurveyAPI;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.lv_listQuestionnaire) ListView lvQuestionnaire;

    private ArrayList<ModelQuestionnaire> questionnaires;
    private QuestionnaireAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        questionnaires = new ArrayList<>();
        adapter = new QuestionnaireAdapter(questionnaires, getApplicationContext());
        lvQuestionnaire.setAdapter(adapter);

        getRecentQuestionnaire();
    }

    /**
     * Font set up for the activity.
     * @param newBase: The context which the fonts will be set on.
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @OnClick(R.id.btn_platform)
    public void platformClick(View v) {
        Intent activityLogin = new Intent(MainActivity.this, PlatformLoginActivity.class);
        startActivity(activityLogin);
    }

    private void getRecentQuestionnaire() {
        SurveyAPI.getQuestionnaire(questionnaireListener, questionnaireErrorListener);
    }

    private Response.Listener<ApiResultAllQuestionnaire> questionnaireListener = new Response.Listener<ApiResultAllQuestionnaire>() {
        @Override
        public void onResponse(ApiResultAllQuestionnaire response) {
            questionnaires.clear();
            questionnaires.addAll(Arrays.asList(response.getItems()));
            adapter.notifyDataSetChanged();

            SharedPrefHelper.setNumQuestionnaire(getApplicationContext(), questionnaires.size());
        }
    };

    private Response.ErrorListener questionnaireErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            String msg = error.getMessage();
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    };
}
