package cn.royhoo.address.Entity;

import cn.royhoo.address.dictionary.DivisionPlaceDictionary;
import com.hankcs.hanlp.seg.common.Vertex;
import com.hankcs.hanlp.seg.common.WordNet;

import java.util.List;

/**
 * @author royhoo
 * @date 2017/10/25
 * 对地址进行理解之后的结果
 */
public class UnderstandingResult {
    private String address;
    private List<Vertex> vertexs;
    private WordNet wordNetAll;
    private DivisionPlaceDictionary.Attribute divisionAttribute;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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

    public DivisionPlaceDictionary.Attribute getDivisionAttribute() {
        return divisionAttribute;
    }

    public void setDivisionAttribute(DivisionPlaceDictionary.Attribute divisionAttribute) {
        this.divisionAttribute = divisionAttribute;
    }
}
