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
                "深圳市宝安区西乡街道麻布社区",
        };
        for(String str : address){
            System.out.println(AddressUnderstanding.understanding(str).getVertexs());
        }

        Assert.assertEquals(AddressUnderstanding.understanding("深圳市宝安区西乡街道麻布社区").getVertexs().toString(), "[深圳市/4403, 宝安区/440306, 西乡街道/440306003, 麻布社区/440306003012]");
    }
}
