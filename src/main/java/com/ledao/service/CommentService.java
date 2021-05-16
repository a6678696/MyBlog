package com.ledao.service;

import com.ledao.entity.Comment;

import java.util.List;
import java.util.Map;

/**
 * 评论Service接口
 *
 * @author LeDao
 * @company
 * @create 2021-02-04 20:45
 */
public interface CommentService {

    /**
     * 分页分条件查询评论
     *
     * @param map
     * @return
     */
    List<Comment> list(Map<String, Object> map);

    /**
     * 获取记录数
     *
     * @param map
     * @return
     */
    Long getTotal(Map<String, Object> map);

    /**
     * 添加评论
     *
     * @param comment
     * @return
     */
    Integer add(Comment comment);

    /**
     * 修改评论(只修改状态)
     *
     * @param comment
     * @return
     */
    Integer update(Comment comment);

    /**
     * 根据id删除评论
     *
     * @param id
     * @return
     */
    Integer deleteById(Integer id);

    /**
     * 根据评论人IP查询评论
     *
     * @param ip
     * @return
     */
    List<Comment> findByIp(String ip);

    /**
     * 根据id查询评论
     *
     * @param id
     * @return
     */
    Comment findById(Integer id);

    /**
     * 根据博客id删除评论
     *
     * @param blogId
     * @return
     */
    Integer deleteByBlogId(Integer blogId);

    /**
     * 获取某ip今日评论数量
     *
     * @param ip
     * @return
     */
    Long getTodayCommentCount(String ip);

    /**
     * 获取某ip最近5分钟评论数量
     *
     * @param ip
     * @return
     */
    Long getLastFiveMinutesCommentCount(String ip);
}
