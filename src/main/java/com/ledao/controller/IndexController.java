package com.ledao.controller;

import cn.hutool.http.HtmlUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.ledao.entity.*;
import com.ledao.lucene.BlogIndex;
import com.ledao.service.*;
import com.ledao.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 首页Controller
 *
 * @author LeDao
 * @company
 * @create 2020-09-08 20:04
 */
@Controller
public class IndexController {

    @Value("${blogImageFilePath}")
    private String blogImageFilePath;

    @Value("${lucenePath}")
    private String lucenePath;

    @Value("${blogImageFilePath1}")
    private String blogImageFilePath1;

    @Value("${lucenePath1}")
    private String lucenePath1;

    @Value("${mysqlPath1}")
    private String mysqlPath1;

    @Value("${mysqlPassword}")
    private String mysqlPassword;

    @Resource
    private UserService userService;

    @Resource
    private BlogService blogService;

    @Resource
    private BlogTypeService blogTypeService;

    @Resource
    private InterviewRecordService interviewRecordService;

    @Resource
    private IpForBannedService ipForBannedService;

    @Resource
    private LinkService linkService;

    @Resource
    private CommentService commentService;

    @Resource
    private LikeService likeService;

    /**
     * 首页地址(为了直接输入首地址就能访问网站)
     *
     * @return
     */
    @RequestMapping("/")
    public ModelAndView root(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "blogTypeId", required = false) String blogTypeId, @RequestParam(value = "releaseDateStr", required = false) String releaseDateStr, HttpServletRequest request, HttpSession session) throws IOException {
        ModelAndView mav = new ModelAndView();
        mav.addObject("mainPage", "page/indexFirst" + StringUtil.readSkin());
        InterviewRecord interviewRecord = new InterviewRecord(request.getRemoteAddr(), "访问博客首页");
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
            if (session.getAttribute("isMe") == null) {
                interviewRecordService.add(interviewRecord);
            }
        }
        Map<String, Object> map = new HashMap<>(16);
        if (page == null) {
            page = 1;
        }
        int pageSize = 8;
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
        if (!(RedisUtil.existKey("blogTypeList")&&RedisUtil.existKey("blogCountList"))) {
            addRedis();
        }
        //从Redis获取两个类别列表
        List<String> redisBlogTypeList = RedisUtil.listRange("blogTypeList", 0L, -1L);
        List<String> redisBlogCountList = RedisUtil.listRange("blogCountList", 0L, -1L);
        List<BlogType> blogTypeList = new ArrayList<>();
        List<Blog> blogCountList = new ArrayList<>();
        for (String s : redisBlogTypeList) {
            BlogType blogType22 = BlogType.jsonToEntity(s);
            blogTypeList.add(blogType22);
        }
        for (String s : redisBlogCountList) {
            Blog blog22 = Blog.jsonToEntity(s);
            blogCountList.add(blog22);
        }
        for (int i = 0; i < blogCountList.size(); i++) {
            blogCountList.get(i).setColor(blogTypeList.get(i).getColor());
        }
        StringBuffer param = new StringBuffer();
        if (StringUtil.isNotEmpty(blogTypeId)) {
            param.append("&blogTypeId=" + blogTypeId);
        }
        if (StringUtil.isNotEmpty(releaseDateStr)) {
            param.append("&releaseDateStr=" + releaseDateStr);
        }
        mav.addObject("ipCount", interviewRecordService.getCountInterviewIpNum());
        mav.addObject("thisIpCount", interviewRecordService.getCountInterviewerInAppearNum(request.getRemoteAddr()));
        mav.addObject("myWebSitRunDays", interviewRecordService.getMyWebSitRunDays());
        mav.addObject("thisIp", request.getRemoteAddr());
        mav.addObject("interviewCount", interviewRecordService.getCount(null));
        mav.addObject("menuBlogList", blogService.getMenuBlogList());
        mav.addObject("isHome", 1);
        mav.addObject("linkList", linkService.list(null));
        mav.addObject("blogList", blogList);
        mav.addObject("blogTypeList", blogTypeList);
        mav.addObject("blogCountList", blogCountList);
        mav.addObject("title", "LeDao的博客");
        switch (StringUtil.readSkin()) {
            case 1:
                mav.addObject("pageCode", PageUtil.genPagination1("/index", total, page, pageSize, param.toString()));
                break;
            case 2:
                mav.addObject("pageCode", PageUtil.genPagination2("/index", total, page, pageSize, param.toString()));
                break;
            case 3:
                mav.addObject("pageCode", PageUtil.genPagination3("/index", total, page, pageSize, param.toString()));
                break;
            case 4:
                mav.addObject("pageCode", PageUtil.genPagination4("/index", total, page, pageSize, param.toString()));
                break;
            default:
                break;
        }
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
    public ModelAndView root2(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "blogTypeId", required = false) String blogTypeId, @RequestParam(value = "releaseDateStr", required = false) String releaseDateStr, HttpServletRequest request, HttpSession session) throws IOException {
        ModelAndView mav = new ModelAndView();
        Map<String, Object> map = new HashMap<>(16);
        StringBuffer param = new StringBuffer();
        if (StringUtil.isNotEmpty(blogTypeId)) {
            param.append("&blogTypeId=" + blogTypeId);
            if (page == null) {
                InterviewRecord interviewRecord2 = new InterviewRecord(request.getRemoteAddr(), "查看分类：" + blogTypeService.findById(Integer.valueOf(blogTypeId)).getName() + "(按博客类别分类)");
                interviewRecord2.setTrueAddress(AddressUtil.getAddress2(interviewRecord2.getInterviewerIp()));
                if (session.getAttribute("isMe") == null) {
                    interviewRecordService.add(interviewRecord2);
                }
            }
            BlogType blogType = blogTypeService.findById(Integer.parseInt(blogTypeId));
            mav.addObject("title", "当前分类:" + blogType.getName() + "--LeDao的博客");
        } else if (StringUtil.isEmpty(releaseDateStr) && StringUtil.isEmpty(blogTypeId)) {
            mav.addObject("title", "LeDao的博客");
        }
        if (StringUtil.isNotEmpty(releaseDateStr)) {
            param.append("&releaseDateStr=" + releaseDateStr);
            if (page == null) {
                InterviewRecord interviewRecord3 = new InterviewRecord(request.getRemoteAddr(), "查看分类：" + releaseDateStr + "(按日期分类)");
                interviewRecord3.setTrueAddress(AddressUtil.getAddress2(interviewRecord3.getInterviewerIp()));
                if (session.getAttribute("isMe") == null) {
                    interviewRecordService.add(interviewRecord3);
                }
            }
            mav.addObject("title", "当前分类:" + releaseDateStr + "--LeDao的博客");
        } else if (StringUtil.isEmpty(releaseDateStr) && StringUtil.isEmpty(blogTypeId)) {
            mav.addObject("title", "LeDao的博客");
        }
        if (page == null) {
            page = 1;
        }
        int pageSize = 8;
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
        if (!(RedisUtil.existKey("blogTypeList")&&RedisUtil.existKey("blogCountList"))) {
            addRedis();
        }
        //从Redis获取两个类别列表
        List<String> redisBlogTypeList = RedisUtil.listRange("blogTypeList", 0L, -1L);
        List<String> redisBlogCountList = RedisUtil.listRange("blogCountList", 0L, -1L);
        List<BlogType> blogTypeList = new ArrayList<>();
        List<Blog> blogCountList = new ArrayList<>();
        for (String s : redisBlogTypeList) {
            BlogType blogType22 = BlogType.jsonToEntity(s);
            blogTypeList.add(blogType22);
        }
        for (String s : redisBlogCountList) {
            Blog blog22 = Blog.jsonToEntity(s);
            blogCountList.add(blog22);
        }
        for (int i = 0; i < blogCountList.size(); i++) {
            blogCountList.get(i).setColor(blogTypeList.get(i).getColor());
        }
        //是第一页
        if ((page == 1 || page == null)) {
            //不是按类别和时间分类
            if (blogTypeId == null && releaseDateStr == null) {
                mav.addObject("isHome", 1);
            }
        }
        mav.addObject("ipCount", interviewRecordService.getCountInterviewIpNum());
        mav.addObject("myWebSitRunDays", interviewRecordService.getMyWebSitRunDays());
        mav.addObject("thisIp", request.getRemoteAddr());
        mav.addObject("interviewCount", interviewRecordService.getCount(null));
        mav.addObject("menuBlogList", blogService.getMenuBlogList());
        mav.addObject("linkList", linkService.list(null));
        mav.addObject("blogList", blogList);
        mav.addObject("blogTypeList", blogTypeList);
        mav.addObject("blogCountList", blogCountList);
        switch (StringUtil.readSkin()) {
            case 1:
                mav.addObject("pageCode", PageUtil.genPagination1("/index", total, page, pageSize, param.toString()));
                break;
            case 2:
                mav.addObject("pageCode", PageUtil.genPagination2("/index", total, page, pageSize, param.toString()));
                break;
            case 3:
                mav.addObject("pageCode", PageUtil.genPagination3("/index", total, page, pageSize, param.toString()));
                break;
            case 4:
                mav.addObject("pageCode", PageUtil.genPagination4("/index", total, page, pageSize, param.toString()));
                break;
            default:
                break;
        }
        mav.addObject("mainPage", "page/indexFirst" + StringUtil.readSkin());
        if (ipForBannedService.findByIp(request.getRemoteAddr()) != null) {
            mav.addObject("mainPage", "page/ipForBanned" + StringUtil.readSkin());
            mav.addObject("ipNow", request.getRemoteAddr());
        } else {
            if (page == 1 && StringUtil.isEmpty(blogTypeId) && StringUtil.isEmpty(releaseDateStr)) {
                InterviewRecord interviewRecord4 = new InterviewRecord(request.getRemoteAddr(), "访问博客首页/index");
                interviewRecord4.setTrueAddress(AddressUtil.getAddress2(interviewRecord4.getInterviewerIp()));
                if (session.getAttribute("isMe") == null) {
                    interviewRecordService.add(interviewRecord4);
                }
            }
        }
        mav.addObject("thisIpCount", interviewRecordService.getCountInterviewerInAppearNum(request.getRemoteAddr()));
        mav.addObject("mainPageKey", "#b");
        mav.setViewName("index" + StringUtil.readSkin());
        return mav;
    }

    private void addRedis() {
        List<BlogType> blogTypeList = blogTypeService.list(null);
        for (BlogType blogType : blogTypeList) {
            blogType.setBlogNum(blogTypeService.getBlogNumThisType(blogType.getId()));
        }
        List<Blog> blogCountList = blogService.countList();
        if (RedisUtil.existKey("blogTypeList")) {
            //清空
            Long length = RedisUtil.listLength("blogTypeList");
            if (length > 0) {
                for (int i = 0; i < length; i++) {
                    RedisUtil.listRightPop("blogTypeList");
                }
            }
            //添加
            for (int i = 0; i < blogTypeList.size(); i++) {
                RedisUtil.listRightPush("blogTypeList", RedisUtil.entityToJson(blogTypeList.get(i)));
            }
        } else {
            //添加
            for (int i = 0; i < blogTypeList.size(); i++) {
                RedisUtil.listRightPush("blogTypeList", RedisUtil.entityToJson(blogTypeList.get(i)));
            }
        }
        if (RedisUtil.existKey("blogCountList")) {
            //清空
            Long length = RedisUtil.listLength("blogCountList");
            if (length > 0) {
                for (int i = 0; i < length; i++) {
                    RedisUtil.listRightPop("blogCountList");
                }
            }
            //添加
            for (int i = 0; i < blogCountList.size(); i++) {
                RedisUtil.listRightPush("blogCountList", RedisUtil.entityToJson(blogCountList.get(i)));
            }
        } else {
            //添加
            for (int i = 0; i < blogCountList.size(); i++) {
                RedisUtil.listRightPush("blogCountList", RedisUtil.entityToJson(blogCountList.get(i)));
            }
        }
    }

    /**
     * 无需进入后台,直接在前台看一些数据
     *
     * @return
     * @throws IOException
     */
    @RequestMapping("/show")
    public ModelAndView show(HttpServletRequest request, HttpSession session) throws IOException {
        ModelAndView mav = new ModelAndView();
        Map<String, Object> map = new HashMap<>(16);
        List<Like> likeList = likeService.list(map);
        for (Like like : likeList) {
            Blog blog = blogService.findById(like.getBlogId());
            like.setBlog(blog);
        }
        List<Comment> commentList = commentService.list(map);
        List<IpForBanned> ipForBannedList = ipForBannedService.list(map);
        InterviewRecord interviewRecord = new InterviewRecord(request.getRemoteAddr(), "查看show");
        interviewRecord.setTrueAddress(AddressUtil.getAddress2(interviewRecord.getInterviewerIp()));
        if (session.getAttribute("isMe") == null) {
            interviewRecordService.add(interviewRecord);
        }
        map.put("start", 0);
        map.put("size", 50);
        List<InterviewRecord> interviewRecordList = interviewRecordService.list(map);
        mav.addObject("likeList", likeList);
        mav.addObject("commentList", commentList);
        mav.addObject("ipForBannedList", ipForBannedList);
        mav.addObject("interviewRecordList", interviewRecordList);
        mav.addObject("title", "一些数据--LeDao的博客");
        mav.addObject("mainPage", "show" + StringUtil.readSkin());
        if (ipForBannedService.findByIp(request.getRemoteAddr()) != null) {
            mav.addObject("mainPage", "page/ipForBanned" + StringUtil.readSkin());
            mav.addObject("ipNow", request.getRemoteAddr());
        }
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
    public Object login(HttpSession session) throws IOException {
        User user = userService.findByUserName("admin");
        session.setAttribute("currentUserNickName", user.getNickName());
        session.setAttribute("sendMailStatus", StringUtil.readSendMail().equals("0") ? "未设置" : "已设置");
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
    public Map<String, Object> changeSkin(String skin) throws IOException {
        Map<String, Object> resultMap = new HashMap<>(16);
        if (!skin.equals("1") && !skin.equals("2") && !skin.equals("3") && !skin.equals("4")) {
            skin = "2";
        }
        StringUtil.updateSkin(skin);
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 读取是否邮件提醒配置
     *
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/readSendMail")
    public Map<String, Object> readSendMail() throws IOException {
        String status = StringUtil.readSendMail();
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("success", "true");
        resultMap.put("status", status);
        return resultMap;
    }

    /**
     * 设置评论时提醒与否
     *
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/changeSendMail")
    public Map<String, Object> changeSendMail(HttpSession session) throws IOException {
        String status = StringUtil.readSendMail();
        Map<String, Object> resultMap = new HashMap<>(16);
        //有人评论时不提醒
        String isSendMail = "0";
        if (isSendMail.equals(status)) {
            StringUtil.changeSendMail("1", session);
        } else {
            StringUtil.changeSendMail("0", session);
        }
        resultMap.put("success", "true");
        return resultMap;
    }

    /**
     * 备份数据（数据库、Lucene索引、博客图片）
     *
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/backup")
    public Map<String, Object> backup() throws IOException {
        Map<String, Object> resultMap = new HashMap<>(16);
        File srcDir = new File(blogImageFilePath);
        File destDir = new File(blogImageFilePath1);
        File srcDir2 = new File(lucenePath);
        File destDir2 = new File(lucenePath1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        System.out.println(simpleDateFormat.format(date) + "--------------<<<<<<开始手动备份>>>>>>--------------");
        CopyUtil.copyImage(srcDir, destDir);
        CopyUtil.copyLucene(srcDir2, destDir2);
        new BackupUtil("root", mysqlPassword, "db_myblog", null, "utf8",
                mysqlPath1).backup_run();
        Date date2 = new Date();
        System.out.println(simpleDateFormat.format(date2) + "-------------->>>>>>手动备份成功<<<<<<--------------");
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 修改代码风格配置
     *
     * @param codeStyle
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/changeCodeStyle")
    public Map<String, Object> changeCodeStyle(String codeStyle) throws IOException {
        Map<String, Object> resultMap = new HashMap<>(16);
        StringUtil.changeCodeStyle(codeStyle);
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 读取代码风格配置
     *
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/readCodeStyle")
    public Map<String, Object> readCodeStyle() throws IOException {
        String codeStyle = StringUtil.readCodeStyle();
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("success", "true");
        resultMap.put("codeStyle", codeStyle);
        return resultMap;
    }

    /**
     * 读取代码字体
     *
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/readCodeFamily")
    public Map<String, Object> readCodeFamily() throws IOException {
        String codeFamily = StringUtil.readCodeFamily();
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("success", "true");
        resultMap.put("codeFamily", codeFamily);
        return resultMap;
    }

    /**
     * 修改代码字体
     *
     * @param codeFamily
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/changeCodeFamily")
    public Map<String, Object> changeCodeFamily(String codeFamily, String password) throws IOException {
        Map<String, Object> resultMap = new HashMap<>(16);
        if (password.equals(userService.findByUserName("modifyCodeFontFamilyPassword").getPassword())) {
            StringUtil.changeCodeFamily(codeFamily);
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }

    /**
     * 统计每日访问ip数
     *
     * @param days
     * @return
     */
    @ResponseBody
    @RequestMapping("/countIpNumByDay")
    public Map<String, Object> countIpNumByDay(String days) {
        Map<String, Object> resultMap = new HashMap<>(16);
        List<CountIpNumByDay> countIpNumByDayList = interviewRecordService.countIpNumByDay(Integer.parseInt(days));
        resultMap.put("rows", countIpNumByDayList);
        return resultMap;
    }

    /**
     * 统计每日访问ip数
     *
     * @param days
     * @return
     */
    @ResponseBody
    @RequestMapping("/countInterviewNumByDay")
    public Map<String, Object> countInterviewNumByDay(String days) {
        Map<String, Object> resultMap = new HashMap<>(16);
        List<CountInterviewNumByDay> countInterviewNumByDayList = interviewRecordService.countInterviewNumByDay(Integer.parseInt(days));
        resultMap.put("rows", countInterviewNumByDayList);
        return resultMap;
    }

    /**
     * 确定是站长访问网站
     *
     * @param session
     * @return
     */
    @RequestMapping("/isMe6678696")
    public String isMe6678696(HttpSession session) {
        session.setAttribute("isMe", 1);
        return "redirect:/";
    }

    /**
     * 获取水果列表json(在本系统中无应用)
     *
     * @return
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping("/getFruitListJson")
    public Map<String, Object> getFruitListJson() {
        Map<String, Object> resultMap = new HashMap<>(16);
        Fruit fruit1 = new Fruit(1, "苹果", 11);
        Fruit fruit2 = new Fruit(2, "梨", 6);
        Fruit fruit3 = new Fruit(3, "油桃", 8);
        List<Fruit> fruitList = new ArrayList<>();
        fruitList.add(fruit1);
        fruitList.add(fruit2);
        fruitList.add(fruit3);
        resultMap.put("rows", fruitList);
        return resultMap;
    }

    /**
     * 获取水果对象(在本系统中无应用)
     *
     * @return
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping("/getFruitJson")
    public String getFruitJson() {
        Fruit fruit = new Fruit(1, "苹果", 3);
        Gson gson = new Gson();
        return gson.toJson(fruit);
    }

    /**
     * 根据用户传入的值获取对应的水果对象(在本系统中无应用)
     *
     * @param id
     * @param name
     * @param num
     * @return
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping("/getFruitJson2")
    public String getFruitJson2(int id, String name, int num) {
        Fruit fruit = new Fruit(id, name, num);
        Gson gson = new Gson();
        return gson.toJson(fruit);
    }

    @RequestMapping("/toTobaccoPage")
    public ModelAndView toTobaccoPage() throws IOException {
        ModelAndView mav = new ModelAndView();
        mav.addObject("title", "买烟--LeDao的博客");
        mav.addObject("mainPage", "tobaccoPage" + StringUtil.readSkin());
        mav.addObject("mainPageKey", "#b");
        mav.setViewName("index" + StringUtil.readSkin());
        return mav;
    }

    @ResponseBody
    @RequestMapping("/getResult")
    public Map<String, Object> getResult(String str) {
        Map<String, Object> resultMap = new HashMap<>(16);
        String successInfo = "true";
        int begin = str.indexOf("var cartlistJson =");
        int last = str.indexOf(";\n" +
                "var fromPage = \"\";");
        str = str.substring(begin + "var cartlistJson =".length(), last);
        List<Tobacco> tobaccoList = JSONObject.parseArray(str, Tobacco.class);
        tobaccoList = tobaccoList.stream().sorted(Comparator.comparing(Tobacco::getPrice)).collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        int total = 0;
        for (Tobacco tobacco : tobaccoList) {
            sb.append(tobacco.getCgt_name() + "------- " + tobacco.getReq_qty() + " 条\n");
            total += tobacco.getReq_qty();
        }
        sb.append("\n一共 " + total + " 条");
        String result = sb.toString();
        resultMap.put("result", result);
        resultMap.put("success", successInfo);
        return resultMap;
    }

    /**
     * 添加lucene索引
     *
     * @throws Exception
     */
    @RequestMapping("/addIndex")
    public void addIndex() throws Exception {
        List<Blog> blogList = blogService.list(null);
        BlogIndex blogIndex = new BlogIndex();
        for (Blog blog : blogList) {
            blogIndex.addIndex(blog);
        }
    }
}
