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
 * @date 2017-09-21
 *
 * 四五级区划数据爬取
 */
public class TownAndVillagePageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    public void process(Page page) {
//        List<String> provicesInfo = page.getHtml().xpath("//tr[@class='provincetr']").all();
        page.putField("url", page.getHtml().links().regex("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016.*").all());

    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new TownAndVillagePageProcessor()).addUrl("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016/index.html").run();
    }
}
