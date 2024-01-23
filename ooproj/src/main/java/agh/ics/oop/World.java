package agh.ics.oop;

import agh.ics.oop.model.*;

public class World {
    public static void main(String[] args) {
        Settings testSettings = new Settings(
                15,
                15,
                0,
                7,
                3,
                -1,
                5,
                50,
                10,
                4,
                0,
                1,
                2,
                2,
                // mapType: 0 or 3
                3,
                50,
                10,
                11
                );

        Simulation testSim = new Simulation(testSettings);
        ConsoleSimulationDisplay testSimConsole = new ConsoleSimulationDisplay();
        testSim.addObserver(testSimConsole);
        testSim.run();
        testSim.removeObserver(testSimConsole);

//        List<Animal> myList = new ArrayList<>();
//        myList.add(new Animal(new Vector2d(3,1), testSettings, 0));
//        myList.add(new Animal(new Vector2d(2,1), testSettings, 0));
//        System.out.println(myList.toString());

        //TODO
        //  interfejs
        //  mozliwosc zatrzymania symulacji
        //  mozliwosc klinkiecia i trackowania zwierzaka
        //  zapis wybranych ustawien (preset)
        //  mozliwosc odpalenia wielu symulacji naraz
        //  testy

    }
}
