package com.elementzero23.rockpaperscissorsjfx;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class ConsoleVisualizer implements SimulatorObserver{
    private LinkedList<Token> tokensList;

    public void outputTokensList() {
        System.out.print("Tokens: [");
        tokensList.forEach(token -> {
            System.out.print("(" + token.getType() + "," + token.getX() + "," + token.getY() + "), ");
        });
        System.out.print("]\n");
    }

    public void show() {
        // sort by y-coordinate
        Collections.sort(tokensList, new Comparator<Token>() {
            @Override
            public int compare(Token t1, Token t2) {
                if (t1.getY() == t2.getY()) {
                    return t1.getX() - t2.getX();
                }
                return t1.getY() - t2.getY();
            }
        });

        outputTokensList();


        System.out.print("+");
        for (int i = 0; i < Simulator.gameSize*2; i++) {
            System.out.print("-");
        }
        System.out.println("+");


        int current = 0;
        for (int y = 0; y < Simulator.gameSize; y++) {
            System.out.print("|");
            for (int x = 0; x < Simulator.gameSize; x++) {
                if (current < tokensList.size()) {
                    Token currentToken = tokensList.get(current);
                    if (currentToken.getY() == y && currentToken.getX() == x) {
                        System.out.print(currentToken.getSymbol());
                        current++;
                    } else {
                        System.out.print("  ");
                    }
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println("|");
        }

        System.out.print("+");
        for (int i = 0; i < Simulator.gameSize*2; i++) {
            System.out.print("-");
        }
        System.out.println("+");
        System.out.println(current);
    }

    @Override
    public void update(LinkedList<Token> tokens) {
        this.tokensList = tokens;
        show();
    }
}
