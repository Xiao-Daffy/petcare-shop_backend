package com.psm.petcare.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//MD5 generator
public class MD5Utils {
    public static String md5(String password){
        //create MD5
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            //calculate MD5
            md.update(password.getBytes());
            //BigInteger
            //BigInteger(para1,para2)
            return new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
