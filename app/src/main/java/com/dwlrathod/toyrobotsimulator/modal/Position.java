package com.dwlrathod.toyrobotsimulator.modal;

public class Position {

    /**
     * X coordinate
     */
    int X;

    /**
     * Y coordinate
     */
    int Y;

    /**
     * Face;
     */
    int F;

    public Position(int x, int y, int f) {
        X = x;
        Y = y;
        F = f;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public int getF() {
        return F;
    }

    public void setF(int f) {
        F = f;
    }
}
