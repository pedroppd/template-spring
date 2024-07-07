package com.template.project.domain;

import java.util.UUID;

public class User {

    private UUID uuid;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public User() {}

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public UUID getUuid() {
        return uuid;
    }
}
