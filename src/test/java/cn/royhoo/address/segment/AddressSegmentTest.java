package cn.royhoo.address.segment;

import cn.royhoo.address.segment.AddressSegment;
import org.junit.Test;

/**
 * @author royhoo
 * @date 2017/9/25
 *
 */
public class AddressSegmentTest {
    @Test
    public void testAddressSegment(){
        String[] address = {
                "深圳市南山区深南大道9028号华侨城威尼斯酒店旁益田假日广场3层",
                "香蜜湖街道办事处",
                "青岛市南区",
                "枣庄市中区",
                "青岛市南",
                "深圳市龙岗区布吉路布吉老街时代广场3楼",
                "深圳市福田区香蜜湖街道",
        };
        for(String str : address){
            System.out.println(AddressSegment.segment(str));
        }
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
