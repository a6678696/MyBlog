package com.ledao.entity;

/**
 * @author LeDao
 * @company
 * @create 2021-07-17 10:39
 */
public class Tobacco {

    private String cgt_name;

    private Integer req_qty;

    private Float price;

    public Tobacco() {
    }

    public Tobacco(String cgt_name, Integer req_qty, Float price) {
        this.cgt_name = cgt_name;
        this.req_qty = req_qty;
        this.price = price;
    }

    public String getCgt_name() {
        return cgt_name;
    }

    public void setCgt_name(String cgt_name) {
        this.cgt_name = cgt_name;
    }

    public Integer getReq_qty() {
        return req_qty;
    }

    public void setReq_qty(Integer req_qty) {
        this.req_qty = req_qty;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Tobacco{" +
                "cgt_name='" + cgt_name + '\'' +
                ", req_qty=" + req_qty +
                ", price=" + price +
                '}';
    }
}
