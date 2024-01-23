package agh.ics.oop.model;

import agh.ics.oop.Simulation;

public class ConsoleSimulationDisplay implements SimulationChangeListener {
    @Override
    public void simulationChanged(Simulation simulation, String message) {
        System.out.print(message);
        System.out.printf(" // Simulation %s\n", simulation.getSimulationId());
        System.out.println(simulation.getTestMap());
        simulation.printStats();
        System.out.println("\n" + "================================" + "\n");
    }
}
