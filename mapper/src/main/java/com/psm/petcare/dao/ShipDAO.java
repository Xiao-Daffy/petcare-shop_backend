package com.psm.petcare.dao;

import com.psm.petcare.entity.Ship;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipDAO {

    public Ship queryShipByOrderId(String orderId);
    public int insertShipNumber(Ship ship);
}
