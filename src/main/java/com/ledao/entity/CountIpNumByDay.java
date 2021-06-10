package com.ledao.entity;

/**
 * 每日访问ip个数
 *
 * @author LeDao
 * @company
 * @create 2021-06-08 23:40
 */
public class CountIpNumByDay {

    /**
     * 日期
     */
    private String date;
    /**
     * ip个数
     */
    private Integer num;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
