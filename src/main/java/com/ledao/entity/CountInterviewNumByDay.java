package com.ledao.entity;

/**
 * 每日访问数实体
 *
 * @author LeDao
 * @company
 * @create 2021-06-22 8:57
 */
public class CountInterviewNumByDay {

    /**
     * 日期
     */
    private String date;
    /**
     * 访问总数
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
