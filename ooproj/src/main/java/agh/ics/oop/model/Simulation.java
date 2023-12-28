package agh.ics.oop.model;

import java.util.Arrays;
import java.util.List;
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
        testMap.growPlantsInRandomFields(settings.getStartPlantCount());
        System.out.println("INITIAL MAP");
        System.out.println(testMap);
        System.out.println("\n\n\n");
        for (int dayCnt = 0; dayCnt < settings.getDurationInDays(); dayCnt++) {
            letOneDayPass(testMap, dayCnt);
        }
    }

    private void letOneDayPass(AbstractWorldMap testMap, int currDayVal) {
        testMap.moveAllAnimalsByGene(currDayVal);
        testMap.allAnimalsEatPlantIfPossible();
        testMap.changeAllAnimalsEnergy(-1);
        testMap.removeDeadAnimals(currDayVal);
        testMap.increaseDayCountOfAllAnimals();
        testMap.growPlantMassive();
    }

    private void printStats(AbstractWorldMap testMap, int currDayVal) {
        System.out.println("DAY " + currDayVal);
        System.out.println(testMap);
        List<Animal> currAnimalList = testMap.createCurrAnimalList();
        for (Animal currAnimal : currAnimalList) {
            System.out.println(currAnimal + " " + currAnimal.getPosition() + " E=" + currAnimal.getEnergy() + " days=" + currAnimal.getDaysLived());
        }
        System.out.println("Animal count: " + testMap.getAnimalCount());
        System.out.println("Plant count: " + testMap.getPlantCount());
        System.out.println("Empty Field count: " + testMap.getEmptyFieldCount());
        System.out.println("Avg Energy: " + testMap.getAvgEnergy());
        System.out.println("Avg Lifespan: " + testMap.getAvgLifespanOfDeadAnimals());
        System.out.println("Avg Children count: " + testMap.getAvgChildrenCount());
        System.out.println("\n\n\n");
    }
}
