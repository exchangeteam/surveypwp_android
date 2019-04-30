package com.berkethetechnerd.surveypwp.model;

import com.google.gson.annotations.Expose;

public class ApiResultAllQuestionnaire {

    @Expose
    private ModelQuestionnaire items;

    public ApiResultAllQuestionnaire(ModelQuestionnaire items) {
        this.items = items;
    }

    public ModelQuestionnaire getItems() {
        return items;
    }

    public void setItems(ModelQuestionnaire items) {
        this.items = items;
    }
}
