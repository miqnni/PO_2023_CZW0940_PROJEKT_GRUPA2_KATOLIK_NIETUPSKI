package agh.ics.oop;

import agh.ics.oop.model.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.*;

public class Simulation implements Runnable {
    private final UUID simulationId;
    private Settings settings;

    private DefaultWorldMap map;

    private String currentFilePath;

    private final List<SimulationChangeListener> observers = new LinkedList<>();

    private int simDayCnt;

    private SimulationEngine parentEngine;

    private BooleanProperty stopped = new SimpleBooleanProperty(true);

    private BooleanProperty prepared = new SimpleBooleanProperty(true);

    private BooleanProperty saveToCSV = new SimpleBooleanProperty(true);

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

        this.simulationId = UUID.randomUUID();
        this.simDayCnt = 0;
        this.prepared.set(false);
        this.saveToCSV.set(false);
    }

    public void prepare() {
        if (settings.getMapType() == 3) {
            this.map = new WaterMap(settings);
        }
        else {
            this.map = new DefaultWorldMap(settings);
        }

        for (int i = 0; i < settings.getStartAnimalCount(); i++) {
            Animal currAnimal = new Animal(map.randomField(), settings, 0);
            map.place(currAnimal);
        }
        map.growPlantsInRandomFields(settings.getStartPlantCount());

        this.simDayCnt = 0;
        prepared.set(true);
    }

    public void run() {
        stopped.set(false);

        SimulationCSV simulationCSV = new SimulationCSV(this);

        while (simDayCnt < settings.getDurationInDays()) {
            while (!isStopped() && simDayCnt < settings.getDurationInDays()) {

                if (settings.getMapType() == 3) {
                    try {
                        letOneDayPassWithWater((WaterMap) map);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    try {
                        letOneDayPass(map);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                if (isSaveToCSV()) {
                    simulationCSV.convertSimulationToCSV("simstats", currentFilePath);
                }

                simDayCnt++;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
        prepared.set(false);
        simulationChanged("SIMULATION ENDED: " + simDayCnt + " DAYS PASSED");
    }

    private void letOneDayPass(DefaultWorldMap selectedMap) throws InterruptedException {
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
    }

    private void letOneDayPassWithWater(WaterMap selectedMap) throws InterruptedException {
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
        DefaultWorldMap selectedMap = map;
        System.out.println("Statistics: day " + simDayCnt);
        System.out.println("Animal count: " + selectedMap.getAnimalCount());
        System.out.println("Plant count: " + selectedMap.getPlantCount());
        System.out.println("Empty Field count: " + selectedMap.getEmptyFieldCount());
        System.out.println("Avg Energy: " + selectedMap.getAvgEnergy());
        System.out.println("Avg Lifespan: " + selectedMap.getAvgLifespanOfDeadAnimals());
        System.out.println("Avg Children count: " + selectedMap.getAvgChildrenCount());
        System.out.println("Most frequent gene: " + selectedMap.findMostFrequentGene());
        System.out.println("Dominant genotype: " + findMostPopularGenotype(selectedMap));
        System.out.println("\n\n\n");
    }

    public String getStatsAsString() {
        DefaultWorldMap selectedMap = map;
        StringBuilder result = new StringBuilder();

        result.append("Statistics: day ").append(simDayCnt).append("\n");
        result.append("Animal count: ").append(selectedMap.getAnimalCount()).append("\n");
        result.append("Plant count: ").append(selectedMap.getPlantCount()).append("\n");
        result.append("Empty Field count: ").append(selectedMap.getEmptyFieldCount()).append("\n");
        result.append("Avg Energy: ").append(selectedMap.getAvgEnergy()).append("\n");
        result.append("Avg Lifespan: ").append(selectedMap.getAvgLifespanOfDeadAnimals()).append("\n");
        result.append("Avg Children count: ").append(selectedMap.getAvgChildrenCount()).append("\n");
        result.append("Most frequent gene: ").append(selectedMap.findMostFrequentGene()).append("\n");
        result.append("Dominant genotype: ").append(findMostPopularGenotype(selectedMap)).append("\n\n\n");

        return result.toString();
    }

    private void printMostPopularGenotypes(DefaultWorldMap selectedMap) {
        for (Map.Entry<Genome, Integer> entry : map.getGenomeCount().entrySet()) {
            Genome currGenome = entry.getKey();
            Integer currInteger = entry.getValue();
            System.out.println(currGenome + ": " + currInteger);
        }
    }

    public Genome findMostPopularGenotype(DefaultWorldMap selectedMap) {
        Integer maxCnt = 1;
        Genome dominant = null;
        for (Map.Entry<Genome, Integer> entry : map.getGenomeCount().entrySet()) {
            Genome currGenome = entry.getKey();
            Integer currInteger = entry.getValue();
            if (currInteger > maxCnt) {
                dominant = currGenome;
                maxCnt = currInteger;
            }
        }
        return dominant;
    }

    public DefaultWorldMap getMap() {
        return map;
    }

    public UUID getSimulationId() {
        return simulationId;
    }

    public int getSimDayCnt() {
        return simDayCnt;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public void setParentEngine(SimulationEngine parentEngine) {
        this.parentEngine = parentEngine;
    }

    public SimulationEngine getParentEngine() {
        return parentEngine;
    }

    public boolean isStopped() {
        return stopped.get();
    }

    public void stop() {
        this.stopped.set(true);
    }

    public boolean isPrepared() {
        return prepared.get();
    }

    public void setSaveToCSV(boolean saveToCSV) {
        this.saveToCSV.set(saveToCSV);
    }

    public boolean isSaveToCSV() {
        return saveToCSV.get();
    }

    public void setCurrentFilePath(String currentFilePath) {
        this.currentFilePath = currentFilePath;
    }

    public Set<Vector2d> getPreferredFields(){
        Set<Vector2d> preferredFields = new HashSet<>();
        for (int i = 1; i < settings.getMapWidth() + 1; i++){
            for (int j = map.getMinEquatorHeight() + 1; j < map.getMaxEquatorHeight() + 1; j++){
                preferredFields.add(new Vector2d(i, j));
            }
        }
        return preferredFields;
    }

    public Set<Vector2d> getAnimalsWithDominantGenotype(){
        Set<Vector2d> DominantAnimals = new HashSet<>();
        Genome dominant = findMostPopularGenotype(map);
        if (dominant == null) return DominantAnimals;
        for (int i = 1; i < settings.getMapWidth() + 1; i++){
            for (int j = 1; j < settings.getMapHeight() + 1; j++){
                if (map.getAnimals().get(new Vector2d(i -1, j -1)) != null){
                    if (map.getAnimals().get(new Vector2d(i -1, j -1)).containsGenome(dominant)){
                        DominantAnimals.add(new Vector2d(i, j));
                    }
                }
            }
        }
        return DominantAnimals;
    }
}
