package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

public class World {
    public static void main(String[] args) {
        Settings testSettings = new Settings(
                2,
                5,
                7,
                7,
                4,
                -1,
                3,
                50,
                10,
                4,
                -1,
                -1,
                -1,
                10,
                -1,
                50,
                0.0015);

        Simulation testSim = new Simulation(testSettings);
        testSim.run();

//        List<Animal> myList = new ArrayList<>();
//        myList.add(new Animal(new Vector2d(3,1), testSettings, 0));
//        myList.add(new Animal(new Vector2d(2,1), testSettings, 0));
//        System.out.println(myList.toString());

        //TODO
        //  warianty mapy
        //  wariamty genow
        //  wszystko ma dzialac
        //  interfejs

    }
}
