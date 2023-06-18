package com.psm.petcare.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

// Add pet function, can make  in reservation page
// pet will be documented, when user come to store to do the pet service
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pet {

    private int petId;
    private int ownerId; // user id, can get from reservation
    private String ownerName; // user full name
    private int storeId;
    private String age;
    private String petGender;
    private String petWeight;
    private String petLength;
    private String petSpeciese;
    private String petImg;
    private int fullVaccine;
    private Date createTime;
    private Date updateTime;
    private int isDocument;
}
