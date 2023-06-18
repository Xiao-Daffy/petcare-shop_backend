package com.psm.petcare.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    private String itemId;
    private String orderId;
    private int storeId;
    private int productId;
    private String productName;
    private String productImg;
    private double productPrice;
    private int buyCounts;
    private double totalAmount;
    private Date cartDate;
    private Date buyTime;
    private int isComment;
}
