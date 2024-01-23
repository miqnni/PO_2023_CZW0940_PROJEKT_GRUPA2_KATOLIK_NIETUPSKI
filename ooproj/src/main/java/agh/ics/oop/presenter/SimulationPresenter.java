package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.Settings;
import agh.ics.oop.model.SimulationChangeListener;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    public Spinner<Integer> mapTypeSpinner;
    public Spinner<Integer> durationInDaysSpinner;
    public Spinner<Integer> halfCycleLengthSpinner;
    public Spinner<Integer> waterRangeLimitSpinner;
    public Label statsLabel;
    public Button stopButton;
    public CheckBox saveCheckbox;
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

    @FXML
    private void initialize() {
        startButton.setOnAction(event -> {
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
                    mutationTypeSpinner.getValue(),
                    genomeLengthSpinner.getValue(),
                    mapTypeSpinner.getValue(),
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
