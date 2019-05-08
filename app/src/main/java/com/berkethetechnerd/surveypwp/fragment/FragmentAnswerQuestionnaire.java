package com.berkethetechnerd.surveypwp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.berkethetechnerd.surveypwp.R;
import com.berkethetechnerd.surveypwp.adapter.AnswerAdapter;
import com.berkethetechnerd.surveypwp.model.ModelAnswer;
import com.berkethetechnerd.surveypwp.model.ModelQuestion;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentAnswerQuestionnaire.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentAnswerQuestionnaire#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAnswerQuestionnaire extends Fragment {

    @BindView(R.id.tv_answerQuestionnaireTitle) TextView tvTitle;
    @BindView(R.id.tv_answerQuestionnaireDescription) TextView tvDescription;
    @BindView(R.id.tv_answerWelcomeQuestions) TextView tvWelcome;
    @BindView(R.id.btn_goBack) Button btnGoBack;
    @BindView(R.id.btn_answerSubmitQuestionnaire) Button btnSubmit;
    @BindView(R.id.lv_answerQuestions) ListView lvAnswers;

    private static final String ARG_TITLE = "questionnaire_title";
    private static final String ARG_DESC = "questionnaire_description";
    private static final String ARG_ID = "questionnaire_id";
    private static final String ARG_USER = "questionnaire_user";

    private ArrayList<ModelQuestion> questions;
    private AnswerAdapter adapter;
    private String Qusername;
    private int Qid;

    private OnFragmentInteractionListener mListener;

    public FragmentAnswerQuestionnaire() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentAnswerQuestionnaire.
     */
    public static FragmentAnswerQuestionnaire newInstance(String title, String desc, String username, int id) {
        FragmentAnswerQuestionnaire fragment = new FragmentAnswerQuestionnaire();

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_DESC, desc);
        args.putInt(ARG_ID, id);
        args.putString(ARG_USER, username);

        fragment.setArguments(args);
        fragment.questions = new ArrayList<>();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_answer_questionnaire, container, false);
        ButterKnife.bind(this, rootView);
        Bundle args = getArguments();

        if(args != null) {
            String title = "Title: " + getArguments().getString(ARG_TITLE) + " (ID: " + String.valueOf(getArguments().getInt(ARG_ID)) + ")";
            String description = getArguments().getString(ARG_DESC);
            String welcomeText = "Questions (User: " + getArguments().getString(ARG_USER) + ")";

            Qusername = getArguments().getString(ARG_USER);
            Qid = getArguments().getInt(ARG_ID);

            if(description == null || description.isEmpty()) {
                description = "No description provided.";
            } else {
                description = "Description: " + description;
            }

            tvTitle.setText(title);
            tvDescription.setText(description);
            tvWelcome.setText(welcomeText);
        }

        if(adapter == null) {
            adapter = new AnswerAdapter(questions, getContext());
            lvAnswers.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnClick(R.id.btn_answerSubmitQuestionnaire)
    public void submitQuestionnaire(View v) {
        mListener.submitAnswerQuestionnaire(questions, Qusername, Qid);
    }

    @OnClick(R.id.btn_goBack)
    public void returnHomePage(View view) {
        mListener.returnHomePage();
    }

    public void setQuestionData(ModelQuestion[] data) {
        questions.clear();
        questions.addAll(Arrays.asList(data));

        if(adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            adapter = new AnswerAdapter(questions, getContext());
            lvAnswers.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    public void setAnswerContent(ModelAnswer[] items) {
        for(ModelAnswer answer: items) {
            for(ModelQuestion question: questions) {
                if(question.getId() == answer.getQuestion_id()) {
                    question.setContent(answer.getAnswer());
                }
            }
        }

        if(adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            adapter = new AnswerAdapter(questions, getContext());
            lvAnswers.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        btnGoBack.setVisibility(View.VISIBLE);
        btnSubmit.setVisibility(View.GONE);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void submitAnswerQuestionnaire(ArrayList<ModelQuestion> listOfAnswers, String username, int questionnaireID);
        void returnHomePage();
    }
}
