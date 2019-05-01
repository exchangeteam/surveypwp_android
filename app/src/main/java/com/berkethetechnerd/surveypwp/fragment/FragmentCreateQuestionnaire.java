package com.berkethetechnerd.surveypwp.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.berkethetechnerd.surveypwp.R;
import com.berkethetechnerd.surveypwp.adapter.QuestionAdapter;
import com.berkethetechnerd.surveypwp.model.ModelQuestion;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentCreateQuestionnaire.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentCreateQuestionnaire#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCreateQuestionnaire extends Fragment
    implements QuestionAdapter.OnAdapterInteractionListener {

    @BindView(R.id.tv_newQuestionnaireTitle) TextView tvTitle;
    @BindView(R.id.tv_newQuestionnaireDescription) TextView tvDescription;
    @BindView(R.id.lv_newQuestions) ListView lvQuestions;

    private static final String ARG_TITLE = "questionnaire_title";
    private static final String ARG_DESC = "questionnaire_description";
    private static final String ARG_ID = "questionnaire_id";

    private ArrayList<ModelQuestion> questions;
    private QuestionAdapter adapter;
    private int Qid;

    private OnFragmentInteractionListener mListener;

    public FragmentCreateQuestionnaire() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentCreateQuestionnaire.
     */
    public static FragmentCreateQuestionnaire newInstance(String title, String desc, int id) {
        FragmentCreateQuestionnaire fragment = new FragmentCreateQuestionnaire();

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_DESC, desc);
        args.putInt(ARG_ID, id);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_create_questionnaire, container, false);
        ButterKnife.bind(this, rootView);
        Bundle args = getArguments();

        if(args != null) {
            String title = getArguments().getString(ARG_TITLE) + " (ID: " + String.valueOf(getArguments().getInt(ARG_ID)) + ")";
            String description = getArguments().getString(ARG_DESC);
            Qid = getArguments().getInt(ARG_ID);

            if(description == null || description.isEmpty()) {
                description = "No description provided.";
            }

            tvTitle.setText(title);
            tvDescription.setText(description);
        }

        questions = new ArrayList<>();
        adapter = new QuestionAdapter(questions, getContext(), this);
        lvQuestions.setAdapter(adapter);

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

    @OnClick(R.id.btn_addQuestion)
    public void addQuestion(View v) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
        LayoutInflater layoutInflater = LayoutInflater.from(v.getContext());

        final View view = layoutInflater.inflate(R.layout.dialog_add_question, null);
        alertDialog.setView(view);
        alertDialog.setTitle(getResources().getString(R.string.dialog_newQuestionTitle));
        alertDialog.setMessage(getResources().getString(R.string.dialog_newQuestionMessage));

        alertDialog.setPositiveButton(getResources().getString(R.string.btn_create), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText etTitle = view.findViewById(R.id.et_newQuestionTitle);
                EditText etDescription = view.findViewById(R.id.et_newQuestionDescription);

                String title = etTitle.getText().toString();
                String description = etDescription.getText().toString();
                String msg = "Title cannot be empty!";

                if(title.isEmpty()) {
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                } else {
                    questions.add(new ModelQuestion(0, title, description, null));
                    adapter.notifyDataSetChanged();

                    dialog.dismiss();
                }
            }
        });

        alertDialog.setNegativeButton(getResources().getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }

    @OnClick(R.id.btn_submitQuestionnaire)
    public void submitQuestionnaire(View v) {
        mListener.submitQuestionnaire(questions, Qid);
    }

    @Override
    public void editQuestion(final ModelQuestion question) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        final View view = layoutInflater.inflate(R.layout.dialog_add_question, null);
        alertDialog.setView(view);
        alertDialog.setTitle(getResources().getString(R.string.dialog_editQuestionTitle));

        final EditText etTitle = view.findViewById(R.id.et_newQuestionTitle);
        final EditText etDescription = view.findViewById(R.id.et_newQuestionDescription);

        etTitle.setText(question.getTitle());
        etDescription.setText(question.getDescription());

        alertDialog.setPositiveButton(getResources().getString(R.string.btn_edit), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = etTitle.getText().toString();
                String description = etDescription.getText().toString();
                String msg = "Title cannot be empty!";

                if(title.isEmpty()) {
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                } else {
                    ModelQuestion questionToChange = questions.get(questions.indexOf(question));
                    questionToChange.setTitle(title);
                    questionToChange.setDescription(description);

                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });

        alertDialog.setNeutralButton(getResources().getString(R.string.btn_delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                questions.remove(question);
                adapter.notifyDataSetChanged();

                dialog.dismiss();
            }
        });

        alertDialog.setNegativeButton(getResources().getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
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
        void submitQuestionnaire(ArrayList<ModelQuestion> listOfQuestions, int questionnaireID);
    }
}
