package com.example.ayushmandey.rentpay;

/**
 * Created by Raghu on 4/3/18.
 */

public class Users {
    String name;
    String email;

    Users(){

    }

    public Users(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
