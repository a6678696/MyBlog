package com.ledao.quartz;

import com.ledao.util.BackupUtil;
import com.ledao.util.CopyUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

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
        CopyUtil.copyImage();
        CopyUtil.copyLucene();
        new BackupUtil("root", "123456", "db_myblog", null, "utf8",
                "C:\\backup2\\myblog\\db_myblog.sql").backup_run();
    }
}
