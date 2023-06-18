package com.psm.petcare.service;

import com.psm.petcare.entity.User;
import com.psm.petcare.vo.ResultVO;
import org.springframework.stereotype.Repository;


public interface UserService {

    public ResultVO userRegister(String email,String pwd);

    public ResultVO checkLogin(String email, String pwd);

    public ResultVO restPassword(String email, String password);

    public ResultVO getProfile(String userId);

    public ResultVO updateProfile(String userId, User user);
}
