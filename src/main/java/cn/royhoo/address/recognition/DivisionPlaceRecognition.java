package cn.royhoo.address.recognition;

import cn.royhoo.address.dictionary.DivisionPlaceDictionary;
import com.hankcs.hanlp.collection.trie.DoubleArrayTrie;
import com.hankcs.hanlp.corpus.tag.Nature;
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
        while (searcher.next())
        {
            List<DivisionPlaceDictionary.Attribute> DivisionPlaceAttribute = searcher.value;
            int grade = DivisionPlaceDictionary.getPlaceGradeFromAttributes(DivisionPlaceAttribute).get(0);
            wordNetAll.add(searcher.begin + 1, new Vertex(Predefine.TAG_PLACE, new String(charArray, searcher.begin, searcher.length), getAttributeByGrade(grade), WORD_ID));
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
}
