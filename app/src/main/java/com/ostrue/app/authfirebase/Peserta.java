package com.ostrue.app.authfirebase;

public class Peserta {
    private String username;
    private String email;

    Peserta(){}

    public Peserta(String username, String email) {
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
        return "Peserta{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
