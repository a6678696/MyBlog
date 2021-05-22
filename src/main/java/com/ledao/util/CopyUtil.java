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
    public static void copyImage(File srcDir,File destDir) throws IOException {
        if (destDir.exists()) {
            FileUtils.deleteDirectory(destDir);
        }
        FileUtils.copyDirectory(srcDir, destDir);
        System.out.println("博客图片已经备份成功！！");
    }

    /**
     * 备份Lucene索引资源
     *
     * @throws IOException
     */
    public static void copyLucene(File srcDir,File destDir) throws IOException {
        if (destDir.exists()) {
            FileUtils.deleteDirectory(destDir);
        }
        FileUtils.copyDirectory(srcDir, destDir);
        System.out.println("Lucene索引资源已经备份成功！！");
    }

    public static void main(String[] args) throws IOException {
        File srcDir = new File("C:\\Java\\apache-tomcat-9.0.22-windows-x64\\apache-tomcat-9.0.22-windows-x64\\apache-tomcat-9.0.22\\webapps\\MyBlog\\static\\images\\blogImage");
        File destDir = new File("C:\\backup\\myblog\\blogImage");
        File srcDir2 = new File("C:\\lucene\\MyBlog");
        File destDir2 = new File("C:\\backup\\myblog\\Lucene\\MyBlog");
        copyImage(srcDir,destDir);
        copyLucene(srcDir2,destDir2);
    }
}
