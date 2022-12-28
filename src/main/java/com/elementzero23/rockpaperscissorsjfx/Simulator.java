package com.elementzero23.rockpaperscissorsjfx;

import java.util.LinkedList;
import java.util.Random;

/**
 * Main class of the project that runs the simulation step by step. 
 * In each step every two tokens that intersect compete with each other 
 * and the losing token becomes the winning token's type. 
 * The simulation stops when there is only one token type left.
 */
public class Simulator {
    private Thread thread;
    private LinkedList<SimulatorObserver> observers;

    public LinkedList<Token> getTokens() {
        return tokens;
    }

    private LinkedList<Token> tokens;

    private Random random;

    // size of the square game area
    public static final int gameSize = 300;

    // delay between steps in milliseconds
    public static final int gameDelayInMs = 200;

    private boolean gameOver;

    public Simulator() {
        observers = new LinkedList<>();
        tokens = new LinkedList<>();
        random = new Random();
        gameOver = false;
        createRandomTokens(100);

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!gameOver) {
                    step();
                    try {
                        Thread.sleep(gameDelayInMs);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    public void startSimulation() {
        thread.start();
    }

    public void addObserver(SimulatorObserver observer) {
        this.observers.add(observer);
    }

    /**
     * Creates the provided number of tokens with
     *  - random types and
     *  - random coordinates.
     *
     * @param count How many random tokens should be created
     */
    public void createRandomTokens(int count) {
        for (int i = 0; i < count; i++) {
            double d = random.nextDouble();
            TokenType type;
            if (d < 0.33) {
                type = TokenType.ROCK;
            } else if (d < 0.67) {
                type = TokenType.PAPER;
            } else {
                type = TokenType.SCISSORS;
            }
            int x = (int) (random.nextDouble() * gameSize);
            int y = (int) (random.nextDouble() * gameSize);
            tokens.add(new Token(type, x, y));
        }
    }



    /**
     * Checks for every pair of token if they are close to each other
     * (close = coordinates differ by less then 10).
     * Close tokens will compete with each other.<br>
     *
     * Moves tokens afterwards.<br>
     *
     * At the end, the observers get notified about changes in the tokens list.
     */
    public void step() {
        tokens.forEach(token -> {
            tokens.forEach(otherToken -> {
                if (getDistance(token, otherToken) < 20) {
                    // tokens intersect
                    compete(token, otherToken);
                }
            });
            token.move();
        });
        observers.forEach(simulatorObserver -> simulatorObserver.update(tokens));
        gameOver = checkGameOver();
    }

    /**
     * Calculate the distance of the center points of two tokens.
     * @param token
     * @param otherToken
     * @return
     */
    private double getDistance(Token token, Token otherToken) {
        return Math.sqrt(Math.pow(token.getX()-GraphicalVisualizer.LABEL_WIDTH/2 - otherToken.getX()-GraphicalVisualizer.LABEL_WIDTH/2, 2) + Math.pow(token.getY()-GraphicalVisualizer.LABEL_HEIGHT/2 - otherToken.getY()-GraphicalVisualizer.LABEL_HEIGHT/2, 2));
    }

    /**
     * Checks if there is only one token type left.
     * @return true if there is only one token type left or false if there are still tokens that differ in type
     */
    private boolean checkGameOver() {
        TokenType t = tokens.getFirst().getType();
        for (Token token : tokens) {
            if (token.getType() != t) return false;
        }
        return true;
    }

    /**
     * Competes two tokens. The losing token becomes the winning token's type.
     * Rock wins over Scissors.
     * Scissors win over Paper.
     * Paper wins over Rock.
     *
     * @param t1 token 1
     * @param t2 token 2
     */
    public void compete(Token t1, Token t2) {
        switch (t1.getType()) {
            case ROCK:
                switch (t2.getType()) {
                    case ROCK:
                        // draw
                        break;
                    case PAPER:
                        // t2 wins, t1 changes
                        t1.setType(TokenType.PAPER);
                        break;
                    case SCISSORS:
                        // t1 wins, t2 changes
                        t2.setType(TokenType.ROCK);
                        break;
                }
                break;
            case PAPER:
                switch (t2.getType()) {
                    case ROCK:
                        // t1 wins, t2 changes
                        t2.setType(TokenType.PAPER);
                        break;
                    case PAPER:
                        // draw
                        break;
                    case SCISSORS:
                        // t2 wins, t1 changes
                        t1.setType(TokenType.SCISSORS);
                        break;
                }
                break;
            case SCISSORS:
                switch (t2.getType()) {
                    case ROCK:
                        // t2 wins, t1 changes
                        t1.setType(TokenType.ROCK);
                        break;
                    case PAPER:
                        // t1 wins, t2 changes
                        t2.setType(TokenType.SCISSORS);
                        break;
                    case SCISSORS:
                        // draw
                        break;
                }
                break;
        }
    }
}