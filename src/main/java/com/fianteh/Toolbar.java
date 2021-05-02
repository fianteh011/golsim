package com.fianteh;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {

    private MainView mw;

    public Toolbar(MainView mw) {
        this.mw = mw;
        Button draw = new Button("Draw");
        //buttons must provide an action
        draw.setOnAction(this::handleDraw);
        Button erase = new Button("Erase");
        erase.setOnAction(this::handleErase);
        Button step = new Button("Step");
        step.setOnAction(this::handleStep);

        this.getItems().addAll(draw, erase, step);
    }

    private void handleStep(ActionEvent actionEvent) {
        System.out.println("Step pressed");
        this.mw.getSimulation().step();
        this.mw.draw();
    }

    private void handleErase(ActionEvent actionEvent) {
        System.out.println("Erase pressed");
        this.mw.setDrawMode(Simulation.DEAD);

    }

    private void handleDraw(ActionEvent actionEvent) {
        System.out.println("Draw pressed");
        this.mw.setDrawMode(Simulation.ALIVE);
    }
}
