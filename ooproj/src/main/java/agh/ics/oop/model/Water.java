package agh.ics.oop.model;

public class Water implements WorldElement {

    private final Vector2d position;


    public Water(Vector2d position) {
        this.position = position;
    }

    @Override
    public Vector2d getPosition() {
        return null;
    }

    @Override
    public String toString() {
        return "=";
    }
}
