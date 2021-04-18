package com.company;

import java.awt.*;

public class Utilities {

    public static Color getDimmer(Color color, int factor) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        int offset = factor / 4 * 2;

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

    public static Color getDimmerShadow(Color color, int offset) {
        int alpha = color.getAlpha();
        int difference = offset / 10 * 5;

        alpha = alpha - difference >= 0 ? alpha - difference : 0;

        return new Color(0, 0, 0, alpha);
    }

    public static Color getAveragedColor(Color color, Color otherColor) {
        int r = (color.getRed() + otherColor.getRed()) / 2;
        int g = (color.getGreen() + otherColor.getGreen()) / 2;
        int b = (color.getBlue() + otherColor.getBlue()) / 2;
        int alpha = (color.getAlpha() + otherColor.getAlpha()) / 2;

        return new Color(r, g, b, alpha);
    }
}
