package cn.royhoo.address.dictionary;

import cn.royhoo.address.Config;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author royhoo
 * @date 2017/9/27
 * 地名后缀词典
 */
public class DivisionPlacePostfixDictionary {
    private final static Logger logger = Logger.getLogger(DivisionPlaceDictionary.class);
    private final static String path = Config.DivisionPlacePostfixDictionaryPath;
    public static Map<String, List<Integer>> dat = new HashMap<>();
    public static int longestPostfixLength = 0;   // 后缀的最大长度
    /**
     * 根据级别获取相应地名后缀的字典
     */
    private static List<List<String>> datGradeToStr;

    // 加载字典
    static {
        if (!loadDictionary(path)) {
            logger.error("地名后缀词典" + path + "加载失败");
        } else {
            logger.info("地名后缀词典加载成功:" + dat.size() + "个词条。");
        }
    }

    private static boolean loadDictionary(String path){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null){
                String[] param = line.split("\\s");
                if (param[0].length() == 0) continue;   // 排除空行
                if (param[0].length() > longestPostfixLength) longestPostfixLength = param[0].length();
                List<Integer> grades = new ArrayList<>();
                for(int i = 1; i < param.length; i++){
                    grades.add(Integer.parseInt(param[i]));
                }
                dat.put(param[0], grades);
            }
            br.close();
        } catch (FileNotFoundException e) {
            logger.error("地名后缀词典" + path + "不存在！" + e);
            return false;
        } catch (IOException e) {
            logger.error("地名后缀词典" + path + "读取失败！" + e);
            return false;
        } catch (Exception e) {
            logger.error("地名后缀词典" + path + "生成失败！" + e);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 构造字典datGradeToStr，该字典可以根据级别，查询对应的地名后缀。
     */
    private static void loadDatGradeToStr(){
        datGradeToStr = new ArrayList<>();
        for (Map.Entry<String, List<Integer>> entry : dat.entrySet()) {
            String placePostfix = entry.getKey();
            List<Integer> grades = entry.getValue();
            for(int grade : grades){
                for(int j = datGradeToStr.size(); j <= grade; j++){
                    List<String> placePostfixs = new ArrayList<>();
                    datGradeToStr.add(placePostfixs);
                }
                datGradeToStr.get(grade).add(placePostfix);
                // 将所有的地名后缀添加在grade为0的位置
                datGradeToStr.get(0).add(placePostfix);
            }
            // grade为0存放的所有的地名后缀，存在重复，需要去重
            datGradeToStr.set(0, datGradeToStr.get(0).stream().distinct().collect(Collectors.toList()));
        }

        // 同一级别的地名后缀，按照长度进行倒序排序。
        for(List<String> postfixs : datGradeToStr){
            postfixs.sort((String postfix1, String postfix2) -> (postfix2.length() - postfix1.length()));
        }
    }

    /**
     * 查询地名后缀的级别
     * @param postfix 地名后缀
     * @return 地名后缀级别
     */
    public static List<Integer> getGradeByPlacePostfix(String postfix){
        return dat.get(postfix);
    }

    /**
     * 根据级别查询对应的地名后缀。如果传入的级别为0，则返回所有的地名后缀
     * @param grade 地名级别
     * @return 地名级别对应的地名后缀
     */
    public static List<String> getPlacePostfixByGrade(int grade){
        if(datGradeToStr == null){
            synchronized (DivisionPlacePostfixDictionary.class) {
                if(datGradeToStr == null){
                    loadDatGradeToStr();
                }
            }
        }

        return datGradeToStr.get(grade);
    }

    /**
     * 获取地名简称
     * @param placeName 地名
     * @param grade 地名词级别
     * @return 简称
     */
    public static List<String> getPlaceShortName(String placeName, int grade){
        List<String> shortNames = getMinorityPlaceShortName(placeName);
        if(shortNames.size() == 0){
            List<String> placePostfixs = getPlacePostfixByGrade(grade);
            for(String placePostfix : placePostfixs){
                if(placeName.endsWith(placePostfix)){
                    String shortName = placeName.substring(0, placeName.length() - placePostfix.length());
                    if(shortName.length() > 1){
                        shortNames.add(shortName);
                        break;
                    }
                }
            }
        }
        return shortNames;
    }

    public static List<String> getPlaceShortName(String placeName){
        return getPlaceShortName(placeName, 0);
    }

    /**
     * 获取少数民族自治区域地名简称
     * @param placeName 少数民族自治区域地名
     * @return 简称
     */
    public static List<String> getMinorityPlaceShortName(String placeName){
        List<String> shortNames = new ArrayList<>();
        /**
         * 首先匹配五个省级自治区
         */
        String regex = "(.{2,}?)(?:(?:[壮回]族)|(?:维吾尔族?))?自治区";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(placeName);
        if(matcher.find()){
            String shortName = matcher.group(1).trim();
            shortNames.add(shortName);
            return shortNames;
        }
        /**
         * 匹配非省级省级自治区
         */
        regex = "(.{2,}?)(?:(?:[各壮满回苗彝藏侗瑶白黎傣畲水佤羌土怒京]族)|(?:(?:维吾尔|土家|蒙古|布依|朝鲜|哈尼|哈萨克|傈僳|" +
                "仡佬|东乡|高山|拉祜|纳西|仫佬|锡伯|柯尔克孜|达斡尔|景颇|毛南|撒拉|布朗|塔吉克|阿昌|普米|鄂温克|基诺|德昂|保安|俄罗斯|" +
                "裕固|乌兹别克|门巴|鄂伦春|独龙|塔塔尔|赫哲|珞巴)族?))+.*([州县区乡镇村])";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(placeName);
        if(matcher.find()){
            String shortName = matcher.group(1).trim();
            String postfix = matcher.group(2).trim();
            shortNames.add(shortName);
            shortNames.add(shortName+postfix);
        }

        return shortNames;
    }

}
