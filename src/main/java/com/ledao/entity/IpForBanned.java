package com.ledao.entity;

import java.util.Date;

/**
 * 被封禁的Ip实体
 *
 * @author LeDao
 * @company
 * @create 2021-05-17 1:19
 */
public class IpForBanned {

    /**
     * 编号
     */
    private Integer id;
    /**
     * ip地址
     */
    private String ip;
    /**
     * 分为主动封禁和自动封禁
     */
    private String type;
    /**
     * 封禁时间
     */
    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
