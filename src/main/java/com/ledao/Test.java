package com.ledao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.ledao.entity.Link;

/**
 * @author LeDao
 * @company
 * @create 2021-06-07 7:25
 */
public class Test {

    public static void main(String[] args) throws JsonProcessingException {
        Link link = new Link();
        link.setId(1);
        link.setName("LeDao的博客");
        link.setUrl("http://www.zoutl.cn");
        link.setSortNum(1);
        //实体类转Json
        Gson gson = new Gson();
        String jsonString = gson.toJson(link);
        System.out.println(jsonString);
        //Json转实体类
        Link link2 = gson.fromJson(jsonString, Link.class);
        System.out.println(link2.toString());
    }
}
