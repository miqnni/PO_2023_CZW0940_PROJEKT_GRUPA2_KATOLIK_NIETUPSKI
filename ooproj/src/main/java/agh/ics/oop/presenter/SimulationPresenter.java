package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SimulationPresenter implements SimulationChangeListener {

    @FXML
    public Label dayNumber;
    public Button startButton;
    public Spinner<Integer> mapWidthSpinner;
    public Spinner<Integer> mapHeightSpinner;
    public Spinner<Integer> startPlantCountSpinner;
    public Spinner<Integer> energyPerPlantSpinner;
    public Spinner<Integer> newPlantsPerDaySpinner;
    public Spinner<Integer> startAnimalCountSpinner;
    public Spinner<Integer> startAnimalEnergySpinner;
    public Spinner<Integer> reproductionEnergyThresholdSpinner;
    public Spinner<Integer> energyUsedByParentsSpinner;
    public Spinner<Integer> minMutationCountSpinner;
    public Spinner<Integer> maxMutationCountSpinner;
    public Spinner<Integer> mutationTypeSpinner;
    public Spinner<Integer> genomeLengthSpinner;
//    public Spinner<Integer> mapTypeSpinner;
    public Spinner<Integer> durationInDaysSpinner;
    public Spinner<Integer> halfCycleLengthSpinner;
    public Spinner<Integer> waterRangeLimitSpinner;
    public Label statsLabel;
    public Button stopButton;
    public CheckBox saveCheckbox;
    public ComboBox<String> mapTypeComboBox;
    public ComboBox<String> mutationTypeComboBox;
    public HBox actionButtons;
    public HBox onlyBorder;
    public GridPane mainGridPane;
    public Button savePresetButton;
    public Button loadPresetButton;
    public GridPane mapGrid;
    public Label animalStatsLabel;
    public Button stopTrackingButton;
    public HBox presetButtons;
    @FXML
    private Label infoLabel;
    private Simulation simulation;
    private Animal trackedAnimal;


    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public void drawSimulationStatus(String message) {
        dayNumber.setText(message);
//        infoLabel.setText(this.simulation.getTestMap().toString());
        statsLabel.setText(simulation.getStatsAsString());
    }

    @Override
    public void simulationChanged(Simulation simulation, String message) {
        Platform.runLater(() -> {
            drawSimulationStatus(message);
            renderMapGrid();
        });
    }

    private String calculateColor(int energy, int maxEnergy) {
        maxEnergy = Math.max(maxEnergy, 1);
        int intensity = (int) ((double) energy / maxEnergy * 255);
        intensity = Math.min(255, Math.max(0, intensity));

        return String.format("#%02x%02x%02x", 255 - intensity, 0, Math.min(intensity, 255));
    }

    private void renderMapGrid() {

        clearGrid();
        for (int j = 0; j < simulation.getSettings().getMapHeight(); j++) {
            for (int i = 0; i < simulation.getSettings().getMapWidth(); i++) {
                Label label = new Label(" ");
                Vector2d position = new Vector2d(i, j);
                if (simulation.getTestMap().isOccupied(position)) {
//                    label.setText(simulation.getTestMap().objectAt(position).toString());


                    if (simulation.getTestMap().isOccupiedByWater(position)) {
                        label.getStyleClass().add("waterCell");
                    }
                    else if (simulation.getTestMap().getAnimals().get(position) != null) {
                        label.getStyleClass().add("animalCell");
                        String colorCode = calculateColor(simulation.getTestMap().getAnimals().get(position).findBestAnimal().getEnergy(), 250);
                        label.setStyle("-fx-background-color: " + colorCode + ";");
                    }
                    else if (simulation.getTestMap().getPlants().get(position) != null) {
                        label.getStyleClass().add("plantCell");
                    }

                }
                else {
                    label.getStyleClass().add("emptyCell");
                }

                if (trackedAnimal != null) {
                    animalStatsLabel.setText(trackedAnimal.getAnimalStats());
                }
                label.setOnMouseClicked(event -> {
                    if (simulation.getTestMap().getAnimals().get(position) != null) {
                        trackedAnimal = simulation.getTestMap().getAnimals().get(position).findBestAnimal();
                        animalStatsLabel.setText(trackedAnimal.getAnimalStats());
                        stopTrackingButton.setVisible(true);
                    }
                });


                mapGrid.add(label, i + 1, simulation.getSettings().getMapHeight() - (j));
            }
        }
        for (int i = 0; i < simulation.getSettings().getMapWidth(); i++) {
            Label label = new Label(String.valueOf(i));
            mapGrid.add(label, i + 1, 0);
        }
        for (int j = 0; j < simulation.getSettings().getMapHeight(); j++) {
            Label label = new Label(String.valueOf(j));
            mapGrid.add(label, 0, simulation.getSettings().getMapHeight() - (j));
        }



    }



    public int getSelectedMapTypeValue() {
        String selectedValue = mapTypeComboBox.getValue();

        if ("Water".equals(selectedValue)) {
            return 3;
        } else {
            return 0;
        }
    }

    public int getSelectedMutationTypeValue() {
        String selectedValue = mutationTypeComboBox.getValue();

        if ("Slight Replacement".equals(selectedValue)) {
            return 2;
        } else {
            return 0;
        }
    }

    private void useSettings() {
        simulation.setSettings(new Settings(
                mapWidthSpinner.getValue(),
                mapHeightSpinner.getValue(),
                startPlantCountSpinner.getValue(),
                energyPerPlantSpinner.getValue(),
                newPlantsPerDaySpinner.getValue(),
                startAnimalCountSpinner.getValue(),
                startAnimalEnergySpinner.getValue(),
                reproductionEnergyThresholdSpinner.getValue(),
                energyPerPlantSpinner.getValue(),
                minMutationCountSpinner.getValue(),
                maxMutationCountSpinner.getValue(),
                getSelectedMutationTypeValue(),
                genomeLengthSpinner.getValue(),
                getSelectedMapTypeValue(),
                durationInDaysSpinner.getValue(),
                halfCycleLengthSpinner.getValue(),
                waterRangeLimitSpinner.getValue()
        ));
    }


    @FXML
    private void initialize() {
        stopButton.setDisable(true);
        stopTrackingButton.setVisible(false);
        mapTypeComboBox.setValue("Default");
        mutationTypeComboBox.setValue("Pure Randomness");

        startButton.setOnAction(event -> {
            /*System.out.println(mapTypeComboBox.getValue() + getSelectedMapTypeValue());
            System.out.println(mutationTypeComboBox.getValue() + getSelectedMutationTypeValue());*/

            startButton.setDisable(true);
            stopButton.setDisable(false);
            loadPresetButton.setDisable(true);
            useSettings();
            if (!simulation.isPrepared()) {
                simulation.prepare();
                trackedAnimal = null;
                if(saveCheckbox.isSelected()) {
                    simulation.setSaveToCSV(true);
                    simulation.setCurrentFilePath("logs");
                }
            }
            simulation.getParentEngine().runAsync();
            }

        );

    }

    public void onStopClicked(ActionEvent actionEvent) {
        stopButton.setDisable(true);
        startButton.setDisable(false);
        if (simulation.getSimDayCnt() == simulation.getSettings().getDurationInDays()) {
            loadPresetButton.setDisable(false);
        }
        simulation.stop();
    }

    public void onLoadPresetClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Configuration File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File file = fileChooser.showOpenDialog(mainGridPane.getScene().getWindow());

        if (file != null) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                List<String> lines = new ArrayList<>();

                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
                reader.close();

                simulation.getSettings().fromText(lines.toArray(new String[0]));
                updateUIWithConfig();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateUIWithConfig() {
        mapWidthSpinner.getValueFactory().setValue(simulation.getSettings().getMapWidth());
        mapHeightSpinner.getValueFactory().setValue(simulation.getSettings().getMapHeight());
        startPlantCountSpinner.getValueFactory().setValue(simulation.getSettings().getStartPlantCount());
        energyPerPlantSpinner.getValueFactory().setValue(simulation.getSettings().getEnergyPerPlant());
        newPlantsPerDaySpinner.getValueFactory().setValue(simulation.getSettings().getNewPlantsPerDay());
        startAnimalCountSpinner.getValueFactory().setValue(simulation.getSettings().getStartAnimalCount());
        startAnimalEnergySpinner.getValueFactory().setValue(simulation.getSettings().getStartAnimalEnergy());
        reproductionEnergyThresholdSpinner.getValueFactory().setValue(simulation.getSettings().getReproductionEnergyThreshold());
        energyUsedByParentsSpinner.getValueFactory().setValue(simulation.getSettings().getEnergyUsedByParents());
        minMutationCountSpinner.getValueFactory().setValue(simulation.getSettings().getMinMutationCount());
        maxMutationCountSpinner.getValueFactory().setValue(simulation.getSettings().getMaxMutationCount());
        mutationTypeComboBox.setValue(simulation.getSettings().getMutationType() == 2 ? "Slight Replacement" : "Pure Randomness");
        genomeLengthSpinner.getValueFactory().setValue(simulation.getSettings().getGenomeLength());
        mapTypeComboBox.setValue(simulation.getSettings().getMapType() == 3 ? "Water" : "Default");
        durationInDaysSpinner.getValueFactory().setValue(simulation.getSettings().getDurationInDays());
        halfCycleLengthSpinner.getValueFactory().setValue(simulation.getSettings().getHalfCycleLength());
        waterRangeLimitSpinner.getValueFactory().setValue(simulation.getSettings().getWaterRangeLimit());
    }

    @FXML
    private void onSavePresetClicked() {
        useSettings();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Configuration File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showSaveDialog(mainGridPane.getScene().getWindow());

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                String configText = simulation.getSettings().toText();
                writer.write(configText);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void clearGrid() {
        if (simulation.getSimDayCnt() > 0) {
//            mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0));
            List<Node> childrenToRemove = new ArrayList<>(mapGrid.getChildren());
            mapGrid.getChildren().removeAll(childrenToRemove);
            mapGrid.getColumnConstraints().clear();
            mapGrid.getRowConstraints().clear();
        }
    }


    public void onStopTrackingClicked(ActionEvent actionEvent) {
        trackedAnimal = null;
        stopTrackingButton.setVisible(false);
        animalStatsLabel.setText("");
    }
}

