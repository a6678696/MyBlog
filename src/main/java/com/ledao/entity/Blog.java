package com.ledao.entity;

import com.google.gson.Gson;

import java.util.Date;

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
    /**
     * 用于判断当前IP是否点赞过这篇博客
     */
    private Integer isLike;
    /**
     * 点赞数
     */
    private Integer likeNum;
    /**
     * 是否是导航条文章（0或空代表不是，1代表是）
     */
    private Integer isMenuBlog;
    /**
     * 设置成为导航条文章的时间
     */
    private Date setMenuBlogDate;
    /**
     * 颜色
     */
    private String color;

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

    public Integer getIsLike() {
        return isLike;
    }

    public void setIsLike(Integer isLike) {
        this.isLike = isLike;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Integer getIsMenuBlog() {
        return isMenuBlog;
    }

    public void setIsMenuBlog(Integer isMenuBlog) {
        this.isMenuBlog = isMenuBlog;
    }

    public Date getSetMenuBlogDate() {
        return setMenuBlogDate;
    }

    public void setSetMenuBlogDate(Date setMenuBlogDate) {
        this.setMenuBlogDate = setMenuBlogDate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public static Blog jsonToEntity(String json) {
        Gson gson = new Gson();
        Blog blog = gson.fromJson(json, Blog.class);
        return blog;
    }
}
