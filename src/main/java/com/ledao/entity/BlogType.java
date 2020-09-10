package com.ledao.entity;

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

    @Override
    public String toString() {
        return "BlogType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sortNum=" + sortNum +
                '}';
    }
}
