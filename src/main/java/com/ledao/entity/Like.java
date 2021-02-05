package com.ledao.entity;

import java.util.Date;

/**
 * 点赞记录实体
 *
 * @author LeDao
 * @company
 * @create 2021-02-05 14:31
 */
public class Like {

    /**
     * 编号
     */
    private Integer id;
    /**
     * 点赞时间
     */
    private Date date;
    /**
     * 点赞博客id
     */
    private Integer blogId;
    /**
     * 点赞博客实体
     */
    private Blog blog;
    /**
     * 点赞人IP地址
     */
    private String ip;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }
}
