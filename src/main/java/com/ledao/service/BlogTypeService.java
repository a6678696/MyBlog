package com.ledao.service;

import com.ledao.entity.BlogType;

import java.util.List;
import java.util.Map;

/**
 * 博客类别Service接口
 *
 * @author LeDao
 * @company
 * @create 2020-09-10 21:39
 */
public interface BlogTypeService {

    /**
     * 分页分条件查询博客类型
     *
     * @param map
     * @return
     */
    List<BlogType> list(Map<String, Object> map);

    /**
     * 获取记录数
     *
     * @param map
     * @return
     */
    Long getCount(Map<String, Object> map);

    /**
     * 添加博客类型
     *
     * @param blogType
     * @return
     */
    Integer add(BlogType blogType);

    /**
     * 修改博客类别
     *
     * @param blogType
     * @return
     */
    Integer update(BlogType blogType);

    /**
     * 根据id删除博客类型
     *
     * @param id
     * @return
     */
    Integer delete(Integer id);

    /**
     * 根据名称查找博客类别
     *
     * @param name
     * @return
     */
    List<BlogType> findByName(String name);

    /**
     * 根据id查找博客类别
     *
     * @param id
     * @return
     */
    BlogType findById(Integer id);

    /**
     * 根据id获取该类别的博客数量
     *
     * @param id
     * @return
     */
    Long getBlogNumThisType(Integer id);
}
