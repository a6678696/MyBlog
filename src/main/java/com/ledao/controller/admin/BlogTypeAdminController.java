package com.ledao.controller.admin;

import com.ledao.entity.BlogType;
import com.ledao.entity.PageBean;
import com.ledao.service.BlogTypeService;
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
            blogTypeService.delete(id);
            deleteKey++;
        }
        if (deleteKey>0) {
            resultMap.put("success", true);
        }
        return resultMap;
    }
}
