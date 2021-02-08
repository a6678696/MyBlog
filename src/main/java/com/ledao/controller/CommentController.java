package com.ledao.controller;

import com.ledao.entity.Comment;
import com.ledao.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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
    public Map<String, Object> add(String content, Integer blogId, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>(16);
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setBlogId(blogId);
        comment.setIp(request.getRemoteAddr());
        if ("0:0:0:0:0:0:0:1".equals(comment.getIp())) {
            comment.setIp("127.0.0.1");
        }
        //这个IP发表过评论
        if (commentService.findByIp(comment.getIp()).size() > 0) {
            Comment comment1 = commentService.findByIp(comment.getIp()).get(0);
            comment.setImageName(comment1.getImageName());
        } else {
            comment.setImageName((int) (1 + Math.random() * 10) + ".png");
        }
        Integer key = commentService.add(comment);
        if (key > 0) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }
}
