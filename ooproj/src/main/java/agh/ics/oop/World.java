package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

public class World {
    public static void main(String[] args) {
        Settings testSettings = new Settings(
                1,
                5,
                0,
                7,
                0,
                -1,
                2,
                50,
                -1,
                -1,
                -1,
                -1,
                -1,
                5,
                -1,
                50,
                0.00);

        Simulation testSim = new Simulation(testSettings);
        testSim.run();

//        List<Animal> myList = new ArrayList<>();
//        myList.add(new Animal(new Vector2d(3,1), testSettings, 0));
//        myList.add(new Animal(new Vector2d(2,1), testSettings, 0));
//        System.out.println(myList.toString());

        //TODO
        //  PROBLEM!!!
        //  When more than 1 animal enter the same field, only one remains on the map and the rest get removed.


    }
}
