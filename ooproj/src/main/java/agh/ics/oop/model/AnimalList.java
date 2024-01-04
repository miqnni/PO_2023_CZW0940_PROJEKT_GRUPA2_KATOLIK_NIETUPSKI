package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;

public class AnimalList implements WorldElement {

    private List<Animal> animals;

    public AnimalList() {
        animals = new ArrayList<>();
    }

    @Override
    public Vector2d getPosition() {
        return null;
    }

    @Override
    public String toString() {
        return animals.toString();
    }


    public int size() {
        return animals.size();
    }

    public void remove(Animal animal) {
        animals.remove(animal);
    }

    public void add(Animal animal) {
        animals.add(animal);
    }

    public Animal get(int idx) {
        return animals.get(idx);
    }

    public List<Animal> getAnimals() {
        return animals;
    }

}
