package cn.royhoo.address.understanding.preproccess.marking;

import cn.royhoo.address.understanding.tag.AR;
import com.hankcs.hanlp.seg.common.Vertex;

/**
 * @author royhoo
 * @date 2017/10/31
 * 区划地名角色标识类
 */
public class IdentityDivisionPlace implements IdentifyAddressRole {
    @Override
    public boolean identify(Vertex vertex) {
        if (vertex.divisionPlaceAttribute != null) return true;
        return false;
    }

    @Override
    public AR getAddressRole() {
        return AR.PlaceDivision;
    }
}
