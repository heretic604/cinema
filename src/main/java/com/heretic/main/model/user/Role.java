package com.heretic.main.model.user;

public enum Role {

    VISITOR("visitor"),
    MANAGER("manager"),
    ADMIN("admin");

    private final String title;

    Role(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
