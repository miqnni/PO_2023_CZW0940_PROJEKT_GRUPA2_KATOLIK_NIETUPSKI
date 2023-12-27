package agh.ics.oop.model;

public class Settings {
    private int mapWidth;
    private int mapHeight;
    private int startPlantCount;
    private int energyPerPlant;
    private int newPlantsPerDay;
    private int plantGrowthType;
    private int startAnimalCount;
    private int startAnimalEnergy;
    private int reproductionEnergyThreshold;
    private int energyUsedByParents;
    private int minMutationCount;
    private int maxMutationCount;
    private int mutationType;
    private int genomeLength;
    private int behaviorType;
    private int durationInDays;

    public Settings(int mapWidth, int mapHeight, int startPlantCount, int energyPerPlant, int newPlantsPerDay, int plantGrowthType, int startAnimalCount, int startAnimalEnergy, int reproductionEnergyThreshold, int energyUsedByParents, int minMutationCount, int maxMutationCount, int mutationType, int genomeLength, int behaviorType, int durationInDays) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.startPlantCount = startPlantCount;
        this.energyPerPlant = energyPerPlant;
        this.newPlantsPerDay = newPlantsPerDay;
        this.plantGrowthType = plantGrowthType;
        this.startAnimalCount = startAnimalCount;
        this.startAnimalEnergy = startAnimalEnergy;
        this.reproductionEnergyThreshold = reproductionEnergyThreshold;
        this.energyUsedByParents = energyUsedByParents;
        this.minMutationCount = minMutationCount;
        this.maxMutationCount = maxMutationCount;
        this.mutationType = mutationType;
        this.genomeLength = genomeLength;
        this.behaviorType = behaviorType;
        this.durationInDays = durationInDays;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getStartPlantCount() {
        return startPlantCount;
    }

    public int getEnergyPerPlant() {
        return energyPerPlant;
    }

    public int getStartAnimalCount() {
        return startAnimalCount;
    }

    public int getStartAnimalEnergy() {
        return startAnimalEnergy;
    }

    public int getReproductionEnergyThreshold() {
        return reproductionEnergyThreshold;
    }

    public int getEnergyUsedByParents() {
        return energyUsedByParents;
    }

    public int getMinMutationCount() {
        return minMutationCount;
    }

    public int getMaxMutationCount() {
        return maxMutationCount;
    }

    public int getMutationType() {
        return mutationType;
    }

    public int getGenomeLength() {
        return genomeLength;
    }

    public int getBehaviorType() {
        return behaviorType;
    }

    public int getNewPlantsPerDay() {
        return newPlantsPerDay;
    }

    public int getPlantGrowthType() {
        return plantGrowthType;
    }

    public int getDurationInDays() {
        return durationInDays;
    }
}
