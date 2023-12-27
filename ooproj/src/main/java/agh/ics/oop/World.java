package agh.ics.oop;

import agh.ics.oop.model.*;

public class World {
    public static void main(String[] args) {
        Settings testSettings = new Settings(
                20,
                20,
                10,
                10,
                10,
                10,
                13,
                5,
                10,
                10,
                10,
                10,
                10,
                5,
                1,
                10);

        Simulation testSim = new Simulation(testSettings);
        testSim.run();
    }
}
