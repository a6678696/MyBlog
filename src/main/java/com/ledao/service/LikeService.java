package com.ledao.service;

import com.ledao.entity.Like;

import java.util.List;
import java.util.Map;

/**
 * 点赞记录Service接口
 *
 * @author LeDao
 * @company
 * @create 2021-02-05 14:41
 */
public interface LikeService {

    /**
     * 分页分条件查询点赞记录
     *
     * @param map
     * @return
     */
    List<Like> list(Map<String, Object> map);

    /**
     * 获取记录数
     *
     * @param map
     * @return
     */
    Long getTotal(Map<String, Object> map);

    /**
     * 添加点赞记录
     *
     * @param like
     * @return
     */
    Integer add(Like like);

    /**
     * 删除点赞记录
     *
     * @param id
     * @return
     */
    Integer deleteById(Integer id);
}
