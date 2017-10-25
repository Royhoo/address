package cn.royhoo.address.understanding;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author royhoo
 * @date 2017/10/25
 * 请添加描述
 */
public class AddressUnderstandingTest {
    @Test
    public void testSynonymPostfix(){
        String[] address = {
//                "深圳市宝安区西乡街道麻布社区",
//                "深圳市宝安区浪心村11号老乡村酒楼旁",
//                "深圳市宝安区新安四路198号宝立方B座一楼",
//                "深圳市福田区滨河大道9289号京基滨河时代北区4层401",
//                "深圳市龙岗区六约社区麟恒广场一楼（沃尔玛工商银行旁）",
//                "深圳市宝安区松白路汽车站配套大楼3楼（星港城对面）",
//                "深圳市龙华新区清湖村和平东路66号四季大厦1-8楼",
                "北京市朝阳区朝阳大悦城对面佳亿青年汇四层",
//                "北京市朝阳区工体北路8号院三里屯SOHO2号商场B1层B1-216",
//                "北京市朝阳区三里屯工体北路4号院66栋",
//                "北京市昌平区宏福大道温都水城广场1层",
        };
        for(String str : address){
            System.out.println(AddressUnderstanding.understanding(str).getVertexs());
        }

//        Assert.assertEquals(AddressUnderstanding.understanding("深圳市宝安区西乡街道麻布社区").getVertexs().toString(), "[深圳市/4403, 宝安区/440306, 西乡街道/440306003, 麻布社区/440306003012]");
    }
}
