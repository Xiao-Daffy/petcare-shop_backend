package com.psm.petcare.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVO {

    private String orderId;
    private int userId;
    private String receiverName;
    private String receiverMobile;
    private String receiverAddress;//address, city, state
    private double totalAmount;
    private double actualAmount;
    private String paymentMethod;
    private String orderRemark;
    private String closeType;
    private Date deliveryTime;
    private Date finishTime;
    private Date paidTime;
    private Date cancelTime;
    private String orderStatus;
    private Date createTime;
    private Date updateTime;

    // orderItem
    private String itemId;
    private int storeId;
    private int productId;
    private String productName;
    private String productImg;
    private double productPrice;
    private int buyCounts;
    private Date cartDate;
    private Date buyTime;
    private int isComment;
    // Store
    private String storeName;
    // Ship
    private int shipId;
    private String shipNumber;
    private Date receiverTime;
}
