package com.ledao.service.impl;

import com.ledao.entity.IpForBanned;
import com.ledao.mapper.IpForBannedMapper;
import com.ledao.service.IpForBannedService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 被封禁的IpService实现类
 *
 * @author LeDao
 * @company
 * @create 2021-05-17 1:37
 */
@Service("ipForBannedService")
public class IpForBannedServiceImpl implements IpForBannedService {

    @Resource
    private IpForBannedMapper ipForBannedMapper;

    @Override
    public List<IpForBanned> list(Map<String, Object> map) {
        return ipForBannedMapper.list(map);
    }

    @Override
    public Long getCount(Map<String, Object> map) {
        return ipForBannedMapper.getCount(map);
    }

    @Override
    public Integer add(IpForBanned ipForBanned) {
        return ipForBannedMapper.add(ipForBanned);
    }

    @Override
    public Integer delete(Integer id) {
        return ipForBannedMapper.delete(id);
    }

    @Override
    public IpForBanned findByIp(String ip) {
        return ipForBannedMapper.findByIp(ip);
    }
}
