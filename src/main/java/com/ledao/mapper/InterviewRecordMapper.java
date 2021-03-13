package com.ledao.mapper;

import com.ledao.entity.InterviewRecord;

import java.util.List;
import java.util.Map;

/**
 * 访问记录Mapper接口
 *
 * @author LeDao
 * @company
 * @create 2020-10-21 19:36
 */
public interface InterviewRecordMapper {

    /**
     * 分页分条件查询访问记录
     *
     * @param map
     * @return
     */
    List<InterviewRecord> list(Map<String, Object> map);

    /**
     * 获取记录数
     *
     * @param map
     * @return
     */
    Long getCount(Map<String, Object> map);

    /**
     * 添加访问记录
     *
     * @param interviewRecord
     * @return
     */
    Integer add(InterviewRecord interviewRecord);

    /**
     * 更新访问记录(如果有真实地址为空的时候用到)
     *
     * @param interviewRecord
     * @return
     */
    Integer update(InterviewRecord interviewRecord);

    /**
     * 根据id删除访问记录
     *
     * @param id
     * @return
     */
    Integer delete(Integer id);

    /**
     * 获取客户访问博客首页的总次数
     *
     * @return
     */
    Long getCountInterviewFirstPageNum();

    /**
     * 获取访问博客系统的客户ip地址的总数
     *
     * @return
     */
    Long getCountInterviewIpNum();

    /**
     * 获取今天客户访问博客首页的总次数
     *
     * @return
     */
    Long getCountTodayInterviewFirstPageNum();

    /**
     * 获取今天访问博客系统的客户ip地址的总数
     *
     * @return
     */
    Long getCountTodayInterviewIpNum();

    /**
     * 获取某个ip地址出现的次数
     *
     * @param interviewerIp
     * @return
     */
    Long getCountInterviewerInAppearNum(String interviewerIp);

    /**
     * 查出真实地址为空的访问记录(限制5条)
     *
     * @return
     */
    List<InterviewRecord> trueAddressIsNull();

    /**
     * 查出真实地址为空的访问记录(查出全部)
     *
     * @return
     */
    List<InterviewRecord> trueAddressIsNullNoLimit();

    /**
     * 查出真实地址不为空的访问记录
     *
     * @return
     */
    List<InterviewRecord> trueAddressIsNotNull();
}
