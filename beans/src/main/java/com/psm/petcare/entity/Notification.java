package com.psm.petcare.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    private int id;
    private String title;
    private String content;
    private String nostatus;
    private int sentFrom; // sender' store id / sender's user id
    private int sendTo; // receiver user id   / receiver store id
    private Date sendTime;
    private int receiveStore;
    private int receiveUser;
}
