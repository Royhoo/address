package cn.royhoo.address.understanding.preproccess.marking;

import cn.royhoo.address.understanding.tag.AR;
import com.hankcs.hanlp.seg.common.Vertex;

/**
 * @author royhoo
 * @date 2017/10/31
 * 地址角色识别接口
 */
public interface IdentifyAddressRole {
    public boolean identify(Vertex vertex);
    public AR getAddressRole();
}
