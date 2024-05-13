

package com.example.employeemanagement.dto;

import com.example.employeemanagement.entity.Department;
import com.example.employeemanagement.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public class UserDTO {

        private String firstName;
        private String lastName;
        private String sallary;
        private Department department;
        private String username;
        private String email;
        private String role;
        private String password;
        private User user;
//        private int statusCode;
//        private String error;
//        private String message;
//        private String token;
//        private String refreshToken;
//        private String expirationTime;
    }

