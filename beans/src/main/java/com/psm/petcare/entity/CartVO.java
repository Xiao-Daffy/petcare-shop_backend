package com.psm.petcare.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartVO { // integrate cart, product, store

    private int cartId;
    private int productId;
    private int storeId;
    private int userId;
    private int productNum;
    private Date cartTime; //Time for adding the products to the shopping cart.
    private Date createTime;
    private Date updateTime;

    // product
    private String productName;
    private String weight; // 100g
    private String mainImg;
    private double actualPrice;
    private double salePrice;
    private int stock;

    // store
    private String storeName;
    private String state;
    private String city;

}
