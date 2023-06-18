package com.psm.petcare.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

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
}
