package com.mycompany.facebook_hacker_cup.main;

import java.util.Arrays;

public enum Quadrant {

    NE(50, 100, 50, 100),
    SE(50, 100, 0, 49),
    SW(0, 49, 0, 50),
    NW(0, 49, 51, 100);

    private double minX;
    private double maxX;
    private double minY;
    private double maxY;

    private Quadrant(double minX, double maxX, double minY, double maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    public static Quadrant getFrom(double x, double y) {
        return Arrays.asList(values()).stream()
                .filter(v -> v.validX(x))
                .filter(v -> v.validY(y))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No quadrant found, wtf!?"));
    }

    private boolean validX(double x) {
        return minX <= x && x <= maxX;
    }

    private boolean validY(double y) {
        return minY <= y && y <= maxY;
    }

}
