package cn.royhoo.address.understanding.preproccess;

import cn.royhoo.address.understanding.preproccess.marking.IdentifyAddressRole;
import cn.royhoo.address.understanding.preproccess.marking.IdentifyElementDivisionPostfix;
import cn.royhoo.address.understanding.preproccess.marking.IdentityDivisionPlace;
import com.hankcs.hanlp.seg.common.Vertex;

import java.util.ArrayList;
import java.util.List;

/**
 * @author royhoo
 * @date 2017/10/31
 * 预处理
 */
public class PreProcess {
    private static List<IdentifyAddressRole> identifyList;

    static {
        initIdentifyList();
    }

    public static void preProcess(List<Vertex> vertices) {
        // 标识角色
        for (Vertex vertex : vertices) {
            marking(vertex);
        }
    }

    /**
     * 对节点标识地址角色
     * @param vertex
     */
    private static void marking(Vertex vertex){
        for (IdentifyAddressRole identifyAddressRole : identifyList) {
            if (identifyAddressRole.identify(vertex)) {
                vertex.ar = identifyAddressRole.getAddressRole();
            }
        }
    }

    private static void initIdentifyList(){
        identifyList = new ArrayList<>();
        identifyList.add(new IdentityDivisionPlace());
        identifyList.add(new IdentifyElementDivisionPostfix());
    }
}
