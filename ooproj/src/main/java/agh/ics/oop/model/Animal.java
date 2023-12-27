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
    }

    @Override
    public String toString() {
//        return orientation.toString();
        return "O";
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
        for (int i = 0; i < timesTurned; i++)
            currDir = next(currDir);
        orientation = currDir;
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

    public Object getEnergy() {
        return energy;
    }
}
