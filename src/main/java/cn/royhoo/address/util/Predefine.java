package cn.royhoo.address.util;

/**
 * @author royhoo
 * @date 2017/10/25
 * 预定义的全局静态变量
 */
public class Predefine {
    /**
     * 前后区划匹配，计算分值时所要乘的倍率
     */
    public final static double VALUE_RATIO_MATCHED_DIFFER_ONE = 0.01;   // 前后区划匹配并且级别相差1
    public final static double VALUE_RATIO_MATCHED_DIFFER_TWO = 0.1;    // 前后区划匹配并且级别相差2
    public final static double VALUE_RATIO_UNMATCHED = 10;              // 前后区划不匹配
}
