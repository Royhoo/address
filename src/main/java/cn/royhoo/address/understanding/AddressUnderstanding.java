package cn.royhoo.address.understanding;

import cn.royhoo.address.Entity.SegmentResult;
import cn.royhoo.address.Entity.UnderstandingResult;
import cn.royhoo.address.segment.AddressSegment;
import cn.royhoo.address.understanding.preproccess.PreProcess;
import com.hankcs.hanlp.seg.common.Vertex;

import java.util.List;

/**
 * @author royhoo
 * @date 2017/10/25
 * 地址理解
 */
public class AddressUnderstanding {
    public UnderstandingResult process(String address){
        UnderstandingResult understandingResult = new UnderstandingResult();
        /**
         * 地址分词
         */
        char[] text = address.toCharArray();
        AddressSegment segment = new AddressSegment();
        SegmentResult segmentResult = segment.segSentenceToVertex(text);
        List<Vertex> vertices = segmentResult.getVertexs();

        PreProcess.preProcess(vertices);

        understandingResult.setVertexs(vertices);
        understandingResult.setAddress(address);
        return understandingResult;
    }

    public static UnderstandingResult understanding(String address){
        AddressUnderstanding addressUnderstanding = new AddressUnderstanding();
        return addressUnderstanding.process(address);
    }
}
