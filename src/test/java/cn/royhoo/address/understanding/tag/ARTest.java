package cn.royhoo.address.understanding.tag;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author royhoo
 * @date 2017/10/31
 */
public class ARTest {
    @Test
    public void testStartsWith() {
        Assert.assertTrue(AR.PlaceDivision.startsWith("Place"));
    }

    @Test
    public void testIsPlaceRole() {
        Assert.assertTrue(AR.PlaceRoad.isPlaceRole());
        Assert.assertTrue(!AR.ElementOther.isPlaceRole());
    }

    @Test
    public void testIsElementRole() {
        Assert.assertTrue(AR.ElementRoadPostfix.isElementRole());
    }
}
