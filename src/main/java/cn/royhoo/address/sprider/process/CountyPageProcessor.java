package cn.royhoo.address.sprider.process;

import cn.royhoo.address.sprider.dao.SpriderAddressDao;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author royhoo
 * @date 2017-09-20
 *
 * 县级区划数据爬取
 */
public class CountyPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    public void process(Page page) {
        /**
         * 解析区划数据
         */
        List<String> placeInfos = page.getHtml().xpath("//p[@class='MsoNormal']").all();
        List<String[]> places = new ArrayList<String[]>();
        SpriderAddressDao spriderAddressDao = new SpriderAddressDao();
        for(String placeInfo : placeInfos){
            // []中是一个全角空格
            String regex = "<span lang=\"EN-US\">(\\d+)<span>.*<span style=\"font-family: 宋体\">[　]*(.+)</span>";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(placeInfo);
            if(matcher.find()){
                String[] place = new String[2];
                place[0] = matcher.group(1).trim();
                place[1] = matcher.group(2).trim();
                places.add(place);
            }
        }
        spriderAddressDao.insertThreeGradeData(places);
//        page.putField("code", result);
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new CountyPageProcessor()).addUrl("http://www.stats.gov.cn/tjsj/tjbz/xzqhdm/201703/t20170310_1471429.html").run();
    }
}
