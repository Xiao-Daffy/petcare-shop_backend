package com.psm.petcare.service.impl;

import com.psm.petcare.dao.UserAddressDAO;
import com.psm.petcare.dao.UserDAO;
import com.psm.petcare.entity.User;
import com.psm.petcare.entity.UserAddress;
import com.psm.petcare.service.UserAddressService;
import com.psm.petcare.vo.RespondStatus;
import com.psm.petcare.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class UserAddressServiceImpl implements UserAddressService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserAddressDAO userAddressDAO;

    @Override
    public ResultVO getListAddress(String userId) {
        //check the user if existing
        int uid = Integer.parseInt(userId);
        User user = userDAO.queryUserById(uid);
        if(user == null){
            return new ResultVO(RespondStatus.NO, "User not existing", null);
        }else{

            // user existing
            // return list of address
            List<UserAddress> userAddresses = userAddressDAO.listAddress(uid);
            return new ResultVO(RespondStatus.OK, " Fetched address", userAddresses);
        }

    }

    @Override
    public ResultVO deleteAddress(String addrId) {
        //check the user if existing
        int aid = Integer.parseInt(addrId);
        int i = userAddressDAO.deleteAddress(aid);
        if(i > 0){
            return new ResultVO(RespondStatus.OK, "Successed deleted", null);
        }else{
            return new ResultVO(RespondStatus.NO, "Failed deleted", null);
        }
    }

    @Override
    public ResultVO addAddress(UserAddress userAddress) {
        userAddress.setCreateTime(new Date());
        userAddress.setUpdateTime(new Date());
        // check the user set it's default address or not
        if(userAddress.getDefaultAddr()==1){
            // if set, then remove previous default userAddress as
            int i = userAddressDAO.removeDefaultAddress(userAddress.getUserId());
            if(i > 0){
                // remove successfully
                int i1 = userAddressDAO.addAddress(userAddress);
                if(i1 > 0){
                    return new ResultVO(RespondStatus.OK, "Successed to add", null);
                }else{
                    return new ResultVO(RespondStatus.NO, "Failed to add", null);
                }
            }else{
               // failed to remove the default address
                return new ResultVO(RespondStatus.NO, "Failed to remove default address", null);
            }
        }else{
            // then just add the user
            int i1 = userAddressDAO.addAddress(userAddress);
            if(i1 > 0){
                return new ResultVO(RespondStatus.OK, "Successed to add", null);
            }else{
                return new ResultVO(RespondStatus.NO, "Failed to add", null);
            }
        }


    }

    @Override
    public ResultVO getAddress(String addrId) {
        int aid =Integer.parseInt(addrId);

        UserAddress userAddress = userAddressDAO.queryAddressById(aid);
        if(userAddress == null){
            return new ResultVO(RespondStatus.NO, "Not found", null);
        }else{
            return new ResultVO(RespondStatus.OK, "Found", userAddress);
        }
    }

    @Override
    public ResultVO updateAddressInfo(UserAddress userAddress) {
        userAddress.setUpdateTime(new Date());
        if(userAddress.getDefaultAddr()==1){
            // if set, then remove previous default userAddress as
            int i = userAddressDAO.removeDefaultAddress(userAddress.getUserId());
            if(i > 0){
                // remove successfully
                int i1 = userAddressDAO.updateAddress(userAddress);
                if(i1 > 0){
                    return new ResultVO(RespondStatus.OK, "Successed to update", null);
                }else{
                    return new ResultVO(RespondStatus.NO, "Failed to update", null);
                }
            }else{
                // failed to remove the default address
                return new ResultVO(RespondStatus.NO, "Failed to remove default address", null);
            }
        }else{
            // then just update the user
            int i1 = userAddressDAO.updateAddress(userAddress);
            if(i1 > 0){
                return new ResultVO(RespondStatus.OK, "Successed to update", null);
            }else{
                return new ResultVO(RespondStatus.NO, "Failed to update", null);
            }
        }

    }
}
