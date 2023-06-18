package com.psm.petcare.service.impl;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.psm.petcare.dao.*;
import com.psm.petcare.entity.*;
import com.psm.petcare.service.OrderService;
import com.psm.petcare.service.config.MyWXPayConfig;
import com.psm.petcare.vo.RespondStatus;
import com.psm.petcare.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@Service
public class OrderServiceImpl implements OrderService  {

    @Autowired
    private CartDAO cartDAO;
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private OrderItemDAO orderItemDAO;
    @Autowired
    private PetProductDAO productDAO;
    @Autowired
    private ShipDAO shipDAO;

    /**
     *
     *
     * @param cids    1,2
     * @return
     */
    @Transactional // make sure all sql operation in addOrder commit at same time,
    // which mean, if any one of sql operation(eg, add order item fails) then others won't success
    public ResultVO addOrder(String cids, Order order){

        //1. get the cart details based on the list of cid
        String[] arr= cids.split(",");
        List<Integer> cidsList = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            cidsList.add(Integer.parseInt(arr[i]));

        }
        // list of cart to be checked out
        List<CartVO> cartVOS = cartDAO.queryListCartByMultiCartId(cidsList);

        // check the stock, even though it already checked,
        // when user add the cart
        boolean f = true;
        for(CartVO cv: cartVOS){
            if(cv.getProductNum() > cv.getStock()){
                // out of the stock,
                f = false;
                break;
            }
        }

