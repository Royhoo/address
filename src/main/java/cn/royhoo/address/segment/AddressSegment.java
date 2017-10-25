package cn.royhoo.address.segment;

import cn.royhoo.address.Entity.SegmentResult;
import cn.royhoo.address.dictionary.DivisionPlaceDictionary;
import cn.royhoo.address.recognition.DivisionPlaceRecognition;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.recognition.nr.PersonRecognition;
import com.hankcs.hanlp.recognition.ns.PlaceRecognition;
import com.hankcs.hanlp.recognition.nt.OrganizationRecognition;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.seg.common.Vertex;
import com.hankcs.hanlp.seg.common.WordNet;
import com.hankcs.hanlp.seg.Viterbi.ViterbiSegment;

import java.util.LinkedList;
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
     */
    public SegmentResult segSentenceToVertex(char[] sentence)
    {
        SegmentResult segmentResult = new SegmentResult();
        WordNet wordNetAll = new WordNet(sentence);
        GenerateWordNet(wordNetAll);
        if (HanLP.Config.DEBUG)
        {
            System.out.printf("粗分词网：\n%s\n", wordNetAll);
        }

        // 区划地名识别
        DivisionPlaceRecognition.Recognition(wordNetAll);


        List<Vertex> vertexList = divisionPlaceViterbi(wordNetAll, segmentResult);

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

        vertexList.remove(0);
        vertexList.remove(vertexList.size() - 1);
        segmentResult.setWordNetAll(wordNetAll);
        segmentResult.setVertexs(vertexList);
        return segmentResult;
    }

    // 增加了区划地名识别的Viterbi
    protected static List<Vertex> divisionPlaceViterbi(WordNet wordNet, SegmentResult segmentResult){
        LinkedList<Vertex> nodes[] = wordNet.getVertexes();
        LinkedList<Vertex> vertexList = new LinkedList<Vertex>();
        DivisionPlaceDictionary.Attribute clearDivisionAttribute = null;  // 已明确的，最具体的区划信息
        for (Vertex node : nodes[1])
        {
            // 如果首词具有一级地名词属性，直接设置区划属性为该一级地名词的区划属性
            List<DivisionPlaceDictionary.Attribute> firstGradeAttributes = DivisionPlaceDictionary.getAttributesByGrade(node.maybeDivisionPlaceAttributes, 1);
            if (firstGradeAttributes.size() > 0){
                node.divisionPlaceAttribute = firstGradeAttributes.get(0);
                clearDivisionAttribute = firstGradeAttributes.get(0);
            } else{
                // 如果首词具有二级地名词属性，直接设置区划属性为该二级地名词的区划属性
                List<DivisionPlaceDictionary.Attribute> secondGradeAttributes = DivisionPlaceDictionary.getAttributesByGrade(node.maybeDivisionPlaceAttributes, 2);
                if (secondGradeAttributes.size() > 0){
                    node.divisionPlaceAttribute = secondGradeAttributes.get(0);
                    clearDivisionAttribute = secondGradeAttributes.get(0);
                }
            }
            // 如果当前节点只有一个区划编码，说明该节点的区划编码是确定的，可以对该节点赋予区划编码。（比如“广东省”这个词，不需要上下文环境，足以确定唯一的区划编码）
//            if (node.divisionPlaceAttribute == null && node.maybeDivisionPlaceAttributes != null
//                    && node.maybeDivisionPlaceAttributes.size() == 1 && node.divisionPlaceAttribute == null){
//                /**
//                 * 需要加一个限制：改词是一二三级地名词的话，才设置区划
//                 */
//                int placeGrade = node.maybeDivisionPlaceAttributes.get(0).placeGrade;
//                if (placeGrade >= 1 && placeGrade <= 3){
//                    node.divisionPlaceAttribute = node.maybeDivisionPlaceAttributes.get(0);
//                    clearDivisionAttribute = node.maybeDivisionPlaceAttributes.get(0);
//                }
//            }
            node.updateFrom(nodes[0].getFirst());
        }
        for (int i = 1; i < nodes.length - 1; ++i)
        {
            LinkedList<Vertex> nodeArray = nodes[i];
            if (nodeArray == null) continue;
            for (Vertex node : nodeArray)
            {
                if (node.divisionPlaceAttribute == null && node.maybeDivisionPlaceAttributes != null && node.maybeDivisionPlaceAttributes.size() == 1){
                    /**
                     * 需要加一个限制：改词是一二三级地名词的话，才设置区划
                     */
                    int placeGrade = node.maybeDivisionPlaceAttributes.get(0).placeGrade;
                    if (placeGrade >= 1 && placeGrade <= 3){
                        node.divisionPlaceAttribute = node.maybeDivisionPlaceAttributes.get(0);
                        clearDivisionAttribute = node.maybeDivisionPlaceAttributes.get(0);
                    }
                }
                if (node.from == null) continue;
                for (Vertex to : nodes[i + node.realWord.length()])
                {
                    clearDivisionAttribute = to.updateFromWithDivisionPlaceRelation(node, clearDivisionAttribute);
                }
            }
        }
        Vertex from = nodes[nodes.length - 1].getFirst();
        while (from != null)
        {
            vertexList.addFirst(from);
            from = from.from;
        }
        segmentResult.setClearDivisionAttribute(clearDivisionAttribute);
        return vertexList;
    }

    public static SegmentResult segment(String address){
        char[] text = address.toCharArray();
        AddressSegment segment = new AddressSegment();
        return segment.segSentenceToVertex(text);
    }
}
