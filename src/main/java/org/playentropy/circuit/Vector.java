package org.playentropy.circuit;

import java.lang.Math;

public class Vector {
    private final int x;
    private final int y;

    public Vector(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public Vector add(Vector other) {
        return new Vector(x + other.getX(), y + other.getY());
    }

    public boolean withinField(Vector lowerBound, Vector upperBound) {
        return x >= lowerBound.getX() && y >= lowerBound.getY()
            && x <= upperBound.getX() && y <= upperBound.getY();
    }

    public int area() {
        return Math.abs(x * y);
    }

    public double distance() {
        return Math.sqrt(x * x + y * y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof Vector)) return false;

        Vector vector = (Vector)other;
        return x == vector.getX() && y == vector.getY();
    }
}
