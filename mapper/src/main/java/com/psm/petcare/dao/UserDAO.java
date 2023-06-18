package com.psm.petcare.dao;

import com.psm.petcare.entity.User;
import org.springframework.stereotype.Repository;

@Repository // for @Autowired in UserService class
public interface UserDAO {


    /* user register to system by email & password
       return done(1) or user id
    */
    public int insertUser(User user); //1: success insert  0: fail

    // Search user by email -- login
    public User queryUserByEmail(String email);

    // user change the password by email
    public int updatePassword(String email, String password);

    // users query user based on user's id.
    public User queryUserById(int userId);

    // users update their profile
    public int updateUserInfo(User user);


    public int updateUserType(String userType, int userId);
}
