package com.ledao.service.impl;

import com.ledao.entity.BlogType;
import com.ledao.mapper.BlogTypeMapper;
import com.ledao.service.BlogService;
import com.ledao.service.BlogTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 博客类别Service实现类
 *
 * @author LeDao
 * @company
 * @create 2020-09-10 21:39
 */
@Service("blogTypeService")
public class BlogTypeServiceImpl implements BlogTypeService {

    @Resource
    private BlogTypeMapper blogTypeMapper;

    @Override
    public List<BlogType> list(Map<String, Object> map) {
        return blogTypeMapper.list(map);
    }

    @Override
    public Long getCount(Map<String, Object> map) {
        return blogTypeMapper.getCount(map);
    }

    @Override
    public Integer add(BlogType blogType) {
        return blogTypeMapper.add(blogType);
    }

    @Override
    public Integer update(BlogType blogType) {
        return blogTypeMapper.update(blogType);
    }

    @Override
    public Integer delete(Integer id) {
        return blogTypeMapper.delete(id);
    }

    @Override
    public List<BlogType> findByName(String name) {
        return blogTypeMapper.findByName(name);
    }

    @Override
    public BlogType findById(Integer id) {
        return blogTypeMapper.findById(id);
    }

    @Override
    public Long getBlogNumThisType(Integer id) {
        return blogTypeMapper.getBlogNumThisType(id);
    }
}
