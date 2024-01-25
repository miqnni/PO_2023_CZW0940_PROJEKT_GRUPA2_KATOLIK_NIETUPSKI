package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultWorldMapTest {

    @Test
    void moveForward() {
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
                0,
                15,
                40,
                5
        );
        DefaultWorldMap testMap =  new DefaultWorldMap(testSettings);

        Animal animal_test0 = new Animal(new Vector2d(0, 6),testSettings, 0);
        Animal animal_test1 = new Animal(new Vector2d(29, 5),testSettings, 0);

        animal_test0.setOrientation(MapDirection.NORTHWEST);
        animal_test1.setOrientation(MapDirection.EAST);

        testMap.place(animal_test0);
        testMap.place(animal_test1);

        testMap.moveForward(animal_test0);
        testMap.moveForward(animal_test1);

        assertEquals(new Vector2d(29, 7), animal_test0.getPosition());
        assertEquals(new Vector2d(0, 5), animal_test1.getPosition());

    }
}