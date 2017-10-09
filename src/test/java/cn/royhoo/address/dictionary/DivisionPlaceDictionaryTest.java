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
                "香蜜湖",
                "南区",
                "广东省",
                "白云区",
                "城关镇",
                "黔东南",
                "黔东南州",
                "高要市",
                "长春市",
                "吉林省",
                "吉林",
                "广东",
                "白云",
                "长春",
                "深圳",
                "田面",
                "六约社区",
                "香蜜湖街道",
        };
        for(String place : places){
            System.out.println("地名：" + place + "，区划信息" + DivisionPlaceDictionary.getPlaceAttributeByName(place));
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
        }
    }
}
