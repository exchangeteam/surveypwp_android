package com.berkethetechnerd.surveypwp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.berkethetechnerd.surveypwp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentLogin.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentLogin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLogin extends Fragment {

    @BindView(R.id.et_questionnaireTitle) EditText etQuestionnaireTitle;
    @BindView(R.id.et_questionnaireDescription) EditText etQuestionnaireDesc;
    @BindView(R.id.tv_btnEditQuestionnaire) TextView tvEditQuestionnaire;
    @BindView(R.id.tv_btnAnswerPlatform) TextView tvAnswerPlatform;

    private OnFragmentInteractionListener mListener;

    public FragmentLogin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentLogin.
     */
    public static FragmentLogin newInstance() {
        return new FragmentLogin();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, rootView);

        tvEditQuestionnaire.setOnClickListener(editQuestionnaireClickListener);
        tvAnswerPlatform.setOnClickListener(answerPlatformClickListener);

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

    @OnClick(R.id.btn_createQuestionnaire)
    public void createQuestionnaire(View v) {
        String title = etQuestionnaireTitle.getText().toString();
        String description = etQuestionnaireDesc.getText().toString();
        String msg = "Title cannot be empty!";

        if(title.isEmpty()) {
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        } else {
            mListener.createQuestionnaire(title, description);
        }
    }

    private View.OnClickListener editQuestionnaireClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mListener.editQuestionnairePlatform();
        }
    };

    private View.OnClickListener answerPlatformClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mListener.answerPlatform();
        }
    };

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
        void createQuestionnaire(String title, String description);
        void editQuestionnairePlatform();
        void answerPlatform();
    }
}
