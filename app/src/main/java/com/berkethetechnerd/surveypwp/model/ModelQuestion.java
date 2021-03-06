package com.berkethetechnerd.surveypwp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelQuestion {

    @Expose
    private int id;

    @Expose
    private int questionnaire_id;

    @Expose
    private String title;

    @Expose
    private String description;

    @Expose
    private String content;

    @SerializedName("@controls")
    private HyperControls controls;

    private boolean isDeleted = false;

    public ModelQuestion(int id, int questionnaire_id, String title, String description, String content, HyperControls controls) {
        this.id = id;
        this.questionnaire_id = questionnaire_id;
        this.title = title;
        this.description = description;
        this.content = content;
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

    public HyperControls getControls() {
        return controls;
    }

    public String getContent() {
        return content;
    }

    public boolean isDeleted() {
        return this.isDeleted;
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

    public void setContent(String content) {
        this.content = content;
    }

    public void setControls(HyperControls controls) {
        this.controls = controls;
    }

    public void setDeleted(boolean deleted) {
        this.isDeleted = deleted;
    }
}
