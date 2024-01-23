package agh.ics.oop;

import agh.ics.oop.model.ConsoleSimulationDisplay;
import agh.ics.oop.model.Settings;
import agh.ics.oop.presenter.SimulationPresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class SimulationApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.show();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();
        SimulationPresenter presenter = loader.getController();

        Settings testSettings = new Settings(
                30,
                25,
                0,
                15,
                0,
                1,
                20,
                20,
                10,
                0,
                1,
                2,
                15,
                0,
                15,
                40,
                5
        );

        Simulation testSim = new Simulation(testSettings);
        testSim.addObserver(presenter);
        presenter.setSimulation(testSim);

        ConsoleSimulationDisplay testConsole = new ConsoleSimulationDisplay();
        testSim.addObserver(testConsole);

        List<Simulation> testSimList = new ArrayList<>();
        testSimList.add(testSim);
        SimulationEngine e1 = new SimulationEngine(testSimList);
        testSim.setParentEngine(e1);


        configureStage(primaryStage, viewRoot);
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }

}
