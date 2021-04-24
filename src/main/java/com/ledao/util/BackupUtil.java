package com.ledao.util;

/**
 * @author LeDao
 * @company
 * @create 2021-04-23 21:39
 */
public class BackupUtil {

    /**
     * 数据库用户名
     */
    private String user_name;
    /**
     * 数据库密码
     */
    private String user_psw;
    /**
     * 需要备份的数据库名
     */
    private String db_name;
    /**
     * 主机IP
     */
    private String host_ip;
    /**
     * 字符集
     */
    private String user_charset;
    /**
     * 存放备份文件的路径
     */
    private String backup_path;
    /**
     * 命令
     */
    private String stmt;

    public BackupUtil(String user_name, String user_psw, String db_name,
                      String host_ip, String user_charset, String backup_path) {
        this.user_name = user_name;
        this.user_psw = user_psw;
        this.db_name = db_name;
        // 主机IP;
        if (host_ip == null || host_ip.equals("")) {
            // 默认为本机
            this.host_ip = "localhost";
        } else {
            this.host_ip = host_ip;
        }
        // 字符集
        if (user_charset == null || user_charset.equals("")) {
            // 默认为安装时设置的字符集
            this.user_charset = " ";
        } else {
            this.user_charset = " --default-character-set=" + user_charset;
        }
        this.backup_path = backup_path;
        this.stmt = "C:\\Program Files\\MySQL\\MySQL Server 5.6\\bin\\mysqldump "
                + this.db_name + " -h " + this.host_ip + " -u" + this.user_name
                + " -p" + this.user_psw + this.user_charset + " --result-file="
                + this.backup_path;
    }

    public boolean backup_run() {
        boolean run_result = false;
        try {
            Runtime.getRuntime().exec(this.stmt);
            run_result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return run_result;
    }

    public static void main(String[] args) {
        BackupUtil backup = new BackupUtil("root", "123456", "db_myblog", null, "utf8",
                "C:\\backup\\myblog\\db_myblog.sql");
        boolean result = backup.backup_run();
        if (result) {
            System.out.println("备份成功");
        }
    }
}
