package com.ledao.controller;

import cn.hutool.http.HtmlUtil;
import com.ledao.entity.Blog;
import com.ledao.entity.BlogType;
import com.ledao.entity.InterviewRecord;
import com.ledao.entity.User;
import com.ledao.service.*;
import com.ledao.util.AddressUtil;
import com.ledao.util.PageUtil;
import com.ledao.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页Controller
 *
 * @author LeDao
 * @company
 * @create 2020-09-08 20:04
 */
@Controller
public class IndexController {

    @Resource
    private UserService userService;

    @Resource
    private BlogService blogService;

    @Resource
    private BlogTypeService blogTypeService;

    @Resource
    private InterviewRecordService interviewRecordService;

    @Resource
    private LinkService linkService;

    /**
     * 首页地址(为了直接输入首地址就能访问网站)
     *
     * @return
     */
    @RequestMapping("/")
    public ModelAndView root(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "blogTypeId", required = false) String blogTypeId, @RequestParam(value = "releaseDateStr", required = false) String releaseDateStr, HttpServletRequest request) throws IOException {
        ModelAndView mav = new ModelAndView();
        InterviewRecord interviewRecord = new InterviewRecord(request.getRemoteAddr(), "访问博客首页");
        interviewRecord.setTrueAddress(AddressUtil.getAddress2(interviewRecord.getInterviewerIp()));
        interviewRecordService.add(interviewRecord);
        Map<String, Object> map = new HashMap<>(16);
        if (page == null) {
            page = 1;
        }
        int pageSize = 6;
        map.put("start", (page - 1) * pageSize);
        map.put("size", pageSize);
        map.put("blogTypeId", blogTypeId);
        map.put("isMenuBlogKey", 1);
        map.put("releaseDateStr", releaseDateStr);
        Long total = blogService.getCount(map);
        List<Blog> blogList = blogService.list(map);
        for (Blog blog : blogList) {
            //博客里的内容
            String blogInfo = blog.getContent();
            //抓取出博客里的内容
            Document document = Jsoup.parse(blogInfo);
            //提出.jpg图片
            Elements jpgs = document.select("img[src$=.jpg]");
            //提取一个博客里的一张图片
            if (jpgs.size() != 0) {
                for (int i = 0; i < 1; i++) {
                    Element jpg = jpgs.get(i);
                    blog.setImageName(jpg.toString());

                }
                int begin = blog.getImageName().indexOf("/static");
                int last = blog.getImageName().indexOf(".jpg");
                blog.setImageName(blog.getImageName().substring(begin, last) + ".jpg");
            }
            blog.setSummary(HtmlUtil.unescape(blog.getSummary()));
            blog.setBlogType(blogTypeService.findById(blog.getBlogTypeId()));
        }
        List<BlogType> blogTypeList = blogTypeService.list(null);
        for (BlogType blogType : blogTypeList) {
            blogType.setBlogNum(blogTypeService.getBlogNumThisType(blogType.getId()));
        }
        List<Blog> blogCountList = blogService.countList();
        StringBuffer param = new StringBuffer();
        if (StringUtil.isNotEmpty(blogTypeId)) {
            param.append("&blogTypeId=" + blogTypeId);
        }
        if (StringUtil.isNotEmpty(releaseDateStr)) {
            param.append("&releaseDateStr=" + releaseDateStr);
        }
        //用于判断是否是首页,是首页就显示公告
        mav.addObject("isIndexFirst", 1);
        mav.addObject("menuBlogList", blogService.getMenuBlogList());
        mav.addObject("isHome", 1);
        mav.addObject("linkList", linkService.list(null));
        mav.addObject("blogList", blogList);
        mav.addObject("blogTypeList", blogTypeList);
        mav.addObject("blogCountList", blogCountList);
        mav.addObject("title", "LeDao的博客");
        if (1 == StringUtil.readSkin()) {
            mav.addObject("pageCode", PageUtil.genPagination1("/index", total, page, pageSize, param.toString()));
        } else if (2 == StringUtil.readSkin()) {
            mav.addObject("pageCode", PageUtil.genPagination2("/index", total, page, pageSize, param.toString()));
        }
        mav.addObject("mainPage", "page/indexFirst" + StringUtil.readSkin());
        mav.addObject("mainPageKey", "#b");
        mav.setViewName("index" + StringUtil.readSkin());
        return mav;
    }

    /**
     * 首页地址(分页,分类使用)
     *
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView root2(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "blogTypeId", required = false) String blogTypeId, @RequestParam(value = "releaseDateStr", required = false) String releaseDateStr, HttpServletRequest request) throws IOException {
        ModelAndView mav = new ModelAndView();
        Map<String, Object> map = new HashMap<>(16);
        StringBuffer param = new StringBuffer();
        if (StringUtil.isNotEmpty(blogTypeId)) {
            param.append("&blogTypeId=" + blogTypeId);
            if (page == null) {
                InterviewRecord interviewRecord2 = new InterviewRecord(request.getRemoteAddr(), "查看分类：" + blogTypeService.findById(Integer.valueOf(blogTypeId)).getName() + "(按博客类别分类)");
                interviewRecord2.setTrueAddress(AddressUtil.getAddress2(interviewRecord2.getInterviewerIp()));
                interviewRecordService.add(interviewRecord2);
            }
            BlogType blogType = blogTypeService.findById(Integer.parseInt(blogTypeId));
            System.out.println(blogType.getName());
            mav.addObject("title", "当前分类:" + blogType.getName() + "--LeDao的博客");
        } else if (StringUtil.isEmpty(releaseDateStr) && StringUtil.isEmpty(blogTypeId)) {
            mav.addObject("title", "LeDao的博客");
        }
        if (StringUtil.isNotEmpty(releaseDateStr)) {
            param.append("&releaseDateStr=" + releaseDateStr);
            if (page == null) {
                InterviewRecord interviewRecord3 = new InterviewRecord(request.getRemoteAddr(), "查看分类：" + releaseDateStr + "(按日期分类)");
                interviewRecord3.setTrueAddress(AddressUtil.getAddress2(interviewRecord3.getInterviewerIp()));
                interviewRecordService.add(interviewRecord3);
            }
            mav.addObject("title", "当前分类:" + releaseDateStr + "--LeDao的博客");
        } else if (StringUtil.isEmpty(releaseDateStr) && StringUtil.isEmpty(blogTypeId)) {
            mav.addObject("title", "LeDao的博客");
        }
        if (page == null) {
            page = 1;
        }
        int pageSize = 6;
        map.put("start", (page - 1) * pageSize);
        map.put("size", pageSize);
        map.put("isMenuBlogKey", 1);
        map.put("blogTypeId", blogTypeId);
        map.put("releaseDateStr", releaseDateStr);
        Long total = blogService.getCount(map);
        List<Blog> blogList = blogService.list(map);
        for (Blog blog : blogList) {
            //博客里的内容
            String blogInfo = blog.getContent();
            //抓取出博客里的内容
            Document document = Jsoup.parse(blogInfo);
            //提出.jpg图片
            Elements jpgs = document.select("img[src$=.jpg]");
            //提取一个博客里的一张图片
            if (jpgs.size() != 0) {
                for (int i = 0; i < 1; i++) {
                    Element jpg = jpgs.get(i);
                    blog.setImageName(jpg.toString());

                }
                int begin = blog.getImageName().indexOf("/static");
                int last = blog.getImageName().indexOf(".jpg");
                blog.setImageName(blog.getImageName().substring(begin, last) + ".jpg");
            }
            blog.setSummary(HtmlUtil.unescape(blog.getSummary()));
            blog.setBlogType(blogTypeService.findById(blog.getBlogTypeId()));
        }
        List<BlogType> blogTypeList = blogTypeService.list(null);
        for (BlogType blogType : blogTypeList) {
            blogType.setBlogNum(blogTypeService.getBlogNumThisType(blogType.getId()));
        }
        List<Blog> blogCountList = blogService.countList();
        //是第一页
        if ((page == 1 || page == null)) {
            //不是按类别和时间分类
            if (blogTypeId == null && releaseDateStr == null) {
                mav.addObject("isHome", 1);
            }
        }
        mav.addObject("menuBlogList", blogService.getMenuBlogList());
        mav.addObject("linkList", linkService.list(null));
        mav.addObject("blogList", blogList);
        mav.addObject("blogTypeList", blogTypeList);
        mav.addObject("blogCountList", blogCountList);
        if (1 == StringUtil.readSkin()) {
            mav.addObject("pageCode", PageUtil.genPagination1("/index", total, page, pageSize, param.toString()));
        } else if (2 == StringUtil.readSkin()) {
            mav.addObject("pageCode", PageUtil.genPagination2("/index", total, page, pageSize, param.toString()));
        }
        mav.addObject("mainPage", "page/indexFirst" + StringUtil.readSkin());
        mav.addObject("mainPageKey", "#b");
        mav.setViewName("index" + StringUtil.readSkin());
        return mav;
    }

    /**
     * 登录请求
     *
     * @return
     */
    @RequestMapping("/login")
    public Object login(HttpSession session) {
        User user = userService.findByUserName("admin");
        session.setAttribute("currentUserNickName", user.getNickName());
        return "/login";
    }

    /**
     * 进入后台管理请求
     *
     * @return
     */
    @RequestMapping("/admin")
    public String toAdmin() {
        return "/admin/main";
    }

    /**
     * 验证码是否正确
     *
     * @param imageCode
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkCodeIsSuccess")
    public Map<String, Object> checkCodeIsSuccess(String imageCode, HttpSession session) {
        Map<String, Object> resultMap = new HashMap<>(16);
        String checkCode = (String) session.getAttribute("checkCode");
        resultMap.put("checkCode", checkCode);
        if (StringUtil.isNotEmpty(checkCode)) {
            if (imageCode.equals(checkCode)) {
                resultMap.put("success", true);
            } else {
                resultMap.put("success", false);
            }
        }
        return resultMap;
    }

    /**
     * 切换前台皮肤
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/changeSkin")
    public Map<String, Object> changeSkin() throws IOException {
        Map<String, Object> resultMap = new HashMap<>(16);
        int skin = StringUtil.readSkin();
        if (skin == 1) {
            StringUtil.updateSkin(2);
            resultMap.put("success", true);
        } else if (skin == 2) {
            StringUtil.updateSkin(1);
            resultMap.put("success", true);
        }
        return resultMap;
    }
}
