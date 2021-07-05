package com.ledao.controller.admin;

import com.ledao.entity.Blog;
import com.ledao.entity.BlogType;
import com.ledao.entity.PageBean;
import com.ledao.service.BlogService;
import com.ledao.service.BlogTypeService;
import com.ledao.util.RedisUtil;
import com.ledao.util.StringUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台管理博客类别Controller层
 *
 * @author LeDao
 * @company
 * @create 2020-09-10 21:43
 */
@RestController
@RequestMapping("/admin/blogType")
public class BlogTypeAdminController {

    @Resource
    private BlogTypeService blogTypeService;

    @Resource
    private BlogService blogService;

    /**
     * 下拉框模糊查询
     *
     * @param q
     * @return
     */
    @RequestMapping("/comboList")
    public List<BlogType> comboList(String q) {
        if (q == null) {
            q = "";
        }
        return blogTypeService.findByName(StringUtil.formatLike(q));
    }

    /**
     * 分页分条件查询博客类别
     *
     * @param blogType
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    public Map<String, Object> list(BlogType blogType, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) {
        PageBean pageBean = new PageBean(page, rows);
        Map<String, Object> resultMap = new HashMap<>(16);
        Map<String, Object> map = new HashMap<>(16);
        map.put("name", StringUtil.formatLike(blogType.getName()));
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        List<BlogType> blogTypeList = blogTypeService.list(map);
        for (BlogType type : blogTypeList) {
            type.setBlogNum((long) blogService.findByBlogTypeId(type.getId()).size());
        }
        Long total = blogTypeService.getCount(map);
        resultMap.put("rows", blogTypeList);
        resultMap.put("total", total);
        return resultMap;
    }

    /**
     * 添加或修改博客类别
     *
     * @param blogType
     * @return
     */
    @RequestMapping("/save")
    public Map<String, Object> save(BlogType blogType) {
        Map<String, Object> resultMap = new HashMap<>(16);
        //key值用于判断是否添加或修改成功
        int key;
        if (blogType.getId() == null) {
            key = blogTypeService.add(blogType);
        } else {
            key = blogTypeService.update(blogType);
        }
        if (key > 0) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        //将两个类别列表添加到Redis
        addRedis();
        return resultMap;
    }

    @RequestMapping("/delete")
    public Map<String, Object> delete(String ids) {
        Map<String,Object> resultMap=new HashMap<>(16);
        String[] idsStr = ids.split(",");
        //代表删除的个数,用于判断是否成功删除
        int deleteKey=0;
        for (int i = 0; i < idsStr.length; i++) {
            int id = Integer.parseInt(idsStr[i]);
            List<Blog> blogList = blogService.findByBlogTypeId(id);
            if (blogList.size() > 0) {
                resultMap.put("errorInfo", "你要删除的博客类型下有<span style='color:red'>" + blogList.size() + "</span>篇博客,不能删除!");
                return resultMap;
            } else {
                blogTypeService.delete(id);
                deleteKey++;
            }
        }
        if (deleteKey>0) {
            resultMap.put("success", true);
        }
        //将两个类别列表添加到Redis
        addRedis();
        return resultMap;
    }

    private void addRedis() {
        List<BlogType> blogTypeList = blogTypeService.list(null);
        for (BlogType blogType : blogTypeList) {
            blogType.setBlogNum(blogTypeService.getBlogNumThisType(blogType.getId()));
        }
        List<Blog> blogCountList = blogService.countList();
        if (RedisUtil.existKey("blogTypeList")) {
            //清空
            Long length = RedisUtil.listLength("blogTypeList");
            if (length > 0) {
                for (int i = 0; i < length; i++) {
                    RedisUtil.listRightPop("blogTypeList");
                }
            }
            //添加
            for (int i = 0; i < blogTypeList.size(); i++) {
                RedisUtil.listRightPush("blogTypeList", RedisUtil.entityToJson(blogTypeList.get(i)));
            }
        } else {
            //添加
            for (int i = 0; i < blogTypeList.size(); i++) {
                RedisUtil.listRightPush("blogTypeList", RedisUtil.entityToJson(blogTypeList.get(i)));
            }
        }
        if (RedisUtil.existKey("blogCountList")) {
            //清空
            Long length = RedisUtil.listLength("blogCountList");
            if (length > 0) {
                for (int i = 0; i < length; i++) {
                    RedisUtil.listRightPop("blogCountList");
                }
            }
            //添加
            for (int i = 0; i < blogCountList.size(); i++) {
                RedisUtil.listRightPush("blogCountList", RedisUtil.entityToJson(blogCountList.get(i)));
            }
        } else {
            //添加
            for (int i = 0; i < blogCountList.size(); i++) {
                RedisUtil.listRightPush("blogCountList", RedisUtil.entityToJson(blogCountList.get(i)));
            }
        }
    }
}
