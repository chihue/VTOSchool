package com.example.school.login.model;

import javax.validation.constraints.NotNull;

public class Login {
    @NotNull(message = "username is required")
    private String username;
    @NotNull(message = "password is required")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
