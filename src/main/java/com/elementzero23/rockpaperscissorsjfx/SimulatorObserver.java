package com.elementzero23.rockpaperscissorsjfx;

import java.util.LinkedList;

public interface SimulatorObserver {
    public void update(LinkedList<Token> tokens);
}
