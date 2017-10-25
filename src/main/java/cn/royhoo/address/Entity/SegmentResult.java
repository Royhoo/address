package cn.royhoo.address.Entity;

import cn.royhoo.address.dictionary.DivisionPlaceDictionary;
import com.hankcs.hanlp.seg.common.Vertex;
import com.hankcs.hanlp.seg.common.WordNet;

import java.util.List;

/**
 * @author royhoo
 * @date 2017/10/25
 * 分词之后得到的结果
 */
public class SegmentResult {
    /**
     * 分词后最终的顶点集
     */
    private List<Vertex> vertexs;
    /**
     * 全词词网
     */
    private WordNet wordNetAll;
    /**
     * 已经确定的，区域最具体的区划编码
     */
    private DivisionPlaceDictionary.Attribute clearDivisionAttribute;

    public List<Vertex> getVertexs() {
        return vertexs;
    }

    public void setVertexs(List<Vertex> vertexs) {
        this.vertexs = vertexs;
    }

    public WordNet getWordNetAll() {
        return wordNetAll;
    }

    public void setWordNetAll(WordNet wordNetAll) {
        this.wordNetAll = wordNetAll;
    }

    public DivisionPlaceDictionary.Attribute getClearDivisionAttribute() {
        return clearDivisionAttribute;
    }

    public void setClearDivisionAttribute(DivisionPlaceDictionary.Attribute clearDivisionAttribute) {
        this.clearDivisionAttribute = clearDivisionAttribute;
    }
}
