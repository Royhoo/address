package cn.royhoo.address.segment;

import cn.royhoo.address.segment.AddressSegment;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author royhoo
 * @date 2017/9/25
 *
 */
public class AddressSegmentTest {
    @Test
    public void testAddressSegment(){
        // TODO:下一步要进行地名和地名关键字的合并了
        String[] address = {
                "深圳市南山区深南大道9028号华侨城威尼斯酒店旁益田假日广场3层",
                "深圳市龙岗区布吉路布吉老街时代广场3楼",
        };
        for(String str : address){
            System.out.println(AddressSegment.segment(str));
        }
    }

    /**
     * 测试区划地址分词效果
     */
    @Test
    public void testDivisionPlaceSegment(){
        // TODO:下一步要进行地名和地名关键字的合并了
        String[] address = {
                "西藏省",
                //TODO:“西乡-街道”是个特例，没有合并。是因为“西乡”本身有地名结尾词。
                "深圳市宝安区西乡街道麻布社区",
                "深圳市宝安区西乡麻布社区",
                "香蜜湖街道",
                "深圳市福田区香蜜湖街道侨香社区",
                "深圳市福田区香蜜湖街道办事处侨香社区",
                "深圳市福田区香蜜湖街道办事处侨香社区居委会",
                "深圳市南山",
                "深圳市福田区香蜜湖街道",
                "青岛市南",
                "深圳市南",
                "青岛市南区",
                "香蜜湖街道办事处",
                "枣庄市中区",
        };
        for(String str : address){
            System.out.println(AddressSegment.segment(str));
        }
        Assert.assertEquals(AddressSegment.segment("深圳市南").toString(), "[深圳市/4403, 南]");
        Assert.assertEquals(AddressSegment.segment("青岛市南").toString(), "[青岛/3702, 市南/370202]");
    }

    /**
     * 测试具有同义后缀的地名词。
     * 例如，字典中存在“香蜜湖街道办事处”而没有“香蜜湖街道”，需要识别出后者也是一个词，并且区划信息与前者一致。
     */
    @Test
    public void testSynonymPostfix(){
        String[] address = {
                "香蜜湖街道",
                "香蜜湖街道办",
                "大木罗村",
                "大木罗社区",
        };
        for(String str : address){
            System.out.println(AddressSegment.segment(str));
        }
    }
}
