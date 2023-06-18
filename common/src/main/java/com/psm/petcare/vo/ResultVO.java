package com.psm.petcare.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import  javax.annotation.Resource;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO {
    // the code respond to front end
    private int code;

    // the message respond to front end
    private String msg;

    // the data respond to front end
    private Object data;
}
