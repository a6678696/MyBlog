package com.ledao.util;

import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Redis工具类
 *
 * @author LeDao
 * @company
 * @create 2021-06-22 12:25
 */
public class RedisUtil {

    private static final String host = "192.168.0.153";
    private static Integer port = 6379;
    private static final String authPassword = "123456";

    /**
     * Java实体转化为json
     *
     * @param o
     * @return
     */
    public static String entityToJson(Object o) {
        Gson gson = new Gson();
        String json = gson.toJson(o);
        return json;
    }

    /**
     * json转化为Java实体
     *
     * @param json
     * @param o
     * @return
     */
    public static Object jsonToEntity(String json, Object o) {
        Gson gson = new Gson();
        o = gson.fromJson(json, o.getClass());
        return o;
    }

    /**
     * 设置key
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean setKey(String key, String value) {
        Jedis jedis = new Jedis(host, port);
        jedis.auth(authPassword);
        String result = jedis.set(key, value);
        jedis.close();
        return "OK".equals(result);
    }

    /**
     * 获取key对应的值
     *
     * @param key
     * @return
     */
    public static String getKeyValue(String key) {
        Jedis jedis = new Jedis(host, port);
        jedis.auth(authPassword);
        String result = jedis.get(key);
        jedis.close();
        return result;
    }

    /**
     * key是否存在
     *
     * @param key
     * @return
     */
    public static boolean existKey(String key) {
        Jedis jedis = new Jedis(host, port);
        jedis.auth(authPassword);
        boolean result = jedis.exists(key);
        jedis.close();
        return result;
    }

    /**
     * 删除key
     *
     * @param key
     * @return
     */
    public static boolean delKey(String key) {
        Jedis jedis = new Jedis(host, port);
        jedis.auth(authPassword);
        Long result;
        //key存在就删除
        if (existKey(key)) {
            result = jedis.del(key);
        } else {
            result = 0L;
        }
        jedis.close();
        return result > 0;
    }

    /**
     * 从右边添加元素
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean listRightPush(String key, String value) {
        Jedis jedis = new Jedis(host, port);
        jedis.auth(authPassword);
        Long result = jedis.rpush(key, value);
        jedis.close();
        return result > 0;
    }

    /**
     * 从右边弹出元素
     *
     * @param key
     * @return
     */
    public static boolean listRightPop(String key) {
        Jedis jedis = new Jedis(host, port);
        jedis.auth(authPassword);
        String result = jedis.rpop(key);
        jedis.close();
        return "OK".equals(result);
    }

    /**
     * 获取list的元素个数
     *
     * @param key
     * @return
     */
    public static Long listLength(String key) {
        Jedis jedis = new Jedis(host, port);
        jedis.auth(authPassword);
        Long result = jedis.llen(key);
        jedis.close();
        return result;
    }

    /**
     * 获取全部元素集合
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static List<String> listRange(String key, Long start, Long end) {
        Jedis jedis = new Jedis(host, port);
        jedis.auth(authPassword);
        List<String> resultList = jedis.lrange(key, start, end);
        jedis.close();
        return resultList;
    }
}
