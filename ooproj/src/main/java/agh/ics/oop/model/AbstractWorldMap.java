package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public class AbstractWorldMap implements WorldMap {
    protected Map<Vector2d, Animal> animals = new HashMap<>();
    protected Map<Vector2d, Plant> plants = new HashMap<>();

    protected double[][] plantGrowthChance;

    protected UUID mapId;

    private final int width;
    private final int height;

    private final int genomeLength;

    public AbstractWorldMap(Settings settings) {
        this.mapId = UUID.randomUUID();
        this.width = settings.getMapWidth();
        this.height = settings.getMapHeight();
        this.plantGrowthChance = new double[width][height];
        for (int xi = 0; xi < width; xi++) {
            for (int yi = 0; yi < height; yi++) {
                plantGrowthChance[xi][yi] = 0.003;
            }
        }
        this.genomeLength = settings.getGenomeLength();
    }


    @Override
    public void place(Animal animal) {
        Vector2d animalPos = animal.getPosition();
//        if (canMoveTo(animalPos)) {
//            animals.put(animalPos, animal);
//        }
        animals.put(animalPos, animal);
    }

    public void growPlant(Vector2d position) {
        Plant plant = new Plant(position);
        plants.put(position, plant);
    }

    public void growPlantMassive() {
        for (int xi = 0; xi < width; xi++) {
            for (int yi = 0; yi < height; yi++) {
                Random rand = new Random();
                double growthTestResult = rand.nextDouble();
                double growthChance = plantGrowthChance[xi][yi];
                if (growthTestResult < growthChance) {
                    growPlant(new Vector2d(xi, yi));
                }
            }
        }
    }

    @Override
    public void moveForward(Animal animal) {
        Vector2d currPos = animal.getPosition();
        animal.moveForward(this);
        Vector2d nextPos = animal.getPosition();
        if (!currPos.equals(nextPos)) {
            animals.remove(currPos);
            animals.put(nextPos, animal);
        }
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
        for (Map.Entry<Vector2d, Animal> entry : animals.entrySet()) {
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

    public void moveAnimalByGene(Animal animal, int dayNo) {
        int geneNo = dayNo % genomeLength;
        int turnVal = animal.getGenes()[geneNo];
        turn(animal, turnVal);
        moveForward(animal);
    }

    public void moveAllAnimalsByGene(int dayNo) {
        List<Animal> animalsToMove = new ArrayList<>();
        for (Map.Entry<Vector2d, Animal> entry : animals.entrySet()) {
            Animal currAnimal = entry.getValue();
            animalsToMove.add(currAnimal);
        }
        for (Animal currAnimal : animalsToMove) {
            moveAnimalByGene(currAnimal, dayNo);
        }
    }

    public Vector2d randomField() {
        Random rand1 = new Random();
        int randX = rand1.nextInt(width);
        int randY = rand1.nextInt(height);
        return new Vector2d(randX, randY);
    }

    public Map<Vector2d, Animal> getAnimals() {
        return animals;
    }
}
