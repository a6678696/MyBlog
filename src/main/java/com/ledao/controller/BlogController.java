package com.ledao.controller;

import com.ledao.entity.*;
import com.ledao.lucene.BlogIndex;
import com.ledao.service.*;
import com.ledao.util.AddressUtil;
import com.ledao.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Resource
    private InterviewRecordService interviewRecordService;

    @Resource
    private CommentService commentService;

    @Resource
    private LikeService likeService;

    @Resource
    private IpForBannedService ipForBannedService;

    private BlogIndex blogIndex = new BlogIndex();

    @RequestMapping("/{id}")
    public ModelAndView details(@PathVariable("id") Integer id, HttpServletRequest request) throws IOException {
        ModelAndView mav = new ModelAndView();
        Blog blog = blogService.findById(id);
        blog.setClick(blog.getClick() + 1);
        blog.setSetMenuBlogDate(null);
        blogService.update(blog);
        blog.setBlogType(blogTypeService.findById(blog.getBlogTypeId()));
        List<BlogType> blogTypeList = blogTypeService.list(null);
        for (BlogType blogType : blogTypeList) {
            blogType.setBlogNum(blogTypeService.getBlogNumThisType(blogType.getId()));
        }
        List<Blog> blogCountList = blogService.countList();
        Map<String, Object> map = new HashMap<>(16);
        map.put("blogTypeId", blog.getBlogTypeId());
        map.put("isMenuBlogKey", 1);
        //根据当前博客类别推荐博客
        List<Blog> recommendBlogList = blogService.list(map);
        //去掉List中的当前博客(即当前正在查看的博客不推荐)
        for (int i = 0; i < recommendBlogList.size(); i++) {
            if (recommendBlogList.get(i).getId().equals(blog.getId())) {
                recommendBlogList.remove(i);
                i--;
            }
        }
        //本来是按发布时间降序排列的,现在将其打乱实现随机推荐
        Collections.shuffle(recommendBlogList);
        //当前博客id
        map.put("blogId", blog.getId());
        //审核已通过的
        map.put("state", 1);
        List<Comment> commentList = commentService.list(map);
        for (Comment comment : commentList) {
            String[] ips = comment.getIp().split("\\.");
            StringBuilder sb = new StringBuilder();
            sb.append(ips[0]).append(".").append(ips[1]).append(".").append(ips[2]).append(".**");
            comment.setIp(sb.toString());
        }
        //判断当前IP是否点赞过当前博客
        List<Like> likeList = likeService.list(null);
        for (Like like : likeList) {
            if (like.getBlogId().equals(blog.getId()) && like.getIp().equals(request.getRemoteAddr().equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : request.getRemoteAddr())) {
                blog.setIsLike(1);
            }
        }
        //获取当前博客的点赞数
        Map<String, Object> map1 = new HashMap<>(16);
        map1.put("blogId", blog.getId());
        List<Like> likes = likeService.list(map1);
        blog.setLikeNum(likes.size());
        mav.addObject("codeStyle", StringUtil.readCodeStyle());
        mav.addObject("blog", blog);
        mav.addObject("menuBlogList", blogService.getMenuBlogList());
        mav.addObject("menuBlogId", id);
        mav.addObject("commentList", commentList);
        mav.addObject("blogTypeList", blogTypeList);
        mav.addObject("blogCountList", blogCountList);
        mav.addObject("recommendBlogList", recommendBlogList);
        mav.addObject("title", blog.getTitle() + "--LeDao的博客");
        mav.addObject("mainPage", "page/blogDetails" + StringUtil.readSkin());
        mav.addObject("mainPageKey", "#b");
        mav.addObject("previousAndNextBlogCode", getPreviousAndNextBlogCode(blogService.getPreviousBlog(id), blogService.getNextBlog(id)));
        mav.setViewName("index" + StringUtil.readSkin());
        InterviewRecord interviewRecord = new InterviewRecord(request.getRemoteAddr(), "查看博客：" + blog.getTitle());
        interviewRecord.setTrueAddress(AddressUtil.getAddress2(interviewRecord.getInterviewerIp()));
        //20秒钟内同一ip最大访问数
        int maxInterviewTimesInOneMinute = 15;
        if (interviewRecordService.getCountInterviewInTwentySecond(interviewRecord.getInterviewerIp()) >= maxInterviewTimesInOneMinute && ipForBannedService.findByIp(interviewRecord.getInterviewerIp()) == null) {
            IpForBanned ipForBanned = new IpForBanned();
            ipForBanned.setIp(interviewRecord.getInterviewerIp());
            ipForBanned.setType("自动封禁");
            ipForBannedService.add(ipForBanned);
        }
        if (ipForBannedService.findByIp(interviewRecord.getInterviewerIp()) != null) {
            mav.addObject("mainPage", "page/ipForBanned" + StringUtil.readSkin());
            mav.addObject("ipNow", request.getRemoteAddr());
        } else {
            interviewRecordService.add(interviewRecord);
        }
        return mav;
    }

    /**
     * 获取上一篇博客和下一篇博客代码
     *
     * @param previousBlog
     * @param nextBlog
     * @return
     */
    private static StringBuilder getPreviousAndNextBlogCode(Blog previousBlog, Blog nextBlog) {
        StringBuilder code = new StringBuilder();
        if (previousBlog != null) {
            code.append("<a href='/blog/" + previousBlog.getId() + "' style='float: left;text-decoration: none' class='btn-primary a11'>上一篇：" + previousBlog.getTitle() + "</a>");
        } else {
            code.append("<a style='float: left;text-decoration: none' class='btn-primary a13'>上一篇：没有上一篇了</a>");
        }
        if (nextBlog != null) {
            code.append("<a href='/blog/" + nextBlog.getId() + "' style='float: right;text-decoration: none' class='btn-primary a12'>下一篇：" + nextBlog.getTitle() + "</a>");
        } else {
            code.append("<a style='float: right;text-decoration: none' class='btn-primary a14'>下一篇：没有下一篇了</a>");
        }
        return code;
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
        ModelAndView mav = new ModelAndView();
        mav.addObject("mainPage", "page/blogResult" + StringUtil.readSkin());
        int pageSize = 6;
        if (page == null) {
            InterviewRecord interviewRecord = new InterviewRecord(request.getRemoteAddr(), "搜索了博客：" + q);
            interviewRecord.setTrueAddress(AddressUtil.getAddress2(interviewRecord.getInterviewerIp()));
            //20秒钟内同一ip最大访问数
            int maxInterviewTimesInOneMinute = 15;
            if (interviewRecordService.getCountInterviewInTwentySecond(interviewRecord.getInterviewerIp()) >= maxInterviewTimesInOneMinute && ipForBannedService.findByIp(interviewRecord.getInterviewerIp()) == null) {
                IpForBanned ipForBanned = new IpForBanned();
                ipForBanned.setIp(interviewRecord.getInterviewerIp());
                ipForBanned.setType("自动封禁");
                ipForBannedService.add(ipForBanned);
            }
            if (ipForBannedService.findByIp(interviewRecord.getInterviewerIp()) != null) {
                mav.addObject("mainPage", "page/ipForBanned" + StringUtil.readSkin());
                mav.addObject("ipNow", request.getRemoteAddr());
            } else {
                interviewRecordService.add(interviewRecord);
            }
        }
        if (StringUtil.isEmpty(page)) {
            page = "1";
        }
        List<BlogType> blogTypeList = blogTypeService.list(null);
        for (BlogType blogType : blogTypeList) {
            blogType.setBlogNum(blogTypeService.getBlogNumThisType(blogType.getId()));
        }
        List<Blog> blogCountList = blogService.countList();
        mav.addObject("blogTypeList", blogTypeList);
        mav.addObject("blogCountList", blogCountList);
        mav.addObject("title", "搜索关键字'" + q + "'结果页面--LeDao博客系统");
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
        if (1 == StringUtil.readSkin()) {
            mav.addObject("pageCode", this.genUpAndDownPageCode(Integer.parseInt(page), blogList.size(), q, pageSize, request.getServletContext().getContextPath()));
        } else if (2 == StringUtil.readSkin()) {
            mav.addObject("pageCode", this.genUpAndDownPageCode2(Integer.parseInt(page), blogList.size(), q, pageSize, request.getServletContext().getContextPath()));
        }
        mav.addObject("q", q);
        mav.addObject("menuBlogList", blogService.getMenuBlogList());
        mav.addObject("resultTotal", blogList.size());
        mav.setViewName("index" + StringUtil.readSkin());
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
    private String genUpAndDownPageCode2(Integer page, Integer totalNum, String q, Integer pageSize, String projectContext) {
        //数据总页数
        long totalPage = totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize + 1;
        StringBuffer pageCode = new StringBuffer();
        if (totalPage == 0) {
            return null;
        } else {
            if (page > 1) {
                pageCode.append("<a href='" + projectContext + "/blog/q?page=" + (page - 1) + "&q=" + q + "' style='text-decoration: none' class='com'><button type='button' class='btn btn-primary btn-sm btn11'>上一页</button></a>");
            } else {
                pageCode.append("<a style='text-decoration: none' class='com'><button type='button' class='btn btn-primary btn-sm btn11'>上一页</button></a>");
            }
            if (page < totalPage) {
                pageCode.append("<a href='" + projectContext + "/blog/q?page=" + (page + 1) + "&q=" + q + "' style='text-decoration: none;margin-left: 2px' class='com'><button type='button' class='btn btn-primary btn-sm btn11'>下一页</button></a>");
            } else {
                pageCode.append("<a style='text-decoration: none;margin-left: 2px' class='com'><button type='button' class='btn btn-primary btn-sm btn11'>下一页</button></a>");
            }
        }
        return pageCode.toString();
    }
}
