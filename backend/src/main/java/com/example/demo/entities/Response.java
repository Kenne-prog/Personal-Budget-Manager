package com.example.demo.entities;

public class Response {

    public String token;  
    public String user_id;

    /* CONSTRUCTORS */
    public Response() { 
        this("", "");
    }

    public Response(String token, String user_id) {
        this.token = token;
        this.user_id = user_id;
    }
}
