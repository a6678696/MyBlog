package com.ledao.quartz;

import com.ledao.util.BackupUtil;
import com.ledao.util.CopyUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定时备份数据
 *
 * @author LeDao
 * @company
 * @create 2021-04-24 22:22
 */
@Configuration
@EnableScheduling
public class Backup {

    /**
     * 每天晚上23点执行一次
     */
    @Scheduled(cron = "0 0 23 * * ?")
    public void work() throws IOException {
        File srcDir = new File("C:\\Java\\apache-tomcat-9.0.22-windows-x64\\apache-tomcat-9.0.22-windows-x64\\apache-tomcat-9.0.22\\webapps\\MyBlog\\static\\images\\blogImage");
        File destDir = new File("C:\\backup2\\myblog\\blogImage");
        File srcDir2 = new File("C:\\lucene\\MyBlog");
        File destDir2 = new File("C:\\backup2\\myblog\\Lucene\\MyBlog");
        CopyUtil.copyImage(srcDir,destDir);
        CopyUtil.copyLucene(srcDir2,destDir2);
        new BackupUtil("root", "123456", "db_myblog", null, "utf8",
                "C:\\backup2\\myblog\\db_myblog.sql").backup_run();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(simpleDateFormat.format(date+"自动备份成功!!"));
    }
}
