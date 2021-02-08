package com.ledao.service.impl;

import com.ledao.entity.Comment;
import com.ledao.mapper.CommentMapper;
import com.ledao.service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 评论Service接口实现类
 *
 * @author LeDao
 * @company
 * @create 2021-02-04 20:45
 */
@Service("commentService")
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Override
    public List<Comment> list(Map<String, Object> map) {
        return commentMapper.list(map);
    }

    @Override
    public Long getTotal(Map<String, Object> map) {
        return commentMapper.getTotal(map);
    }

    @Override
    public Integer add(Comment comment) {
        return commentMapper.add(comment);
    }

    @Override
    public Integer update(Comment comment) {
        return commentMapper.update(comment);
    }

    @Override
    public Integer deleteById(Integer id) {
        return commentMapper.deleteById(id);
    }

    @Override
    public List<Comment> findByIp(String ip) {
        return commentMapper.findByIp(ip);
    }

    @Override
    public Comment findById(Integer id) {
        return commentMapper.findById(id);
    }

    @Override
    public Integer deleteByBlogId(Integer blogId) {
        return commentMapper.deleteByBlogId(blogId);
    }
}
