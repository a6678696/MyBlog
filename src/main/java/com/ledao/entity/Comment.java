package com.ledao.entity;

import java.util.Date;

/**
 * 评论实体
 *
 * @author LeDao
 * @company
 * @create 2021-02-04 19:09
 */
public class Comment {

    /**
     * 编号
     */
    private Integer id;
    /**
     * 文章编号
     */
    private Integer blogId;
    /**
     * 评论时间
     */
    private Date date;
    /**
     * 评论人IP地址
     */
    private String ip;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 当前状态(0未审核 1审核通过 2审核不通过)
     */
    private Integer state;

    /**
     * 图片名称
     */
    private String imageName;
    /**
     * 回复
     */
    private String reply;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
}
