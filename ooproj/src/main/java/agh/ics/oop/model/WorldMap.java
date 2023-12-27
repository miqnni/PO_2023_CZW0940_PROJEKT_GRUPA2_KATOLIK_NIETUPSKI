package agh.ics.oop.model;

import java.util.List;
import java.util.UUID;

public interface WorldMap extends MoveValidator {
    void place(Animal animal);
    void moveForward(Animal animal);
    void turn(Animal animal, int timesTurned);

    boolean isOccupied(Vector2d position);
    WorldElement objectAt(Vector2d position);
    List<WorldElement> getElements();
    UUID getId();
}
