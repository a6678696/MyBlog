package com.ledao.entity;

/**
 * @author LeDao
 * @company
 * @create 2021-06-08 21:28
 */
public class Fruit {

    /**
     * id
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 数量
     */
    private Integer num;

    public Fruit() {
    }

    public Fruit(String name, Integer num) {
        this.name = name;
        this.num = num;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
