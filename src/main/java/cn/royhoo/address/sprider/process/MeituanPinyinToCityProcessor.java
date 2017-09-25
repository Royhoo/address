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
 * @date 2017-09-25
 *
 * 爬取美团网上面城市对应的拼音
 */
public class MeituanPinyinToCityProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    public void process(Page page) {

        List<String> pinyin = page.getHtml().xpath("//a[@class='isonline']/@href").all();
        page.putField("pinyin", pinyin);
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new MeituanPinyinToCityProcessor()).addUrl("http://www.meituan.com/index/changecity/initiative").run();
    }
}
