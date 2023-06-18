package com.psm.petcare.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    private int cartId;
    private int productId;
    private int storeId;
    private int userId;
    private int productNum;
    private Date cartTime; //Time for adding the products to the shopping cart.
    private Date createTime;
    private Date updateTime;


}
