package com.elementzero23.rockpaperscissorsjfx;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.LinkedList;

public class GraphicalVisualizer extends Application implements SimulatorObserver {
    private Scene scene1;
    private Pane root;
    private Label test;

    private LinkedList<Token> tokens;

    // Label dimensions when using Apple Color Emoji @ 40pt
    public static final int LABEL_WIDTH = 40;
    public static final int LABEL_HEIGHT = 62;

    @Override
    public void start(Stage stage) {
        Simulator s = new Simulator();
        s.addObserver(this);
        this.tokens = s.getTokens();

        root = new Pane();

        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            Label l = new Label(token.getSymbol());
            l.setLayoutX(token.getX());
            l.setLayoutY(token.getY());
            l.setFont(new Font("Apple Color Emoji", 40));
            l.setUserData(""+i);
            root.getChildren().add(l);
        }
        /*
        test = new Label("✂️");
        test.setFont(new Font("Apple Color Emoji", 40));
        test.setLayoutX(20);
        test.setLayoutY(50);
        root.getChildren().add(test);
*/

        //root.getChildren().add(test);
        scene1 = new Scene(root, Simulator.gameSize, Simulator.gameSize);


        // longrunning operation runs on different thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Runnable updater = new Runnable() {
                    @Override
                    public void run() {
                        updateLocations();
                    }
                };

                while (true) {
                    try {
                        Thread.sleep(Simulator.gameDelayInMs);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    // UI update is run on the Application thread
                    Platform.runLater(updater);
                }
            }

        });
        // don't let thread prevent JVM shutdown
        thread.setDaemon(true);
        thread.start();

        s.startSimulation();

        stage.setScene(scene1);
        stage.setTitle("Rock Paper Scissors Simulator");
        stage.show();
    }

    public void updateLocations() {
        for (int i = 0; i < tokens.size(); i++) {
            for (Node n : root.getChildren()) {
                if (n.getUserData().equals(""+i)) {
                    Token token = tokens.get(i);
                    Label ll = (Label) n;
                    ll.setLayoutX(token.getX());
                    ll.setLayoutY(token.getY());
                    ll.setText(token.getSymbol());
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void update(LinkedList<Token> tokens) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                updateLocations();
            }
        });

    }


}
