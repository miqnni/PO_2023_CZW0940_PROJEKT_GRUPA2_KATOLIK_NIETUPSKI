package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

public class World {
    public static void main(String[] args) {
        Settings testSettings = new Settings(
                30,
                30,
                0,
                7,
                5,
                -1,
                1,
                10,
                -1,
                -1,
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
        //  zmienic losowanie, gdzie rosnie roslina
        //  rywalizacja o rosline
        //  rozmnazanie
        //  warianty mapy

    }
}
