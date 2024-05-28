package com.example.demo.controllers;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.Response;
import com.example.demo.entities.User;
import com.example.demo.security.JwtHelper;
import com.example.demo.services.UserService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userRepo;
    private final AuthenticationManager authenticationManager;
  

    @Autowired
    public UserController(UserService userRepo, AuthenticationManager authenticationManager) {

        this.userRepo = userRepo;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) 
    {
        userRepo.signup(user);
        return userRepo.findByEmail(user.getEmail()).getID().toHexString();
    }

    @PostMapping("/login")
    public Response loginUser(@RequestBody User requestingUser)
    {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestingUser.getEmail(), requestingUser.getPassword()));
        String token = JwtHelper.generateToken(requestingUser.getEmail());

        return new Response(token, userRepo.findByEmail(requestingUser.getEmail()).getID().toHexString());
    }

    @GetMapping("/{id}")
    public User getUserByID(@PathVariable ObjectId id) {
        User retrievedUser = userRepo.findByID(id);
        retrievedUser.setPassword("");
        return retrievedUser; 
    }

    @GetMapping("/{email}")
    public String getUserID(@PathVariable String email) {
        User retrievedUser = userRepo.findByEmail(email);
        return retrievedUser.getID().toHexString();
    }
 
    @PatchMapping("/{id}/{password}") 
    public User updateUser(@PathVariable ObjectId id, @RequestBody String password) {
        return userRepo.updateUser(id, password);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable ObjectId id) {
        userRepo.deleteUser(id);
    }

}