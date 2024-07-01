package com.salman.book_network.util.email;

public enum EmailTemplateName {
    ACTIVATE_ACCOUNT("activate_account");

    private final  String name;

    EmailTemplateName(String name) {
        this.name = name;
    }
}
