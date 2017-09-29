package cn.royhoo.address.dictionary;

import org.junit.Test;

/**
 * @author royhoo
 * @date 2017/9/27
 * 区划地名词典测试类
 */
public class DivisionPlaceDictionaryTest {
    @Test
    public void testDictionary(){
        String[] places = {
                "广东省",
                "白云区",
                "城关镇",
                "黔东南",
                "黔东南州",
                "广东",
                "白云",
                "长春",
                "深圳",
                "田面",
                "六约社区",
        };
        for(String place : places){
            System.out.println("地名：" + place + "，区划信息" + DivisionPlaceDictionary.dat.get(place));
        }
    }

    /**
     *
     */
    @Test
    public void testShortName(){
        String[] places = {

        };
        for(String place : places){
            System.out.println(DivisionPlaceDictionary.dat.get(place));
        }
    }
}
