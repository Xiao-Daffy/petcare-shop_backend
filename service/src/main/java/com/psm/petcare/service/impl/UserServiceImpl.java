package com.psm.petcare.service.impl;

import com.psm.petcare.dao.UserDAO;
import com.psm.petcare.entity.User;
import com.psm.petcare.service.UserService;
import com.psm.petcare.utils.MD5Utils;
import com.psm.petcare.vo.RespondStatus;
import com.psm.petcare.vo.ResultVO;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

@Service
@Scope("singleton") // it's for synchronized(this){}
// no matter how many threads create the UserServiceImpl对象, they're using one
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    //user register
    @Transactional //数据库事务，防止多人同时用相同的email进行注册
    public ResultVO userRegister(String email, String psw) {



        synchronized (this) { // avoid users register with same email under multi threads at same time
            User user = userDAO.queryUserByEmail(email);
            // if user never register
            if (user == null) {
                // insert user info into database
                user = new User();

                //encrypt the password based on MD5
                String md5Psw = MD5Utils.md5(psw);
                user.setPassword(md5Psw);
                user.setEmail(email);
                user.setUserType("user");
                user.setUserRegisterTime(new Date());
                int i = userDAO.insertUser(user);

                // if return 1 means successed, 0: failed
                if (i > 0) {
                    return new ResultVO(RespondStatus.OK, "Successfully registered", null);
                } else {
                    return new ResultVO(RespondStatus.NO, "Failed registered", null);
                }
            } else {
                // user already registered
                return new ResultVO(RespondStatus.NO, "User already registered", null);
            }
        }

    }

    // login
    @Override
    public ResultVO checkLogin(String email, String psw) {

        // check email first, then check password by calling user.getPassword()
        User user = userDAO.queryUserByEmail(email);

        //if user null
        if(user == null){
            return new ResultVO(RespondStatus.NO, "User not found", null);
        }else{

            // user can found
            //encrypt the password based on MD5
            String md5Psw = MD5Utils.md5(psw);
            if(user.getPassword().equals(md5Psw)){

                // if successfully login, then using JWT(JSON Web Token) to generate the token
                // token is for proofing user logged in the system already so that user can access restricted resources
                // and then save to ResultVO.msg,
                JwtBuilder builder =  Jwts.builder();
                HashMap<String, Object> map = new HashMap<>();
                String token = builder.setSubject("Petcare Shop 1231") // Jwt's data
                        .setIssuedAt(new Date()) // Jwt's issued date
                        .setId(user.getUserId() + "") // Jwt's id, usually using user'id as jwt's id
                        .setClaims(map) // map can store user characters such as {user, owner}
                        .setExpiration(new Date(System.currentTimeMillis() + 24 * 60* 60 * 1000)) // jwt expired date
                        .signWith(SignatureAlgorithm.HS256, "Zhang") // using HS256 to generate the token, and "zhang" is the password for jwt
                        .compact();
                System.out.println("user testing");
                // password also correct
                return new ResultVO(RespondStatus.OK, token, user);
            }else{
                // password is wrong
                return new ResultVO(RespondStatus.NO, "Wrong password", null);
            }
        }

    }

    @Override
    public ResultVO restPassword(String email, String password) {

        // check the email if it registered or not before
        User user = userDAO.queryUserByEmail(email);
        if(user == null){
            return new ResultVO(RespondStatus.NO, "The email cannot be found", null);
        }else{

            // user registered
            // update the password
            String md5Psw = MD5Utils.md5(password);
            int i = userDAO.updatePassword(email, md5Psw);
            if(i >0){
                // success change
                return new ResultVO(RespondStatus.OK, "Password successfully changed", null);
            }else{
                return new ResultVO(RespondStatus.NO, "Failed to change password, please try again", null);
            }
        }
    }

    // User view profile
    @Override
    public ResultVO getProfile(String userId) {

        int uid = Integer.parseInt(userId);

        User user = userDAO.queryUserById(uid);

        if(user == null){
            return new ResultVO(RespondStatus.NO, "User not existing", null);
        }else{
            return new ResultVO(RespondStatus.OK,"User exists", user);
        }
    }

    // user edit profile
    @Override
    public ResultVO updateProfile(String userId, User user) {

        // check user if in the DB
        int uid = Integer.parseInt(userId);
        User user1 = userDAO.queryUserById(uid);

        if(user == null){
            // if not existing
            return new ResultVO(RespondStatus.NO, "User not existing", null);
        }else{
            // if existing
            int i = userDAO.updateUserInfo(user);
            if(i > 0){
                // update successfully
                return new ResultVO(RespondStatus.OK, "Successed to update", null);
            }else{
                return new ResultVO(RespondStatus.NO, "Failed to update", null);
            }
        }
    }






}
