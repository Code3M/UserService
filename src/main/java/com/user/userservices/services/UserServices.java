package com.user.userservices.services;


import com.user.userservices.customException.ResourceNotFoundException;
import com.user.userservices.model.UserEntity;
import com.user.userservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //Create User



    public void saveUser(UserEntity user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    //find User by ID
    //The Optional class was introduced in Java 8 to handle cases where a value may or may not be present.
    //It's a container that may or may not contain a non-null value. This helps to avoid NullPointerExceptions when working with potentially null values.
    public UserEntity getUser(String userId){
        return userRepository.findById(userId).orElseThrow(ResourceNotFoundException::new);
    }
    //find All users
    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }
    //Delete UserByID
    public void deleteUser(String userId){
        UserEntity userEntity= userRepository.findById(userId).orElseThrow(ResourceNotFoundException::new);;
        userRepository.deleteById(userId);
    }

    //Update User Data
    public UserEntity update(String userId, UserEntity user){
        UserEntity userEntity=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User is Not Present. Please Create New User"));
        userEntity.setUserName(user.getUserName());
        userEntity.setUserEmail(user.getUserEmail());
        userEntity.setUserMobile(user.getUserMobile());
        return  userRepository.save(userEntity);
    }
    public UserEntity partialUpdateUser(String userId, Map<String, Object> updates) {
        UserEntity existingUser = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User is Not Present. Please Create New User"));
        if (existingUser != null) {
            // Apply updates
            updates.forEach((key, value) -> {
                switch (key) {
                    case "userEmail":
                        existingUser.setUserName((String) value);
                        break;
                    case "email":
                        existingUser.setUserEmail((String) value);
                        break;
                    case "userMobile":
                        existingUser.setUserMobile((long) value);
                        break;
                }
            });
            return userRepository.save(existingUser);
        }
        return null;
    }
}
