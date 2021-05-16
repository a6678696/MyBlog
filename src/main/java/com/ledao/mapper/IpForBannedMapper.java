package com.ledao.mapper;

import com.ledao.entity.IpForBanned;

import java.util.List;
import java.util.Map;

/**
 * @author LeDao
 * @company
 * @create 2021-05-17 1:24
 */
public interface IpForBannedMapper {

    /**
     * 分页分条件查询被封禁的Ip
     *
     * @param map
     * @return
     */
    List<IpForBanned> list(Map<String, Object> map);

    /**
     * 获取记录数
     *
     * @param map
     * @return
     */
    Long getCount(Map<String, Object> map);

    /**
     * 添加被封禁的Ip
     *
     * @param ipForBanned
     * @return
     */
    Integer add(IpForBanned ipForBanned);

    /**
     * 根据id删除被封禁的Ip
     *
     * @param id
     * @return
     */
    Integer delete(Integer id);

    /**
     * 根据id查找被封禁的ip
     *
     * @param ip
     * @return
     */
    IpForBanned findByIp(String ip);
}
