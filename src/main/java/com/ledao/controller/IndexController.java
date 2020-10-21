package com.ledao.controller;

import com.ledao.entity.Blog;
import com.ledao.entity.BlogType;
import com.ledao.entity.InterviewRecord;
import com.ledao.entity.User;
import com.ledao.service.BlogService;
import com.ledao.service.BlogTypeService;
import com.ledao.service.InterviewRecordService;
import com.ledao.service.UserService;
import com.ledao.util.PageUtil;
import com.ledao.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    /**
     * 首页地址(为了直接输入首地址就能访问网站)
     *
     * @return
     */
    @RequestMapping("/")
    public ModelAndView root(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "blogTypeId", required = false) String blogTypeId, @RequestParam(value = "releaseDateStr", required = false) String releaseDateStr, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        String localIp = "0:0:0:0:0:0:0:1";
        InterviewRecord interviewRecord = new InterviewRecord(request.getRemoteAddr(), "访问博客首页");
        if (interviewRecord.getInterviewerIp().equals(localIp)) {
            interviewRecord.setInterviewerIp("127.0.0.1");
        }
        interviewRecordService.add(interviewRecord);
        Map<String, Object> map = new HashMap<>(16);
        if (page == null) {
            page = 1;
        }
        int pageSize = 3;
        map.put("start", (page - 1) * pageSize);
        map.put("size", pageSize);
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
            blog.setSummary(blog.getSummary().replace("&quot;", "\""));
            blog.setSummary(blog.getSummary().replace("&nbsp;", " "));
            blog.setSummary(blog.getSummary().replace("&#39;", "\'"));
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
        mav.addObject("blogList", blogList);
        mav.addObject("blogTypeList", blogTypeList);
        mav.addObject("blogCountList", blogCountList);
        mav.addObject("title", "首页--LeDao的博客");
        mav.addObject("pageCode", PageUtil.genPagination("/index", total, page, pageSize, param.toString()));
        mav.addObject("mainPage", "page/indexFirst");
        mav.addObject("mainPageKey", "#b");
        mav.setViewName("index");
        return mav;
    }

    /**
     * 首页地址(分页,分类使用)
     *
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView root2(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "blogTypeId", required = false) String blogTypeId, @RequestParam(value = "releaseDateStr", required = false) String releaseDateStr, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        String localIp = "0:0:0:0:0:0:0:1";
        Map<String, Object> map = new HashMap<>(16);
        if (page == null) {
            page = 1;
        }
        int pageSize = 3;
        map.put("start", (page - 1) * pageSize);
        map.put("size", pageSize);
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
            blog.setSummary(blog.getSummary().replace("&quot;", "\""));
            blog.setSummary(blog.getSummary().replace("&nbsp;", " "));
            blog.setSummary(blog.getSummary().replace("&#39;", "\'"));
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
            InterviewRecord interviewRecord2 = new InterviewRecord(request.getRemoteAddr(), "查看分类：" + blogTypeService.findById(Integer.valueOf(blogTypeId)).getName() + "(按博客类别分类)");
            if (interviewRecord2.getInterviewerIp().equals(localIp)) {
                interviewRecord2.setInterviewerIp("127.0.0.1");
            }
            interviewRecordService.add(interviewRecord2);
        }
        if (StringUtil.isNotEmpty(releaseDateStr)) {
            param.append("&releaseDateStr=" + releaseDateStr);
            InterviewRecord interviewRecord3 = new InterviewRecord(request.getRemoteAddr(), "查看分类：" + releaseDateStr + "(按日期分类)");
            if (interviewRecord3.getInterviewerIp().equals(localIp)) {
                interviewRecord3.setInterviewerIp("127.0.0.1");
            }
            interviewRecordService.add(interviewRecord3);
        }
        mav.addObject("blogList", blogList);
        mav.addObject("blogTypeList", blogTypeList);
        mav.addObject("blogCountList", blogCountList);
        mav.addObject("title", "首页--LeDao的博客");
        mav.addObject("pageCode", PageUtil.genPagination("/index", total, page, pageSize, param.toString()));
        mav.addObject("mainPage", "page/indexFirst");
        mav.addObject("mainPageKey", "#b");
        mav.setViewName("index");
        return mav;
    }

    /**
     * 源码下载
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/download")
    public ModelAndView download() throws Exception {
        ModelAndView mav = new ModelAndView();
        List<BlogType> blogTypeList = blogTypeService.list(null);
        for (BlogType blogType : blogTypeList) {
            blogType.setBlogNum(blogTypeService.getBlogNumThisType(blogType.getId()));
        }
        List<Blog> blogCountList = blogService.countList();
        mav.addObject("blogTypeList", blogTypeList);
        mav.addObject("blogCountList", blogCountList);
        mav.addObject("title", "本站源码下载");
        mav.addObject("mainPage", "page/download");
        mav.setViewName("index");
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
}
