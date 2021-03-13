package com.ledao.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * 根据IP获取真实地址
 *
 * @author LeDao
 * @company
 * @create 2021-03-13 11:18
 */
public class AddressUtil {

    public static String getAddress(String ip) {
        String result1 = HttpUtil.get("https://www.ip138.com/iplookup.asp?ip=" + ip + "&action=2");
        String result = result1.split("\"ASN归属地\":\"")[1].split("\", \"iP段\":")[0];
        return result;
    }

    public static String getAddress2(String ip) {
        String url = "http://ip.ws.126.net/ipquery?ip=" + ip;
        String str = HttpUtil.get(url);
        if (!StrUtil.hasBlank(str)) {
            String substring = str.substring(str.indexOf("{"), str.indexOf("}") + 1);
            JSONObject jsonObject = JSONUtil.parseObj(substring);
            String province = jsonObject.getStr("province");
            String city = jsonObject.getStr("city");
            return province + " " + city;
        }
        return null;
    }

    public static void main(String[] args) {
        String ip = "51.254.59.113";
        System.out.println(getAddress(ip));
        System.out.println(getAddress2(ip));
    }
}
