package com.company;

import java.awt.*;

public class Cube extends GeometricObject {

    private int side;

    private void calculatePoints() {
        for (int y = center.getY() - side / 2; y <= center.getY() + side / 2; y++)
            for (int x = center.getX() - side / 2; x <= center.getX() + side / 2; x++)
                points.add(new Coordinates(x, y));
    }

    public Cube(int x, int y, Color color, int side) {
        super(x, y, color);
        this.side = side;
        calculatePoints();
    }

    @Override
    public boolean containsPoint(Coordinates coordinates) {
        return (points.contains(coordinates));
    }
}
