package com.psm.petcare.dao;

import com.psm.petcare.entity.Cart;
import com.psm.petcare.entity.CartVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDAO {

    // add to cart
    public int insertCart(Cart cart);

    // query a cart -- for checking product already add to cart or not
    public Cart queryCartByProductId(int productId);
    public Cart queryCartByProductIdUserId(int productId, int userId);
    // add product number -- add one by one
    public int addProductNumber(Cart cart);

    // add product number -- add random
    public int addProductNumberByRandom(int productId);


    // shopping cart list -- CartVO
    public List<CartVO> queryListCartByUserId(int userId);

    // shopping cart list -- delete
    public int deleteCartByCardId(int cardId);

    // update product numbers in the cart
    public int updateProductNumByCartId(int cartId, int productNum);

    // for checkout page:  query list of selected cart by different cart_id
    public List<CartVO> queryListCartByMultiCartId(List<Integer> cartId);


}
