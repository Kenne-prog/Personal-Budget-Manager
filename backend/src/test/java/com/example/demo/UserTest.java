package com.example.demo;
import org.junit.jupiter.api.Test;

import com.example.demo.entities.User;

public class UserTest {
    private User user = new User();

    @Test
    public void idCheck()
    {
        assert user.setID(null) == false : "User ID cannot be null";
        System.out.println("ID check successful");
    }

    @Test
    public void usernameCheck()
    {
        assert user.setUsername(null) == false : "User username cannot be null";
        assert user.setUsername("") == false : "User username cannot be empty";
        assert user.setUsername("  ") == false : "User username cannot be blank";
        System.out.println("Username check successful");
    }

    @Test
    public void passwordCheck()
    {
        assert user.setPassword(null) == false : "User password cannot be null";
        assert user.setPassword("Pass1?") == false : "User password cannot less than 8 charcters";
        assert user.setPassword("password123") == false : "User password must contain an uppercase letter";
        assert user.setPassword("Passwording") == false : "User password must contain a number";
        assert user.setPassword("PASSWORD123") == false : "User password must contain a lowercase letter";
        assert user.setPassword("Password123") == false : "User password must contain a special character";
        assert user.setPassword("Password123?") == true : "User password should contain uppercase and lowercase letters, numbers, and special characters";
        System.out.println("Password check successful");
    }

    @Test
    public void emailCheck()
    {
        assert user.setEmail(null) == false : "User email cannot be null";
        assert user.setEmail("@.com") == false : "User email must be an email";
        assert user.setUsername("student@csulb.edu") == true : "User email must be formatted correctly";
        System.out.println("Email check successful");
    }
}
