package agh.ics.oop.model;

import java.util.*;

import static agh.ics.oop.model.MapDirection.next;

public class Animal implements WorldElement {
    private MapDirection orientation;
    private Vector2d position;
    private int energy;
    private int childrenCount;
    private boolean alive;
    private int daysLived;
    private final int dayOfBirth;
    private int dayOfDeath;
//    private Genome genome;
    private int[] genes;

    private Genome genome;

    private int startGeneId;

    private int plantsEaten;

    private Settings settings;

    private Animal parent1;
    private Animal parent2;

    private List<Animal> children;

    private DefaultWorldMap mapWhereItLives;

    private final char letterMark;

    public Animal(Vector2d position, Settings settings, int dayOfBirth) {

        Random rand = new Random();

        // LOSOWA ORIENTACJA
        MapDirection[] directions = MapDirection.values();
        this.orientation = directions[rand.nextInt(directions.length)];

        this.position = position;
        this.daysLived = 0;
        this.energy = settings.getStartAnimalEnergy();
        this.dayOfBirth = dayOfBirth;
        this.alive = true;
        this.genes = new int[settings.getGenomeLength()];
        this.genome = new Genome(genes);
        for (int i = 0; i < settings.getGenomeLength(); i++) {
            int newGene = rand.nextInt(8);
            genes[i] = newGene;
        }

        // LOSOWY GEN STARTOWY
        this.startGeneId = rand.nextInt(settings.getGenomeLength());

        this.childrenCount = 0;
        this.children = new ArrayList<>();
        this.plantsEaten = 0;
        this.settings = settings;

        this.letterMark = (char)(rand.nextInt(90 - 65 + 1) + 65);
    }

    public void setStartGeneId(int startGeneId) {
        this.startGeneId = startGeneId;
    }

    @Override
    public String toString() {

        return String.valueOf(letterMark);
    }

    public boolean isAt(Vector2d position) {
        return position.equals(this.position);
    }

    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public int[] getGenes() {
        return genes;
    }

    public void moveForward(MoveValidator mValid) {
        int boundX = settings.getMapWidth();
        int boundY = settings.getMapHeight();
        Vector2d toAdd = MapDirection.toUnitVector(orientation);
        Vector2d newLocation = position.add(toAdd);



        if (newLocation.getX() >= boundX) {
            newLocation = new Vector2d(0, newLocation.getY());
        }
        else if (newLocation.getX() < 0) {
            newLocation = new Vector2d(boundX - 1, newLocation.getY());
        }

        if (mapWhereItLives.isOccupiedByWater(newLocation)) {
            return;
        }

        if (newLocation.getY() >= boundY || newLocation.getY() < 0) {
            newLocation = new Vector2d(newLocation.getX(), position.getY());
            if (mapWhereItLives.isOccupiedByWater(newLocation)) {
                return;
            }
            turn(4);
        }

        if (mValid.canMoveTo(newLocation)) {
            position = newLocation;
        }
    }

    public void undoMove(Vector2d oldLocation) {
        position = oldLocation;
    }

    public void turn(int timesTurned) {
        int tempTimesTurned = timesTurned;
        MapDirection currDir = orientation;
        tempTimesTurned %= 8;
        for (int i = 0; i < tempTimesTurned; i++)
            currDir = next(currDir);
        orientation = currDir;
    }

    public void eatPlant() {
        changeEnergy(settings.getEnergyPerPlant());
        plantsEaten++;
    }

    public void changeEnergy(int dEnergy) {
        energy += dEnergy;
        if (energy <= 0) {
            die();
        }
    }

    public void die() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getEnergy() {
        return energy;
    }

    public int getStartGeneId() {
        return startGeneId;
    }

    public int getDaysLived() {
        return daysLived;
    }

    public void setOrientation(MapDirection orientation) {
        this.orientation = orientation;
    }

    public void setDayOfDeath(int dayOfDeath) {
        this.dayOfDeath = dayOfDeath;
    }

    public void addOneDay() {
        daysLived++;
    }

    public int getChildrenCount() {
        return childrenCount;
    }

    public void setParent1(Animal parent1) {
        this.parent1 = parent1;
    }

    public void setParent2(Animal parent2) {
        this.parent2 = parent2;
    }

    public void setGenes(int[] genes) {
        this.genes = genes;
        this.genome = new Genome(genes);
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setMapWhereItLives(DefaultWorldMap mapWhereItLives) {
        this.mapWhereItLives = mapWhereItLives;
    }

    public Genome getGenome() {
        return genome;
    }

    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }

    public String getAnimalStats() {
        return "Animal info: " + "\n" +
                "Position: " + this.position.toString() + "\n" +
                "Energy: " + this.energy + "\n" +
                "Direction: " + this.orientation.toString() + "\n" +
                "Genotype: " + Arrays.toString(this.genes) + "\n" +
                "Children: " + this.childrenCount + "\n" +
                "Descendants: " + this.getDescendantCount() + "\n" +
                "Eaten plant count: " + this.plantsEaten + "\n" +
                "Birth day: " + this.dayOfBirth + "\n" +
                "Age: " + this.daysLived + "\n" +
                "Alive: " + this.alive + "\n";
    }

    public void addChild(Animal animal) {
        children.add(animal);
    }

    public int getDescendantCount() {
        if (children.isEmpty()) return 0;
        int ans = children.size();

        Iterator<Animal> iterator = children.iterator();

        while (iterator.hasNext()) {
            Animal currAnimal = iterator.next();
            ans += currAnimal.getDescendantCount();
        }

        return ans;
    }
}
