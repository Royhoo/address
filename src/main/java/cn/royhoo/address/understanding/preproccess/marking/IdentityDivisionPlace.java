package cn.royhoo.address.understanding.preproccess.marking;

/**
 * @author royhoo
 * @date 2017/10/31
 * 区划地名角色标识类
 */
public class IdentityDivisionPlace implements IdentifyAddressRole {
    @Override
    public boolean identify() {
        return false;
    }
}
