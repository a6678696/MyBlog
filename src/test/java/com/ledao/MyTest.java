package com.ledao;

import com.ledao.util.AscUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author LeDao
 * @company
 * @create 2021-06-24 2:12
 */
public class MyTest {

    @Before
    public void start() {
        System.out.println("开始测试");
    }

    @After
    public void end() {
        System.out.println("测试结束");
    }

    @Ignore
    public void main() {
        System.out.println("忽略我");
    }

    @Test
    public void AscTest() {
        String password = "@abcd6666122@";
        String password2 = AscUtil.encrypt(password);
        System.out.println(password2);
        System.out.println(AscUtil.decrypt(password2));
    }
}
