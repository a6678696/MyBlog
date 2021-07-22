package com.ledao;

import cn.hutool.http.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ledao.entity.Fruit;

import java.util.List;

/**
 * @author LeDao
 * @company
 * @create 2021-06-21 12:38
 */
public class Test {

    public static void main(String[] args) {
        //http请求获取json字符串
        String str = HttpUtil.get("https://www.zoutl.cn/getFruitListJson");
        int start = str.indexOf("[");
        int end = str.indexOf("]") + 1;
        //截取字符串
        str = str.substring(start, end);
        System.out.println(str);
        //转为list
        Gson gson = new Gson();
        List<Fruit> fruitList = gson.fromJson(str, new TypeToken<List<Fruit>>(){}.getType());
        for (Fruit fruit : fruitList) {
            System.out.println(fruit.getId() + "," + fruit.getName() + "," + fruit.getNum());
        }
    }
}
 