package com.ledao.controller;

import com.ledao.entity.Comment;
import com.ledao.service.CommentService;
import com.ledao.util.DateUtil;
import com.ledao.util.StringUtil;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 前台评论Controller层
 *
 * @author LeDao
 * @company
 * @create 2021-02-05 12:03
 */
@Controller
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    @Resource
    private JavaMailSender javaMailSender;


    /**
     * 添加评论
     *
     * @param content
     * @param blogId
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/add")
    public Map<String, Object> add(String content, Integer blogId, HttpServletRequest request) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(16);
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setBlogId(blogId);
        String ip = request.getRemoteAddr();
        comment.setIp(ip);
        //本机运行项目时
        String localhostIp = "0:0:0:0:0:0:0:1";
        if (localhostIp.equals(comment.getIp())) {
            comment.setIp("127.0.0.1");
        }
        //这个IP发表过评论
        if (commentService.findByIp(comment.getIp()).size() > 0) {
            Comment comment1 = commentService.findByIp(comment.getIp()).get(0);
            comment.setImageName(comment1.getImageName());
        } else {
            comment.setImageName((int) (1 + Math.random() * 10) + ".png");
        }
        //每天最多评论5次
        Integer maxCommentCount = 5;
        //已经评论了5次
        if (commentService.getTodayCommentCount(comment.getIp()) >= maxCommentCount) {
            resultMap.put("success", false);
            resultMap.put("errorInfo", "你今天已经评论了5次" + "\n" + "每天最多评论5次，每日0点重置");
        } else {
            //5分钟内评论了一次
            if (commentService.getLastFiveMinutesCommentCount(comment.getIp()) > 0) {
                Map<String, Object> map = new HashMap<>(16);
                map.put("ip", comment.getIp());
                List<Comment> commentList = commentService.list(map);
                resultMap.put("success", false);
                resultMap.put("errorInfo", "5分钟内只能评论一次" + "\n" + "上一次评论时间为：" + DateUtil.dateFormat(commentList.get(0).getDate()));
            } else {
                Integer key = commentService.add(comment);
                //成功评论
                if (key > 0) {
                    //有人评论了是否发邮件提醒
                    String sendMailStatus = StringUtil.readSendMail();
                    //"1"代表发邮件提醒
                    String isSendMail = "1";
                    if (isSendMail.equals(sendMailStatus)) {
                        SimpleMailMessage mailMessage = new SimpleMailMessage();
                        mailMessage.setFrom("******@qq.com");
                        mailMessage.setTo("******@qq.com");
                        mailMessage.setSubject("LeDao的博客");
                        mailMessage.setText("有人给你评论了，内容为：" + content);
                        javaMailSender.send(mailMessage);
                    }
                    resultMap.put("success", true);
                } else {
                    resultMap.put("success", false);
                    resultMap.put("errorInfo", "评论失败！！");
                }
            }
        }
        return resultMap;
    }
}
