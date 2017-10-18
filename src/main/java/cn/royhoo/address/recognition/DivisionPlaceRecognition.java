package cn.royhoo.address.recognition;

import cn.royhoo.address.dictionary.DivisionPlaceDictionary;
import cn.royhoo.address.dictionary.DivisionPlacePostfixDictionary;
import com.hankcs.hanlp.collection.trie.DoubleArrayTrie;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.dictionary.CoreDictionary;
import com.hankcs.hanlp.seg.common.Vertex;
import com.hankcs.hanlp.seg.common.WordNet;
import com.hankcs.hanlp.utility.Predefine;

import java.util.ArrayList;
import java.util.List;

/**
 * @author royhoo
 * @date 2017/10/9
 * 区划地名识别
 */
public class DivisionPlaceRecognition {
    /**
     * 此次识别专注的词的ID
     */
    static final int WORD_ID = CoreDictionary.getWordID(Predefine.TAG_PLACE);
    /**
     * 此次识别专注的词的属性
     */
    static CoreDictionary.Attribute[] ATTRIBUTES;
    public static boolean Recognition(WordNet wordNetAll){
        final char[] charArray = wordNetAll.charArray;

        // 区划地名词典查询
        DoubleArrayTrie<List<DivisionPlaceDictionary.Attribute>>.Searcher searcher = DivisionPlaceDictionary.dat.getSearcher(charArray, 0);
        int lastBegin = 0;  // 上一个词的开始位置
        int lastLength = 0;  // 上一个词的词长
        List<DivisionPlaceDictionary.Attribute> lastAttributes = null;  // 上一个词的属性值
        while (searcher.next())
        {
            List<DivisionPlaceDictionary.Attribute> divisionPlaceAttributes = searcher.value;
            int grade = DivisionPlaceDictionary.getPlaceGradeFromAttributes(divisionPlaceAttributes).get(0);
            wordNetAll.addOptimizeOld(searcher.begin + 1, new Vertex(Predefine.TAG_PLACE, new String(charArray, searcher.begin, searcher.length), getAttributeByGrade(grade), WORD_ID, divisionPlaceAttributes));
            
            if (lastBegin != searcher.begin && (searcher.begin + searcher.length < charArray.length)){
                /**
                 * 如果上一个区划词没有后缀，则需要判断改词之后是否存在后缀，以及上一区划词能否与其后的后缀合并。
                 * 例如，区划词典加载了“香蜜湖”，但是没加载“香蜜湖街道”。下面的代码就是要识别出“香蜜湖街道”。
                 */
                if (DivisionPlacePostfixDictionary.getDivisionPlacePostfix(new String(charArray, lastBegin, lastLength)) == null){  // 上一区划词不含后缀
                    String behindStr = new String(charArray, lastBegin + lastLength, charArray.length - lastBegin - lastLength);
                    List<Integer> postfixInfo = DivisionPlacePostfixDictionary.getStartsWithPostfixGrade(behindStr);
                    List<DivisionPlaceDictionary.Attribute> newDivisionPlaceAttributes = getAttributeMatchedPostfix(charArray, lastBegin, lastLength, lastAttributes);
                    if (newDivisionPlaceAttributes.size() > 0){
                        wordNetAll.addOptimizeOld(lastBegin + 1, new Vertex(Predefine.TAG_PLACE,
                                new String(charArray, lastBegin, lastLength + postfixInfo.get(0)), getAttributeByGrade(postfixInfo.get(1)), WORD_ID, newDivisionPlaceAttributes));
                    }
                }
                lastBegin = searcher.begin;
            }
            lastLength = searcher.length;
            lastAttributes = searcher.value;
        }

        if (lastAttributes != null && (lastBegin + lastLength < charArray.length)){
            /**
             * 如果上一个区划词没有后缀，则需要判断改词之后是否存在后缀，以及上一区划词能否与其后的后缀合并。
             * 例如，区划词典加载了“香蜜湖”，但是没加载“香蜜湖街道”。下面的代码就是要识别出“香蜜湖街道”。
             */
            if (DivisionPlacePostfixDictionary.getDivisionPlacePostfix(new String(charArray, lastBegin, lastLength)) == null){  // 上一区划词不含后缀
                String behindStr = new String(charArray, lastBegin + lastLength, charArray.length - lastBegin - lastLength);
                List<Integer> postfixInfo = DivisionPlacePostfixDictionary.getStartsWithPostfixGrade(behindStr);
                List<DivisionPlaceDictionary.Attribute> newDivisionPlaceAttributes = getAttributeMatchedPostfix(charArray, lastBegin, lastLength, lastAttributes);
                if (newDivisionPlaceAttributes.size() > 0){
                    wordNetAll.addOptimizeOld(lastBegin + 1, new Vertex(Predefine.TAG_PLACE,
                            new String(charArray, lastBegin, lastLength + postfixInfo.get(0)), getAttributeByGrade(postfixInfo.get(1)), WORD_ID, newDivisionPlaceAttributes));
                }
            }
        }
        return true;
    }

    /**
     * 根据级别获取地名词属性
     */
    public static CoreDictionary.Attribute getAttributeByGrade(int grade){
        if (ATTRIBUTES == null){
            ATTRIBUTES = new CoreDictionary.Attribute[5];
            for (int i = 0; i < 5; i++){
                ATTRIBUTES[i] = new CoreDictionary.Attribute(Nature.ns, getFrequencyByGrade(i + 1));
            }
        }

        return ATTRIBUTES[grade - 1];
    }

    /**
     * 各级别的区划地名对应的词频
     */
    public static int getFrequencyByGrade(int grade){
        switch (grade){
            case 1:
                return 1000;
            case 2:
                return 500;
            case 3:
                return 100;
            case 4:
                return 10;
            case 5:
                return 1;
        }
        return 0;
    }

    /**
     * 从上次已识别出区划地名的位置开始，识别出地名后缀。由后缀跟之前的地名合并成新的地名。
     * @param charArray 待分词字符串生成的char数组
     * @param lastBegin 上次识别出区划地名的开始位置
     * @param lastLength 上次识别出的区划地名的长度
     * @param lastAttributes 上次识别出的区划地名的属性
     * @return 组合出来的新地名的属性
     */
    private static List<DivisionPlaceDictionary.Attribute> getAttributeMatchedPostfix(char[] charArray, int lastBegin,
                    int lastLength, List<DivisionPlaceDictionary.Attribute> lastAttributes){
        List<DivisionPlaceDictionary.Attribute> newDivisionPlaceAttributes = new ArrayList<>();
        String behindStr = new String(charArray, lastBegin + lastLength, charArray.length - lastBegin - lastLength);
        List<Integer> postfixInfo = DivisionPlacePostfixDictionary.getStartsWithPostfixGrade(behindStr);
        if (postfixInfo != null && postfixInfo.size() > 0){
            /**
             * 识别出的新地名的属性。该地名的属性级别，需要与后缀的级别一致。
             */
            for (int i = 1; i < postfixInfo.size(); i++){
                int postfixGrade = postfixInfo.get(i);
                for (DivisionPlaceDictionary.Attribute attribute : lastAttributes){
                    if (postfixGrade == attribute.placeGrade) newDivisionPlaceAttributes.add(attribute);
                }
            }
        }
        return newDivisionPlaceAttributes;
    }
}
