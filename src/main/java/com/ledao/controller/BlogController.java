package com.ledao.controller;

import com.ledao.entity.Blog;
import com.ledao.entity.BlogType;
import com.ledao.lucene.BlogIndex;
import com.ledao.service.BlogService;
import com.ledao.service.BlogTypeService;
import com.ledao.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author LeDao
 * @company
 * @create 2020-09-17 19:50
 */
@Controller
@RequestMapping("/blog")
public class BlogController {

    @Resource
    private BlogService blogService;

    @Resource
    private BlogTypeService blogTypeService;

    private BlogIndex blogIndex = new BlogIndex();

    @RequestMapping("/{id}")
    public ModelAndView details(@PathVariable("id")Integer id) {
        ModelAndView mav = new ModelAndView();
        Blog blog = blogService.findById(id);
        blog.setClick(blog.getClick()+1);
        blogService.update(blog);
        blog.setBlogType(blogTypeService.findById(blog.getBlogTypeId()));
        List<BlogType> blogTypeList = blogTypeService.list(null);
        for (BlogType blogType : blogTypeList) {
            blogType.setBlogNum(blogTypeService.getBlogNumThisType(blogType.getId()));
        }
        List<Blog> blogCountList=blogService.countList();
        mav.addObject("blog", blog);
        mav.addObject("blogTypeList", blogTypeList);
        mav.addObject("blogCountList", blogCountList);
        mav.addObject("title", blog.getTitle()+"--LeDao的博客");
        mav.addObject("mainPage", "page/blogDetails");
        mav.addObject("mainPageKey", "#b");
        mav.setViewName("index");
        return mav;
    }

    /**
     * 根据关键字查询相关博客信息
     *
     * @param q 查询条件
     * @return
     * @throws Exception
     */
    @RequestMapping("/q")
    public ModelAndView search(@RequestParam(value = "q", required = false) String q, @RequestParam(value = "page", required = false) String page, HttpServletRequest request) throws Exception {
        int pageSize = 3;
        if (StringUtil.isEmpty(page)) {
            page = "1";
        }
        List<BlogType> blogTypeList = blogTypeService.list(null);
        for (BlogType blogType : blogTypeList) {
            blogType.setBlogNum(blogTypeService.getBlogNumThisType(blogType.getId()));
        }
        List<Blog> blogCountList=blogService.countList();
        ModelAndView mav = new ModelAndView();
        mav.addObject("blogTypeList", blogTypeList);
        mav.addObject("blogCountList", blogCountList);
        mav.addObject("title", "搜索关键字'" + q + "'结果页面--LeDao博客系统");
        mav.addObject("mainPage", "page/blogResult");
        mav.addObject("mainPageKey", "#b");
        List<Blog> blogList = blogIndex.searchBlog(q);
        for (Blog blog : blogList) {
            Blog trueBlog = blogService.findById(blog.getId());
            blog.setClick(trueBlog.getClick());
            blog.setReleaseDate(trueBlog.getReleaseDate());
            BlogType blogType = blogTypeService.findById(trueBlog.getBlogTypeId());
            blog.setBlogType(blogType);
        }
        //算出第一页到当前页的总记录条数
        Integer toIndex = blogList.size() >= Integer.parseInt(page) * pageSize ? Integer.parseInt(page) * pageSize : blogList.size();
        mav.addObject("blogList", blogList.subList((Integer.parseInt(page) - 1) * pageSize, toIndex));
        mav.addObject("pageCode", this.genUpAndDownPageCode(Integer.parseInt(page), blogList.size(), q, pageSize, request.getServletContext().getContextPath()));
        mav.addObject("q", q);
        mav.addObject("resultTotal", blogList.size());
        mav.setViewName("index");
        return mav;
    }

    /**
     * 获取上一页，下一页代码
     *
     * @param page           当前页
     * @param totalNum       总记录数
     * @param q              查询条件
     * @param pageSize       每页记录数
     * @param projectContext url地址
     * @return
     */
    private String genUpAndDownPageCode(Integer page, Integer totalNum, String q, Integer pageSize, String projectContext) {
        //数据总页数
        long totalPage = totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize + 1;
        StringBuffer pageCode = new StringBuffer();
        if (totalPage == 0) {
            return null;
        } else {
            pageCode.append("<nav>");
            pageCode.append("<ul>");
            if (page > 1) {
                pageCode.append("<li><a href='" + projectContext + "/blog/q?page=" + (page - 1) + "&q=" + q + "' style='text-decoration: none;margin-left: 40%' class='com'>上一页</a></li>");
            } else {
                pageCode.append("<li class='disabled'><a href='#' style='text-decoration: none;margin-left: 45%' class='com'>上一页</a></li>");
            }
            if (page < totalPage) {
                pageCode.append("<li><a href='" + projectContext + "/blog/q?page=" + (page + 1) + "&q=" + q + "' style='text-decoration: none' class='com'>下一页</a></li>");
            } else {
                pageCode.append("<li class='disabled' style='text-decoration: none'><a href='#' style='text-decoration: none' class='com'>下一页</a></li>");
            }
            pageCode.append("</ul>");
            pageCode.append("</nav>");
        }
        return pageCode.toString();
    }
}
