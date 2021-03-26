package com.ledao.service.impl;

import com.ledao.entity.Blog;
import com.ledao.mapper.BlogMapper;
import com.ledao.service.BlogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 博客Service接口实现类
 *
 * @author LeDao
 * @company
 * @create 2020-09-11 00:34
 */
@Service("blogService")
public class BlogServiceImpl implements BlogService {

    @Resource
    private BlogMapper blogMapper;

    @Override
    public List<Blog> list(Map<String, Object> map) {
        return blogMapper.list(map);
    }

    @Override
    public Long getCount(Map<String, Object> map) {
        return blogMapper.getCount(map);
    }

    @Override
    public Integer add(Blog blog) {
        return blogMapper.add(blog);
    }

    @Override
    public Integer update(Blog blog) {
        return blogMapper.update(blog);
    }

    @Override
    public Integer delete(Integer id) {
        return blogMapper.delete(id);
    }

    @Override
    public Blog findById(Integer id) {
        return blogMapper.findById(id);
    }

    @Override
    public List<Blog> countList() {
        return blogMapper.countList();
    }

    @Override
    public List<Blog> findByBlogTypeId(Integer blogTypeId) {
        return blogMapper.findByBlogTypeId(blogTypeId);
    }

    @Override
    public Blog getPreviousBlog(Integer id) {
        return blogMapper.getPreviousBlog(id);
    }

    @Override
    public Blog getNextBlog(Integer id) {
        return blogMapper.getNextBlog(id);
    }

    @Override
    public List<Blog> getMenuBlogList() {
        return blogMapper.getMenuBlogList();
    }
}
