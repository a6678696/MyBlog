package com.ledao.controller.admin;

import com.ledao.entity.Blog;
import com.ledao.entity.PageBean;
import com.ledao.lucene.BlogIndex;
import com.ledao.service.BlogService;
import com.ledao.service.BlogTypeService;
import com.ledao.service.CommentService;
import com.ledao.service.LikeService;
import com.ledao.util.DateUtil;
import com.ledao.util.StringUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台管理博客Controller层
 *
 * @author LeDao
 * @company
 * @create 2020-09-11 22:18
 */
@RestController
@RequestMapping("/admin/blog")
public class BlogAdminController {

    @Value("${blogImageFilePath}")
    private String blogImageFilePath;

    @Resource
    private BlogService blogService;

    @Resource
    private BlogTypeService blogTypeService;

    @Resource
    private LikeService likeService;

    @Resource
    private CommentService commentService;

    private BlogIndex blogIndex = new BlogIndex();

    /**
     * 分页分条件查询博客
     *
     * @param blog
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    public Map<String, Object> list(Blog blog, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) {
        PageBean pageBean = new PageBean(page, rows);
        Map<String, Object> resultMap = new HashMap<>(16);
        Map<String, Object> map = new HashMap<>(16);
        map.put("title", StringUtil.formatLike(blog.getTitle()));
        map.put("blogTypeId", blog.getBlogTypeId());
        map.put("isMenuBlog", blog.getIsMenuBlog());
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        List<Blog> blogList = blogService.list(map);
        for (Blog blog1 : blogList) {
            blog1.setBlogType(blogTypeService.findById(blog1.getBlogTypeId()));
        }
        Long total = blogService.getCount(map);
        resultMap.put("rows", blogList);
        resultMap.put("total", total);
        return resultMap;
    }

    /**
     * 添加或修改博客
     *
     * @param blog
     * @return
     */
    @RequestMapping("/save")
    public Map<String, Object> save(Blog blog) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(16);
        int key;
        int maxStringLength = 600;
        if (blog.getId() == null) {
            blog.setSummary(StripHT(blog.getContent()));
            if (blog.getSummary().length() >= maxStringLength) {
                blog.setSummary(blog.getSummary().substring(0, 600));
            }
            key = blogService.add(blog);
            List<Blog> blogList = blogService.list(null);
            blogIndex.addIndex(blogList.get(0));
        } else {
            String summary = StripHT(blog.getContent());
            if (summary.length() >= maxStringLength) {
                blog.setSummary(summary.substring(0, 600));
            } else {
                blog.setSummary(summary);
            }
            key = blogService.update(blog);
            blogIndex.updateIndex(blog);
        }
        if (key > 0) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }

    /**
     * 根据id串删除博客(可批量删除)
     *
     * @param ids
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public Map<String, Object> delete(String ids) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(16);
        int key = 0;
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
            int id = Integer.parseInt(idsStr[i]);
            //删除Lucene的索引
            blogIndex.deleteIndex(String.valueOf(id));
            //删除该博客下的点赞记录
            likeService.deleteByBlogId(id);
            //删除该博客下的评论
            commentService.deleteByBlogId(id);
            key = blogService.delete(id);
        }
        if (key > 0) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }

    /**
     * ckeditor上传图片
     *
     * @param file
     * @param CKEditorFuncNum
     * @return
     * @throws Exception
     */
    @RequestMapping("/ckeditorUpload")
    public String ckeditorUpload(@RequestParam("upload") MultipartFile file, String CKEditorFuncNum) throws Exception {
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件的后缀
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //拼接新的文件名
        String newFileName = DateUtil.getCurrentDateStr2() + ".jpg";
        FileUtils.copyInputStreamToFile(file.getInputStream(), new File(blogImageFilePath + "/" + newFileName));
        StringBuffer sb = new StringBuffer();
        sb.append("<script type=\"text/javascript\">");
        sb.append("window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum + ",'" + "/static/images/blogImage/" + newFileName + "','')");
        sb.append("</script>");
        return sb.toString();
    }

    /**
     * 获得纯文本
     *
     * @param strHtml
     * @return
     */
    public static String StripHT(String strHtml) {
        //剔出<html>的标签
        String txtcontent = strHtml.replaceAll("</?[^>]+>", "");
        //去除字符串中的空格,回车,换行符,制表符
        txtcontent = txtcontent.replaceAll("<a>\\s*|\t|\r|\n</a>", "");
        return txtcontent;
    }

    /**
     * 设置成为导航条文章与否
     *
     * @param id
     * @return
     */
    @RequestMapping("setMenuBlog")
    public Map<String, Object> setMenuBlog(Integer id) {
        Map<String, Object> resultMap = new HashMap<>(16);
        Blog blob = blogService.findById(id);
        if (blob.getIsMenuBlog() == null || blob.getIsMenuBlog() != 1) {
            blob.setIsMenuBlog(1);
            blob.setSetMenuBlogDate(new Date());
        } else {
            blob.setIsMenuBlog(0);
        }
        blogService.update(blob);
        resultMap.put("success", true);
        return resultMap;
    }
}
