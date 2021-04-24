package com.ledao.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * 资源备份工具类
 *
 * @author LeDao
 * @company
 * @create 2021-04-24 17:32
 */
public class CopyUtil {

    /**
     * 备份博客图片
     *
     * @throws IOException
     */
    public static void copyImage() throws IOException {
        File srcDir = new File("C:\\Java\\apache-tomcat-9.0.22-windows-x64\\apache-tomcat-9.0.22-windows-x64\\apache-tomcat-9.0.22\\webapps\\MyBlog\\static\\images\\blogImage");
        File destDir = new File("C:\\backup\\myblog\\blogImage");
        if (destDir.exists()) {
            FileUtils.deleteDirectory(destDir);
            System.out.println("目标文件夹(博客图片)已删除！！");
        }
        FileUtils.copyDirectory(srcDir, destDir);
        System.out.println("博客图片已经备份成功！！");
    }

    /**
     * 备份Lucene索引资源
     *
     * @throws IOException
     */
    public static void copyLucene() throws IOException {
        File srcDir = new File("C:\\lucene\\MyBlog");
        File destDir = new File("C:\\backup\\myblog\\Lucene\\MyBlog");
        if (destDir.exists()) {
            FileUtils.deleteDirectory(destDir);
            System.out.println("目标文件夹(Lucene索引资源)已删除！！");
        }
        FileUtils.copyDirectory(srcDir, destDir);
        System.out.println("Lucene索引资源已经备份成功！！");
    }

    public static void main(String[] args) throws IOException {
        copyImage();
        copyLucene();
    }
}
