package com.psm.petcare.service.impl;

import com.psm.petcare.dao.OrderDAO;
import com.psm.petcare.dao.ShipDAO;
import com.psm.petcare.entity.Ship;
import com.psm.petcare.service.ShipService;
import com.psm.petcare.vo.RespondStatus;
import com.psm.petcare.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ShipServiceImpl implements ShipService {

    @Autowired
    private ShipDAO shipDAO;
    @Autowired
    private OrderDAO orderDAO;
    @Override
    public ResultVO addShipNumber(Ship ship) {
        ship.setReceiverTime(new Date());
        int i = shipDAO.insertShipNumber(ship);
        if(i > 0){

            // change the order status
            orderDAO.updateOrderStatusByOrderId2(ship.getOrderId(), "To Receive");


            return new ResultVO(RespondStatus.OK, "added", null);

        }else{
            return new ResultVO(RespondStatus.NO, "Failed", null);
        }
    }
}
