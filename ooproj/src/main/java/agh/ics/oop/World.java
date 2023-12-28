package agh.ics.oop;

import agh.ics.oop.model.*;

public class World {
    public static void main(String[] args) {
        Settings testSettings = new Settings(
                20,
                20,
                7,
                7,
                0,
                10,
                5,
                10,
                10,
                10,
                10,
                10,
                10,
                5,
                1,
                50,
                0.003);

        Simulation testSim = new Simulation(testSettings);
        testSim.run();

        //TODO
        //  PROBLEM!!!
        //  When more than 1 animal enter the same field, only one remains on the map and the rest get removed.
    }
}
