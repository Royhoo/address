package cn.royhoo.address.understanding.preproccess.marking;

import cn.royhoo.address.understanding.tag.AR;

/**
 * @author royhoo
 * @date 2017/10/31
 * 地址角色识别接口
 */
public interface IdentifyAddressRole {
    public AR ar = null;
    public boolean identify();
}
