package com.ostrue.app.authfirebase;

public class Admin {
    private String username;
    private String email;

    Admin(){}

    public Admin(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
