package com.ledao.util;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @author LeDao
 * @company
 * @create 2020-12-12 15:41
 */
public class Md5Util {

    public static final String SALT = "LeDao333";

    /**
     * MD5加密
     *
     * @param str
     * @param salt
     * @return
     */
    public static String md5(String str, String salt) {
        return new Md5Hash(str, salt).toString();
    }

    public static void main(String[] args) {
        String password = "abcd6666122";
        System.out.println("MD5加密后:"+Md5Util.md5(password,Md5Util.SALT));
    }
}
