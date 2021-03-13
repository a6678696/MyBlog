package com.ledao.quartz;

import com.ledao.entity.InterviewRecord;
import com.ledao.service.InterviewRecordService;
import com.ledao.util.AddressUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 定时补全空访问记录的空真实地址
 *
 * @author LeDao
 * @company
 * @create 2021-03-13 12:48
 */
@Configuration
@EnableScheduling
public class InsertInterviewRecordTrueAddress {

    @Resource
    private InterviewRecordService interviewRecordService;

    /**
     * 每一小时执行一次
     */
    @Scheduled(cron = "0 0 */1 * * ?")
    public void work() {
        List<InterviewRecord> interviewRecordList = interviewRecordService.trueAddressIsNull();
        if (interviewRecordList.size() > 0) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(simpleDateFormat.format(new Date()) + "开始自动补全访问记录的空真实地址");
            for (InterviewRecord interviewRecord : interviewRecordList) {
                interviewRecord.setTrueAddress(AddressUtil.getAddress2(interviewRecord.getInterviewerIp()));
                interviewRecordService.update(interviewRecord);
            }
            System.out.println(simpleDateFormat.format(new Date()) + "全部访问记录的空真实地址已经自动填入完毕!");
        } else {
            System.out.println("没有真实地址为空的访问记录!");
        }
        checkNullAddressIpIsExist();
    }

    /**
     * 真实地址为空的访问记录的ip如果在(真实地址不为空的访问记录集合中),可直接插入数据库中存在的同ip真实地址
     */
    private void checkNullAddressIpIsExist() {
        List<InterviewRecord> interviewRecordListIsNull = interviewRecordService.trueAddressIsNullNoLimit();
        List<InterviewRecord> interviewRecordListNotNull = interviewRecordService.trueAddressIsNotNull();
        for (int i = 0; i < interviewRecordListIsNull.size(); i++) {
            InterviewRecord interviewRecord = interviewRecordListIsNull.get(i);
            for (int i1 = 0; i1 < interviewRecordListNotNull.size(); i1++) {
                InterviewRecord interviewRecord2 = interviewRecordListNotNull.get(i1);
                if (interviewRecord.getInterviewerIp().equals(interviewRecord2.getInterviewerIp())) {
                    interviewRecord.setTrueAddress(interviewRecord2.getTrueAddress());
                    interviewRecordService.update(interviewRecord);
                    break;
                }
            }
        }
    }
}
