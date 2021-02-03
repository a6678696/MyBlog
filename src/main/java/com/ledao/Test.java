package com.ledao;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LeDao
 * @company
 * @create 2021-01-14 16:28
 */
public class Test {

    public static void main(String[] args) {
        List<String> stringList = new ArrayList<>();
        stringList.add("a");
        stringList.add("v");
        stringList.add("b");
        stringList.add("c");
        System.out.println("删除前:" + stringList);
        for (String s : stringList) {
            if (s.equals("a")) {
                stringList.remove(s);
            }
        }
        System.out.println("删除后:" + stringList);
    }
}
