package com.ledao.controller;

import com.ledao.entity.Blog;
import com.ledao.entity.BlogType;
import com.ledao.service.BlogService;
import com.ledao.service.BlogTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
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
}
