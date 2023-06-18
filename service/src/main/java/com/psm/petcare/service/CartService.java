package com.psm.petcare.service;

import com.psm.petcare.entity.Cart;
import com.psm.petcare.vo.ResultVO;

import java.util.List;

public interface CartService {

    // create a cart
    public ResultVO createCart(Cart cart);

    // check if product added to cart or not
    public ResultVO checkProductAddCart(String pid, String uid);

    // add on the product number when user keeps clicking cart button
    public ResultVO addOnProductNum(Cart cart);

    public ResultVO listCartByUserId(String uid);

    public ResultVO removeProductFromCart(String cid);

    // update product number on the cart
    public ResultVO updateProductNumber(int cartId, int productNum);

    // for checkout page, get list of cart by multi cartid
    public ResultVO getListCartByMultiCartId(List<String> cartId);

}
