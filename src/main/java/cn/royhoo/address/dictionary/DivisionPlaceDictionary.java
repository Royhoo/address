package cn.royhoo.address.dictionary;

import cn.royhoo.address.Config;
import com.hankcs.hanlp.collection.trie.DoubleArrayTrie;
import com.hankcs.hanlp.corpus.io.IOUtil;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * 区划地名词典
 * @author royhoo
 * @date 2017/9/26
 */
public class DivisionPlaceDictionary {
    private final static Logger logger = Logger.getLogger(DivisionPlaceDictionary.class);
    private final static String path = Config.DivisionPlaceDictionaryPath;
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
                List<DivisionPlaceDictionary.Attribute> attributes;
                DivisionPlaceDictionary.Attribute attribute = new DivisionPlaceDictionary.Attribute(param[0], param[1]);
                /**
                 * 如果地名不存在存在，新生成一个地名属性列表。否则，在原因的列表中追加。
                 */
                attributes = map.get(param[1]);
                if(attributes == null) attributes = new ArrayList<>();
                attributes.add(attribute);

                map.put(param[1], attributes);
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
     * 区划地名词属性
     */
    static public class Attribute implements Serializable{
        // 待完善
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

}
