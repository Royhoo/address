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
        // TODO: 爬下来的数据存在乱码。例如，区划编码为360821103214的地名就是乱码。感觉很多生僻字都是乱码，有时间需要解决一下。
        page.addTargetRequests(page.getHtml().links().regex("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016.*").all());
        List<String> townInfos = page.getHtml().xpath("//tr[@class='towntr']").all();
        List<String> villageInfos = page.getHtml().xpath("//tr[@class='villagetr']").all();
        List<String[]> result = new ArrayList<String[]>();
        SpriderAddressDao spriderAddressDao = new SpriderAddressDao();
        /**
         * 解析四级区划
         */
        String regex = "html\">(\\d+)</a>[\\s\\S]*html\">(.+)</a>";
        for(String townInfo : townInfos){
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(townInfo);
            if(matcher.find()){
                String[] town = new String[2];
                town[0] = matcher.group(1).trim();
                town[1] = matcher.group(2).trim();
                result.add(town);
            }
        }
        /**
         * 解析五级数据
         */
        regex = "<td>(\\d+)[\\s\\S]*<td>(\\d+)[\\s\\S]*<td>(.+)</td>";
        for(String villageInfo : villageInfos){
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(villageInfo);
            if(matcher.find()){
                String[] village  = new String[3];
                village[0] = matcher.group(1).trim();
                village[1] = matcher.group(2).trim();
                village[2] = matcher.group(3).trim();
                result.add(village);
            }
        }
        /**
         * 将数据写入数据库
         */
        if(result.size() > 0){
            spriderAddressDao.insertTownVillageData(result);
        }

    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new TownAndVillagePageProcessor()).addUrl("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016/index.html").thread(5).run();
//        Spider.create(new TownAndVillagePageProcessor()).addUrl("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016/11/01/02/110102015.html").thread(1).run();
    }
}
