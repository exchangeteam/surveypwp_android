package com.berkethetechnerd.surveypwp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.berkethetechnerd.surveypwp.R;
import com.berkethetechnerd.surveypwp.model.ModelAnswer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnswerAdapter extends ArrayAdapter<ModelAnswer> {

    // The container list.
    private ArrayList<ModelAnswer> dataSet;

    // The context that the adapter will work on.
    private Context context;

    /**
     * ViewHolder class is the part where a row view's components are initialized.
     * All components are constructed.
     */
    static class ViewHolder {
        @BindView(R.id.tv_answerQuestionTitle) TextView tvTitle;
        @BindView(R.id.tv_answerQuestionDescription) TextView tvDescription;
        @BindView(R.id.et_answerContent) EditText etAnswer;

        private ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public AnswerAdapter(ArrayList<ModelAnswer> dataSet, Context context) {
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
        // The comment in the placement of the specific row.
        final ModelAnswer answer = getItem(position);

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
        setViewContent(viewHolder, answer);
        return convertView;
    }

    /**
     * Fill view with its content.
     *
     * @param viewHolder,    the holder of view which will be displayed.
     * @param answer:       The answer object which holds the content.
     */
    private void setViewContent(ViewHolder viewHolder, final ModelAnswer answer) {
        if (answer != null) {
            String title = answer.getTitle();
            String description = answer.getDescription();

            if(description == null || description.isEmpty()) {
                description = "No description provided.";
            }

            viewHolder.tvTitle.setText(title);
            viewHolder.tvDescription.setText(description);
        }
    }
}