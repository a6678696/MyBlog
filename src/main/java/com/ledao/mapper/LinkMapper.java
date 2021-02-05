package com.ledao.mapper;

import com.ledao.entity.Link;

import java.util.List;
import java.util.Map;

/**
 * 友情链接Mapper接口
 *
 * @author LeDao
 * @company
 * @create 2021-02-03 18:01
 */
public interface LinkMapper {

    /**
     * 分页分条件查询博客类别
     *
     * @param map
     * @return
     */
    List<Link> list(Map<String, Object> map);

    /**
     * 获取记录数
     *
     * @param map
     * @return
     */
    Long getTotal(Map<String, Object> map);

    /**
     * 添加友情链接
     *
     * @param link
     * @return
     */
    Integer add(Link link);

    /**
     * 修改友情链接
     *
     * @param link
     * @return
     */
    Integer update(Link link);

    /**
     * 根据id删除友情链接
     *
     * @param id
     * @return
     */
    Integer deleteById(Integer id);
}
