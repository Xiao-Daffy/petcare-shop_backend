package com.psm.controller;

import com.github.wxpay.sdk.WXPay;
import com.psm.petcare.entity.Notification;
import com.psm.petcare.service.config.MyWXPayConfig;
import com.psm.petcare.entity.Order;
import com.psm.petcare.service.OrderService;
import com.psm.petcare.vo.RespondStatus;
import com.psm.petcare.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public ResultVO add( String cids, String produName, @RequestBody Order order){

        ResultVO resultVO =null;
        try {

            resultVO = orderService.addOrder(cids, order);
            String orderId = (String)resultVO.getData();
            System.out.println("order id: "+orderId);
            if (orderId!=null){
                HashMap<String, String> orderInfo = new HashMap<>();
                orderInfo.put("orderId", orderId);
//                orderInfo.put("total", order.getActualAmount()+"");
                // WeChat Pay
                // configure wechat pay
                WXPay wxPay = new WXPay(new MyWXPayConfig());
                HashMap<String, String> data = new HashMap<>();
                data.put("body", produName); // pet product description
                data.put("out_trade_no", orderId); // current order number
                data.put("fee_type", "CNY"); // exchange current
                data.put("total_fee", "1");//一分
                data.put("trade_type", "NATIVE");// trade type
                data.put("notify_url", "http://petshop.gz2vip.91tunnel.com/pay/success"); // whom to notify after pay


                // set the pay request, and get the response
                Map<String, String> payResponse = wxPay.unifiedOrder(data);
                orderInfo.put("payUrl", payResponse.get("code_url"));
                System.out.println(payResponse.get("code_url"));


                resultVO  = new ResultVO(RespondStatus.OK, "add order",orderInfo);
            }else{
                System.out.println("PayUrl fail");
                resultVO  = new ResultVO(RespondStatus.NO, "Failed to add order", null);
            }
        }catch (Exception e){
            System.out.println("throw error");
            resultVO  = new ResultVO(RespondStatus.NO, "Failed to add order", null);
        }

        return resultVO;
    }


    @GetMapping("/status/{oid}")
    public ResultVO getOrderStatus(@PathVariable("oid") String orderId, @RequestHeader("token") String token){

        return orderService.getAnOrder(orderId);
    }


    @GetMapping("/list/{uid}")
    public ResultVO getListOrder(@PathVariable("uid")String userId,@RequestHeader("token")String token){
        return orderService.getListOrderVObyUserId(userId);
    }
    @GetMapping("/all/{sid}")
    public ResultVO getListOrderbyStoreId(@PathVariable("sid")String storeId,@RequestHeader("token")String token){
        return orderService.getOrderVOsbyStoreId(storeId);
    }
    @GetMapping("/detail/{otmid}")
    public ResultVO getAnOrder(@PathVariable("otmid")String itemId,@RequestHeader("token")String token){
        return orderService.getOrderVObyItemId(itemId);
    }

    @PutMapping("/cancel/{oid}")
    public ResultVO cancelOrderWithoutFound(@PathVariable("oid")String orderId,@RequestHeader("token")String token){
        return orderService.cancelOrderWithoutRefund(orderId);
    }



    @PutMapping("/complete/{oid}")
    public ResultVO completeOrder(@PathVariable("oid")String orderId,@RequestHeader("token")String token){
        return orderService.completeOrder(orderId);
    }

    @PutMapping("/tocomment/{oid}")
    public ResultVO completeOrder(@PathVariable("oid")String orderId){
        return orderService.ChangeToComment(orderId, new Date());
    }

}
