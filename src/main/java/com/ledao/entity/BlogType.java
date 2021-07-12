package com.ledao.entity;

import com.google.gson.Gson;

/**
 * 博客类别实体类
 *
 * @author LeDao
 * @company
 * @create 2020-09-10 21:22
 */
public class BlogType {

    /**
     * id
     */
    private Integer id;
    /**
     * 博客类别名称
     */
    private String name;
    /**
     * 排列数字
     */
    private Integer sortNum;
    /**
     * 颜色
     */
    private String color;
    /**
     * 该类别的博客数量
     */
    private Long blogNum;

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

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Long getBlogNum() {
        return blogNum;
    }

    public void setBlogNum(Long blogNum) {
        this.blogNum = blogNum;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "BlogType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sortNum=" + sortNum +
                ", color='" + color + '\'' +
                ", blogNum=" + blogNum +
                '}';
    }

    public static BlogType jsonToEntity(String json) {
        Gson gson = new Gson();
        BlogType blogType = gson.fromJson(json, BlogType.class);
        return blogType;
    }
}
