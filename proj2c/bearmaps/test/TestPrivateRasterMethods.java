package bearmaps.test;

import bearmaps.proj2c.server.handler.impl.RasterAPIHandler;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.StringJoiner;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestPrivateRasterMethods {

    /**
     * Tests the findLonDPP method of the RasterAPIHandler class.
     * Uses the test.html file example of depth 7, the test1234.html file of depth 1,
     * and the testTwelveImages.html file of depth 2.
     */
    @Test
    public void testFindLonDPP() {
        RasterAPIHandler raster = new RasterAPIHandler();
        //test.html test
        double testLRlon = -122.24053369025242;
        double testULlon = -122.24163047377972;
        double testWPixel = 892.0;
        int expected = 7;
        int actual = raster.findDepth(testLRlon, testULlon, testWPixel);
        assertEquals(expected, actual);

        //test1234.html test
        double LRlon = -122.20908713544797;
        double ULlon = -122.3027284165759;
        double WPixel = 305.0;
        int expected2 = 1;
        int actual2 = raster.findDepth(LRlon, ULlon, WPixel);
        assertEquals(expected2, actual2);

        //testTwelveImages.html test
        double twelveLRlon = -122.2104604264636;
        double twelveULlon = -122.30410170759153;
        double twelveWPixel = 1091.0;
        int expected3 = 2;
        int actual3 = raster.findDepth(twelveLRlon, twelveULlon, twelveWPixel);
        assertEquals(expected3, actual3);
    }

    @Test
    public void testValueToFileName() {
        RasterAPIHandler raster = new RasterAPIHandler();
        String actual = raster.valueToFileName(2, 4, 7);
        String expected = "d2_x4_y7.png";
        assertEquals(expected, actual);
    }

    @Test
    public void testListTo2D() {
        RasterAPIHandler raster = new RasterAPIHandler();
        String[] s = new String[]{"d2_x0_y1.png", "d2_x1_y1.png","d2_x2_y1.png","d2_x3_y1.png",
                "d2_x0_y2.png","d2_x1_y2.png", "d2_x2_y2.png", "d2_x3_y2.png","d2_x0_y3.png","d2_x1_y3.png",
                "d2_x2_y3.png","d2_x3_y3.png"};
        String[][] actual = raster.listToTwoD(s, 3, 4);
        String[][] expected = new String[][]{{"d2_x0_y1.png", "d2_x1_y1.png","d2_x2_y1.png","d2_x3_y1.png"},
                {"d2_x0_y2.png","d2_x1_y2.png", "d2_x2_y2.png", "d2_x3_y2.png"}, {"d2_x0_y3.png","d2_x1_y3.png",
                "d2_x2_y3.png","d2_x3_y3.png"}};
        assertArrayEquals(expected, actual);
    }
}
