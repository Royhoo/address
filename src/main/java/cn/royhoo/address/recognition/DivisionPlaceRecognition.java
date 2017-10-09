package cn.royhoo.address.recognition;

import cn.royhoo.address.dictionary.DivisionPlaceDictionary;
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
     * 此次识别专注的词的ID
     */
    static final int WORD_ID = CoreDictionary.getWordID(Predefine.TAG_PLACE);
    /**
     * 此次识别专注的词的属性
     */
    static final CoreDictionary.Attribute ATTRIBUTE = CoreDictionary.get(WORD_ID);
    public static boolean Recognition(WordNet wordNetAll){
        final char[] charArray = wordNetAll.charArray;

        // 区划地名词典查询
        DoubleArrayTrie<List<DivisionPlaceDictionary.Attribute>>.Searcher searcher = DivisionPlaceDictionary.dat.getSearcher(charArray, 0);
        while (searcher.next())
        {
            wordNetAll.add(searcher.begin + 1, new Vertex(Predefine.TAG_PLACE, new String(charArray, searcher.begin, searcher.length), ATTRIBUTE, WORD_ID));
        }
        return true;
    }
}
