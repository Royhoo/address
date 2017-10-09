package cn.royhoo.address.dictionary;

import cn.royhoo.address.Config;
import com.hankcs.hanlp.collection.trie.DoubleArrayTrie;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * 区划地名词典
 * @author royhoo
 * @date 2017/9/26
 */
public class DivisionPlaceDictionary {
    private final static Logger logger = Logger.getLogger(DivisionPlaceDictionary.class);
    /**
     * 区划地名词典路径
     */
    private final static String path = Config.DivisionPlaceDictionaryPath;
    /**
     * 区划地名词典
     */
    public static DoubleArrayTrie<List<DivisionPlaceDictionary.Attribute>> dat = new DoubleArrayTrie<>();

    // 自动加载词典
    static {
        long start = System.currentTimeMillis();
        if (!loadDivisionPlaceDictionary(path)) {
            logger.error("区划地名词典" + path + "加载失败");
        } else {
            logger.info("区划地名词典加载成功:" + dat.size() + "个词条，耗时" + (System.currentTimeMillis() - start) + "ms");
        }
    }

    private static boolean loadDivisionPlaceDictionary(String path){
        logger.info("区划地名词典开始加载:" + path);
        try {
            TreeMap<String, List<DivisionPlaceDictionary.Attribute>> map = new TreeMap<>();
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null){
                String[] param = line.split("\\s");
                if (param[0].length() == 0) continue;   // 排除空行
                DivisionPlaceDictionary.Attribute attribute = new DivisionPlaceDictionary.Attribute(param[0], param[1]);
                /**
                 * 将地名全称加入map
                 */
                addPlaceToMap(param[1], attribute, map);
                /**
                 * 如果该地名存在简称，将简称加入map
                 */
                List<String> shortNames = DivisionPlacePostfixDictionary.getPlaceShortName(param[1], attribute.placeGrade);
                for(String shortName : shortNames){
                    addPlaceToMap(shortName, attribute, map);
                }
            }
            br.close();
            if(map.size() == 0){
                logger.error("区划地名词典" + path + "未地区到任何数据！");
                return false;
            }
            dat.build(map);
        } catch (FileNotFoundException e){
            logger.error("区划地名词典" + path + "不存在！" + e);
            return false;
        } catch (IOException e) {
            logger.error("区划地名词典" + path + "读取失败！" + e);
            return false;
        } catch (Exception e) {
            logger.error("区划地名词典" + path + "生成失败！" + e);
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private static void addPlaceToMap(String placeName, DivisionPlaceDictionary.Attribute attribute,
                               TreeMap<String, List<DivisionPlaceDictionary.Attribute>> map) {
        /**
         * 如果地名不存在存在，新生成一个地名属性列表。否则，在该地名原有的属性列表中追加。
         */
        List<DivisionPlaceDictionary.Attribute> attributes = map.get(placeName);
        if(attributes == null) attributes = new ArrayList<>();
        attributes.add(attribute);
        map.put(placeName, attributes);
    }

    /**
     * 根据区划编码获取地名级别
     * @param placeCode 区划编码
     * @return 地名级别
     */
    public static int getPlaceGradeByCode(String placeCode){
        if(placeCode == null) return 0;
        int length = placeCode.length();
        switch (length){
            case 2:
                return 1;
            case 4:
                return 2;
            case 6:
                return 3;
            case 9:
                return 4;
            case 12:
                return 5;
        }
        return 0;
    }

    /**
     * 获得地名对应的地名词属性
     * @param placeName 地名词
     * @return 地名词属性
     */
    public static List<DivisionPlaceDictionary.Attribute> getPlaceAttributeByName(String placeName){
        List<DivisionPlaceDictionary.Attribute> result = new ArrayList<>();
        int uniqueGrade = 0;
        /**
         * 获取全称对应的地名属性
         */
        List<DivisionPlaceDictionary.Attribute> fullNameAttribute = dat.get(placeName);
        if(fullNameAttribute != null && fullNameAttribute.size() > 0) {
            result = fullNameAttribute;
            /**
             * 判断该地名的级别是否唯一。如果级别唯一，则将地名级别赋给uniqueGrade。否则，uniqueGrade保持初始值0。
             */
            int placeCode = fullNameAttribute.get(0).placeGrade;
            for (int i = 1; i < fullNameAttribute.size(); i++){
                if(placeCode != fullNameAttribute.get(i).placeGrade){
                    placeCode = 0;
                    break;
                }
            }
            uniqueGrade = placeCode;
        }
        /**
         * 找到地名对应的最短简称，并查询该简称对应的地名属性
         */
        List<String> shortNames = DivisionPlacePostfixDictionary.getPlaceShortName(placeName);
        if(shortNames != null && shortNames.size() > 0){
            shortNames.sort((String s1, String s2) -> (s1.length() - s2.length()));
            String shortestName = shortNames.get(0);
            List<DivisionPlaceDictionary.Attribute> shortNameAttribute = dat.get(shortestName);
            if(shortNameAttribute != null && shortNameAttribute.size() > 0) {
                if(uniqueGrade > 0){
                    /**
                     * 根据全称查到的地名信息，地名级别是唯一的，返回结果中只保留与该级别匹配的地名
                     * 例如：查询“广东省”对应的地名，查询结果中不应存在“广东村”这种数据
                     */
                    for(DivisionPlaceDictionary.Attribute attribute : shortNameAttribute){
                        if(uniqueGrade == attribute.placeGrade) result.add(attribute);
                    }
                } else{
                    result.addAll(shortNameAttribute);
                }
            }
        }

        return result.stream().distinct().collect(Collectors.toList());
    }

    /**
     * 区划地名词属性
     */
    static public class Attribute implements Serializable{
        /**
         * 区划编码
         */
        public String placeCode;
        /**
         * 地名全称
         */
        public String placeName;
        /**
         * 地名级别
         */
        public int placeGrade;

        public Attribute(String placeCode, String placeName, int placeGrade){
            this.placeCode = placeCode;
            this.placeName = placeName;
            this.placeGrade = placeGrade;
        }

        public Attribute(String placeCode, String placeName){
            this(placeCode, placeName, getPlaceGradeByCode(placeCode));
        }

        public String toString(){
            return placeCode + " " + placeName + " " + placeGrade;
        }

    }

    /**
     * 区划地名搜索工具
     */
    public class DivisionPlaceSearcher{
        /**
         * 传入的字符数组
         */
        private char[] charArray;
        /**
         * 起始位置
         */
        private int offset;
        private DoubleArrayTrie<List<DivisionPlaceDictionary.Attribute>>.Searcher searcher;

        public DivisionPlaceSearcher(int offset, char[] charArray){
            this.offset = offset;
            this.charArray = charArray;
            this.searcher = dat.getSearcher(charArray, offset);
        }

        /**
         * 取下一个命中输出
         */
        public boolean next(){
            while (searcher.next()){

            }
            return false;
        }
    }

    public DivisionPlaceSearcher getSearcher(char[] text, int offset)
    {
        return new DivisionPlaceSearcher(offset, text);
    }

}
