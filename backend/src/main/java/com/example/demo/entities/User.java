package com.example.demo.entities;
 
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.types.ObjectId; 
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")  
public class User {
    @Id
    private ObjectId id;    //MAY CHANGE DATATYPE TO SOMETHING MORE SUITABLE
    private String username;
    private String password;
    private String email;


    /* CONSTRUCTORS */
    public User() 
    { 
        setUsername("");
        setPassword("");
        setEmail("");
        setID(null);
    }

    public User(String username, String password, String email)
    {
        setUsername(username);
        setPassword(password);
        setEmail(email);
    }

    /* GETTERS */
    public ObjectId getID()
    {
        return id;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getEmail()
    {
        return email;
    }

    /* SETTERS */
    public boolean setID(ObjectId id)
    {
        this.id = id; 
        return true;
    }

    public boolean setUsername(String username)
    {
        this.username = username;
        return true;
    }

    public boolean setPassword(String password)
    {
        this.password = password;
        return true;
    }

    public boolean setEmail(String email)
    {
        this.email = email;
        return true;
    }

}
