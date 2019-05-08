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
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentEditQuestionnaire.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentEditQuestionnaire#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentEditQuestionnaire extends Fragment
        implements QuestionAdapter.OnAdapterInteractionListener {

    @BindView(R.id.tv_editQuestionnaireTitle) TextView tvTitle;
    @BindView(R.id.tv_editQuestionnaireDescription) TextView tvDescription;
    @BindView(R.id.tv_editQuestionnaireDelete) TextView tvDelete;
    @BindView(R.id.lv_editQuestions) ListView lvQuestions;

    private static final String ARG_TITLE = "questionnaire_title";
    private static final String ARG_DESC = "questionnaire_description";
    private static final String ARG_ID = "questionnaire_id";

    private ArrayList<ModelQuestion> questions;
    private ArrayList<ModelQuestion> dataToSubmit;
    private QuestionAdapter adapter;
    private int Qid;

    private OnFragmentInteractionListener mListener;

    public FragmentEditQuestionnaire() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentCreateQuestionnaire.
     */
    public static FragmentEditQuestionnaire newInstance(String title, String desc, int id) {
        FragmentEditQuestionnaire fragment = new FragmentEditQuestionnaire();

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_DESC, desc);
        args.putInt(ARG_ID, id);

        fragment.setArguments(args);
        fragment.questions = new ArrayList<>();
        fragment.dataToSubmit = new ArrayList<>();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit_questionnaire, container, false);
        ButterKnife.bind(this, rootView);
        Bundle args = getArguments();

        if(args != null) {
            String title = getArguments().getString(ARG_TITLE) + " (ID: " + String.valueOf(getArguments().getInt(ARG_ID)) + ")";
            String description = getArguments().getString(ARG_DESC);
            Qid = getArguments().getInt(ARG_ID);

            if(description == null || description.isEmpty()) {
                description = "No description provided.";
            } else {
                description = "Description: " + description;
            }

            tvTitle.setText(title);
            tvDescription.setText(description);
        }

        adapter = new QuestionAdapter(questions, getContext(), this);
        lvQuestions.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setTitle(getResources().getString(R.string.dialog_deleteQuestionnaireConfirmationTitle));
                alertDialog.setMessage(getResources().getString(R.string.dialog_deleteQıestionnaireConfirmationMessage));

                alertDialog.setPositiveButton(getResources().getString(R.string.btn_delete), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.deleteQuestionnaire(Qid);
                        dialog.dismiss();
                    }
                });

                alertDialog.setNegativeButton(getResources().getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });

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

    @OnClick(R.id.btn_editAddQuestion)
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
                    ModelQuestion newQ = new ModelQuestion(0, 0, title, description, "", null);
                    questions.add(newQ);
                    dataToSubmit.add(newQ);
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

    @OnClick(R.id.btn_editSubmitQuestionnaire)
    public void submitQuestionnaire(View v) {
        mListener.submitEditQuestionnaire(dataToSubmit, Qid);
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

                    ModelQuestion questionToChange2 = dataToSubmit.get(dataToSubmit.indexOf(question));
                    questionToChange2.setTitle(title);
                    questionToChange2.setDescription(description);

                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });

        alertDialog.setNeutralButton(getResources().getString(R.string.btn_delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ModelQuestion questionToChange = questions.get(questions.indexOf(question));
                questions.remove(questionToChange);

                ModelQuestion questionToChange2 = dataToSubmit.get(dataToSubmit.indexOf(question));
                if(questionToChange2.getId() == 0) {
                    dataToSubmit.remove(questionToChange2);
                } else {
                    questionToChange2.setDeleted(true);
                }

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

    public void setQuestionData(ModelQuestion[] data) {
        questions.clear();
        dataToSubmit.clear();

        questions.addAll(Arrays.asList(data));
        dataToSubmit.addAll(Arrays.asList(data));

        if(adapter != null) {
            adapter.notifyDataSetChanged();
        }
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
        void submitEditQuestionnaire(ArrayList<ModelQuestion> listOfQuestions, int questionnaireID);
        void deleteQuestionnaire(int id);
    }
}
