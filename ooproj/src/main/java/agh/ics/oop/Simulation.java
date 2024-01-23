package agh.ics.oop;

import agh.ics.oop.model.*;
import java.util.*;

public class Simulation implements Runnable {
    private final UUID simulationId;
    private final Settings settings;

    private final AbstractWorldMap testMap;

    private final List<SimulationChangeListener> observers = new LinkedList<>();

    private int simDayCnt;

    public void addObserver(SimulationChangeListener newObserver) {
        observers.add(newObserver);
    }

    public void removeObserver(SimulationChangeListener toRemove) {
        observers.remove(toRemove);
    }

    public void simulationChanged(String s) {
        for (SimulationChangeListener observer : observers)
            observer.simulationChanged(this, s);
    }

    public Simulation(Settings settings) {
        this.settings = settings;
        if (settings.getMapType() == 3) {
            this.testMap = new WaterMap(settings);
        }
        else {
            this.testMap = new AbstractWorldMap(settings);
        }
        this.simulationId = UUID.randomUUID();
        this.simDayCnt = 0;
    }

    public void run() {
        for (int i = 0; i < settings.getStartAnimalCount(); i++) {
            Animal currAnimal = new Animal(testMap.randomField(), settings, 0);
            testMap.place(currAnimal);
        }
        testMap.growPlantsInRandomFields(settings.getStartPlantCount());
        for (int dayCnt = 0; dayCnt < settings.getDurationInDays(); dayCnt++) {
            if (settings.getMapType() == 3) {
                letOneDayPassWithWater((WaterMap) testMap);
            }
            else letOneDayPass(testMap);
        }
    }

    private void letOneDayPass(AbstractWorldMap selectedMap) {
        int currDayVal = simDayCnt;
        simulationChanged("DAY " + currDayVal + " START");
        // day
        selectedMap.growPlantMassive();

        selectedMap.moveAllAnimalsByGene(currDayVal);
        selectedMap.massivePlantConsumption();
        selectedMap.reproduceOnEveryPossibleField(currDayVal);

        // night
        selectedMap.changeAllAnimalsEnergy(-1);
        selectedMap.removeDeadAnimals(currDayVal);
        selectedMap.increaseDayCountOfAllAnimals();

        // transition to next day
        simDayCnt++;

    }

    private void letOneDayPassWithWater(WaterMap selectedMap) {
        letOneDayPass(selectedMap);
        int currDayVal = simDayCnt;

        int tidePhase = ((int) (currDayVal / settings.getHalfCycleLength())) % 2;
        // 0 -> water grows
        // 1 -> water shrinks

        if (tidePhase == 0) {
            selectedMap.growWater();
        }
        else {
            selectedMap.shrinkWater();
        }
    }

    public void printStats() {
        AbstractWorldMap selectedMap = testMap;
        System.out.println("Statistics: day " + simDayCnt);
//        System.out.println(selectedMap);
        List<Animal> currAnimalList = selectedMap.createCurrAnimalList();
        for (Animal currAnimal : currAnimalList) {
            System.out.println(currAnimal + " " + currAnimal.getPosition() + " E=" + currAnimal.getEnergy() + " days=" + currAnimal.getDaysLived() + " GENES: " + Arrays.toString(currAnimal.getGenes()));
        }
        System.out.println("Animal count: " + selectedMap.getAnimalCount());
        System.out.println("Plant count: " + selectedMap.getPlantCount());
        System.out.println("Empty Field count: " + selectedMap.getEmptyFieldCount());
        System.out.printf("\t[FREE/ALL] Equator     : [%d/%d]%n", selectedMap.getFreeEquatorFields(), selectedMap.getFreeEquatorFields() + selectedMap.getTakenEquatorFields());
        System.out.printf("\t[FREE/ALL] Non-Equator : [%d/%d]%n", selectedMap.getFreeNonEquatorFields(), selectedMap.getFreeNonEquatorFields() + selectedMap.getTakenNonEquatorFields());
        System.out.println("Avg Energy: " + selectedMap.getAvgEnergy());
        System.out.println("Avg Lifespan: " + selectedMap.getAvgLifespanOfDeadAnimals());
        System.out.println("Avg Children count: " + selectedMap.getAvgChildrenCount());
        System.out.println("Most frequent gene: " + selectedMap.findMostFrequentGene());
        // DEBUG
//        printMostPopularGenotypes(testMap);
        // END DEBUG
        System.out.println("Dominant genotype: " + findMostPopularGenotype(selectedMap));
        System.out.println("\n\n\n");
    }

    private void printMostPopularGenotypes(AbstractWorldMap selectedMap) {
        for (Map.Entry<Genome, Integer> entry : testMap.getGenomeCount().entrySet()) {
            Genome currGenome = entry.getKey();
            Integer currInteger = entry.getValue();
            System.out.println(currGenome + ": " + currInteger);
        }
    }

    private Genome findMostPopularGenotype(AbstractWorldMap selectedMap) {
        Integer maxCnt = 1;
        Genome dominant = null;
        for (Map.Entry<Genome, Integer> entry : testMap.getGenomeCount().entrySet()) {
            Genome currGenome = entry.getKey();
            Integer currInteger = entry.getValue();
            if (currInteger > maxCnt) {
                dominant = currGenome;
                maxCnt = currInteger;
            }
        }
        return dominant;
    }

    public AbstractWorldMap getTestMap() {
        return testMap;
    }

    public UUID getSimulationId() {
        return simulationId;
    }
}
