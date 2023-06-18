package com.psm.petcare.service;

import com.psm.petcare.entity.UserAddress;
import com.psm.petcare.vo.ResultVO;

public interface UserAddressService {

    // 1. get list of address
    public ResultVO getListAddress(String userId);

    //2. delete address
    public ResultVO deleteAddress(String addrId);

    //3. add address
    public ResultVO addAddress(UserAddress userAddress);

    // get address
    public ResultVO getAddress(String addrId);

    // update address
    public ResultVO updateAddressInfo( UserAddress userAddress);


}
