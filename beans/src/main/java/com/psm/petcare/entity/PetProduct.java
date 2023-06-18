package com.psm.petcare.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetProduct {

    private int productId;
    private int storeId;
    private String productName;
    private double actualPrice;
    private double salePrice;
    private int soldNum;
    private int productStatus; // 0: deactivate, 1: active
    private String productDesc1;

    private String madePlace;
    private String productPeriod; // count by day
    private String brand;
    private String factoryName;
    private String factoryAddress;
    private String weight; // 100g
    private String storageMethod;
    private double discount;
    private int stock;
    private String mainImg;
    private String otherImg;
    private Date createTime;
    private Date updateTime;


}
