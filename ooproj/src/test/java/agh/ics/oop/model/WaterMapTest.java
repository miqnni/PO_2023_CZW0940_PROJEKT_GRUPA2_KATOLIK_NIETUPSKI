package agh.ics.oop.model;

import com.sun.source.tree.AssertTree;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WaterMapTest {

    @Test
    void growWater() {
        Settings testSettings = new Settings(
                30,
                25,
                0,
                15,
                0,
                1,
                20,
                20,
                10,
                0,
                1,
                2,
                3,
                // mapType: 0 or 3
                3,
                15,
                40,
                5
        );
        WaterMap testMap =  new WaterMap(testSettings);
        testMap.growWater();
        testMap.growWater();
        testMap.growWater();
        assertTrue(testMap.isOccupiedByWater(new Vector2d(0, 3)));
        assertFalse(testMap.isOccupiedByWater(new Vector2d(0, 4)));
        assertTrue(testMap.isOccupiedByWater(new Vector2d(3, 0)));
        assertTrue(testMap.isOccupiedByWater(new Vector2d(2, 1)));
        assertTrue(testMap.isOccupiedByWater(new Vector2d(26, 24)));
        assertFalse(testMap.isOccupiedByWater(new Vector2d(25, 24)));

    }

    @Test
    void shrinkWater() {
        Settings testSettings = new Settings(
                30,
                25,
                0,
                15,
                0,
                1,
                20,
                20,
                10,
                0,
                1,
                2,
                3,
                // mapType: 0 or 3
                3,
                15,
                40,
                5
        );
        WaterMap testMap =  new WaterMap(testSettings);
        testMap.growWater();
        testMap.growWater();
        testMap.growWater();
        testMap.shrinkWater();
        assertTrue(testMap.isOccupiedByWater(new Vector2d(0, 2)));
        assertFalse(testMap.isOccupiedByWater(new Vector2d(0, 3)));
        assertTrue(testMap.isOccupiedByWater(new Vector2d(2, 0)));
        assertTrue(testMap.isOccupiedByWater(new Vector2d(1, 0)));
        assertFalse(testMap.isOccupiedByWater(new Vector2d(2, 1)));
        assertTrue(testMap.isOccupiedByWater(new Vector2d(27, 24)));
        assertFalse(testMap.isOccupiedByWater(new Vector2d(26, 24)));


    }
}