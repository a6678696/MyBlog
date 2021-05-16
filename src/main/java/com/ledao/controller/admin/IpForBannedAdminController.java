package com.ledao.controller.admin;

import com.ledao.entity.IpForBanned;
import com.ledao.entity.PageBean;
import com.ledao.service.IpForBannedService;
import com.ledao.util.StringUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LeDao
 * @company
 * @create 2021-05-17 3:25
 */
@RestController
@RequestMapping("/admin/ipForBanned")
public class IpForBannedAdminController {

    @Resource
    private IpForBannedService ipForBannedService;

    /**
     * 分页分条件查询封禁ip
     *
     * @param ipForBanned
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    public Map<String, Object> list(IpForBanned ipForBanned, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) {
        PageBean pageBean = new PageBean(page, rows);
        Map<String, Object> resultMap = new HashMap<>(16);
        Map<String, Object> map = new HashMap<>(16);
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        map.put("ip", StringUtil.formatLike(ipForBanned.getIp()));
        map.put("type", ipForBanned.getType());
        List<IpForBanned> ipForBannedList = ipForBannedService.list(map);
        Long total = ipForBannedService.getCount(map);
        resultMap.put("total", total);
        resultMap.put("rows", ipForBannedList);
        return resultMap;
    }

    /**
     * 添加被封禁ip
     *
     * @param ipForBanned
     * @return
     */
    @RequestMapping("/save")
    public Map<String, Object> save(IpForBanned ipForBanned) {
        ipForBanned.setType("人工封禁");
        Map<String, Object> resultMap = new HashMap<>(16);
        //key值用于判断是否添加或修改成功
        int key = 0;
        if (ipForBanned.getId() == null) {
            key = ipForBannedService.add(ipForBanned);
        }
        if (key > 0) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }

    /**
     * 删除封禁ip(可批量删除)
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Map<String, Object> delete(String ids) {
        Map<String, Object> resultMap = new HashMap<>(16);
        String[] idsStr = ids.split(",");
        int key = 0;
        for (int i = 0; i < idsStr.length; i++) {
            int id = Integer.parseInt(idsStr[i]);
            ipForBannedService.delete(id);
            key++;
        }
        if (key > 0) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }
}
