package agh.ics.oop.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Simulation {
    private final Settings settings;

    private final AbstractWorldMap testMap;

    public Simulation(Settings settings) {
        this.settings = settings;
        if (settings.getMapType() == 3) {
            this.testMap = new WaterMap(settings);
        }
        else {
            this.testMap = new AbstractWorldMap(settings);
        }
    }

    public void run() {
//        AbstractWorldMap testMap = new AbstractWorldMap(settings);
        for (int i = 0; i < settings.getStartAnimalCount(); i++) {
            Animal currAnimal = new Animal(testMap.randomField(), settings, 0);
            testMap.place(currAnimal);
        }
        testMap.growPlantsInRandomFields(settings.getStartPlantCount());
        for (int dayCnt = 0; dayCnt < settings.getDurationInDays(); dayCnt++) {
            if (settings.getMapType() == 3) {
                letOneDayPassWithWater((WaterMap) testMap, dayCnt);
            }
            else letOneDayPass(testMap, dayCnt);
        }
    }

    private void letOneDayPass(AbstractWorldMap selectedMap, int currDayVal) {
        // day
        selectedMap.growPlantMassive();  // MOD

        selectedMap.moveAllAnimalsByGene(currDayVal);
        selectedMap.massivePlantConsumption();
        selectedMap.reproduceOnEveryPossibleField(currDayVal);

        // night
        selectedMap.changeAllAnimalsEnergy(-1);
        selectedMap.removeDeadAnimals(currDayVal);
        selectedMap.increaseDayCountOfAllAnimals();
//        selectedMap.growPlantMassive();

        printStats(selectedMap, currDayVal);
    }

    private void letOneDayPassWithWater(WaterMap selectedMap, int currDayVal) {
        letOneDayPass(selectedMap, currDayVal);

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

    private void printStats(AbstractWorldMap selectedMap, int currDayVal) {
        System.out.println("DAY " + currDayVal);
        System.out.println(selectedMap);
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
        printMostPopularGenotypes(testMap);
        System.out.println("\n\n\n");
    }

    private void printMostPopularGenotypes(AbstractWorldMap selectedMap) {

        for (Map.Entry<Genome, Integer> entry : testMap.getGenomeCount().entrySet()) {
            Genome currGenome = entry.getKey();
            Integer currInteger = entry.getValue();
            System.out.println(currGenome + ": " + currInteger);
        }
    }
}
