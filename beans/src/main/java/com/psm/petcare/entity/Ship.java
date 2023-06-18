package com.psm.petcare.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Ship {

    private int shipId;
    private String orderId;
    private String orderItemId;
    private String shipNumber;
    private Date receiverTime;
    // status?
}
