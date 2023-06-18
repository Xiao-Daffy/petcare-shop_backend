package com.psm.petcare.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Store {

    private int storeId;
    private int userId;
    private String storeName;
    private String StoreDesc;
    private String state;
    private String city;
    private String address;
    private int storeStatus;
    private String storeImg;

    private Date createTime;
    private Date updateTime;

}
