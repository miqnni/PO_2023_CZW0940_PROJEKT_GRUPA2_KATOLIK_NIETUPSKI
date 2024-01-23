package agh.ics.oop;

import agh.ics.oop.model.AbstractWorldMap;

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

    public void toCSV(String baseFileName, String directoryPath) {

        try {
            Path simulationsFolderPath = Paths.get(directoryPath, "");
            if (!Files.exists(simulationsFolderPath)) {
                Files.createDirectory(simulationsFolderPath);
            }
            if (currentFilePath == null) {
                Path baseFilePath = simulationsFolderPath.resolve(baseFileName + ".csv");
                currentFilePath = createUniqueFilePath(baseFilePath);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (currentFilePath == null) {
            Path directory = Paths.get(directoryPath);
            Path baseFilePath = directory.resolve(baseFileName + ".csv");
            currentFilePath = createUniqueFilePath(baseFilePath);
        }

        StringBuilder csvBuilder = new StringBuilder();

        if (!Files.exists(currentFilePath)) {
            csvBuilder.append("Statistic,Value\n");
        }

        csvBuilder.append("Day,").append(simulation.getSimDayCnt()).append("\n");
        csvBuilder.append("Animal count,").append(simulation.getTestMap().getAnimalCount()).append("\n");
        csvBuilder.append("Plant count,").append(simulation.getTestMap().getPlantCount()).append("\n");
        csvBuilder.append("Empty Field count,").append(simulation.getTestMap().getEmptyFieldCount()).append("\n");
        csvBuilder.append("Avg Energy,").append(simulation.getTestMap().getAvgEnergy()).append("\n");
        csvBuilder.append("Avg Lifespan,").append(simulation.getTestMap().getAvgLifespanOfDeadAnimals()).append("\n");
        csvBuilder.append("Avg Children count,").append(simulation.getTestMap().getAvgChildrenCount()).append("\n");
        csvBuilder.append("Dominant Genotype,").append(simulation.findMostPopularGenotype(simulation.getTestMap()) != null ? simulation.findMostPopularGenotype(simulation.getTestMap()).toString() : "No Dominant Genotype").append("\n");
        csvBuilder.append("\n");

        try {
            Files.write(currentFilePath, csvBuilder.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private Path createUniqueFilePath(Path originalPath) {
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
