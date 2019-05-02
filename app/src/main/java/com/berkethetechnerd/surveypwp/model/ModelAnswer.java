package com.berkethetechnerd.surveypwp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelAnswer {

    @Expose
    private int id;

    @Expose
    private int questionnaire_id;

    @Expose
    private String title;

    @Expose
    private String description;

    @Expose
    private String answer;

    @SerializedName("@controls")
    private HyperControls controls;

    public ModelAnswer(int id, int questionnaire_id, String title, String description, String answer, HyperControls controls) {
        this.id = id;
        this.questionnaire_id = questionnaire_id;
        this.title = title;
        this.description = description;
        this.answer = answer;
        this.controls = controls;
    }

    public int getId() {
        return id;
    }

    public int getQuestionnaire_id() {
        return questionnaire_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAnswer() {
        return answer;
    }

    public HyperControls getControls() {
        return controls;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuestionnaire_id(int questionnaire_id) {
        this.questionnaire_id = questionnaire_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setControls(HyperControls controls) {
        this.controls = controls;
    }
}
