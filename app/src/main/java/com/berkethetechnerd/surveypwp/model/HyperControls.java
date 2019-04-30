package com.berkethetechnerd.surveypwp.model;

import com.google.gson.annotations.Expose;

public class HyperControls {

    @Expose
    private Self self;

    @Expose
    private Profile profile;

    public HyperControls(Self self, Profile profile) {
        this.self = self;
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }

    public Self getSelf() {
        return self;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void setSelf(Self self) {
        this.self = self;
    }
}

class Self {

    @Expose
    private String href;

    public Self(String href) {
        this.href = href;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}

class Profile {

    @Expose
    private String href;

    public Profile(String href) {
        this.href = href;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
