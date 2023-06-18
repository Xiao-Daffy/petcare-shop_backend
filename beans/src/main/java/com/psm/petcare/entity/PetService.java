package com.psm.petcare.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetService {

    private int serviceId;
    private int storeId;
    private String serviceName;
    private String serviceImg;
    private String petSize;
    private double price;
    private double duration; // the time service needs
    private int serviceStatus;
    private String serviceDesc;
    private Date createTime;
    private Date updateTime;
}
