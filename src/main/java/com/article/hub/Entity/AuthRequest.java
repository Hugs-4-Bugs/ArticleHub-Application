package com.article.hub.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class AuthRequest {

    private String email;
    private String username;
    private String password;


    // getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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


    // default constructor
    public AuthRequest(){

    }

    public AuthRequest(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
