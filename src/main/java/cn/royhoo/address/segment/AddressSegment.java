package cn.royhoo.address.segment;

import cn.royhoo.address.recognition.DivisionPlaceRecognition;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.other.CharTable;
import com.hankcs.hanlp.recognition.nr.JapanesePersonRecognition;
import com.hankcs.hanlp.recognition.nr.PersonRecognition;
import com.hankcs.hanlp.recognition.nr.TranslatedPersonRecognition;
import com.hankcs.hanlp.recognition.ns.PlaceRecognition;
import com.hankcs.hanlp.recognition.nt.OrganizationRecognition;
import com.hankcs.hanlp.seg.WordBasedGenerativeModelSegment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.seg.common.Vertex;
import com.hankcs.hanlp.seg.common.WordNet;
import com.hanks.hanlp.seg.Viterbi.ViterbiSegment;

import java.util.List;

/**
 * @author royhoo
 * @date 2017/9/25
 *
 * 地址分词，核心使用Hanlp的Viterbi分词器
 */
public class AddressSegment extends ViterbiSegment {
    /**
     * Hanlp自带的segSentence方法返回的是List<Term>，所含信息太少，不利于进一步优化。所以增加一个方法，返回List<Vertex>。
     * add by royhoo
     */
    public List<Vertex> segSentenceToVertex(char[] sentence)
    {
        WordNet wordNetAll = new WordNet(sentence);
        GenerateWordNet(wordNetAll);
        if (HanLP.Config.DEBUG)
        {
            System.out.printf("粗分词网：\n%s\n", wordNetAll);
        }

        // 区划地名识别
        DivisionPlaceRecognition.Recognition(wordNetAll);


        List<Vertex> vertexList = viterbi(wordNetAll);

        if (config.useCustomDictionary)
        {
            if (config.indexMode)
                combineByCustomDictionary(vertexList, wordNetAll);
            else combineByCustomDictionary(vertexList);
        }

        if (HanLP.Config.DEBUG)
        {
            System.out.println("粗分结果" + convert(vertexList, false));
        }

        // 数字识别
        if (config.numberQuantifierRecognize)
        {
            mergeNumberQuantifier(vertexList, wordNetAll, config);
        }

        // 实体命名识别
        if (config.ner)
        {
            WordNet wordNetOptimum = new WordNet(sentence, vertexList);
            int preSize = wordNetOptimum.size();
            if (config.nameRecognize)
            {
                PersonRecognition.Recognition(vertexList, wordNetOptimum, wordNetAll);
            }
            if (config.placeRecognize)
            {
                PlaceRecognition.Recognition(vertexList, wordNetOptimum, wordNetAll);
            }
            if (config.organizationRecognize)
            {
                // 层叠隐马模型——生成输出作为下一级隐马输入
                vertexList = viterbi(wordNetOptimum);
                wordNetOptimum.clear();
                wordNetOptimum.addAll(vertexList);
                preSize = wordNetOptimum.size();
                OrganizationRecognition.Recognition(vertexList, wordNetOptimum, wordNetAll);
            }
            if (wordNetOptimum.size() != preSize)
            {
                vertexList = viterbi(wordNetOptimum);
                if (HanLP.Config.DEBUG)
                {
                    System.out.printf("细分词网：\n%s\n", wordNetOptimum);
                }
            }
        }

        // 是否标注词性
        if (config.speechTagging)
        {
            speechTagging(vertexList);
        }

        return vertexList;
    }

    public static List<Term> segment(String address){
        char[] text = address.toCharArray();
        AddressSegment segment = new AddressSegment();
        List<Vertex> vertexs = segment.segSentenceToVertex(text);
        /**
         * 针对地址的优化
         */

        return convert(vertexs);
    }
}
