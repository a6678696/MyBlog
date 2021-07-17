package com.ledao.util;

/**
 * @author LeDao
 * @company
 * @create 2021-06-24 0:39
 */
public class AscUtil {

    /**
     * 解密
     *
     * @param ssoToken 字符串
     * @return String 返回解密字符串
     */
    public static String decrypt(String ssoToken) {
        try {
            String name = new String();
            java.util.StringTokenizer st = new java.util.StringTokenizer(ssoToken, "%");
            while (st.hasMoreElements()) {
                int asc = Integer.parseInt((String) st.nextElement()) - 27;
                name = name + (char) asc;
            }

            return name;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 加密
     *
     * @param ssoToken 字符串
     * @return String 返回加密字符串
     */
    public static String encrypt(String ssoToken) {
        try {
            byte[] _ssoToken = ssoToken.getBytes("ISO-8859-1");
            String name = new String();
            for (int i = 0; i < _ssoToken.length; i++) {
                int asc = _ssoToken[i];
                _ssoToken[i] = (byte) (asc + 27);
                name = name + (asc + 27) + "%";
            }
            return name;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(encrypt("123456"));
        System.out.println(decrypt("76%77%78%79%80%81%"));
    }
}
