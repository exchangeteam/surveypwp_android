package com.berkethetechnerd.surveypwp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.berkethetechnerd.surveypwp.R;
import com.berkethetechnerd.surveypwp.model.ModelQuestion;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnswerAdapter extends ArrayAdapter<ModelQuestion> {

    // The container list.
    private ArrayList<ModelQuestion> dataSet;

    // The context that the adapter will work on.
    private Context context;

    /**
     * ViewHolder class is the part where a row view's components are initialized.
     * All components are constructed.
     */
    static class ViewHolder {
        @BindView(R.id.tv_answerQuestionTitle) TextView tvTitle;
        @BindView(R.id.tv_answerQuestionDescription) TextView tvDescription;
        @BindView(R.id.et_answerContent) TextView etAnswer;

        private ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public AnswerAdapter(ArrayList<ModelQuestion> dataSet, Context context) {
        super(context, R.layout.adapter_all_answers_row, dataSet);

        this.dataSet = dataSet;
        this.context = context;
    }

    /**
     * Constructs the view and sets it content. Returns the result which will be displayed.
     * It's a get method and it will be called by the occupant listView object.
     *
     * @param position:    The placement of the specific row within the list.
     * @param convertView: The view object to display
     * @param parent:      The parent object which holds views jointly inside.
     * @return The view object to display as filled with content.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ModelQuestion question = getItem(position);

        /*
         * ViewHolder is used to avoid instantiating a view for every item in your adapter,
         * when a view scrolls off-screen, it can be reused, or recycled.
         */
        ViewHolder viewHolder;

        /*
         * If convertView view object has never been constructed, then inflate the layout,
         * create the viewHolder and set convertView's tag.
         * Else get convertView's tag.
         */
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.adapter_all_answers_row, parent, false);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Sets the content of the view.
        setViewContent(viewHolder, question);
        return convertView;
    }

    /**
     * Fill view with its content.
     *
     * @param viewHolder,    the holder of view which will be displayed.
     * @param question:       The answer object which holds the content.
     */
    private void setViewContent(final ViewHolder viewHolder, final ModelQuestion question) {
        if (question != null) {
            String title = question.getTitle();
            String description = question.getDescription();
            String content = question.getContent();

            if(description == null || description.isEmpty()) {
                description = "No description provided.";
            }

            viewHolder.tvTitle.setText(title);
            viewHolder.tvDescription.setText(description);
            viewHolder.etAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    LayoutInflater layoutInflater = LayoutInflater.from(getContext());

                    final View view = layoutInflater.inflate(R.layout.dialog_add_answer, null);
                    alertDialog.setView(view);
                    alertDialog.setTitle(getContext().getResources().getString(R.string.dialog_answerTitle));

                    final EditText etAnswer = view.findViewById(R.id.et_newAnswerBox);

                    alertDialog.setPositiveButton(getContext().getResources().getString(R.string.btn_answer), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String Qanswer = etAnswer.getText().toString();
                            String msg = "Answer cannot be empty!";

                            if(Qanswer.isEmpty()) {
                                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                            } else {
                                question.setContent(Qanswer);
                                viewHolder.etAnswer.setText(Qanswer);
                                dialog.dismiss();
                            }
                        }
                    });

                    alertDialog.setNegativeButton(getContext().getResources().getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    alertDialog.show();
                }
            });

            if(content != null && !content.isEmpty()) {
                viewHolder.etAnswer.setText(content);
            }
        }
    }
}