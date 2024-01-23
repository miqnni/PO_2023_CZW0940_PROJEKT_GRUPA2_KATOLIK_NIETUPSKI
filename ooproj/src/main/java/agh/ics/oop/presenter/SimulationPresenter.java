package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.MapChangeListener;
import agh.ics.oop.model.Settings;
import agh.ics.oop.model.SimulationChangeListener;
import agh.ics.oop.model.WorldMap;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;

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
    @FXML
    private Label infoLabel;

    private Simulation simulation;


    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public void drawMap() {
        infoLabel.setText(this.simulation.getTestMap().toString());
    }


    @Override
    public void simulationChanged(Simulation simulation, String message) {
        dayNumber.setText(message);
        drawMap();
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

            simulation.run();
            }

        );
    }
//    public void onSimulationStartClicked() {
////        Settings currSettings = new Settings();
////        currSettings.setMapWidth(mapWidthSpinner.getValue());
////        currSettings.setMapHeight(mapHeightSpinner.getValue());
////        simulation.setSettings(currSettings);
//        simulation.run();
//    }

}
