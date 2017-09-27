package cn.royhoo.address.dictionary;

import cn.royhoo.address.Config;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author royhoo
 * @date 2017/9/27
 * 地名后缀词典
 */
public class DivisionPlacePostfixDictionary {
    private final static Logger logger = Logger.getLogger(DivisionPlaceDictionary.class);
    private final static String path = Config.DivisionPlacePostfixDictionaryPath;
    private static Map<String, Integer> dat = new HashMap<>();

    // 加载字典
    static {
        if (!loadDictionary(path))
        {
            logger.error("地名后缀词典" + path + "加载失败");
        }
        else
        {
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
                dat.put(param[0], Integer.parseInt(param[1]));
            }
            br.close();
        } catch (UnsupportedEncodingException e) {
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
}
