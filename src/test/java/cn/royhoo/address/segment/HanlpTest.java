package cn.royhoo.address.segment;

import com.hankcs.hanlp.HanLP;
import org.junit.Test;

/**
 * @author royhoo
 * @date 2017/9/25
 *
 * Hanlp测试类
 */
public class HanlpTest {
    @Test
    public void testSegment(){
        String[] address = {
                "深圳市福田区香蜜湖街道",
                "武汉市汉口北",
                "深圳市龙岗区龙翔街道中心城龙翔大道7188号龙岗万科广场4层（吉祥地铁站C出口）",
                "深圳市龙岗区龙翔大道龙岗中心城世贸中心3层",
                "深圳市龙岗区龙平东路278号即沃尔玛对面(君盛百货沃尔玛正门对面)",
                "深圳市龙岗区爱南路666号星河COCOPARK四楼L3C-038（星河COCOPARK里）",
                "深圳市龙岗区布吉路布吉老街时代广场3楼（肯德基楼上）",
                "深圳市龙岗区华南城舌尖美食1号街（靠近二号交易广场，一号美食街大富源会所楼下）",
                "深圳市龙岗区六约社区麟恒广场一楼（沃尔玛工商银行旁）",
                "深圳市龙华新区清湖村和平东路66号四季大厦1-8楼",
                "深圳市龙华新区和平路青年城邦商业街（近龙华地铁站C出口）",
                "深圳市龙华新区华繁路大浪商业中心二期纷享城四楼",
                "深圳市罗湖区金城大厦一楼",
                "深圳市罗湖区文锦路与爱国路并汇处俊园大厦3楼（爱国大厦公交站旁，翠竹站B2出口）",
                "深圳市罗湖区宝安北路乐尚购物广场二楼（公交站：罗湖人才市场，正后方）",
                "深圳市罗湖区东门步行街东门町美食广场三楼M021铺（近东门太阳百货）",
                "深圳市福田区香蜜湖度假村内A区停车场处（深南大道正门进入直走50米右手边）",
                "深圳市福田区石厦北二街89号马成时代广场4层408A（椰青现砍处）",
                "深圳市福田区益田路4088号香格里拉大酒店1楼（近会展中心、福田站、购物公园、中心城）",
                "深圳市福田区滨河大道9289号京基滨河时代北区4层401",
                "深圳市福田区景田南路1号橄榄绿洲商业4楼（客稻楼上）",
                "深圳市福田区振华西路田面设计之都2栋叮叮码头餐厅（中心公园公交站对面）",
                "深圳市福田区巴丁街4-1",
                "深圳市盐田区沙头角壹海城一区负一层65号（盐田区政府对面）",
                "深圳市宝安区西乡街道麻布社区海城路新海城美食街90号（坪洲地铁B出口）",
                "深圳市宝安区西乡街道西乡大道7天酒店1楼（大益广场旁）",
                "深圳市宝安区浪心村11号老乡村酒楼旁",
                "深圳市宝安区松白路汽车站配套大楼3楼（星港城对面）",
                "深圳市宝安区新安四路198号宝立方B座一楼",
                "深圳市南山区深南大道9028号华侨城威尼斯酒店旁益田假日广场3层（1号线世界之窗站A出口）",
                "深圳市南山区海德三道85号天利名城购物中心B座F3楼-08商铺（近海岸城）",
                "深圳市南山区海德一道与文心五路交叉口保利文化广场负1楼PL-B152B（家乐福里面福利彩票处）",
                "深圳市南山区南海大道2088号信和春天广场4楼（近花园城中心）",
                "深圳市南山区东滨路城市山林花园一期B-B01（近南山村）",
        };
        for(String addr : address){
            System.out.println(HanLP.segment(addr));
        }
    }
}
