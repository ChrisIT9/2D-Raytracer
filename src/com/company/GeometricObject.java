package com.company;

import java.awt.*;
import java.util.ArrayList;

public abstract class GeometricObject {
    protected Coordinates center;
    protected Color color;
    protected ArrayList<Coordinates> points;

    public GeometricObject(int x, int y, Color color) {
        center = new Coordinates(x, y);
        this.color = color;
        points = new ArrayList<>();
    }

    public int getX() {
        return center.getX();
    }

    public int getY() {
        return center.getY();
    }

    public Color getColor() {
        return color;
    }

    abstract public boolean containsPoint(Coordinates coordinates);

    public ArrayList<Coordinates> getPoints() {
        return points;
    }
}
