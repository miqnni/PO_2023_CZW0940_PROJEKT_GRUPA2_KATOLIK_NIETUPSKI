package agh.ics.oop.model;

import java.util.Random;

import static agh.ics.oop.model.MapDirection.next;

public class Animal implements WorldElement {
    private MapDirection orientation;
    private Vector2d position;
    private int energy;
    private int childrenCount;
    private boolean alive;
    private int daysLived;
    private int dayOfBirth;
    private int dayOfDeath;
//    private Genome genome;
    private int[] genes;

    private int startGeneId;

    private int plantsEaten;

    private Settings settings;

    private int OZNACZENIE;

    public Animal(Vector2d position, Settings settings, int dayOfBirth) {
        this.orientation = MapDirection.NORTH;
        this.position = position;
        this.daysLived = 0;
        this.energy = settings.getStartAnimalEnergy();
        this.dayOfBirth = dayOfBirth;
        this.alive = true;
//        this.genome = new Genome(settings);
        this.genes = new int[settings.getGenomeLength()];
        for (int i = 0; i < settings.getGenomeLength(); i++) {
            Random rand = new Random();
            int newGene = rand.nextInt(8);
            genes[i] = newGene;
        }
        this.startGeneId = 0;
        this.childrenCount = 0;
        this.plantsEaten = 0;
        this.settings = settings;

        Random random = new Random();
        this.OZNACZENIE = random.nextInt(10);
    }

    @Override
    public String toString() {
//        return orientation.toString();
//        return "O";

        return String.valueOf(OZNACZENIE);
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
        Vector2d toAdd = MapDirection.toUnitVector(orientation);
        Vector2d newLocation = position.add(toAdd);
        if (mValid.canMoveTo(newLocation)) {
            position = newLocation;
        }
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

    public void setDayOfDeath(int dayOfDeath) {
        this.dayOfDeath = dayOfDeath;
    }

    public void addOneDay() {
        daysLived++;
    }

    public int getChildrenCount() {
        return childrenCount;
    }


}
