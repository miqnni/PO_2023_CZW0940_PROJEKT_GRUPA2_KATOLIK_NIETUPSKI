package agh.ics.oop.model;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class WaterMap extends AbstractWorldMap {

    public WaterMap(Settings settings) {
        super(settings);
    }

    @Override
    public void place(Animal animal) {
        super.place(animal);
    }

    @Override
    public void growPlant(Vector2d position) {
        super.growPlant(position);
    }

    @Override
    public void growPlantMassive() {
        super.growPlantMassive();
    }

    @Override
    public boolean isEquatorOvergrown() {
        return super.isEquatorOvergrown();
    }

    @Override
    public void growPlantEquator() {
        super.growPlantEquator();
    }

    @Override
    public void growPlantNonEquator() {
        super.growPlantNonEquator();
    }

    @Override
    public void growPlantsInRandomFields(int plantCnt) {
        super.growPlantsInRandomFields(plantCnt);
    }

    @Override
    public void moveForward(Animal animal) {
        super.moveForward(animal);
    }

    @Override
    public void turn(Animal animal, int timesTurned) {
        super.turn(animal, timesTurned);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return super.isOccupied(position);
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return super.objectAt(position);
    }

    @Override
    public List<WorldElement> getElements() {
        return super.getElements();
    }

    @Override
    public UUID getId() {
        return super.getId();
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return super.canMoveTo(position);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public List<Animal> createCurrAnimalList() {
        return super.createCurrAnimalList();
    }

    @Override
    public void moveAnimalByGene(Animal animal, int dayNo) {
        super.moveAnimalByGene(animal, dayNo);
    }

    @Override
    public void moveAllAnimalsByGene(int dayNo) {
        super.moveAllAnimalsByGene(dayNo);
    }

    @Override
    public void changeOneAnimalsEnergy(Animal animal, int dEnergy) {
        super.changeOneAnimalsEnergy(animal, dEnergy);
    }

    @Override
    public void changeAllAnimalsEnergy(int dEnergy) {
        super.changeAllAnimalsEnergy(dEnergy);
    }

    @Override
    public void removeDeadAnimals(int dayNo) {
        super.removeDeadAnimals(dayNo);
    }

//    @Override
//    public void animalEatsPlantIfPossible(Animal animal) {
//        super.animalEatsPlantIfPossible(animal);
//    }
//
//    @Override
//    public void allAnimalsEatPlantIfPossible() {
//        super.allAnimalsEatPlantIfPossible();
//    }

    @Override
    public Animal findBestAnimal(AnimalList animalList) {
        return super.findBestAnimal(animalList);
    }

    @Override
    public void plantConsumptionOnFieldIfPossible(Vector2d position) {
        super.plantConsumptionOnFieldIfPossible(position);
    }

    @Override
    public void massivePlantConsumption() {
        super.massivePlantConsumption();
    }

    @Override
    public void reproduceOnFieldIfPossible(Vector2d position, int currDay) {
        super.reproduceOnFieldIfPossible(position, currDay);
    }

    @Override
    public void mutateByReplacement(Animal animal) {
        super.mutateByReplacement(animal);
    }

    @Override
    public void mutateByReplacementOrSwap(Animal animal) {
        super.mutateByReplacementOrSwap(animal);
    }

    @Override
    public void reproduceOnEveryPossibleField(int currDay) {
        super.reproduceOnEveryPossibleField(currDay);
    }

    @Override
    public Vector2d randomField() {
        return super.randomField();
    }

    @Override
    public Vector2d randomFieldEquator() {
        return super.randomFieldEquator();
    }

    @Override
    public Vector2d randomFieldNonEquator() {
        return super.randomFieldNonEquator();
    }

    @Override
    public Map<Vector2d, AnimalList> getAnimals() {
        return super.getAnimals();
    }

    @Override
    public int getActivatedGeneIdx(Animal animal, int dayNo) {
        return super.getActivatedGeneIdx(animal, dayNo);
    }

    @Override
    public int getAnimalCount() {
        return super.getAnimalCount();
    }

    @Override
    public int getPlantCount() {
        return super.getPlantCount();
    }

    @Override
    public int getEmptyFieldCount() {
        return super.getEmptyFieldCount();
    }

    @Override
    public double getAvgEnergy() {
        return super.getAvgEnergy();
    }

    @Override
    public double getAvgLifespanOfDeadAnimals() {
        return super.getAvgLifespanOfDeadAnimals();
    }

    @Override
    public double getAvgChildrenCount() {
        return super.getAvgChildrenCount();
    }

    @Override
    public void increaseDayCountOfAllAnimals() {
        super.increaseDayCountOfAllAnimals();
    }

    @Override
    public int findMostFrequentGene() {
        return super.findMostFrequentGene();
    }


}
