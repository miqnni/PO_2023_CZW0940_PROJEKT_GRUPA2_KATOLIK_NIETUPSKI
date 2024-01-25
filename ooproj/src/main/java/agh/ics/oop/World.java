package agh.ics.oop;

import agh.ics.oop.model.*;

public class World {
    public static void main(String[] args) {
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
                15,
                // mapType: 0 or 3
                0,
                15,
                40,
                5
                );

        Simulation testSim = new Simulation(testSettings);
        ConsoleSimulationDisplay testSimConsole = new ConsoleSimulationDisplay();
        testSim.addObserver(testSimConsole);
        testSim.run();
        testSim.removeObserver(testSimConsole);

        //TODO
        //  interfejs
        //  mozliwosc zatrzymania symulacji
        //  mozliwosc klinkiecia i trackowania zwierzaka
        //  zapis wybranych ustawien (preset)
        //  mozliwosc odpalenia wielu symulacji naraz
        //  testy

    }
}
