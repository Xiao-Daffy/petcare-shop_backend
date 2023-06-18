package com.psm.controller;


import com.psm.petcare.dao.CartDAO;
import com.psm.petcare.entity.Cart;
import com.psm.petcare.service.CartService;
import com.psm.petcare.vo.RespondStatus;
import com.psm.petcare.vo.ResultVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/cart")
@CrossOrigin
public class CartController {

    @Resource
    private CartService cartService;

    // get list of cart under the specific user,
    // but only if user login system already
//    @GetMapping("/list")
//    public ResultVO listCarts(@RequestHeader("token")String token){
//        String d = token;
//        return new ResultVO(RespondStatus.OK, "It is displaying list of shopping cart!", null);
//
//    }


    @PostMapping("/new")
    public ResultVO newCart(@RequestBody Cart cart,  @RequestHeader("token")String token){
        return cartService.createCart(cart);
    }

    // check product is already in the cart, true: yes, false: no
    @GetMapping("/check")
    public ResultVO checkProductAddedCart(@RequestParam(value = "pid") String pid,
                                          @RequestParam(value = "uid") String uid,
                                          @RequestHeader("token")String token){

        System.out.println(pid);
        System.out.println(uid);
        return cartService.checkProductAddCart(pid, uid);
    }



    @PutMapping("/increase/{pid}")
    public ResultVO addOnProductNumbers(@RequestBody Cart cart,@RequestHeader("token")String token){


        return cartService.addOnProductNum(cart);
    }

    @GetMapping("/list/{uid}")
    public ResultVO listCart(@PathVariable("uid")String uid, @RequestHeader("token")String token){

        return cartService.listCartByUserId(uid);
    }
    @DeleteMapping("/delete/{cid}")
    public ResultVO deleteProductFromCart(@PathVariable("cid")String cid, @RequestHeader("token")String token){

        return cartService.removeProductFromCart(cid);
    }

    // update cart to change the product number
    @PutMapping("/update/{cid}/{cnum}")
    public ResultVO updateCart(@PathVariable("cid") int cid, @PathVariable("cnum") int cnum, @RequestHeader("token")String token){
        return cartService.updateProductNumber(cid, cnum);
    }

    // for checkout page, get list of cart
    @GetMapping("/listcart/{cid}")
    public ResultVO getListCardByMultiId(@PathVariable("cid")List<String> cid, @RequestHeader("token")String token){
        return cartService.getListCartByMultiCartId(cid);
    }
}
