package com.psm.dao;

import com.psm.ApiApplication;
import com.psm.petcare.dao.*;
import com.psm.petcare.entity.*;
import com.psm.petcare.service.UserAddressService;
import com.psm.petcare.vo.ResultVO;
import com.sun.javaws.IconUtil;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiApplication.class)
public class UserDAOTest {

    @Resource
    private UserDAO userDAO;

    @Resource
    private CartDAO cartDAO;
    @Resource
    private PetServiceDAO petServiceDAO;
    @Resource
    private UserAddressDAO userAddressDAO;

    @Resource
    private ReservationDAO reservationDAO;
    @Resource
    private PetProductDAO petProductDAO;
    @Resource
    private OrderItemDAO orderItemDAO;
    @Resource
    private ShipDAO shipDAO;
    @Autowired
    private OrderDAO orderDAO;
    @Test
    public void queryUserByName() {
        User user = userDAO.queryUserByEmail("daffy@gmail.com");
        System.out.println(user);
    }

    @Test
    public void updatePassword() {

        int i = userDAO.updatePassword("daffy@gmail.com", "101010");
        System.out.println(i);
    }

    @Test
    public void updateAddress() {
        UserAddress userAddress = new UserAddress();
        userAddress.setAddrId(1);
        userAddress.setReceiverName("Zhang");
        userAddress.setReceiverMobile("111023791");
        userAddress.setState("Johor");
//        List<UserAddress> userAddresses = userAddressDAO.listAddress(1);
//        userAddressDAO.removeDefaultAddress(1);
//        System.out.println(userAddresses);

    }

    @Test
    public void updateStore(){

        OrderItem orderItem = orderItemDAO.queryOrderItemByItemId("168442934064661676");
        System.out.println(orderItem);
    }
}