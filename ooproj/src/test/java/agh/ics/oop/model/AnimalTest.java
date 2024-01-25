package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

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
        AbstractWorldMap testMap =  new AbstractWorldMap(testSettings);

        Animal animal_test0 = new Animal(new Vector2d(0, 0),testSettings, 0);
        animal_test0.setOrientation(MapDirection.NORTHEAST);
        animal_test0.setGenes(new int[] {0, 0, 0});
        animal_test0.setStartGeneId(0);
        Animal animal_test1 = new Animal(new Vector2d(5, 5),testSettings, 0);
        animal_test1.setOrientation(MapDirection.NORTH);
        animal_test1.setGenes(new int[] {7, 7, 1}); // ma byc na (2, 7)
        animal_test1.setStartGeneId(0);

        testMap.place(animal_test0);
        testMap.place(animal_test1);

        testMap.moveAnimalByGene(animal_test1, 0);
        testMap.moveAnimalByGene(animal_test1, 1);
        testMap.moveAnimalByGene(animal_test1, 2);

        animal_test0.moveForward(testMap);
        animal_test0.moveForward(testMap);
        animal_test0.moveForward(testMap);
        assertEquals(new Vector2d(3, 3), animal_test0.getPosition());
        assertEquals(new Vector2d(2, 7), animal_test1.getPosition());
    }

    @Test
    void turn() {
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
        AbstractWorldMap testMap =  new AbstractWorldMap(testSettings);

        Animal animal_test0 = new Animal(new Vector2d(0, 0),testSettings, 0);
        animal_test0.setOrientation(MapDirection.NORTHEAST);

        testMap.place(animal_test0);
        animal_test0.turn(4);
        assertEquals(MapDirection.SOUTHWEST, animal_test0.getOrientation());

        animal_test0.turn(3);
        assertEquals(MapDirection.NORTH, animal_test0.getOrientation());

        animal_test0.turn(7);
        assertEquals(MapDirection.NORTHWEST, animal_test0.getOrientation());

        animal_test0.turn(11);
        assertEquals(MapDirection.EAST, animal_test0.getOrientation());
    }
}