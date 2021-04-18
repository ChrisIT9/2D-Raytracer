package com.company;

import javax.swing.*;
import java.awt.*;

public class Main extends JComponent {


    public static void main(String[] args) {
        Space2D space = new Space2D(640, 480);
        space.addObject(new Cube(200, 219, Color.YELLOW, 70));
        //space.addLightSource(new LightSource(50, 50));

        JFrame frame = new JFrame("2D Raytracer");
        frame.setVisible(true);
        frame.setBounds(0, 0, 800, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        frame.add(space);






    }
}
