package com.ledao.controller;

import com.ledao.entity.Like;
import com.ledao.service.LikeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 前台点赞记录Controller层
 *
 * @author LeDao
 * @company
 * @create 2021-02-05 12:03
 */
@Controller
@RequestMapping("/like")
public class LikeController {

    @Resource
    private LikeService likeService;


    /**
     * 添加点赞记录
     *
     * @param blogId
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/add")
    public Map<String, Object> add(Integer blogId, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>(16);
        Like like = new Like();
        like.setBlogId(blogId);
        like.setIp(request.getRemoteAddr());
        if ("0:0:0:0:0:0:0:1".equals(like.getIp())) {
            like.setIp("127.0.0.1");
        }
        Integer key = likeService.add(like);
        if (key > 0) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }
}
