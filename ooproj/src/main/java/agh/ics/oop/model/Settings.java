package agh.ics.oop.model;

public class Settings {
    private int mapWidth;
    private int mapHeight;
    private int startPlantCount;
    private int energyPerPlant;
    private int newPlantsPerDay;
    private int startAnimalCount;
    private int startAnimalEnergy;
    private int reproductionEnergyThreshold;
    private int energyUsedByParents;
    private int minMutationCount;
    private int maxMutationCount;
    private int mutationType;
    private int genomeLength;
    private int mapType;
    private int durationInDays;
    private int halfCycleLength;

    private int waterRangeLimit;

    public Settings(int mapWidth, int mapHeight, int startPlantCount, int energyPerPlant, int newPlantsPerDay, int startAnimalCount, int startAnimalEnergy, int reproductionEnergyThreshold, int energyUsedByParents, int minMutationCount, int maxMutationCount, int mutationType, int genomeLength, int mapType, int durationInDays, int halfCycleLength, int waterRangeLimit) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.startPlantCount = startPlantCount;
        this.energyPerPlant = energyPerPlant;
        this.newPlantsPerDay = newPlantsPerDay;
        this.startAnimalCount = startAnimalCount;
        this.startAnimalEnergy = startAnimalEnergy;
        this.reproductionEnergyThreshold = reproductionEnergyThreshold;
        this.energyUsedByParents = energyUsedByParents;
        this.minMutationCount = minMutationCount;
        this.maxMutationCount = maxMutationCount;
        this.mutationType = mutationType;
        this.genomeLength = genomeLength;
        this.mapType = mapType;
        this.durationInDays = durationInDays;
        this.halfCycleLength = halfCycleLength;
        this.waterRangeLimit = waterRangeLimit;
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

    public int getMapType() {
        return mapType;
    }

    public int getNewPlantsPerDay() {
        return newPlantsPerDay;
    }

    public int getDurationInDays() {
        return durationInDays;
    }

    public int getHalfCycleLength() {
        return halfCycleLength;
    }

    public int getWaterRangeLimit() {
        return waterRangeLimit;
    }


    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    public void setStartPlantCount(int startPlantCount) {
        this.startPlantCount = startPlantCount;
    }

    public void setEnergyPerPlant(int energyPerPlant) {
        this.energyPerPlant = energyPerPlant;
    }

    public void setNewPlantsPerDay(int newPlantsPerDay) {
        this.newPlantsPerDay = newPlantsPerDay;
    }

    public void setStartAnimalCount(int startAnimalCount) {
        this.startAnimalCount = startAnimalCount;
    }

    public void setStartAnimalEnergy(int startAnimalEnergy) {
        this.startAnimalEnergy = startAnimalEnergy;
    }

    public void setReproductionEnergyThreshold(int reproductionEnergyThreshold) {
        this.reproductionEnergyThreshold = reproductionEnergyThreshold;
    }

    public void setEnergyUsedByParents(int energyUsedByParents) {
        this.energyUsedByParents = energyUsedByParents;
    }

    public void setMinMutationCount(int minMutationCount) {
        this.minMutationCount = minMutationCount;
    }

    public void setMaxMutationCount(int maxMutationCount) {
        this.maxMutationCount = maxMutationCount;
    }

    public void setMutationType(int mutationType) {
        this.mutationType = mutationType;
    }

    public void setGenomeLength(int genomeLength) {
        this.genomeLength = genomeLength;
    }

    public void setMapType(int mapType) {
        this.mapType = mapType;
    }

    public void setDurationInDays(int durationInDays) {
        this.durationInDays = durationInDays;
    }

    public void setHalfCycleLength(int halfCycleLength) {
        this.halfCycleLength = halfCycleLength;
    }

    public void setWaterRangeLimit(int waterRangeLimit) {
        this.waterRangeLimit = waterRangeLimit;
    }
}
