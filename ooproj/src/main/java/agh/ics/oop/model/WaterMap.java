package agh.ics.oop.model;

import java.util.*;

import static java.lang.Math.min;

public class WaterMap extends AbstractWorldMap {

    protected Map<Vector2d, Water> waterFields = new HashMap<>();

    private int waterRange;
    private final int maxWaterRange;

    public WaterMap(Settings settings) {
        super(settings);
        placeWater(new Vector2d(0, 0));
        placeWater(new Vector2d(width - 1, height - 1));
        this.waterRange = 1;
        this.maxWaterRange = min(width, height) - 2;
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
        boolean growthSuccessful = false;
        while (!growthSuccessful) {
            Vector2d currRandPos = randomFieldEquator();
            if (plants.get(currRandPos) == null && waterFields.get(currRandPos) == null) {
                growPlant(currRandPos);
                freeEquatorFields--;
                takenEquatorFields++;
                growthSuccessful = true;
            }
        }
    }

    @Override
    public void growPlantNonEquator() {
        boolean growthSuccessful = false;
        while (!growthSuccessful) {
            Vector2d currRandPos = randomFieldNonEquator();
            if (plants.get(currRandPos) == null && waterFields.get(currRandPos) == null) {
                growPlant(currRandPos);
                freeNonEquatorFields--;
                takenNonEquatorFields++;
                growthSuccessful = true;
            }
        }
    }

    @Override
    public void growPlantsInRandomFields(int plantCnt) {
        super.growPlantsInRandomFields(plantCnt);
    }

    @Override
    public void moveForward(Animal animal) {
        Vector2d oldPos = animal.getPosition();
        super.moveForward(animal);
        correctWater(oldPos);

    }

    public void undoMove(Animal animal, Vector2d oldPos) {
        Vector2d currPos = animal.getPosition();
        AnimalList currPosAnimalList = animals.get(currPos);
        if (currPosAnimalList.size() == 1) {
            animals.remove(currPos);
        }
        else {
            currPosAnimalList.remove(animal);
        }

        if (animals.get(oldPos) == null) {
            AnimalList newAnimalList = new AnimalList();
            newAnimalList.add(animal);
            animals.put(oldPos, newAnimalList);
        }
        else {
            AnimalList nextAnimalList = animals.get(oldPos);
            nextAnimalList.add(animal);
        }
    }

    @Override
    public void turn(Animal animal, int timesTurned) {
        super.turn(animal, timesTurned);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return super.isOccupied(position) || waterFields.get(position) != null;
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
//        return super.objectAt(position);
        if (animals.get(position) != null )
            return animals.get(position);
        if (waterFields.get(position) != null)
            return waterFields.get(position);
        return plants.get(position);
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

    private boolean isPositionInWaterRange(Vector2d currPos) {
        int maxX = width - 1;
        int maxY = height - 1;
        int currPosRange = currPos.getX() + currPos.getY() + 1;
        int maxPosRange = maxX + maxY + 1;
        return (currPosRange <= waterRange || currPosRange > maxPosRange - waterRange);
    }

    private void correctWater(Vector2d currPos) {
        if (isPositionInWaterRange(currPos)) placeWater(currPos);
//        if (currPos.getX() == 0 || currPos.getY() == height - 1) {
//            if (currPos.getY() == 0 || currPos.getX() == width - 1 || waterFields.get(new Vector2d(currPos.getX() + 1, currPos.getY() - 1)) != null) {
//                placeWater(currPos);
//            }
//        }
//        else if (currPos.getX() == width - 1 || currPos.getY() == 0) {
//            if (waterFields.get(new Vector2d(currPos.getX() - 1, currPos.getY() + 1)) != null) {
//                placeWater(currPos);
//            }
//        }
//        else  {
//            if (waterFields.get(new Vector2d(currPos.getX() + 1, currPos.getY() - 1)) != null && waterFields.get(new Vector2d(currPos.getX() - 1, currPos.getY() + 1)) != null) {
//                placeWater(currPos);
//            }
//        }
    }

    @Override
    public void removeDeadAnimals(int dayNo) {
        Iterator<Animal> iterator = livingAnimals.iterator();

        while (iterator.hasNext()) {
            Animal currAnimal = iterator.next();
            if (!currAnimal.isAlive()) {
                currAnimal.setDayOfDeath(dayNo);
                iterator.remove();
                deadAnimals.add(currAnimal);
                removeGenome(currAnimal.getGenome());


                // animal removal procedure
                Vector2d currPos = currAnimal.getPosition();
                AnimalList currPosAnimalList = animals.get(currPos);
                if (currPosAnimalList.size() == 1) {
                    animals.remove(currPos);
                }
                else {
                    currPosAnimalList.remove(currAnimal);
                }

                // if the animal had died surrounded by water, fill the field with water
                correctWater(currPos);

            }
        }
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
        Vector2d currRandField = super.randomField();
        while (isOccupiedByWater(currRandField))
            currRandField =  super.randomField();
//        System.out.println("\tRandom field: " + currRandField);
        return currRandField;
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
        return super.getEmptyFieldCount() - waterFields.size();
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

    public void placeWater(Vector2d position) {
        Water water = new Water(position);
        if (animals.get(position) != null)
            return;
        if (plants.get(position) != null)
            removePlant(position);
        waterFields.put(position, water);
        if (minEquatorHeight <= position.getY() && position.getY() < maxEquatorHeight) {
            // equator
            freeEquatorFields--;
            takenEquatorFields++;
        }
        else {
            // non-equator
            freeNonEquatorFields--;
            takenNonEquatorFields++;
        }
    }

    public void removeWater(Vector2d position) {
        waterFields.remove(position);
        if (minEquatorHeight <= position.getY() && position.getY() < maxEquatorHeight) {
            // equator
            freeEquatorFields++;
            takenEquatorFields--;
        }
        else {
            // non-equator
            freeNonEquatorFields++;
            takenNonEquatorFields--;
        }
    }

    public void growWater() {

        if (waterRange >= maxWaterRange)
            return;

        // lower left
        int currX = 0;
        int currY = waterRange;
        while (currY >= 0 && currX < width) {
            placeWater(new Vector2d(currX, currY));
            currX++;
            currY--;
        }

        // upper right
        currX = width - 1 - waterRange;
        currY = height - 1;
        while (currY >= 0 && currX < width) {
            placeWater(new Vector2d(currX, currY));
            currX++;
            currY--;
        }

        waterRange++;
    }

    public void shrinkWater() {
        if (waterRange <= 1)
            return;

        // lower left
        int currX = 0;
        int currY = waterRange - 1;
        while (currY >= 0 && currX < width) {
            removeWater(new Vector2d(currX, currY));
            currX++;
            currY--;
        }

        // upper right
        currX = width - waterRange;
        currY = height - 1;
        while (currY >= 0 && currX < width) {
            removeWater(new Vector2d(currX, currY));
            currX++;
            currY--;
        }

        waterRange--;
    }

    public int getWaterRange() {
        return waterRange;
    }

    public int getMaxWaterRange() {
        return maxWaterRange;
    }

    @Override
    public boolean isOccupiedByWater(Vector2d position) {
        return waterFields.get(position) != null;
    }

    @Override
    public Map<Genome, Integer> getGenomeCount() {
        return super.getGenomeCount();
    }
}
