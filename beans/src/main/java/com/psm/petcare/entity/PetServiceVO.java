package com.psm.petcare.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetServiceVO {

    private int serviceId;
    private int storeId;
    private String serviceName;
    private String serviceImg;
    private String petSize;
    private double price;
    private double duration; // the time service needs

    private String serviceDesc;

    // add store information
    private String storeName;
    private String state;
    private String city;
    private String address;
}
