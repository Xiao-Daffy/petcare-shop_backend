package com.psm.petcare.service;

import com.psm.petcare.entity.PetProduct;
import com.psm.petcare.entity.Reservation;
import com.psm.petcare.vo.ResultVO;

import javax.xml.transform.Result;

public interface PetProductService {

    // add product
    public ResultVO addProduct(PetProduct petProduct);

    public ResultVO listProduct(String storeId);

    // delete
    public ResultVO deleteProduct(String productId);

    // get a product
    public ResultVO getProduct(String productId);

    // edit
    public ResultVO editProduct(PetProduct product);

    // user -- get all product
    public ResultVO getAllProduct();

    // get productvo by product id
    public ResultVO getProductByProductId(String pid);

    // get 3 latest new product
    public ResultVO getNewestProduct();

    // get 3 best sale product
    public ResultVO getBestSaleProduct();
}
