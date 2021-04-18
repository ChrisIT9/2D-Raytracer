package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class Space2D extends JComponent implements MouseListener, KeyListener, MouseWheelListener, MouseMotionListener, Runnable {
    private ArrayList<GeometricObject> objects;
    private LightSource lightSource;
    private int width, height;
    private BufferedImage image;
    private boolean inPlaceMode;
    private boolean lightPlaceMode;
    private int radius;
    private Thread[] threads;
    private BufferedImage[] subImages;
    private int heightTasks;
    private int widthTasks;


    public Space2D(int width, int height) {
        objects = new ArrayList<>();
        this.width = width;
        this.height = height;
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        lightSource = null;
        inPlaceMode = false;
        lightPlaceMode = false;
        radius = 30;
        addMouseListener(this);
        addMouseWheelListener(this);
        addKeyListener(this);
        addMouseMotionListener(this);
        setFocusable(true);
        threads = new Thread[4];
        subImages = new BufferedImage[4];
        heightTasks = height / 2;
        widthTasks = width / 2;

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(this, Integer.toString(i));
            subImages[i] = new BufferedImage(widthTasks, heightTasks, BufferedImage.TYPE_INT_ARGB);
            threads[i].start();
            //System.out.println("Started thread " + threads[i].getName());
        }
    }

    public void addObject(GeometricObject object) {
        objects.add(object);
    }

    public void addLightSource(LightSource lightSource) {
        this.lightSource = lightSource;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(76, 76, 76, 255));
        g.fillRect(0, 0, width, height);
        g.drawImage(subImages[0], 0, 0, this);
        g.drawImage(subImages[1], 320, 0, this);
        g.drawImage(subImages[2], 0, 240, this);
        g.drawImage(subImages[3], 320, 240, this);
    }

    public void renderImage() {
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(this, Integer.toString(i));
            threads[i].start();
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_R:
                repaint();
                break;
            case KeyEvent.VK_A:
                System.out.println("Place mode");
                inPlaceMode = true;
                break;
            case KeyEvent.VK_L:
                System.out.println("Light source placement");
                lightPlaceMode = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        requestFocusInWindow();
        int x = e.getX(), y = e.getY();
        if (inPlaceMode) {
            System.out.println(x + " " + y);
            addObject(new Sphere(x, y, Color.BLUE, radius));
            inPlaceMode = false;
            radius = 30;
        }
        else if (lightPlaceMode) {
            addLightSource(new LightSource(x, y));
            lightPlaceMode = false;
        }
        renderImage();
        for(int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (inPlaceMode) {
            int wheelRotation = e.getWheelRotation();
            if (wheelRotation == -1 && wheelRotation <= 300)
                radius += 1;
            else if (wheelRotation == 1 && radius >= 5)
                radius -= 1;
            System.out.println("Radius: " + radius);

        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        addLightSource(new LightSource(e.getX(), e.getY()));
        renderImage();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void run() {
        if (lightSource == null) {
            System.out.println("No light source.");
            return;
        }

        int offset = Integer.valueOf(Thread.currentThread().getName());
        int startingX, startingY, endX, endY;
        //System.out.println("Thread " + offset + " running.");

        switch(offset) {
            case 0:
                startingY = 0;
                startingX = 0;
                endY = heightTasks;
                endX = widthTasks;
                break;
            case 1:
                startingY = 0;
                startingX = widthTasks;
                endY = heightTasks;
                endX = width;
                break;
            case 2:
                startingY = heightTasks;
                startingX = 0;
                endY = height;
                endX = widthTasks;
                break;
            case 3:
                startingY = heightTasks;
                startingX = widthTasks;
                endY = height;
                endX = width;
                break;
            default:
                startingY = startingX = endX = endY = 0;
        }


        for (int y = startingY, internalY = 0; y < endY; y++, internalY++) {
            for (int x = startingX, internalX = 0; x < endX; x++, internalX++) {
                Coordinates currentCoordinates = new Coordinates(x, y);

                boolean hitObject = false;

                for (GeometricObject object : objects) {
                    if (object.containsPoint(currentCoordinates)) {
                        //System.out.println("Ray hit at (" + x + ", " + y + ")");

                        for (GeometricObject possibleHitObject : objects) {
                            if (possibleHitObject != object && !hitObject) {

                                for (Coordinates coordinates : possibleHitObject.getPoints()) {
                                    if (Utilities.pointIsBetween(currentCoordinates, coordinates, lightSource.getCoordinates())) {
                                        //System.out.println("Interception at " + coordinates.getX() + ", " + coordinates.getY());
                                        subImages[offset].setRGB(internalX, internalY, Color.BLACK.getRGB());
                                        hitObject = true;
                                        break;
                                    }
                                }
                            }
                        }
                        if (!hitObject) {
                            int distance = (int) Utilities.getDistance(currentCoordinates, lightSource.getCoordinates());
                            Color newColor = Utilities.getDimmer(object.getColor(), distance);
                            subImages[offset].setRGB(internalX, internalY, newColor.getRGB());
                        }
                    }
                }
            }
        }
    }
}
