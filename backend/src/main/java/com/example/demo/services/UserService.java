package com.example.demo.services;

import org.bson.types.ObjectId;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entities.User;
import com.example.demo.interfaces.UserRepository;

// Provides business logic, Controller should use instance of UserService
@Service
public class UserService {
    
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByEmail(String email) {
        User existingUser = userRepository.findByEmail(email).orElse(null);
        return existingUser;
    }
    
    public User findByID(ObjectId id) {
        return userRepository.findById(id).orElse(null);
    }

    public void signup(User user) { // Logic for registering a user (Insert to DB)
        String email = user.getEmail();
        User existingUser = userRepository.findByEmail(email).orElse(null);

        if (existingUser == null) {

            String hashedPassword = passwordEncoder.encode(user.getPassword());
            User registeredUser = new User(user.getUsername(), 
                                        hashedPassword, 
                                        email);
            userRepository.save(registeredUser);
        }

    }

    public User updateUser(ObjectId id, String password) {
        User user = userRepository.findById(id).orElse(null);
        if(password != null && user != null)
        {
            user.setPassword(password);
            return userRepository.save(user);
        }
        return null;
    }

    public void deleteUser(ObjectId id) {
        userRepository.deleteById(id);
    }

}
