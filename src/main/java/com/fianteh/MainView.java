package com.fianteh;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class MainView extends VBox {
    /**
     * MainView has following functions
     * Button at the top, to simulate the simulation
     * canvas to display this
     * Affine to rearrange how things are displayed on the canvas
     */

    private Button stepButton;
    private Canvas my_canvas;
    private Simulation simulation;
    private Affine affine; //allows things to be rearrange

    private int drawMode = 1;


    public MainView() {
        this.stepButton = new Button("step");
        this.stepButton.setOnAction(actionEvent -> {
            simulation.step();
            draw();
        });

        //be able to change drawing mode
        this.setOnKeyPressed(this::onKeyPressed);


        this.my_canvas = new Canvas(800, 800);
        this.my_canvas.setOnMousePressed(this::handleDraw);
        this.my_canvas.setOnMouseDragged(this::handleDraw);
        this.getChildren().addAll(this.stepButton, this.my_canvas);

        this.affine = new Affine();
        this.affine.appendScale(800 / 20f, 800 / 20d);

        this.simulation = new Simulation(20, 20);
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.D){
            this.drawMode = 1;
            System.out.println("Draw mode");
        }else if (keyEvent.getCode() == KeyCode.E){
            this.drawMode = 0;
            System.out.println("Erase mode");
        }
    }

    //figure how to "change" the coordinates to given height and width
    private void handleDraw(MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();

//        System.out.println(mouseX + ", " + mouseY);

        //take screen coordinates and convert bck to simulation coordinates
        try {
            Point2D simCoord = this.affine.inverseTransform(mouseX, mouseY);

            int simCoordX = (int) simCoord.getX();
            int simCoordY = (int) simCoord.getY();

            System.out.println(simCoordX + ", " + simCoordY);
            this.simulation.setState(simCoordX, simCoordY, drawMode);


            draw();

        } catch (NonInvertibleTransformException e) {
//            e.printStackTrace();// only dealing with scaling
            System.out.println("Could not invert transform");
        }

    }

    public void draw() {
        GraphicsContext g = this.my_canvas.getGraphicsContext2D();
        g.setTransform(this.affine);

        g.setFill(Color.LIGHTGRAY);
        g.fillRect(0, 0, 800, 800);

        g.setFill(Color.BLACK);

        for (int x = 0; x < this.simulation.getWidth(); x++) {
            for (int y = 0; y < this.simulation.getHeight(); y++) {

                //draw a alive rectangle
                if (this.simulation.getState(x, y) == 1) {
                    g.fillRect(x, y, 1, 1);

                }
            }
        }


        //drawing the Grid lines
        g.setStroke(Color.GRAY);
        g.setLineWidth(0.05d);
        for (int x = 0; x <= this.simulation.getWidth(); x++) {
            //vertical Lines
            g.strokeLine(x, 0, x, 20);
        }

        for (int y = 0; y <= this.simulation.getHeight(); y++) {
            //draw horizontal lines
            g.strokeLine(0, y, 20, y);
        }
    }


}
