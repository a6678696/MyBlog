package com.ledao.controller.admin;

import com.ledao.entity.InterviewRecord;
import com.ledao.entity.PageBean;
import com.ledao.service.InterviewRecordService;
import com.ledao.util.StringUtil;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台管理访问记录Controller层
 *
 * @author LeDao
 * @company
 * @create 2020-10-21 20:12
 */
@RestController
@RequestMapping("/admin/interviewRecord")
public class InterviewRecordAdminController {

    @Resource
    private InterviewRecordService interviewRecordService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(true);
        //true:允许输入空值，false:不能为空值
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 分页分条件查询访问记录
     *
     * @param interviewRecord
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    public Map<String, Object> list(InterviewRecord interviewRecord, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) {
        PageBean pageBean = new PageBean(page, rows);
        Map<String, Object> resultMap = new HashMap<>(16);
        Map<String, Object> map = new HashMap<>(16);
        map.put("interviewerIp", StringUtil.formatLike(interviewRecord.getInterviewerIp()));
        map.put("interviewContent", StringUtil.formatLike(interviewRecord.getInterviewContent()));
        map.put("bTime", interviewRecord.getbTime());
        map.put("eTime", interviewRecord.geteTime());
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        List<InterviewRecord> interviewRecordList = interviewRecordService.list(map);
        Long total = interviewRecordService.getCount(map);
        for (InterviewRecord record : interviewRecordList) {
            record.setInterviewerIpAppearNum(interviewRecordService.getCountInterviewerInAppearNum(record.getInterviewerIp()));
        }
        resultMap.put("rows", interviewRecordList);
        resultMap.put("total", total);
        return resultMap;
    }

    /**
     * 根据id串删除访问记录(可批量删除)
     *
     * @param ids
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public Map<String, Object> delete(String ids) {
        Map<String, Object> resultMap = new HashMap<>(16);
        int key = 0;
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
            int id = Integer.parseInt(idsStr[i]);
            key = interviewRecordService.delete(id);
        }
        if (key > 0) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }

    /**
     * 获取客户访问博客首页的总次数
     *
     * @return
     */
    @RequestMapping("/getCountInterviewFirstPageNum")
    public Map<String, Object> getCountInterviewFirstPageNum() {
        Map<String, Object> resultMap = new HashMap<>(16);
        Long getCountInterviewFirstPageNum = interviewRecordService.getCountInterviewFirstPageNum();
        resultMap.put("success", true);
        resultMap.put("getCountInterviewFirstPageNum", getCountInterviewFirstPageNum);
        return resultMap;
    }

    /**
     * 获取访问博客系统的客户ip地址的总数
     *
     * @return
     */
    @RequestMapping("/getCountInterviewIpNum")
    public Map<String, Object> getCountInterviewIpNum() {
        Map<String, Object> resultMap = new HashMap<>(16);
        Long getCountInterviewIpNum = interviewRecordService.getCountInterviewIpNum();
        resultMap.put("success", true);
        resultMap.put("getCountInterviewIpNum", getCountInterviewIpNum);
        return resultMap;
    }

    /**
     * 获取今天客户访问博客首页的总次数
     *
     * @return
     */
    @RequestMapping("/getCountTodayInterviewFirstPageNum")
    public Map<String, Object> getCountTodayInterviewFirstPageNum() {
        Map<String, Object> resultMap = new HashMap<>(16);
        Long getCountTodayInterviewFirstPageNum = interviewRecordService.getCountTodayInterviewFirstPageNum();
        resultMap.put("success", true);
        resultMap.put("getCountTodayInterviewFirstPageNum", getCountTodayInterviewFirstPageNum);
        return resultMap;
    }

    /**
     * 获取今天访问博客系统的客户ip地址的总数
     *
     * @return
     */
    @RequestMapping("/getCountTodayInterviewIpNum")
    public Map<String, Object> getCountTodayInterviewIpNum() {
        Map<String, Object> resultMap = new HashMap<>(16);
        Long getCountTodayInterviewIpNum = interviewRecordService.getCountTodayInterviewIpNum();
        resultMap.put("success", true);
        resultMap.put("getCountTodayInterviewIpNum", getCountTodayInterviewIpNum);
        return resultMap;
    }
}
