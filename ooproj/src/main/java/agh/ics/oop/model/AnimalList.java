package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        Animal bestAnimal = findBestAnimal();
        return bestAnimal.toString();
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

    public Animal findBestAnimal() {
        List<Animal> animalsFromTheList = this.getAnimals();
        // ASSUMPTION: this list is not empty

        // find the animals with the most energy
        int maxEnergy = -1;
        List<Animal> greatestEnergyAnimals = new ArrayList<>();
        for (Animal animal : animalsFromTheList) {
            if (animal != null) {
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

}
