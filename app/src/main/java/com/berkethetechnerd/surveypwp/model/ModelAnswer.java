package com.berkethetechnerd.surveypwp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelAnswer {

    @Expose
    private int id;

    @Expose
    private int question_id;

    @Expose
    private String userName;

    @SerializedName("content")
    private String answer;

    @SerializedName("@controls")
    private HyperControls controls;

    public ModelAnswer(int id, int question_id, String userName, String answer, HyperControls controls) {
        this.id = id;
        this.question_id = question_id;
        this.userName = userName;
        this.answer = answer;
        this.controls = controls;
    }

    public int getId() {
        return id;
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

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setControls(HyperControls controls) {
        this.controls = controls;
    }
}
