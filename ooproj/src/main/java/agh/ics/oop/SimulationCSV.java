package agh.ics.oop;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class SimulationCSV {
    private Path currentFilePath;

    private Simulation simulation;

    public SimulationCSV(Simulation simulation) {
        this.simulation = simulation;
    }

    public void convertSimulationToCSV(String baseFileName, String directoryPath) {

        try {
            Path simulationsFolderPath = Paths.get(directoryPath, "");
            if (!Files.exists(simulationsFolderPath)) {
                Files.createDirectory(simulationsFolderPath);
            }
            if (currentFilePath == null) {
                Path baseFilePath = simulationsFolderPath.resolve(baseFileName + ".csv");
                currentFilePath = createFilePath(baseFilePath);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (currentFilePath == null) {
            Path directory = Paths.get(directoryPath);
            Path baseFilePath = directory.resolve(baseFileName + ".csv");
            currentFilePath = createFilePath(baseFilePath);
        }

        StringBuilder csvBuilder = new StringBuilder();

        if (!Files.exists(currentFilePath)) {
            csvBuilder.append("Statistic,Value\n");
        }

        csvBuilder.append("Day,").append(simulation.getSimDayCnt()).append("\n");
        csvBuilder.append("Animal count,").append(simulation.getMap().getAnimalCount()).append("\n");
        csvBuilder.append("Plant count,").append(simulation.getMap().getPlantCount()).append("\n");
        csvBuilder.append("Empty Field count,").append(simulation.getMap().getEmptyFieldCount()).append("\n");
        csvBuilder.append("Avg Energy,").append(simulation.getMap().getAvgEnergy()).append("\n");
        csvBuilder.append("Avg Lifespan,").append(simulation.getMap().getAvgLifespanOfDeadAnimals()).append("\n");
        csvBuilder.append("Avg Children count,").append(simulation.getMap().getAvgChildrenCount()).append("\n");
        csvBuilder.append("Dominant Genotype,").append(simulation.findMostPopularGenotype(simulation.getMap()) != null ? simulation.findMostPopularGenotype(simulation.getMap()).toString() : "None").append("\n");
        csvBuilder.append("\n");

        try {
            Files.write(currentFilePath, csvBuilder.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private Path createFilePath(Path originalPath) {
        int counter = 1;
        Path filePath = originalPath;

        while (Files.exists(filePath)) {
            String fileName = String.format("%s(%d).csv", originalPath.toString().replace(".csv", ""), counter);
            filePath = Paths.get(fileName);
            counter++;
        }

        return filePath;
    }


}
