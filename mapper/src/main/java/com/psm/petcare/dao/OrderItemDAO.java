package com.psm.petcare.dao;

import com.psm.petcare.entity.OrderItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemDAO {

    public int insertOrderItem(OrderItem orderItem);

    public List<OrderItem> getOrderItemByOrderId(String orderId);

    public OrderItem queryOrderItemByItemId(String itemId);

    public int updateOrderItemComment(String itemId,int isComment);
}
