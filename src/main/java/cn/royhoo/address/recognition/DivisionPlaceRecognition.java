package cn.royhoo.address.recognition;

import cn.royhoo.address.dictionary.DivisionPlaceDictionary;
import cn.royhoo.address.dictionary.DivisionPlacePostfixDictionary;
import com.hankcs.hanlp.collection.trie.DoubleArrayTrie;
import com.hankcs.hanlp.dictionary.CoreDictionary;
import com.hankcs.hanlp.seg.common.Vertex;
import com.hankcs.hanlp.seg.common.WordNet;
import com.hankcs.hanlp.utility.Predefine;

import java.util.List;

/**
 * @author royhoo
 * @date 2017/10/9
 * 区划地名识别
 */
public class DivisionPlaceRecognition {
    /**
     * 此处识别专注的词的ID
     */
    static final int WORD_ID = CoreDictionary.getWordID(Predefine.TAG_PLACE);
    /**
     * 此处识别专注的词的属性
     */
    static final CoreDictionary.Attribute ATTRIBUTE = CoreDictionary.get(WORD_ID);
    public static boolean Recognition(WordNet wordNetAll){
        final char[] charArray = wordNetAll.charArray;
        // 区划地名词典查询
        DoubleArrayTrie<List<DivisionPlaceDictionary.Attribute>>.Searcher searcher = DivisionPlaceDictionary.dat.getSearcher(charArray, 0);
        int lastBegin = 0;  // 上次开始位置
        int lastLength = 0;  // 上次词长
        while (searcher.next())
        {
            wordNetAll.add(searcher.begin + 1, new Vertex(Predefine.TAG_PLACE, new String(charArray, searcher.begin, searcher.length), ATTRIBUTE, WORD_ID));
            /**
             * 识别字典中不存在的区划地名。
             * 例如：字典中存在“香蜜湖街道办事处”，但是没有“香蜜湖街道”。事实上，后者也是需要识别出来的。
             */
            if(lastBegin != searcher.begin){
                // 判断上一个词之后，是否存在地名后缀
                int postfixLength = getPlacePostfixInArray(charArray, lastBegin + lastLength);
                if(postfixLength > 0){
                    wordNetAll.add(lastBegin + 1, new Vertex(Predefine.TAG_PLACE, new String(charArray, lastBegin, lastLength + postfixLength), ATTRIBUTE, WORD_ID));
                }
            }
            lastBegin = searcher.begin;
            lastLength = searcher.length;
        }
        return true;
    }

    /**
     * 在字符串数组中查找地名后缀
     * @param charArray 字符串数组
     * @param begin 起始位置
     * @return int 如果找到后缀，返回该后缀的长度，否则返回0
     */
    private static int getPlacePostfixInArray(char[] charArray, int begin){
        int length = DivisionPlacePostfixDictionary.longestPostfixLength < (charArray.length - begin) ?
                DivisionPlacePostfixDictionary.longestPostfixLength : (charArray.length - begin);
        for( ; length > 0; length--){
            if(DivisionPlacePostfixDictionary.dat.containsKey(new String(charArray, begin, length))){
                return length;
            }
        }
        return 0;
    }
}
