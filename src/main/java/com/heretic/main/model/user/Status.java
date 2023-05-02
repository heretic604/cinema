package com.heretic.main.model.user;

public enum Status {

    ACTIVE("active"),
    BLOCKED("blocked");

    private final String title;

    Status(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
