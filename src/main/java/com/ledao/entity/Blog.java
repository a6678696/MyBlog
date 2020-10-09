package com.ledao.entity;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 博客实体
 *
 * @author LeDao
 * @company
 * @create 2020-09-11 00:08
 */
public class Blog {

    /**
     * id
     */
    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 摘要
     */
    private String summary;
    /**
     * 内容
     */
    private String content;
    /**
     * 发布时间
     */
    private Date releaseDate;
    /**
     * 点击次数
     */
    private Integer click;
    /**
     * 博客类别id
     */
    private Integer blogTypeId;
    /**
     * 博客类别
     */
    private BlogType blogType;
    /**
     * 博客里存在的第一张图片，主要用于列表展示的缩略图
     */
    private String imageName;
    /**
     * 博客数量 非博客实际属性 主要是 根据发布日期归档查询数量用到
     */
    private Integer blogCount;
    /**
     * 发布日期的字符串 只取年和月
     */
    private String releaseDateStr;
    /**
     * 该类型的博客数量
     */
    private Integer blogNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getClick() {
        return click;
    }

    public void setClick(Integer click) {
        this.click = click;
    }

    public Integer getBlogTypeId() {
        return blogTypeId;
    }

    public void setBlogTypeId(Integer blogTypeId) {
        this.blogTypeId = blogTypeId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public BlogType getBlogType() {
        return blogType;
    }

    public void setBlogType(BlogType blogType) {
        this.blogType = blogType;
    }

    public Integer getBlogCount() {
        return blogCount;
    }

    public void setBlogCount(Integer blogCount) {
        this.blogCount = blogCount;
    }

    public String getReleaseDateStr() {
        return releaseDateStr;
    }

    public void setReleaseDateStr(String releaseDateStr) {
        this.releaseDateStr = releaseDateStr;
    }

    public Integer getBlogNum() {
        return blogNum;
    }

    public void setBlogNum(Integer blogNum) {
        this.blogNum = blogNum;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", content='" + content + '\'' +
                ", releaseDate=" + releaseDate +
                ", click=" + click +
                ", blogTypeId=" + blogTypeId +
                ", blogType=" + blogType +
                ", imageName='" + imageName + '\'' +
                ", blogCount=" + blogCount +
                ", releaseDateStr='" + releaseDateStr + '\'' +
                ", blogNum=" + blogNum +
                '}';
    }
}
