package com.template.project.domain;

import java.util.UUID;

public class User {

    private UUID uuid;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String verificationCode;
    private boolean enabled;

    public User() {

    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

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
