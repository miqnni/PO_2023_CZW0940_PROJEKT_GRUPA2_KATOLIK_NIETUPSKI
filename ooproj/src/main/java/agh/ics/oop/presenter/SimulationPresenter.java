package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.Settings;
import agh.ics.oop.model.SimulationChangeListener;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    @FXML
    private Label infoLabel;

    private Simulation simulation;


    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public void drawSimulationStatus(String message) {
        dayNumber.setText(message);
        infoLabel.setText(this.simulation.getTestMap().toString());
        statsLabel.setText(simulation.getStatsAsString());
    }

    @Override
    public void simulationChanged(Simulation simulation, String message) {
        Platform.runLater(() -> {
            drawSimulationStatus(message);
        });
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
        mapTypeComboBox.setValue("Default");
        mutationTypeComboBox.setValue("Pure Randomness");

        startButton.setOnAction(event -> {
            /*System.out.println(mapTypeComboBox.getValue() + getSelectedMapTypeValue());
            System.out.println(mutationTypeComboBox.getValue() + getSelectedMutationTypeValue());*/

            useSettings();
            if (!simulation.isPrepared()) {
                simulation.prepare();
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
}

