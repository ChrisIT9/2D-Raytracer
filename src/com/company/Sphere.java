package com.company;

import java.awt.*;

public class Sphere extends GeometricObject {
    private int radius;

    private void calculatePoints() {
        int centerX = center.getX();
        int centerY = center.getY();
        for (int y = centerY - radius; y <= centerY + radius; y++)
            for (int x = centerX - radius; x <= centerX + radius; x++)
                if (containsPoint(new Coordinates(x, y)))
                    points.add(new Coordinates(x, y));
    }

    public Sphere(int x, int y, Color color, int radius) {
        super(x, y, color);
        this.radius = radius;
        calculatePoints();
    }

    @Override
    public boolean containsPoint(Coordinates coordinates) {
        double diffX = Math.pow(center.getX() - coordinates.getX(), 2);
        double diffY = Math.pow(center.getY() - coordinates.getY(), 2);
        return Math.sqrt(diffX + diffY) <= (radius + 0.0001); // margine di errore
    }

    public int getRadius() {
        return radius;
    }
}
