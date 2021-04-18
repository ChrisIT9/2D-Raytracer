package com.company;

import java.awt.*;
import java.util.Random;

public class Utilities {

    public static Color getDimmer(Color color, int factor) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        int offset = factor / 3 * 2;

        r = r - offset >= 0 ? r - offset : 0;
        g = g - offset >= 0 ? g - offset : 0;
        b = b - offset >= 0 ? b - offset : 0;


        return new Color(r, g, b);
    }

    public static double getDistance(Coordinates firstCoordinates, Coordinates secondCoordinates) {
        double diffX = Math.pow(firstCoordinates.getX() - secondCoordinates.getX(), 2);
        double diffY = Math.pow(firstCoordinates.getY() - secondCoordinates.getY(), 2);
        return Math.sqrt(diffX + diffY);
    }

    public static boolean pointIsBetween(Coordinates startingCoordinates, Coordinates pointCoordinates, Coordinates endingCoordinates) {
        double distanceSum = getDistance(startingCoordinates, pointCoordinates) + getDistance(endingCoordinates, pointCoordinates);
        double segmentLength = getDistance(startingCoordinates, endingCoordinates);

        return (Math.abs(distanceSum - segmentLength) <= 0.005);
    }



    public static Color getRandomColor() {
        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);

        return new Color(r, g, b);
    }

    public static Color getDimmerShadow(Color color, double distance) {
        int difference = (int) distance / 6 * 4;
        int finalValue = color.getAlpha() - difference;
        int newAlpha = finalValue >= 0 ? finalValue : 0;

        return new Color(color.getRed(), color.getGreen(), color.getBlue(), newAlpha);
    }
}
