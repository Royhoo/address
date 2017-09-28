package cn.royhoo.address.dictionary;

import org.junit.Test;

import java.util.List;

/**
 * @author royhoo
 * @date 2017/9/28
 * 地名后缀词典测试类
 */
public class DivisionPlacePostfixDictionaryTest {
    /**
     * 测试地名后缀级别的获取
     */
    @Test
    public void testGetGradeByPlacePostfix(){
        String[] postfixs = {
                "省",
                "市",
                "镇",
                "自治区",
        };
        for(String postfix : postfixs){
            System.out.println(postfix + "：" + DivisionPlacePostfixDictionary.getGradeByPlacePostfix(postfix));
        }
    }

    /**
     * 测试根据级别获取对应的地名后缀
     */
    @Test
    public void testGetPlacePostfixByGrade(){
        int[] grades = {0, 1, 2, 3, 4, 5};
        for(int grade : grades){
            System.out.println(grade + "：" + DivisionPlacePostfixDictionary.getPlacePostfixByGrade(grade));
        }
    }

    /**
     * 测试少数民族自治区域简称的获取
     */
    @Test
    public void testGetMinorityPlaceShortName(){
        String[] placeNemes = {
                "黔东南苗族侗族自治州",
                "延边朝鲜族自治州",
                "乳源瑶族自治县",
                "于家务回族乡",
                "西藏自治区",
                "宁夏回族自治区",
                "新疆维吾尔族自治区",
                "新疆维吾尔自治区",
        };
        for(String placeNeme : placeNemes){
            System.out.println(placeNeme + "：" + DivisionPlacePostfixDictionary.getMinorityPlaceShortName(placeNeme));
        }
    }

    /**
     * 测试获取地名简称
     */
    @Test
    public void testGetPlaceShortName(){
        Object[][] paras = {
                {"广东省", 1},
                {"深圳市", 2},
                {"深圳市"},
                {"香蜜湖街道办", 4},
                {"红星社区", 5},
                {"红星社区"},
                {"广西自治区"},
                {"赵县"}, // 两个字的地名，没有简称
                {"松江工业区", 4},
                {"松江工业区"},  // 得到简称“松江工业”，实际上是错误的简称
                {"广西壮族自治区"},
                {"瀍河回族区"},
        };
        for(Object[] para : paras){
            if(para.length == 2){
                String placeName = (String) para[0];
                int grade = (int) para[1];
                System.out.println("全称：" + placeName + ", 级别：" + grade + ", 得到简称："
                        + DivisionPlacePostfixDictionary.getPlaceShortName(placeName, grade));
            } else {
                String placeName = (String) para[0];
                System.out.println("全称：" + placeName + ", 级别：未输入, 得到简称："
                        + DivisionPlacePostfixDictionary.getPlaceShortName(placeName));
            }
        }
    }
}
