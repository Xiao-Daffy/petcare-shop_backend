package com.psm.petcare.dao;

import com.psm.petcare.entity.Order;
import com.psm.petcare.entity.OrderVO;
import com.psm.petcare.entity.Reservation;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderDAO {

    public int insertOrder(Order order);

    // update product numbers in the cart
    public int updateOrderStatusByOrderId(String orderId, String orderStatus, Date paidTime);
    public int updateOrderStatusToComplete(String orderId, String orderStatus, Date finishTime);
    public int updateOrderStatusByOrderId2(String orderId, String orderStatus); // update to "To Ship"
    public int closeOrderForTimeout(String orderId, String orderStatus, Date cancelTime,String closeType, Date updateTime); // update to "To Ship"

    public Order getOrderById(String orderId);
    public int updateOrderStatusToComment(String orderId,  Date updateTime);
    // get list of orders with status 'To Pay' that exceeds 30 mins
    public List<Order> getListOrderToPay();


    public List<OrderVO> queryListOrderVOByUserId(int userId);
    public OrderVO queryOneOrderVOByItemId(String itemId);
    public List<OrderVO> queryOrderVOsByStoreId(int storeId);

    public List<OrderVO> queryBestSaleProduct();


    /**
     * data analysis part
     */
    public List<OrderVO> monthlyProduceSale(int storeId);

    // get 5 recent order
    public List<OrderVO> fiveRecentOrder(int storeId);

    public OrderVO bestSoldProduct(int storeId);

    public List<OrderVO> topFiveSoldProduct(int storeId);
    // current month daily profit
    public List<OrderVO> currentMonthDailyProfit(int storeId);

    // last month daily profit
    public List<OrderVO> lastMonthDailyProfit(int storeId);

    // total sale
    public OrderVO totalSale(int storeId);

}
