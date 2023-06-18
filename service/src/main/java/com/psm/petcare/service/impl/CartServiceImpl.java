package com.psm.petcare.service.impl;

import com.psm.petcare.dao.CartDAO;
import com.psm.petcare.dao.PetProductDAO;
import com.psm.petcare.entity.Cart;
import com.psm.petcare.entity.CartVO;
import com.psm.petcare.entity.PetProduct;
import com.psm.petcare.service.CartService;
import com.psm.petcare.vo.RespondStatus;
import com.psm.petcare.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDAO cartDAO;

    @Autowired
    private PetProductDAO productDAO;

    @Override
    public ResultVO createCart(Cart cart) {
        cart.setCartTime(new Date());
        cart.setCreateTime(new Date());
        cart.setUpdateTime(new Date());
        int i = cartDAO.insertCart(cart);
        if(i > 0){
            return new ResultVO(RespondStatus.OK, "Cart created", null);

        }else{
            return new ResultVO(RespondStatus.NO, "Failed to create", null);
        }
    }

    @Override
    public ResultVO checkProductAddCart(String pid, String uid) {

        boolean flag = false;
        int productId = Integer.parseInt(pid);
        int userId = Integer.parseInt(uid);

        // check if the product already added to cart
        Cart cart = cartDAO.queryCartByProductIdUserId(productId, userId);
        if(cart == null){
            return new ResultVO(RespondStatus.OK, "Never added before", flag);
        }else{
            PetProduct petProduct = productDAO.queryProductById(productId);
            int stock = petProduct.getStock();
            int productNum = cart.getProductNum();
            if(productNum>=stock){
                // exceed the stock, cannot add, 11>=11
                return new ResultVO(RespondStatus.NO, "Exceed the stock", null);

            }else {
                flag = true;
                return new ResultVO(RespondStatus.OK, "Product added before", flag);
            }
        }







    }

    @Override
    public ResultVO addOnProductNum(Cart cart) {


        // check if it's out of stock
        PetProduct petProduct = productDAO.queryProductById(cart.getProductId());
        int stock = petProduct.getStock();

        Cart cart1 = cartDAO.queryCartByProductId(cart.getProductId());


        cart.setCartTime(new Date());
        cart.setUpdateTime(new Date());
        int i = cartDAO.addProductNumber(cart);
        if(i > 0){
            return new ResultVO(RespondStatus.OK, "Product number increase", null);

        }else{
            return new ResultVO(RespondStatus.NO, "Failed to increase", null);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVO listCartByUserId(String uid) {
        int userId = Integer.parseInt(uid);
        List<CartVO> cartVOS = cartDAO.queryListCartByUserId(userId);
        return new ResultVO(RespondStatus.OK, "List of cart", cartVOS);
    }

    @Override
    public ResultVO removeProductFromCart(String cid) {
        int cartId = Integer.parseInt(cid);
        int i = cartDAO.deleteCartByCardId(cartId);
        if(i > 0){
            return new ResultVO(RespondStatus.OK, "Product removed from cart", null);

        }else{
            return new ResultVO(RespondStatus.NO, "Failed to remove", null);
        }
    }

    @Override
    public ResultVO updateProductNumber(int cartId, int productNum) {


        int i = cartDAO.updateProductNumByCartId(cartId, productNum);
        // check the stock, if out of stock then cannot be exceed
        if(i > 0){
            return new ResultVO(RespondStatus.OK, "product number updated", null);

        }else{
            return new ResultVO(RespondStatus.NO, "Failed to update", null);
        }
    }

    @Override
    public ResultVO getListCartByMultiCartId(List<String> cartId) {

        List<Integer> cid = new ArrayList<>();
        for(int i =0; i < cartId.size(); i++){
            cid.add(Integer.parseInt(cartId.get(i)));
        }

        List<CartVO> cartVOS = cartDAO.queryListCartByMultiCartId(cid);
        return new ResultVO(RespondStatus.OK, "list of cart", cartVOS);
    }
}
