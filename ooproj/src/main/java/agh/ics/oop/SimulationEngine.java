package agh.ics.oop;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {

    private final List<Simulation> simulationList;

    private ExecutorService executorService;

    public SimulationEngine(List<Simulation> simulationList) {
        this.simulationList = simulationList;
    }

    public void runAsync() {
        for (Simulation simulation : simulationList) {
            Thread simThread = new Thread(simulation);
            simThread.start();
        }
    }

    public void awaitSimulationEnd() throws InterruptedException {
        executorService.shutdown();
        if(!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
            executorService.shutdownNow();
        }
    }

}
