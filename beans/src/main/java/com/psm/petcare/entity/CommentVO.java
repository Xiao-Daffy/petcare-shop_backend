package com.psm.petcare.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVO {
    private int commentId;
    private int storeId;
    private int productId;
    private String orderItemId;
    private int userId;
    private String content;
    private Date createTime;

    // user info
    private String username;
    private String firstname;
    private String lastname;
    private String userImg;


    // store
    private String storeName;

    // order item
    private String orderId;
    private String productName;
    private String productImg;
    private int buyCounts;
    private double totalAmount;



}
