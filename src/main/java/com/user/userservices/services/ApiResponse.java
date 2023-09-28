package com.user.userservices.services;


import lombok.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Service
public class ApiResponse<T> {
    private String status;
    private String message;
    private String code;
    private T data;

    public String errorCodes(String messageCode) {
        HashMap<String, String> messageMap=new HashMap<String, String>();
        messageMap.put("INVALID_REQUEST", "Invalid request. Please provide all required fields: username, email, password, full_name");
        messageMap.put("USERNAME_EXISTS", "The provided username is already taken. Please choose a different username.");
        messageMap.put("EMAIL_EXISTS", "The provided email is already registered. Please use a different email address.");
        messageMap.put("INVALID_PASSWORD", "The provided password does not meet the requirements. Password must be at least 8 characters long and contain a mix of uppercase and lowercase letters, numbers, and special characters.");
        messageMap.put("INVALID_AGE", "Invalid age value. Age must be a positive integer.");
        messageMap.put("GENDER_REQUIRED", "Gender field is required. Please specify the gender (e.g., male, female, non-binary).");
        messageMap.put("INTERNAL_SERVER_ERROR", "An internal server error occurred. Please try again later.");
        return messageMap.get(messageCode);
    }

    public ApiResponse(String code, String status) {
        this.status = status;
        this.code = code;
        this.message = errorCodes(code);
    }

    public ApiResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
