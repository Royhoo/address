package cn.royhoo.address.understanding.preproccess.marking;

import cn.royhoo.address.dictionary.DivisionPlacePostfixDictionary;
import cn.royhoo.address.understanding.tag.AR;
import com.hankcs.hanlp.seg.common.Vertex;

/**
 * @author royhoo
 * @date 2017/11/1
 * 请添加描述
 */
public class IdentifyElementDivisionPostfix implements IdentifyAddressRole {
    @Override
    public boolean identify(Vertex vertex) {
        if (DivisionPlacePostfixDictionary.dat.containsKey(vertex.getRealWord())){
            return true;
        }
        return false;
    }

    @Override
    public AR getAddressRole() {
        return AR.ElementDivisionPostfix;
    }
}
