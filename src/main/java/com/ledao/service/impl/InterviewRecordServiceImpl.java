package com.ledao.service.impl;

import com.ledao.entity.InterviewRecord;
import com.ledao.mapper.InterviewRecordMapper;
import com.ledao.service.InterviewRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 访问记录Service实现类
 *
 * @author LeDao
 * @company
 * @create 2020-10-21 20:10
 */
@Service("interviewRecordService")
public class InterviewRecordServiceImpl implements InterviewRecordService {

    @Resource
    private InterviewRecordMapper interviewRecordMapper;

    @Override
    public List<InterviewRecord> list(Map<String, Object> map) {
        return interviewRecordMapper.list(map);
    }

    @Override
    public Long getCount(Map<String, Object> map) {
        return interviewRecordMapper.getCount(map);
    }

    @Override
    public Integer add(InterviewRecord interviewRecord) {
        return interviewRecordMapper.add(interviewRecord);
    }

    @Override
    public Integer update(InterviewRecord interviewRecord) {
        return interviewRecordMapper.update(interviewRecord);
    }

    @Override
    public Integer delete(Integer id) {
        return interviewRecordMapper.delete(id);
    }

    @Override
    public Long getCountInterviewFirstPageNum() {
        return interviewRecordMapper.getCountInterviewFirstPageNum();
    }

    @Override
    public Long getCountInterviewIpNum() {
        return interviewRecordMapper.getCountInterviewIpNum();
    }

    @Override
    public Long getCountTodayInterviewFirstPageNum() {
        return interviewRecordMapper.getCountTodayInterviewFirstPageNum();
    }

    @Override
    public Long getCountTodayInterviewIpNum() {
        return interviewRecordMapper.getCountTodayInterviewIpNum();
    }

    @Override
    public Long getCountInterviewerInAppearNum(String interviewerIp) {
        return interviewRecordMapper.getCountInterviewerInAppearNum(interviewerIp);
    }

    @Override
    public List<InterviewRecord> trueAddressIsNull() {
        return interviewRecordMapper.trueAddressIsNull();
    }

    @Override
    public List<InterviewRecord> trueAddressIsNullNoLimit() {
        return interviewRecordMapper.trueAddressIsNullNoLimit();
    }

    @Override
    public List<InterviewRecord> trueAddressIsNotNull() {
        return interviewRecordMapper.trueAddressIsNotNull();
    }

    @Override
    public Long getCountInterviewInTwentySecond(String ip) {
        return interviewRecordMapper.getCountInterviewInTwentySecond(ip);
    }
}
