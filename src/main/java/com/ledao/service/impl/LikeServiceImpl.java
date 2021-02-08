package com.ledao.service.impl;

import com.ledao.entity.Like;
import com.ledao.mapper.LikeMapper;
import com.ledao.service.LikeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 点赞记录Service接口实现类
 *
 * @author LeDao
 * @company
 * @create 2021-02-05 14:42
 */
@Service("likeService")
public class LikeServiceImpl implements LikeService {

    @Resource
    private LikeMapper likeMapper;

    @Override
    public List<Like> list(Map<String, Object> map) {
        return likeMapper.list(map);
    }

    @Override
    public Long getTotal(Map<String, Object> map) {
        return likeMapper.getTotal(map);
    }

    @Override
    public Integer add(Like like) {
        return likeMapper.add(like);
    }

    @Override
    public Integer deleteById(Integer id) {
        return likeMapper.deleteById(id);
    }

    @Override
    public Integer deleteByBlogId(Integer blogId) {
        return likeMapper.deleteByBlogId(blogId);
    }
}
