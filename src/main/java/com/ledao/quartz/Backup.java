package com.ledao.quartz;

import com.ledao.util.BackupUtil;
import com.ledao.util.CopyUtil;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${blogImageFilePath}")
    private String blogImageFilePath;

    @Value("${lucenePath}")
    private String lucenePath;

    @Value("${blogImageFilePath2}")
    private String blogImageFilePath2;

    @Value("${lucenePath2}")
    private String lucenePath2;

    @Value("${mysqlPath2}")
    private String mysqlPath2;

    @Value("${mysqlPassword}")
    private String mysqlPassword;

    /**
     * 每天12点执行一次
     */
    @Scheduled(cron = "0 0 12 * * ?")
    public void work() throws IOException {
        File srcDir = new File(blogImageFilePath);
        File destDir = new File(blogImageFilePath2);
        File srcDir2 = new File(lucenePath);
        File destDir2 = new File(lucenePath2);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        System.out.println(simpleDateFormat.format(date) + "--------------<<<<<<开始自动备份>>>>>>--------------");
        CopyUtil.copyImage(srcDir, destDir);
        CopyUtil.copyLucene(srcDir2, destDir2);
        new BackupUtil("root", mysqlPassword, "db_myblog", null, "utf8",
                mysqlPath2).backup_run();
        Date date2 = new Date();
        System.out.println(simpleDateFormat.format(date2) + "-------------->>>>>>自动备份成功<<<<<<--------------");
    }
}
