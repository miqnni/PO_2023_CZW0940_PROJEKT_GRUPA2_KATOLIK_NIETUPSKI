package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.Settings;
import agh.ics.oop.model.SimulationChangeListener;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

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

    @FXML
    private void initialize() {
        mapTypeComboBox.setValue("Default");
        mutationTypeComboBox.setValue("Pure Randomness");

        startButton.setOnAction(event -> {
            System.out.println(mapTypeComboBox.getValue() + getSelectedMapTypeValue());
            System.out.println(mutationTypeComboBox.getValue() + getSelectedMutationTypeValue());

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
}
