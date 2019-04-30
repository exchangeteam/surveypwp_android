package com.berkethetechnerd.surveypwp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelQuestionnaire {

    @Expose
    private int id;

    @Expose
    private String title;

    @Expose
    private String description;

    @SerializedName("@controls")
    private HyperControls controls;

    public ModelQuestionnaire(int id, String title, String description, HyperControls controls) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.controls = controls;
    }

    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setControls(HyperControls controls) {
        this.controls = controls;
    }
}
