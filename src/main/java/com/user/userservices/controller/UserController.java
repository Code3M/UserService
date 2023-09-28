package com.user.userservices.controller;


import com.user.userservices.model.UserEntity;
import com.user.userservices.repository.UserRepository;
import com.user.userservices.services.ApiResponse;
import com.user.userservices.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;


@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    private UserServices userServices;
    @Autowired
    private UserRepository userRepository;


    //Api for User Creation
    public static boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }


        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            return false;
        }


        if (!Pattern.compile("[a-z]").matcher(password).find()) {
            return false;
        }

        if (!Pattern.compile("\\d").matcher(password).find()) {
            return false;
        }

        if (!Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]").matcher(password).find()) {
            return false;
        }

        return true;
    }
    @PostMapping("/createUser")
    public ResponseEntity<ApiResponse<UserEntity>> createUser(@RequestBody UserEntity user) {
        if (user == null || user.getUserID() == null ||user.getUserName() == null || user.getUserEmail() == null ||
                user.getPassword() == null || user.getAge() == 0 || user.getUserMobile() == 0 || user.getGender() == null) {
            ApiResponse<UserEntity> response = new ApiResponse<>("INVALID_REQUEST", "error");
            return ResponseEntity.badRequest().body(response);
        } else if (userRepository.existsByUserName(user.getUserName())) {
            ApiResponse<UserEntity> response = new ApiResponse<>("USERNAME_EXISTS", "error");
            return ResponseEntity.badRequest().body(response);
        } else if (userRepository.existsByUserEmail(user.getUserEmail())) {
            ApiResponse<UserEntity> response = new ApiResponse<>("EMAIL_EXISTS", "error");
            return ResponseEntity.badRequest().body(response);
        } else if (!isValidPassword(user.getPassword())) {
            ApiResponse<UserEntity> response = new ApiResponse<>("INVALID_PASSWORD", "error");
            return ResponseEntity.badRequest().body(response);
        } else if (user.getAge() < 0) {
            ApiResponse<UserEntity> response = new ApiResponse<>("INVALID_AGE", "error");
            return ResponseEntity.badRequest().body(response);
        } else if (user.getGender() == null) {
            ApiResponse<UserEntity> response = new ApiResponse<>("GENDER_REQUIRED", "error");
            return ResponseEntity.badRequest().body(response);
        } else {
            try {
                userServices.saveUser(user);
                ApiResponse<UserEntity> response = new ApiResponse<>("success", "User successfully registered!", user);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } catch (Exception e) {
                ApiResponse<UserEntity> response = new ApiResponse<>("INTERNAL_SERVER_ERROR", "error");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        }

    }


    //Api for User find by Id
    @GetMapping("/user/{userID}")
    //The Optional class was introduced in Java 8 to handle cases where a value may or may not be present.
    //It's a container that may or may not contain a non-null value. This helps to avoid NullPointerExceptions when working with potentially null values.
    public ResponseEntity<UserEntity> getUserById(@PathVariable String userID){
        UserEntity userEntity = userServices.getUser(userID);
        return ResponseEntity.ok(userEntity);
    }


    //Api for find all user
    @GetMapping("/allUsers")
    public ResponseEntity<List<UserEntity>> findUser(){
        List<UserEntity> userEntity=userServices.getAllUsers();
        return ResponseEntity.ok(userEntity);
    }


    //Api for delete User
    @DeleteMapping("/delete/{userID}")
    public ResponseEntity<ApiResponse<UserEntity>> deleteUser(@PathVariable String userID){
        UserEntity userEntity= userServices.getUser(userID);
        userServices.deleteUser(userID);
        ApiResponse<UserEntity> apiResponse = new ApiResponse<UserEntity>("Success","User Deleted Successfully","200",userEntity);
        return ResponseEntity.ok().body(apiResponse);
    }



    //Api for Update the User information
    @PutMapping("/update/{userID}")
    public ResponseEntity<ApiResponse<UserEntity>> update(@PathVariable String userID, @RequestBody UserEntity userEntity){
        UserEntity user=userServices.update(userID,userEntity);
        ApiResponse<UserEntity> response=new ApiResponse<>("Success","User Updated",user);
        return  ResponseEntity.ok().body(response);
    }
    @PatchMapping("/partialUpdateUser/{userId}")
    public ResponseEntity<UserEntity> partialUpdateUser(@PathVariable String userId, @RequestBody Map<String, Object> updates) {
        UserEntity user = userServices.partialUpdateUser(userId, updates);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
