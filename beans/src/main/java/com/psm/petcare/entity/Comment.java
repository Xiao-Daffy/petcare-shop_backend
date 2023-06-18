package com.psm.petcare.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private int commentId;
    private int storeId;
    private int productId;
    private String orderItemId;
    private int userId;
    private String content;
    private Date createTime;

}
