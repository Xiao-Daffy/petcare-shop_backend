package com.psm.petcare.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int userId;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String phone;
    private String password;
    private String userType;
    private String userImg;
    private Date userRegisterTime;

}
