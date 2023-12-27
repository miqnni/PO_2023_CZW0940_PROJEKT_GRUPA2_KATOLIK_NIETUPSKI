package agh.ics.oop.model;

public class Plant implements WorldElement{
    private final Vector2d position;

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public Plant(Vector2d position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "*";
    }
}
