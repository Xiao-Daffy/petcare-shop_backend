package com.psm.petcare.service;

import com.psm.petcare.entity.Order;
import com.psm.petcare.entity.OrderVO;
import com.psm.petcare.vo.ResultVO;

import java.util.Date;
import java.util.List;

public interface OrderService {

    public ResultVO addOrder(String cids, Order order);

    public int updateOrderStatus(String orderId, String Orderstatus);
    public ResultVO completeOrder(String orderId);
    public ResultVO getAnOrder(String orderId);

    public void closeOrder(String orderId);
    // cancel the order without refund
    public ResultVO cancelOrderWithoutRefund(String orderId);

    public ResultVO getListOrderVObyUserId(String userId);
    public ResultVO getOrderVObyItemId(String itemId);

    public ResultVO getOrderVOsbyStoreId(String storeId);

//    public ResultVO getOrderByOrderId(String orderId);
    public ResultVO ChangeToComment(String orderId,  Date updateTime);


    /**
     * Data Analysis
     *
     */
    public List<OrderVO> getProductMontlySale(String storeId);
    public List<OrderVO> getFiveRecentOrders(String storeId);
    public OrderVO getBestSoldProduct(String storeId);
    public List<OrderVO> getFiveTopProduct(String storeId);

    public List<Double> getLastMonthDailyOrderProfit(String storeId);
    public List<Double> getCurrentMonthDailyOrderProfit(String storeId);
    public OrderVO getTotalSale(String storeId);
}
