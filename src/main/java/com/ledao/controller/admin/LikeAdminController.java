package com.ledao.controller.admin;

import com.ledao.entity.Like;
import com.ledao.entity.PageBean;
import com.ledao.service.BlogService;
import com.ledao.service.LikeService;
import com.ledao.util.StringUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台点赞Controller层
 *
 * @author LeDao
 * @company
 * @create 2021-02-05 18:21
 */
@RestController
@RequestMapping("/admin/like")
public class LikeAdminController {

    @Resource
    private LikeService likeService;

    @Resource
    private BlogService blogService;

    /**
     * 分页分条件查询点赞记录
     *
     * @param like
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    public Map<String, Object> list(Like like, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) {
        PageBean pageBean = new PageBean(page, rows);
        Map<String, Object> resultMap = new HashMap<>(16);
        Map<String, Object> map = new HashMap<>(16);
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        map.put("ip", StringUtil.formatLike(like.getIp()));
        List<Like> likeList = likeService.list(map);
        Long total = likeService.getTotal(map);
        for (Like like1 : likeList) {
            like1.setBlog(blogService.findById(like1.getBlogId()));
        }
        resultMap.put("total", total);
        resultMap.put("rows", likeList);
        return resultMap;
    }

    /**
     * 删除点赞记录(可批量删除)
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Map<String, Object> delete(String ids) {
        Map<String, Object> resultMap = new HashMap<>(16);
        String[] idsStr = ids.split(",");
        int key = 0;
        for (int i = 0; i < idsStr.length; i++) {
            int id = Integer.parseInt(idsStr[i]);
            likeService.deleteById(id);
            key++;
        }
        if (key > 0) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }
}