        if(f){
            // within stock
            //step 1:  save the order to 'orders' database, the data needs:
            // a. userId, receiver info(address) - from front end
            // b. total price/actual price - from front end
            // d. payment method - from front end
            // e. order status: Pending
            // f. create time
            order.setOrderStatus("To Pay");
            order.setCreateTime(new Date());

            // generate the order number by using UUID
            String orderId = UUID.randomUUID().toString().replace("-", "");
            order.setOrderId(orderId);

            // insert to the database
            int i = orderDAO.insertOrder(order);
            if(i > 0){
                //step 2. generate order snap for each cart, store into the 'order_item'
                for(CartVO cv: cartVOS){

                    // generate order_item id
                    String itemId = System.currentTimeMillis()+""+(new Random().nextInt(89999)+10000);
                    OrderItem orderItem = new OrderItem(itemId, orderId, cv.getStoreId(), cv.getProductId(), cv.getProductName(),
                            cv.getMainImg(), cv.getSalePrice(), cv.getProductNum(), cv.getSalePrice() * cv.getProductNum(),
                            cv.getCartTime(), new Date(), 0);

                    int i1 = orderItemDAO.insertOrderItem(orderItem);
                }

                //step 3: reduce the stock for each product
                for(CartVO cv: cartVOS){

                    int productId = cv.getProductId();
                    int newStock = cv.getStock() - cv.getProductNum();
                    int i1 = productDAO.updateStockById(productId, newStock);

                }

                // step 4: delete the cart
                for(int cd: cidsList){
                    cartDAO.deleteCartByCardId(cd);
                }
                return new ResultVO(RespondStatus.OK, "Order made successfully", orderId);

            }else{
                return new ResultVO(RespondStatus.NO, "Failed to increase", null);
            }

        }else{
            // out of the stock
            return new ResultVO(RespondStatus.NO, "Out of stock, fail to checkout", null);
        }

    }

    @Override
    public int updateOrderStatus(String orderId, String Orderstatus) {
        int i = orderDAO.updateOrderStatusByOrderId(orderId, Orderstatus, new Date());
        return i;
    }

    @Override
    public ResultVO completeOrder(String orderId) {
        int i = orderDAO.updateOrderStatusToComplete(orderId, "Completed", new Date());
        if(i > 0){
            return new ResultVO(RespondStatus.OK, "changed", null);

        }else{
            return new ResultVO(RespondStatus.NO, "Failed", null);
        }
    }

    @Override
    public ResultVO getAnOrder(String orderId) {

        Order order = orderDAO.getOrderById(orderId);
        return new ResultVO(RespondStatus.OK, "get an order", order.getOrderStatus());
    }

    /// close order for timeout
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE) // update order status & stock must success at same time
   //isolation = Isolation.SERIALIZABLE : for concurrent issuse, it's like 'syncronized locker'
    public void closeOrder(String orderId) {
        synchronized (this) {
            // update order status
            orderDAO.closeOrderForTimeout(orderId, "Canceled", new Date(), "Timeout", new Date());

            // update stock
            List<OrderItem> orderItems = orderItemDAO.getOrderItemByOrderId(orderId);
            System.out.println(orderItems);
            for (int j = 0; j < orderItems.size(); j++) {
                OrderItem orderItem = orderItems.get(j);
                // update
                PetProduct petProduct = productDAO.queryProductById(orderItem.getProductId());

                int newStock = petProduct.getStock() + orderItem.getBuyCounts();

                int i1 = productDAO.updateStockById(petProduct.getProductId(), newStock);
                System.out.println("result: " + i1);
            }
        }
    }

    // cancel the order not paid yet
    @Override
    public ResultVO cancelOrderWithoutRefund(String orderId) {
        ResultVO resultVO=null;
        // 1. ask whatchat pay server to close current pay request
        WXPay wxPay = new WXPay(new MyWXPayConfig());
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put("out_trade_no", orderId);
            Map<String, String> resp = wxPay.orderQuery(map);
            System.out.println(resp);

            if("NOTPAY".equalsIgnoreCase(resp.get("trade_state"))) {
                // close the wechat pay link from wechat pay server, so that user cannot scan to pay
                Map<String, String> result = wxPay.closeOrder(map);
                System.out.println(result);

                if("SUCCESS".equalsIgnoreCase(resp.get("result_code"))){
                    // which means wechat pay closed the pay access
                    // then close the order
                    closeOrder(orderId);

                   resultVO= new ResultVO(RespondStatus.OK,"canceled", null);
                }else{
                   resultVO = new ResultVO(RespondStatus.NO,"failed", null);
                }
            }else if("CLOSED".equalsIgnoreCase(resp.get("trade_state"))){
                closeOrder(orderId);

                resultVO= new ResultVO(RespondStatus.OK,"canceled", null);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return resultVO;
    }

    @Override
    public ResultVO getListOrderVObyUserId(String userId) {

        int i = Integer.parseInt(userId);
        List<OrderVO> orderVOS = orderDAO.queryListOrderVOByUserId(i);
        return new ResultVO(RespondStatus.OK, "List of orderVO", orderVOS);
    }

    @Override
    public ResultVO getOrderVObyItemId(String itemId) {
        OrderVO orderVO = orderDAO.queryOneOrderVOByItemId(itemId);
        Ship ship = shipDAO.queryShipByOrderId(orderVO.getItemId());
        if(ship!=null){
            orderVO.setShipId(ship.getShipId());
            orderVO.setShipNumber(ship.getShipNumber());
            orderVO.setReceiverTime(ship.getReceiverTime());
        }
        return new ResultVO(RespondStatus.OK, "ordervo", orderVO);
    }

    @Override
    public ResultVO getOrderVOsbyStoreId(String storeId) {
        List<OrderVO> orderVOS = orderDAO.queryOrderVOsByStoreId(Integer.parseInt(storeId));
        return new ResultVO(RespondStatus.OK, "ordervo", orderVOS);
    }

    @Override
    public ResultVO ChangeToComment(String orderId, Date updateTime) {
        int i = orderDAO.updateOrderStatusToComment(orderId, updateTime);
        if(i > 0){
            return new ResultVO(RespondStatus.OK, "changed", null);

        }else{
            return new ResultVO(RespondStatus.NO, "Failed", null);
        }
    }

    /**
     * Data Analysis
     */
    @Override
    public List<OrderVO> getProductMontlySale(String storeId) {
        int sid = Integer.parseInt(storeId);
        List<OrderVO> orderVOS = orderDAO.monthlyProduceSale(sid);

        return orderVOS;
    }

    @Override
    public List<OrderVO> getFiveRecentOrders(String storeId) {
        int sid = Integer.parseInt(storeId);
        List<OrderVO> orderVOS = orderDAO.fiveRecentOrder(sid);
        return orderVOS;
    }

    @Override
    public OrderVO getBestSoldProduct(String storeId) {
        int sid = Integer.parseInt(storeId);
        OrderVO orderVO = orderDAO.bestSoldProduct(sid);
        return orderVO;
    }

    @Override
    public List<OrderVO> getFiveTopProduct(String storeId) {
        int sid = Integer.parseInt(storeId);
        List<OrderVO> orderVOS = orderDAO.topFiveSoldProduct(sid);
        return orderVOS;
    }

    @Override
    public List<Double> getLastMonthDailyOrderProfit(String storeId) {
        int sid = Integer.parseInt(storeId);
        List<OrderVO> orderVOS = orderDAO.lastMonthDailyProfit(sid);
        List<Double> profit = new ArrayList<>(Collections.nCopies(31, 0.0));

        for(int i =0; i < orderVOS.size(); i++){
            OrderVO orderVO = orderVOS.get(i);
            int index = Integer.parseInt(orderVO.getReceiverName())-1;
            profit.set(index, orderVO.getTotalAmount());
        }

        return profit;
    }

    @Override
    public List<Double> getCurrentMonthDailyOrderProfit(String storeId) {
        int sid = Integer.parseInt(storeId);
        List<OrderVO> orderVOS = orderDAO.currentMonthDailyProfit(sid);
        List<Double> profit = new ArrayList<>(Collections.nCopies(31, 0.0));

        for(int i =0; i < orderVOS.size(); i++){
            OrderVO orderVO = orderVOS.get(i);
            int index = Integer.parseInt(orderVO.getReceiverName())-1;
            profit.set(index, orderVO.getTotalAmount());
        }

        return profit;
    }

    /**
     *
     * @param storeId
     * @return
     */
    @Override
    public OrderVO getTotalSale(String storeId) {
        int sid = Integer.parseInt(storeId);
        OrderVO orderVO = orderDAO.totalSale(sid);
        return orderVO;
    }


}
