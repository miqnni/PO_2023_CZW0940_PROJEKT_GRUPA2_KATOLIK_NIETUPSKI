package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public class AbstractWorldMap implements WorldMap {
    protected Map<Vector2d, AnimalList> animals = new HashMap<>();
    protected Map<Vector2d, Plant> plants = new HashMap<>();

    private List<Animal> deadAnimals;
    private List<Animal> livingAnimals;

    protected Map<Integer[], Integer> genomeCount = new HashMap<>();

//    protected double[][] plantGrowthChance;

    protected UUID mapId;

    private final int width;
    private final int height;

    private final int minEquatorHeight;

    private final int maxEquatorHeight;

    private int freeEquatorFields;
    private int takenEquatorFields;

    private int freeNonEquatorFields;
    private int takenNonEquatorFields;

//    private final int genomeLength;

    private final Settings settings;


    public AbstractWorldMap(Settings settings) {
        this.mapId = UUID.randomUUID();
        this.width = settings.getMapWidth();
        this.height = settings.getMapHeight();
//        this.plantGrowthChance = new double[width][height];
//        for (int xi = 0; xi < width; xi++) {
//            for (int yi = 0; yi < height; yi++) {
//                plantGrowthChance[xi][yi] = settings.getDefaultPlantGrowthChance();
//            }
//        }
//        this.genomeLength = settings.getGenomeLength();
        this.settings = settings;
        this.livingAnimals = new ArrayList<>();
        this.deadAnimals = new ArrayList<>();

        this.minEquatorHeight = (int) (0.4 * height);
        this.maxEquatorHeight = (int) (0.6 * height);

        this.freeEquatorFields = width * (maxEquatorHeight - minEquatorHeight);
        this.takenEquatorFields = 0;

        this.freeNonEquatorFields = (width * height) - freeEquatorFields;
        this.takenNonEquatorFields = 0;
    }


    @Override
    public void place(Animal animal) {
        Vector2d animalPos = animal.getPosition();
//        if (canMoveTo(animalPos)) {
//            animals.put(animalPos, animal);
//        }

//        animals.put(animalPos, animal);

        if (animals.get(animalPos) != null) {
            animals.get(animalPos).add(animal);
        }
        else {
            AnimalList newAnimalList = new AnimalList();
            newAnimalList.add(animal);
            animals.put(animalPos, newAnimalList);
        }

        livingAnimals.add(animal);


//        System.out.println("Animal placed at " + animalPos);
    }

    public void growPlant(Vector2d position) {
        Plant plant = new Plant(position);
        plants.put(position, plant);
    }

    public void growPlantMassive() {
//        for (int xi = 0; xi < width; xi++) {
//            for (int yi = 0; yi < height; yi++) {
//                Random rand = new Random();
//                double growthTestResult = rand.nextDouble();
//                double growthChance = plantGrowthChance[xi][yi];
//                if (growthTestResult < growthChance) {
//                    growPlant(new Vector2d(xi, yi));
//                }
//            }
//        }
        int howManyPlantsToGrow = settings.getNewPlantsPerDay();
//        if (getEmptyFieldCount() < howManyPlantsToGrow) {
//            howManyPlantsToGrow = getEmptyFieldCount();
//        }
        growPlantsInRandomFields(howManyPlantsToGrow);

    }

    public boolean isEquatorOvergrown() {
        for (int i = minEquatorHeight; i < maxEquatorHeight; i++) {
            for (int j = 0; j < width; j++) {
                Vector2d fieldToCheck = new Vector2d(j, i);
                if (plants.get(fieldToCheck) == null) return false;
            }
        }
        return true;
    }

    public void growPlantEquator() {
        boolean growthSuccessful = false;
        while (!growthSuccessful) {
            Vector2d currRandPos = randomFieldEquator();
            if (plants.get(currRandPos) == null) {
                growPlant(currRandPos);
                freeEquatorFields--;
                takenEquatorFields++;
                growthSuccessful = true;
            }
        }
    }

    public void growPlantNonEquator() {
        boolean growthSuccessful = false;
        while (!growthSuccessful) {
            Vector2d currRandPos = randomFieldNonEquator();
            if (plants.get(currRandPos) == null) {
                growPlant(currRandPos);
                freeNonEquatorFields--;
                takenNonEquatorFields++;
                growthSuccessful = true;
            }
        }
    }

    public void growPlantsInRandomFields(int plantCnt) {

        Random rand = new Random();
        int growthLocationResult = rand.nextInt(4);

        for (int i = 0; i < plantCnt; i++) {
            if ((growthLocationResult > 0 || freeNonEquatorFields == 0) && freeEquatorFields > 0)  {
                // grow within equator
                growPlantEquator();
            }
            else if (freeNonEquatorFields > 0) {
                // grow within normal fields
                growPlantNonEquator();
            }
            else {
                // don't grow
                break;
            }
        }


    }

    @Override
    public void moveForward(Animal animal) {
        Vector2d currPos = animal.getPosition();
        animal.moveForward(this);
        Vector2d nextPos = animal.getPosition();
//        if (!currPos.equals(nextPos)) {
//            animals.remove(currPos);
//            animals.put(nextPos, animal);
//        }

        if (!currPos.equals(nextPos)) {
            AnimalList currPosAnimalList = animals.get(currPos);

            // remove animal from the list or remove the entire list if it's the only animal
            if (currPosAnimalList.size() == 1) {
                animals.remove(currPos);
            }
            else {
                currPosAnimalList.remove(animal);
            }

            // add animal to an existing list on nextPos or create a new list
            if (animals.get(nextPos) == null) {
                AnimalList newAnimalList = new AnimalList();
                newAnimalList.add(animal);
                animals.put(nextPos, newAnimalList);
            }
            else {
                AnimalList nextAnimalList = animals.get(nextPos);
                nextAnimalList.add(animal);
            }
        }
        System.out.println("Animal " + animal + " moved forward " + currPos + " -> " + nextPos);
    }

    @Override
    public void turn(Animal animal, int timesTurned) {
        animal.turn(timesTurned);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.get(position) != null || plants.get(position) != null;
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        if (animals.get(position) != null )
            return animals.get(position);
        return plants.get(position);

    }

    @Override
    public List<WorldElement> getElements() {
        List<WorldElement> allElements = new ArrayList<>();
        for (Map.Entry<Vector2d, AnimalList> entry : animals.entrySet()) {
            WorldElement currElement = entry.getValue();
            allElements.add(currElement);
        }
        return allElements;
    }

    @Override
    public UUID getId() {
        return this.mapId;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return (position.getX() >= 0
                && position.getX() < width
                && position.getY() >= 0
                && position.getY() < height
        );
    }

    @Override
    public String toString() {
        MapVisualizer toVisualize = new MapVisualizer(this);
        return toVisualize.draw(new Vector2d(0, 0), new Vector2d(width - 1, height - 1));
    }

    public List<Animal> createCurrAnimalList() {
//        List<Animal> currAnimalList = new ArrayList<>();
//        for (Map.Entry<Vector2d, Animal> entry : animals.entrySet()) {
//            Animal currAnimal = entry.getValue();
//            currAnimalList.add(currAnimal);
//        }
//        return currAnimalList;
        return livingAnimals;
    }

    public void moveAnimalByGene(Animal animal, int dayNo) {
        int geneNo = (animal.getStartGeneId() + dayNo) % settings.getGenomeLength();
        int turnVal = animal.getGenes()[geneNo];
        turn(animal, turnVal);
        moveForward(animal);
    }

    public void moveAllAnimalsByGene(int dayNo) {
        List<Animal> animalsToMove = createCurrAnimalList();
        for (Animal currAnimal : animalsToMove) {
            moveAnimalByGene(currAnimal, dayNo);
        }
    }

    public void changeOneAnimalsEnergy(Animal animal, int dEnergy) {
        animal.changeEnergy(dEnergy);
    }

    public void changeAllAnimalsEnergy(int dEnergy) {
        List<Animal> animalsToChange = createCurrAnimalList();
        for (Animal currAnimal : animalsToChange) {
            changeOneAnimalsEnergy(currAnimal, dEnergy);
        }
    }

    public void removeDeadAnimals(int dayNo) {
        Iterator<Animal> iterator = livingAnimals.iterator();

//        List<Animal> animalsToCheck = createCurrAnimalList();
        while (iterator.hasNext()) {
            Animal currAnimal = iterator.next();
            if (!currAnimal.isAlive()) {
                currAnimal.setDayOfDeath(dayNo);
                iterator.remove();
                deadAnimals.add(currAnimal);

//                animals.remove(currAnimal.getPosition());



                // animal removal procedure

                Vector2d currPos = currAnimal.getPosition();
                AnimalList currPosAnimalList = animals.get(currPos);
                if (currPosAnimalList.size() == 1) {
                    animals.remove(currPos);
                }
                else {
                    currPosAnimalList.remove(currAnimal);
                }

            }
        }
    }

//    public void animalEatsPlantIfPossible(Animal animal) {
//        Vector2d position = animal.getPosition();
//        if (plants.get(position) != null) {
//            plants.remove(position);
//            animal.eatPlant();
//        }
//    }

//    public void allAnimalsEatPlantIfPossible() {
//        List<Animal> currAnimalList = createCurrAnimalList();
//        for (Animal currAnimal : currAnimalList) {
//            animalEatsPlantIfPossible(currAnimal);
//        }
//    }

    public Animal findBestAnimal (AnimalList animalList) {
        List<Animal> animalsFromTheList = animalList.getAnimals();
        // ASSUMPTION: this list is not empty

        // find the animals with the most energy
        int maxEnergy = -1;
        List<Animal> greatestEnergyAnimals = new ArrayList<>();
        for (Animal animal : animalsFromTheList) {
            int currEnergy = animal.getEnergy();
            if (currEnergy > maxEnergy) {
                maxEnergy = currEnergy;
                greatestEnergyAnimals = new ArrayList<>();
                greatestEnergyAnimals.add(animal);
            }
            else if (currEnergy == maxEnergy) {
                greatestEnergyAnimals.add(animal);
            }
        }
        if (greatestEnergyAnimals.size() == 1) {
            return greatestEnergyAnimals.get(0);
        }

        // among animals with the most energy, find the oldest animal
        int maxDaysLived = -1;
        List<Animal> oldestAnimals = new ArrayList<>();
        for (Animal animal : greatestEnergyAnimals) {
            int currDaysLived = animal.getDaysLived();
            if (currDaysLived > maxDaysLived) {
                maxDaysLived = currDaysLived;
                oldestAnimals = new ArrayList<>();
                oldestAnimals.add(animal);
            }
            else if (currDaysLived == maxDaysLived) {
                oldestAnimals.add(animal);
            }
        }
        if (oldestAnimals.size() == 1) {
            return oldestAnimals.get(0);
        }

        // among the oldest animals, find the animal with the most children
        int maxChildrenCount = -1;
        List<Animal> greatestChildrenCountAnimals = new ArrayList<>();
        for (Animal animal : oldestAnimals) {
            int currChildrenCount = animal.getChildrenCount();
            if (currChildrenCount > maxChildrenCount) {
                maxChildrenCount = currChildrenCount;
                greatestChildrenCountAnimals = new ArrayList<>();
                greatestChildrenCountAnimals.add(animal);
            }
            else if (currChildrenCount == maxChildrenCount) {
                greatestChildrenCountAnimals.add(animal);
            }
        }
        if (greatestChildrenCountAnimals.size() == 1) {
            return greatestChildrenCountAnimals.get(0);
        }

        // chose the random animal from the remaining list
        Random rand = new Random();
        int upperBound = greatestChildrenCountAnimals.size();
        int randIdx = rand.nextInt(upperBound);
        return greatestChildrenCountAnimals.get(randIdx);
    }

    public void plantConsumptionOnFieldIfPossible(Vector2d position) {
        AnimalList animalsOnField = animals.get(position);
        if (animalsOnField != null && plants.get(position) != null) {
            Animal animal = animalsOnField.get(0);
            if (animalsOnField.size() > 1) {
                animal = findBestAnimal(animalsOnField);
            }
            removePlant(position);
            animal.eatPlant();
//            System.out.println("ATTENTION!!! Animal ate plant at " + position);
        }
    }

    public void massivePlantConsumption() {
        List<Vector2d> fieldsToIterate = new ArrayList<>();
        if (animals.size() < plants.size()) {
            for (Map.Entry<Vector2d, AnimalList> entry : animals.entrySet()) {
                fieldsToIterate.add(entry.getKey());
            }
        }
        else {
            for (Map.Entry<Vector2d, Plant> entry : plants.entrySet()) {
                fieldsToIterate.add(entry.getKey());
            }
        }

        // for every "field to iterate", call method plantConsumptionOnFieldIfPossible
        for (Vector2d fieldToIterate : fieldsToIterate) {
            plantConsumptionOnFieldIfPossible(fieldToIterate);
        }
    }

    public void reproduceOnFieldIfPossible(Vector2d position, int currDay) {

        // check if reproduction on the chosen field is possible (with the number of animals there)
        if (animals.get(position) == null || animals.get(position).size() < 2) {
            return;
        }

//        System.out.println("\t Attempting reproduction at " + position + "...");

        // determine which pair of animals will reproduce
        AnimalList animalList = animals.get(position);
        Animal bestAnimal1 = findBestAnimal(animalList);
        animalList.remove(bestAnimal1);
        Animal bestAnimal2 = findBestAnimal(animalList);
        animalList.add(bestAnimal1);

        // check if the chosen pair of animals has enough energy to reproduce
        // otherwise do not reproduce
        if (bestAnimal1.getEnergy() < settings.getReproductionEnergyThreshold() || bestAnimal2.getEnergy() < settings.getReproductionEnergyThreshold()) {
//            System.out.println("\t Too little energy!" + bestAnimal1.getEnergy() + " <-> " + bestAnimal2.getEnergy());
            return;
        }

        // transfer energy (settings.energyUsedByParents) from parents to children


        // determine how many genes of the first (stronger) animal will be inherited to the child
        // other genes will be inherited from the second (weaker) animal
        int energySum = bestAnimal1.getEnergy() + bestAnimal2.getEnergy();
        double bestAnimal1EnergyShare = (double) bestAnimal1.getEnergy() / energySum;
        int bestAnimal1InheritedGeneCount = (int) (bestAnimal1EnergyShare * settings.getGenomeLength());
        int bestAnimal2InheritedGeneCount = settings.getGenomeLength() - bestAnimal1InheritedGeneCount;

        // determine (random) if the stronger animal passes the genes on the left side or on the right side of the genotype
        Random rand = new Random();
        int randRes = rand.nextInt(2); // 0 -- left, 1 -- right
//        System.out.println("\t taking " + bestAnimal1InheritedGeneCount + " genes from animal "+bestAnimal1+" and " + bestAnimal2InheritedGeneCount + " genes from animal "+bestAnimal2);
//        System.out.println("\t\t side: " + randRes);

        // create the genes of the child
        int[] genes1 = bestAnimal1.getGenes();
        int[] genes2 = bestAnimal2.getGenes();

        int[] childGenes = new int[settings.getGenomeLength()];

        if (randRes == 0) {
            for (int i = 0; i < bestAnimal1InheritedGeneCount; i++) {
                childGenes[i] = genes1[i];
            }
            for (int i = bestAnimal1InheritedGeneCount; i < settings.getGenomeLength(); i++) {
                childGenes[i] = genes2[i];
            }
        }
        else {
            for (int i = 0; i < bestAnimal2InheritedGeneCount; i++) {
                childGenes[i] = genes2[i];
            }
            for (int i = bestAnimal2InheritedGeneCount; i < settings.getGenomeLength(); i++) {
                childGenes[i] = genes1[i];
            }
        }

        // create the child (new Animal) of the animals that have reproduced
        Animal child = new Animal(position, settings, currDay);
        child.setParent1(bestAnimal1);
        child.setParent2(bestAnimal2);
        child.setGenes(childGenes);
        bestAnimal1.changeEnergy((-1)*settings.getEnergyUsedByParents());
        bestAnimal2.changeEnergy((-1)*settings.getEnergyUsedByParents());
        child.setEnergy(2*settings.getEnergyUsedByParents());

        // mutations
        if (settings.getMutationType() == 2) {
            mutateByReplacementOrSwap(child);
        }
        else {
            mutateByReplacement(child);
        }

        // end
        place(child);
//        System.out.println("\t Reproduction successful!");
    }

    public void mutateByReplacement(Animal animal) {
        // select a random number of genes in [minMutationCount; maxMutationCount]
        Random rand = new Random();
        int upperBound = settings.getMaxMutationCount();
        int lowerBound = settings.getMinMutationCount();
        int genesToAlter = rand.nextInt(upperBound + 1 - lowerBound) + lowerBound;

        // select which genes will be altered and alter them
        int allGenesCnt = settings.getGenomeLength();
        for (int i = 0; i < genesToAlter; i++) {
            Random rand2 = new Random();
            int oldGeneIdx = rand2.nextInt(allGenesCnt);
            int newGeneVal = rand2.nextInt(8);
            int[] animalGenes = animal.getGenes();
            System.out.println("Animal " + animal + " has changed its gene (#" + oldGeneIdx + ") " + animalGenes[oldGeneIdx] + " -> " + newGeneVal);
            animalGenes[oldGeneIdx] = newGeneVal;
            animal.setGenes(animalGenes);
        }
    }

    public void mutateByReplacementOrSwap(Animal animal) {
        // select a random number of genes in [minMutationCount; maxMutationCount]
        Random rand = new Random();
        int upperBound = settings.getMaxMutationCount();
        int lowerBound = settings.getMinMutationCount();
        int genesToAlter = rand.nextInt(upperBound + 1 - lowerBound) + lowerBound;

        // select which genes will be altered and alter them
        int allGenesCnt = settings.getGenomeLength();
        for (int i = 0; i < genesToAlter; i++) {
            // select random gene
            Random rand2 = new Random();
            int oldGeneIdx = rand2.nextInt(allGenesCnt);

            // select which mutation type will take place
            // 0 -> replacement
            // 1 -> swap
            Random rand3 = new Random();
            int singleMutationType = rand3.nextInt(2);
            int[] animalGenes = animal.getGenes();

            if (singleMutationType == 1) {
                int geneToReplaceWithIdx = rand2.nextInt(allGenesCnt);
                int geneVal1 = animalGenes[oldGeneIdx];
                int geneVal2 = animalGenes[geneToReplaceWithIdx];
                animalGenes[geneToReplaceWithIdx] = geneVal1;
                animalGenes[oldGeneIdx] = geneVal2;
                System.out.println("Animal " + animal + " has swapped its gene (#" + oldGeneIdx + ") <-> (#" + geneToReplaceWithIdx + ")" );
            }
            else {
                int newGeneVal = rand2.nextInt(8);
                System.out.println("Animal " + animal + " has changed its gene (#" + oldGeneIdx + ") " + animalGenes[oldGeneIdx] + " -> " + newGeneVal);
                animalGenes[oldGeneIdx] = newGeneVal;
                animal.setGenes(animalGenes);
            }
        }
    }

    public void reproduceOnEveryPossibleField(int currDay) {
        List<Vector2d> fieldsToIterate = new ArrayList<>();
        for (Map.Entry<Vector2d, AnimalList> entry : animals.entrySet()) {
            fieldsToIterate.add(entry.getKey());
        }
        for (Vector2d fieldToIterate : fieldsToIterate) {
            reproduceOnFieldIfPossible(fieldToIterate, currDay);
        }
    }

    public Vector2d randomField() {
        Random rand1 = new Random();
        int randX = rand1.nextInt(width);
        int randY = rand1.nextInt(height);
        return new Vector2d(randX, randY);
    }

    public Vector2d randomFieldEquator() {
        Random rand1 = new Random();
        int randX = rand1.nextInt(width);
        int randY = rand1.nextInt(maxEquatorHeight - minEquatorHeight) + minEquatorHeight;
        return new Vector2d(randX, randY);
    }

    public Vector2d randomFieldNonEquator() {
        Random rand1 = new Random();
        int aboveEquator = rand1.nextInt(2);
        int randX = rand1.nextInt(width);
        int randY;

        if (aboveEquator == 1) {
            randY = rand1.nextInt(height - maxEquatorHeight) + maxEquatorHeight;
        }
        else {
            randY = rand1.nextInt(minEquatorHeight);
        }
        return new Vector2d(randX, randY);
    }


    public Map<Vector2d, AnimalList> getAnimals() {
        return animals;
    }

    public int getActivatedGeneIdx(Animal animal, int dayNo) {
        return (animal.getStartGeneId() + dayNo) % settings.getGenomeLength();
    }

    public int getAnimalCount() {
        int res = 0;

        for (Map.Entry<Vector2d, AnimalList> entry : animals.entrySet()) {
            AnimalList currList = entry.getValue();
            res += currList.size();
        }

        return res;
    }

    public int getPlantCount() {
        return plants.size();
    }

    public int getEmptyFieldCount() {
        return ((width) * (height)) - getAnimalCount() - getPlantCount();
    }

    public double getAvgEnergy() {
        List<Animal> currAnimalList = createCurrAnimalList();
        if (currAnimalList.isEmpty())
            return -1;
        int energySum = 0;
        for (Animal animal : currAnimalList) {
            energySum += animal.getEnergy();
        }
        return (double) energySum / (double) currAnimalList.size();
    }

    public double getAvgLifespanOfDeadAnimals() {
        List<Animal> deadAnimalList = deadAnimals;
        if (deadAnimalList.isEmpty())
            return -1;
        int daysLivedSum = 0;
        for (Animal animal : deadAnimalList) {
            daysLivedSum += animal.getDaysLived();
        }
        return (double) daysLivedSum / (double) deadAnimalList.size();
    }

    public double getAvgChildrenCount() {
        List<Animal> currAnimalList = createCurrAnimalList();
        if (currAnimalList.isEmpty())
            return -1;
        int childrenSum = 0;
        for (Animal animal : currAnimalList) {
            childrenSum += animal.getChildrenCount();
        }
        return (double) childrenSum / (double) currAnimalList.size();
    }

    public void increaseDayCountOfAllAnimals() {
        List<Animal> currAnimalList = createCurrAnimalList();
        for (Animal animal : currAnimalList) {
            animal.addOneDay();
        }
    }

//    public void trackGenome(int[] genome) {
//        Integer[] genomeInt = (Integer[]) genome;
//    }

    public int findMostFrequentGene() {
        List<Animal> currAnimalList = createCurrAnimalList();
        int[] geneOccurrences = new int[8];
        for (Animal animal : currAnimalList) {
            int[] currGenes = animal.getGenes();
            for (int gene : currGenes) {
                geneOccurrences[gene]++;
            }
        }
        int mostFrequentGene = -1;
        int mostFrequentGeneCount = -1;
        for (int i = 0; i < 8; i++) {
            if (geneOccurrences[i] > mostFrequentGeneCount) {
                mostFrequentGene = i;
                mostFrequentGeneCount = geneOccurrences[i];
            }
        }
        return mostFrequentGene;
    }

    public void removePlant(Vector2d position) {
        plants.remove(position);
        if (minEquatorHeight <= position.getY() && position.getY() < maxEquatorHeight) {
            freeEquatorFields++;
            takenEquatorFields--;
        }
        else {
            freeNonEquatorFields++;
            takenNonEquatorFields--;
        }
    }

    public int getFreeEquatorFields() {
        return freeEquatorFields;
    }

    public int getFreeNonEquatorFields() {
        return freeNonEquatorFields;
    }

    public int getTakenEquatorFields() {
        return takenEquatorFields;
    }

    public int getTakenNonEquatorFields() {
        return takenNonEquatorFields;
    }
}
