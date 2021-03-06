package cn.royhoo.address.dictionary;

import org.junit.Test;

import java.util.List;

/**
 * @author royhoo
 * @date 2017/9/27
 * 区划地名词典测试类
 */
public class DivisionPlaceDictionaryTest {
    @Test
    public void testForDubug(){
        String place = "迎新村";
        List<DivisionPlaceDictionary.Attribute> attributes = DivisionPlaceDictionary.getPlaceAttributeByName(place);
        System.out.println(attributes);
    }

    @Test
    public void testDictionary(){
        String[] places = {
                "南区",
                "北区",
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
                "街道",
                "北京",
                "北京市",
                "一路",
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
            "广东经济开发区",
            "深圳开发区",
            "深圳",
        };
        for(String place : places){
            System.out.println("地名：" + place + "，区划信息" + DivisionPlaceDictionary.getPlaceAttributeByName(place));
        }
    }
}
