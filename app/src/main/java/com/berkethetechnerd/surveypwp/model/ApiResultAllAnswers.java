package com.berkethetechnerd.surveypwp.model;

import com.google.gson.annotations.Expose;

public class ApiResultAllAnswers {

    @Expose
    private ModelAnswer[] items;

    public ApiResultAllAnswers(ModelAnswer[] items) {
        this.items = items;
    }

    public ModelAnswer[] getItems() {
        return items;
    }

    public void setItems(ModelAnswer[] items) {
        this.items = items;
    }
}
