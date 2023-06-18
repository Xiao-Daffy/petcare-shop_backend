package com.psm.controller;

import com.github.wxpay.sdk.WXPayUtil;
import com.psm.petcare.service.OrderService;
import com.psm.petcare.vo.ResultVO;
import com.psm.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;
    /***
     * receive the response from WeChat when user finish pay --支付回调接口
     */
    @RequestMapping("/success") // localhost:8080/pay/success
    public String paySuccess(HttpServletRequest request) throws Exception {
        System.out.println("test...........");

        // 1. get the response from wechatpay service
        ServletInputStream inputStream = request.getInputStream();
        byte[] bs = new byte[1024];
        int len =-1;
        StringBuilder stringBuilder = new StringBuilder();
        while ((len = inputStream.read(bs))!=-1){
            stringBuilder.append(new String(bs, 0, len));

        }

        String s = stringBuilder.toString(); // xml type

        System.out.println(s);
        // xml type to hashmap type
        Map<String, String> map = WXPayUtil.xmlToMap(s);
        System.out.println(map);

        if(map!=null && "success".equalsIgnoreCase(map.get("result_code"))){
            System.out.println("Paid Successfully");
            // paid successfully
            // 2. update the status of order from "Pending" to "Paid"
            String orderId = map.get("out_trade_no");
            int i = orderService.updateOrderStatus(orderId, "To Ship");// paid

            //3. send the msg to front end using websocket
            WebSocketServer.sendMsg(orderId, "1");

            // 4. Respond to wechatpay service
            if(i >0){

                HashMap<String, String> resp = new HashMap<>();
                resp.put("return_code","success");
                resp.put("return_msg","OK");
                resp.put("app_id",map.get("appid"));
                resp.put("result_code","success");
                return WXPayUtil.mapToXml(resp);
            }

        }
            // failed to pay
            return null;

    }



}
