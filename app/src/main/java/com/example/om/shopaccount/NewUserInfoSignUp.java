package com.example.om.shopaccount;

public class NewUserInfoSignUp {

    private String name,email,password,userType;

    public NewUserInfoSignUp(){

    }

    public NewUserInfoSignUp(String name, String email, String password, String userType) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
