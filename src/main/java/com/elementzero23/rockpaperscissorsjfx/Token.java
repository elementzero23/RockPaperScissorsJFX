package com.elementzero23.rockpaperscissorsjfx;

import java.util.Random;

public class Token {
    private TokenType type;
    private int x, y;
    private Random random;

    public Token(TokenType type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
        random = new Random();
    }

    public String getSymbol() {
        switch (type) {
            case ROCK:
                return "🪨";
            case PAPER:
                return "📜";
            case SCISSORS:
                return "✂️";
            default:
                return "?";
        }
    }

    public TokenType getType() {
        return this.type;
    }

    public void setType(TokenType newType) {
        this.type = newType;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    /**
     * Moves the token randomly up to 5 pixels in each direction.
     */
    public void move() {
        this.x = (random.nextInt(11) - 5);
        while (this.x < 0 || this.x > Simulator.gameSize)
            this.x = (random.nextInt(11) - 5);

        this.y += (random.nextInt(11) - 5);
        while (this.y < 0 || this.y > Simulator.gameSize)
            this.y += (random.nextInt(11) - 5);
    }
}