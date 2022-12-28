package com.elementzero23.rockpaperscissorsjfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GraphicalVisualizer extends Application {
    private Scene scene1;
    private Pane root;
    private Label test;

    @Override
    public void start(Stage stage) throws Exception {
        test = new Label("✂️");
        test.setFont(new Font("Apple Color Emoji", 40));
        test.setLayoutX(20);
        test.setLayoutY(50);
        root = new Pane();
        root.getChildren().add(test);
        scene1 = new Scene(root, 400, 300);
        stage.setScene(scene1);
        stage.setTitle("Rock Paper Scissors Simulator");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
