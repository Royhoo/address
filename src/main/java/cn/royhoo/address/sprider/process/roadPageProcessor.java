package cn.royhoo.address.sprider.process;

import cn.royhoo.address.sprider.dao.SpriderAddressDao;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * @author royhoo
 * @date 2017-09-22
 *
 * 为爬去道路数据，先爬去各城市对应的拼音
 */
public class roadPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
    private SpriderAddressDao spriderAddressDao = new SpriderAddressDao();

    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("http://.+.cityhouse.cn/information/newstreet/type/all/page.*").all());
//        page.addTargetRequests(page.getHtml().links().regex("http://(.+).cityhouse.cn/information/newstreet(.*)").all());
        String city = page.getHtml().xpath("//h2[@class='p_tith3']/text()").get();
        List<String> roads = page.getHtml().xpath("//ul[@class='street_name']/li/a/text()").all();

        if(roads.size() > 0){
            spriderAddressDao.insertRoadData(roads, city);
        }
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider spider = Spider.create(new roadPageProcessor());
        String pinyin[] = getCityPinyin();
        for(int i = 0; i < pinyin.length; i++){
            String url = "http://"+pinyin[i]+".cityhouse.cn/information/newstreet.html";
            spider.addUrl(url);
        }
        spider.thread(10).run();
    }

    /**
     * 为爬取所有城市的道路，已从[http://sz.cityhouse.cn/market/ajaxselectcity.html?distgotourl=&gotourl=]拿到了城市对应的拼音
     */
    public static String[] getCityPinyin(){
        return new String[]{
                "ab",
                "aks",
                "ale",
                "als",
                "alt",
                "al",
                "ak",
                "aq",
                "anshun",
                "ay",
                "as",
                "byne",
                "bazhong",
                "bazhou",
                "bc",
                "baisha",
                "baishan",
                "by",
                "baise",
                "bengbu",
                "bt",
                "baoji",
                "bd",
                "bs",
                "baoting",
                "bh",
                "bj",
                "beitun",
                "bx",
                "bijie",
                "bz",
                "bozhou",
                "bozhouxj",
                "cangzhou",
                "changdu",
                "cj",
                "changjiang",
                "cc",
                "cs",
                "changzhi",
                "changde",
                "cz",
                "cy",
                "chaozhou",
                "chenzhou",
                "cd",
                "chengde",
                "cm",
                "chizhou",
                "cf",
                "chongzuo",
                "chuzhou",
                "cx",
                "cq",
                "dazhou",
                "dali",
                "dl",
                "dq",
                "dt",
                "dxal",
                "dd",
                "danzhou",
                "dh",
                "deyang",
                "dz",
                "diqing",
                "da",
                "dx",
                "df",
                "dg",
                "dy",
                "eds",
                "ez",
                "es",
                "fcg",
                "fs",
                "fz",
                "fushun",
                "fuzhou",
                "fx",
                "fy",
                "gn",
                "ganzi",
                "ganzhou",
                "guyuan",
                "ga",
                "guangyuan",
                "gz",
                "gg",
                "gy",
                "gl",
                "guoluo",
                "hm",
                "hb",
                "haibei",
                "haidong",
                "hk",
                "hnz",
                "hx",
                "hd",
                "hanzhong",
                "hz",
                "hf",
                "ht",
                "hc",
                "heyuan",
                "heze",
                "hezhou",
                "hebi",
                "hegang",
                "heihe",
                "hengshui",
                "hy",
                "honghe",
                "hh",
                "hlbe",
                "huzhou",
                "hld",
                "huaihua",
                "ha",
                "huaibei",
                "hn",
                "hg",
                "huangnan",
                "huangshan",
                "hs",
                "huizhou",
                "jixi",
                "ja",
                "jl",
                "jn",
                "jining",
                "jiyuan",
                "jms",
                "jx",
                "jyg",
                "jm",
                "jiaozuo",
                "jy",
                "jinchang",
                "jh",
                "jz",
                "jc",
                "jinzhong",
                "jingmen",
                "jingzhou",
                "jdz",
                "jj",
                "jq",
                "ks",
                "kf",
                "kkdl",
                "klmy",
                "kz",
                "km",
                "ky",
                "lasa",
                "lb",
                "lw",
                "lz",
                "lf",
                "ledong",
                "leshan",
                "lj",
                "ls",
                "lyg",
                "liangshan",
                "liaoyang",
                "liaoyuan",
                "lc",
                "linzhi",
                "lincang",
                "linfen",
                "lg",
                "lx",
                "linyi",
                "lingshui",
                "liuzhou",
                "la",
                "lps",
                "longyan",
                "ln",
                "ld",
                "luzhou",
                "ll",
                "ly",
                "lh",
                "mas",
                "mm",
                "ms",
                "mz",
                "my",
                "mdj",
                "neijiang",
                "nq",
                "nc",
                "nanchong",
                "nj",
                "nn",
                "np",
                "nt",
                "ny",
                "nb",
                "nd",
                "nujiang",
                "pzh",
                "pj",
                "pds",
                "pl",
                "px",
                "pt",
                "py",
                "pe",
                "qth",
                "qq",
                "qianjiang",
                "qdn",
                "qn",
                "qxn",
                "qinzhou",
                "qhd",
                "qd",
                "qy",
                "qingyang",
                "qh",
                "qiongzhong",
                "qj",
                "quzhou",
                "qz",
                "rkz",
                "rz",
                "smx",
                "sm",
                "ss",
                "sanya",
                "shannan",
                "st",
                "sw",
                "sl",
                "shangqiu",
                "sh",
                "sr",
                "sg",
                "shaoyang",
                "sx",
                "sz",
                "snj",
                "sy",
                "shiyan",
                "shz",
                "sj",
                "szs",
                "shuanghe",
                "sys",
                "shuozhou",
                "sp",
                "songyuan",
                "su",
                "sq",
                "suzhouah",
                "suihua",
                "suizhou",
                "sn",
                "tacheng",
                "tz",
                "ty",
                "taian",
                "taizhoujs",
                "ts",
                "tj",
                "tm",
                "tianshui",
                "tieling",
                "tmg",
                "th",
                "tl",
                "tc",
                "tongling",
                "tr",
                "tmsk",
                "tlf",
                "tunchang",
                "wanning",
                "weihai",
                "wf",
                "wn",
                "wz",
                "wc",
                "ws",
                "wuhai",
                "wlcb",
                "wl",
                "wx",
                "wuzhong",
                "wuhu",
                "wuzhou",
                "wjq",
                "wzs",
                "wh",
                "ww",
                "xa",
                "xn",
                "xsbn",
                "xlgl",
                "xm",
                "xiantao",
                "xianning",
                "xianyang",
                "xiangtan",
                "xx",
                "xy",
                "xg",
                "xinzhou",
                "xinxiang",
                "xinyu",
                "xinyang",
                "xingan",
                "xt",
                "xz",
                "xc",
                "xuancheng",
                "yaan",
                "yt",
                "ya",
                "yanbian",
                "yancheng",
                "yz",
                "yj",
                "yq",
                "yichun",
                "yili",
                "yb",
                "yichang",
                "yichunjx",
                "yiyang",
                "yc",
                "yingtan",
                "yk",
                "yongzhou",
                "yl",
                "yulin",
                "ys",
                "yx",
                "yy",
                "yf",
                "yuncheng",
                "zaozhuang",
                "zj",
                "zjj",
                "zjk",
                "zhangye",
                "zhangzhou",
                "zt",
                "zq",
                "zhenjiang",
                "zz",
                "zhongshan",
                "zw",
                "zs",
                "zk",
                "zhuzhou",
                "zh",
                "zmd",
                "ziyang",
                "zb",
                "zg",
                "zy",
        };
    }
}