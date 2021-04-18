package com.company;

public class LightSource {
    private Coordinates center;

    public LightSource(int x, int y) {
        center = new Coordinates(x, y);
    }

    public Coordinates getCoordinates() {
        return center;
    }
}
