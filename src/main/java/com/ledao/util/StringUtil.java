package com.ledao.util;

import cn.hutool.setting.Setting;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 字符串工具类
 *
 * @author LeDao
 * @company
 * @create 2020-01-16 20:33
 */
public class StringUtil {

    private static final String MY_SETTING_LOCATION = "E://MyProject/IDEA/ActualCombat/MyBlog/src/main/webapp/static/myConfig.setting";

    /**
     * 判断是否是空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str.trim())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否不是空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        if ((str != null) && !"".equals(str.trim())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 格式化模糊查询
     *
     * @param str
     * @return
     */
    public static String formatLike(String str) {
        if (isNotEmpty(str)) {
            return "%" + str + "%";
        } else {
            return null;
        }
    }

    /**
     * 生成四位编号
     *
     * @param code
     * @return
     */
    public static String formatCode(String code) {
        int length = code.length();
        Integer num = Integer.parseInt(code.substring(length - 4, length)) + 1;
        StringBuffer codeNum = new StringBuffer(num + "");
        int codeLength = codeNum.length();
        for (int i = 4; i > codeLength; i--) {
            codeNum.insert(0, "0");
        }
        return codeNum.toString();
    }

    /**
     * 读取皮肤配置
     *
     * @return
     */
    public static int readSkin() throws IOException {
        Setting setting = new Setting(MY_SETTING_LOCATION);
        return setting.getInt("skin");
    }

    /**
     * 修改皮肤配置
     *
     * @param skin
     * @throws IOException
     */
    public static void updateSkin(String skin) throws IOException {
        Setting setting = new Setting(MY_SETTING_LOCATION);
        setting.set("skin", skin);
        setting.store(MY_SETTING_LOCATION);
    }

    /**
     * 读取是否发送邮件配置
     *
     * @return
     * @throws IOException
     */
    public static String readSendMail() throws IOException {
        Setting setting = new Setting(MY_SETTING_LOCATION);
        return setting.getStr("isSendMail");
    }

    /**
     * 修改是否发送邮件配置
     *
     * @param data
     * @param session
     * @throws IOException
     */
    public static void changeSendMail(String data, HttpSession session) throws IOException {
        Setting setting = new Setting(MY_SETTING_LOCATION);
        setting.set("isSendMail", data);
        setting.store(MY_SETTING_LOCATION);
        session.setAttribute("sendMailStatus", StringUtil.readSendMail().equals("0") ? "未设置" : "已设置");
    }

    /**
     * 读取代码风格配置
     *
     * @return
     * @throws IOException
     */
    public static String readCodeStyle() throws IOException {
        Setting setting = new Setting(MY_SETTING_LOCATION);
        return setting.getStr("codeStyle");
    }

    /**
     * 修改代码风格配置
     *
     * @param data
     * @throws IOException
     */
    public static void changeCodeStyle(String data) throws IOException {
        Setting setting = new Setting(MY_SETTING_LOCATION);
        setting.set("codeStyle", data);
        setting.store(MY_SETTING_LOCATION);
    }

    /**
     * 读取Lucene路径
     *
     * @return
     * @throws IOException
     */
    public static String readLucenePath() throws IOException {
        Setting setting = new Setting(MY_SETTING_LOCATION);
        return setting.getStr("lucenePath");
    }

    /**
     * 读取mysqldumpPath路径
     *
     * @return
     */
    public static String readMysqldumpPath() {
        Setting setting = new Setting(MY_SETTING_LOCATION);
        return setting.getStr("mysqldumpPath");
    }

    /**
     * 读取代码字体
     *
     * @return
     * @throws IOException
     */
    public static String readCodeFamily() throws IOException {
        Setting setting = new Setting(MY_SETTING_LOCATION);
        return setting.getStr("codeFamily");
    }

    /**
     * 修改代码字体
     *
     * @param data
     * @throws IOException
     */
    public static void changeCodeFamily(String data) throws IOException {
        Setting setting = new Setting(MY_SETTING_LOCATION);
        setting.set("codeFamily", data);
        setting.store(MY_SETTING_LOCATION);
    }

    public static void main(String[] args) throws IOException {

    }
}
