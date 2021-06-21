package com.ledao.quartz;

import com.ledao.entity.Blog;
import com.ledao.service.BlogService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 每天12点自动删除失效图片(没有被引用的)
 *
 * @author LeDao
 * @company
 * @create 2020-09-23 11:27
 */
@Configuration
@EnableScheduling
public class DeleteInvalidImage {

    @Value("${blogImageFilePath}")
    private String blogImageFilePath;

    @Resource
    private BlogService blogService;

    @Scheduled(cron = "0 0 12 * * ?")
    public void work() {
        List<Blog> blogList = blogService.list(null);
        //博客中引用的图片名称集合
        List<String> blogImageList = new ArrayList<>();
        for (Blog blog : blogList) {
            //博客里的内容
            String blogInfo = blog.getContent();
            //抓取出博客里的内容
            Document document = Jsoup.parse(blogInfo);
            //提出.jpg图片
            Elements jpgs = document.select("img[src$=.jpg]");
            if (jpgs.size() > 0) {
                for (int i = 0; i < jpgs.size(); i++) {
                    Element jpg = jpgs.get(i);
                    int begin = jpg.toString().indexOf("/static/images/blogImage/");
                    int last = jpg.toString().indexOf(".jpg");
                    blogImageList.add((jpg.toString().substring(begin, last) + ".jpg").substring(25));
                }
            }
        }
        File file = new File(blogImageFilePath);
        //文件夹内的图片名称集合
        List<String> dirImageList = new ArrayList<>();
        //仅为了获取文件名
        for (String s : file.list()) {
            dirImageList.add(s);
        }
        //如果文件夹内的图片数量大于实际引用的图片数量
        if (dirImageList.size() > blogImageList.size()) {
            //将有效的图片删除即可得到无效的图片集合
            for (String s : blogImageList) {
                Iterator iterator = dirImageList.iterator();
                while (iterator.hasNext()) {
                    if (iterator.next().equals(s)) {
                        iterator.remove();
                    }
                }
            }
            //无效的图片集合
            List<String> invalidImageList = dirImageList;
            //有无效的图片就将它从文件夹中删除
            if (invalidImageList.size() > 0) {
                System.out.println("删除了" + invalidImageList.size() + "张图片");
                for (String s : invalidImageList) {
                    File file1 = new File(blogImageFilePath + s);
                    file1.delete();
                }
                System.out.println("无效的图片删除完毕!");
            }
        } else {
            System.out.println("没有无效的图片!");
        }
    }
}
