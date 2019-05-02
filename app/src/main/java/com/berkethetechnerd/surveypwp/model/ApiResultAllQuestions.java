package com.berkethetechnerd.surveypwp.model;

import com.google.gson.annotations.Expose;

public class ApiResultAllQuestions {

    @Expose
    private ModelQuestion[] items;

    public ApiResultAllQuestions(ModelQuestion[] items) {
        this.items = items;
    }

    public ModelQuestion[] getItems() {
        return items;
    }

    public void setItems(ModelQuestion[] items) {
        this.items = items;
    }
}
