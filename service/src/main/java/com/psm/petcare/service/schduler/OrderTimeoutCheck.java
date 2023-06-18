package com.psm.petcare.service.schduler;

import com.github.wxpay.sdk.WXPay;
import com.psm.petcare.dao.OrderDAO;
import com.psm.petcare.dao.OrderItemDAO;
import com.psm.petcare.dao.PetProductDAO;
import com.psm.petcare.entity.Order;
import com.psm.petcare.entity.OrderItem;
import com.psm.petcare.entity.PetProduct;
import com.psm.petcare.service.OrderService;
import com.psm.petcare.service.config.MyWXPayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * This class is to cancel the unpaid orders which exceeds 30 mins
 */
@Component
public class OrderTimeoutCheck {

    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private OrderService orderService;

    private WXPay wxPay = new WXPay(new MyWXPayConfig());
    @Scheduled(cron = "* 1/3 * * * ?") //check every 60 seconds
    public void checkAndCloseOrder(){// check all orders
        try {
            // 1. check the orders hasn't paid after 30 mins
            List<Order> orders = orderDAO.getListOrderToPay();
            System.out.println("orders: "+ orders);
            //2. confirm from wechat pay server if the order pay or not again
            for(int i =0; i < orders.size(); i++){
                Order order = orders.get(i);
                HashMap<String, String> map = new HashMap<>();
                map.put("out_trade_no", order.getOrderId());


                Map<String, String> resp = wxPay.orderQuery(map);
                System.out.println(resp);
                if("SUCCESS".equalsIgnoreCase(resp.get("trade_state"))){
                    //2.1 the order is actually paid, but order status is still "To Pay"
                    // maybe cuz of network, concurrent issue
                    // then update the status back to "To Ship"
                    int to_ship = orderDAO.updateOrderStatusByOrderId2(order.getOrderId(), "To Ship");

                }else if("NOTPAY".equalsIgnoreCase(resp.get("trade_state"))){

                    // 2.2 if unpaid, then update the order status -> "Canceled", close_type="Timeout"
                    // update the stock --> order_item --> pet_product

                    // close the wechat pay link from wechat pay server, so that user cannot scan to pay
                    Map<String, String> result = wxPay.closeOrder(map);
                    System.out.println(result);

                    if("SUCCESS".equalsIgnoreCase(resp.get("result_code"))){
                        // which means wechat pay closed the pay access
                        // then close the order
                        orderService.closeOrder(order.getOrderId());

                    }

                }else if("CLOSED".equalsIgnoreCase(resp.get("trade_state"))){
                    orderService.closeOrder(order.getOrderId());
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
