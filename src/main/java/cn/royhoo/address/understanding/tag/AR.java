package cn.royhoo.address.understanding.tag;

/**
 * @author royhoo
 * @date 2017/10/31
 * 地址角色（Address Role）
 */
public enum AR {
    /**
     * Place开头的，是已经识别好的地名，可独立存在
     */

    /**
     * 区划地名
     */
    PlaceDivision,

    /**
     * 道路
     */
    PlaceRoad,

    /**
     * Element开通的是不能独立存在的地名元素，有待识别为地名
     */

    /**
     * 区划地名后缀
     */
    ElementDivisionPostfix,

    /**
     * 道路名后缀
     */
    ElementRoadPostfix,

    /**
     * 其他
     */
    ElementOther,

    ;

    /**
     * 地址角色是否以该前缀开头
     * @param prefix 前缀
     * @return 是否以该前缀开头
     */
    public boolean startsWith(String prefix) {
        return toString().startsWith(prefix);
    }

    /**
     * 该角色是否为地名
     * @return
     */
    public boolean isPlaceRole() {
        return startsWith("Place");
    }

    /**
     * 该角色是否为地名元素
     * @return
     */
    public boolean isElementRole() {
        return startsWith("Element");
    }
}
