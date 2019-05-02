package com.berkethetechnerd.surveypwp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelAnswer {

    @Expose
    private int id;

    @Expose
    private int questionnaire_id;

    @Expose
    private int question_id;

    @Expose
    private String userName;

    @Expose
    private String title;

    @Expose
    private String description;

    @SerializedName("content")
    private String answer;

    @SerializedName("@controls")
    private HyperControls controls;

    public ModelAnswer(int id, int questionnaire_id, int question_id, String userName, String title, String description, String answer, HyperControls controls) {
        this.id = id;
        this.questionnaire_id = questionnaire_id;
        this.question_id = question_id;
        this.userName = userName;
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

    public int getQuestion_id() {
        return question_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuestionnaire_id(int questionnaire_id) {
        this.questionnaire_id = questionnaire_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
