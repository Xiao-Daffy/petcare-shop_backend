package com.psm.petcare.dao;

import com.psm.petcare.entity.UserAddress;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAddressDAO {

    public int updateAddress(UserAddress userAddress);

    public int removeDefaultAddress(int userId);//


    public int addAddress(UserAddress userAddress);

    public int deleteAddress(int addrId);

    public List<UserAddress> listAddress(int userId);

    public UserAddress queryAddressById(int addrId);
}
