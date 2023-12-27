package agh.ics.oop.model;

import java.util.Arrays;
import java.util.Map;

public class Simulation {
    private Settings settings;
    public Simulation(Settings settings) {
        this.settings = settings;
    }

    public void run() {
        AbstractWorldMap testMap = new AbstractWorldMap(settings);
        for (int i = 0; i < settings.getStartAnimalCount(); i++) {
            Animal currAnimal = new Animal(testMap.randomField(), settings, 0);
            testMap.place(currAnimal);
        }
        System.out.println("INITIAL MAP");
        System.out.println(testMap);
        System.out.println("\n\n\n");
        for (int dayCnt = 0; dayCnt < settings.getDurationInDays(); dayCnt++) {
            letOneDayPass(testMap, dayCnt);
        }
    }

    private void letOneDayPass(AbstractWorldMap testMap, int currDayVal) {
        testMap.moveAllAnimalsByGene(currDayVal);
        testMap.growPlantMassive();
        System.out.println("DAY " + currDayVal);
        System.out.println(testMap);
        System.out.println("\n\n\n");
    }
}
