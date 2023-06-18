package com.psm.petcare.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Reservation {

    private int reserveId;
    private int userId;
    private int serviceId;
    private int storeId;
    private String reserverName;
    private String reserverPhone;
    private int numOfPet;
    private double totalAmount;
    private String checkInDate;
    private String checkInTime;
    private String reserveStatus;
    private Date cancelTime;
    private Date completeTime;
    private int isComment;
    private int isDocument;


}
