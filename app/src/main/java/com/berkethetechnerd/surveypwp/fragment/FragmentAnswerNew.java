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
 * {@link FragmentAnswerNew.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentAnswerNew#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAnswerNew extends Fragment {

    @BindView(R.id.et_answerUsername) EditText etAnswerUsername;
    @BindView(R.id.et_answerQuestionnaireID) EditText etQuestionnaireID;
    @BindView(R.id.tv_btnAnswerOfUser) TextView tvAnswerOfUser;

    private OnFragmentInteractionListener mListener;

    public FragmentAnswerNew() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentAnswerNew.
     */
    public static FragmentAnswerNew newInstance() {
        return new FragmentAnswerNew();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_answer_new, container, false);
        ButterKnife.bind(this, rootView);

        tvAnswerOfUser.setOnClickListener(anserOfUserClickListener);

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

    @OnClick(R.id.btn_answerQuestionnaire)
    public void answerQuestionnaire(View v) {
        String username = etAnswerUsername.getText().toString();
        String id = etQuestionnaireID.getText().toString();
        String msg = "You have to fill username and id fields.";

        if(username.isEmpty() || id.isEmpty()) {
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        } else {
            mListener.answerQuestionnaire(username, Integer.valueOf(id));
        }
    }

    private View.OnClickListener anserOfUserClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String username = etAnswerUsername.getText().toString();
            String id = etQuestionnaireID.getText().toString();
            String msg = "You have to fill username and id fields.";

            if(username.isEmpty() || id.isEmpty()) {
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            } else {
                mListener.seeAnswerOfUser(username, Integer.valueOf(id));
            }
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
        void answerQuestionnaire(String username, int id);
        void seeAnswerOfUser(String username, int id);
    }
}
