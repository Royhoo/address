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
        };
        for(String place : places){
            System.out.println(DivisionPlaceDictionary.dat.get(place));
        }
    }

    /**
     * 测试地名简称
     */
    @Test
    public void testShortName(){
        String[] places = {
                "广东",
                "白云",
                "长春",
        };
        for(String place : places){
            System.out.println(DivisionPlaceDictionary.dat.get(place));
        }
    }
}
