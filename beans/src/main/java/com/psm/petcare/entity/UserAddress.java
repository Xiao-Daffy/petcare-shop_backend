package com.psm.petcare.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddress {

    private int addrId;
    private int userId;
    private String receiverName;
    private String receiverMobile;
    private String state;
    private String city;
    private String address;
    private String postCode;
    private int defaultAddr;
    private Date createTime;
    private Date updateTime;
}
