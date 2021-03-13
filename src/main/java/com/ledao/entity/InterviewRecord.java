package com.ledao.entity;

import java.util.Date;

/**
 * 访问记录实体
 *
 * @author LeDao
 * @company
 * @create 2020-10-21 19:22
 */
public class InterviewRecord {

    /**
     * id
     */
    private Integer id;
    /**
     * 访问者ip地址
     */
    private String interviewerIp;
    /**
     * ip地址出现次数
     */
    private Long interviewerIpAppearNum;
    /**
     * 访问的内容
     */
    private String interviewContent;
    /**
     * 访问时间
     */
    private Date interviewDate;
    /**
     * 真实地址
     */
    private String trueAddress;
    /**
     * 起始时间 搜索用到
     */
    private Date bTime;
    /**
     * 结束时间 搜索用到
     */
    private Date eTime;

    public InterviewRecord() {
    }

    public InterviewRecord(String interviewerIp, String interviewContent) {
        this.interviewerIp = interviewerIp;
        this.interviewContent = interviewContent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInterviewerIp() {
        return interviewerIp;
    }

    public void setInterviewerIp(String interviewerIp) {
        this.interviewerIp = interviewerIp;
    }

    public Long getInterviewerIpAppearNum() {
        return interviewerIpAppearNum;
    }

    public void setInterviewerIpAppearNum(Long interviewerIpAppearNum) {
        this.interviewerIpAppearNum = interviewerIpAppearNum;
    }

    public String getInterviewContent() {
        return interviewContent;
    }

    public void setInterviewContent(String interviewContent) {
        this.interviewContent = interviewContent;
    }

    public Date getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(Date interviewDate) {
        this.interviewDate = interviewDate;
    }

    public Date getbTime() {
        return bTime;
    }

    public void setbTime(Date bTime) {
        this.bTime = bTime;
    }

    public Date geteTime() {
        return eTime;
    }

    public void seteTime(Date eTime) {
        this.eTime = eTime;
    }

    public String getTrueAddress() {
        return trueAddress;
    }

    public void setTrueAddress(String trueAddress) {
        this.trueAddress = trueAddress;
    }
}
