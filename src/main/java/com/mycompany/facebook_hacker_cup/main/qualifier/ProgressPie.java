package com.mycompany.facebook_hacker_cup.main.qualifier;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Scanner;

public class ProgressPie {

    private static final double RADIAN_TO_DEGREE_FACTOR = 180 / Math.PI;

    private final Scanner scanner;
    private final BufferedWriter outputWriter;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        ProgressPie main = new ProgressPie();
        main.start();
        main.close();
    }

    public ProgressPie() throws FileNotFoundException {
        scanner = new Scanner(this.getClass().getClassLoader().getResourceAsStream("input_progress_pie.txt"));
        scanner.useDelimiter("\n");
        outputWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("output_progress_pie_pieter.txt")));
    }

    private void start() throws IOException {
        int numberOfCases = Integer.parseInt(scanner.next());
        for (int i = 1; i <= numberOfCases; i++) {
            String line = scanner.next();
            String color = handleCase(line);
            writeToFile("Case #" + i + ": " + color);
        }
    }

    public void close() throws IOException {
        outputWriter.close();
    }

    private String handleCase(String line) {
        Double[] input = readInput(line);
        if (input[0] == 0) {
            return "white";
        }
        if (!inCircle(input[1], input[2])) {
            return "white";
        }
        double progressAngle = 360 * (input[0] / 100);
        Quadrant quadrant = Quadrant.getFrom(input[1], input[2]);
        double angleForPoint = calculateForQuadrant(quadrant, input);
        return progressAngle >= angleForPoint ? "black" : "white";
    }

    private double calculateForQuadrant(Quadrant quadrant, Double[] input) {
        switch (quadrant) {
            case NE:
                return handleE(input[1], input[2]);
            case SE:
                return handleE(input[1], input[2]);
            case SW:
                return handleSW(input[1], input[2]);
            case NW:
                return handleNW(input[1], input[2]);
            default:
                throw new RuntimeException("Not even possible");
        }
    }

    private Double[] readInput(String line) {
        String[] parts = line.split("\\s");
        Double[] inputs = new Double[parts.length];
        for (int i = 0; i < parts.length; i++) {
            inputs[i] = Double.parseDouble(parts[i]);
        }
        return inputs;
    }

    private boolean inCircle(double x, double y) {
        double l = x - 50;
        double h = y - 50;
        return 50 >= Math.sqrt((l * l) + (h * h));
    }

    private double handleE(double x, double y) {
        if (x == 50) {
            return y >= 50 ? 0.0 : 180.0;
        }
        double xCopy = x - 50;
        double yCopy = y - 50;
        return 90 - RADIAN_TO_DEGREE_FACTOR * Math.atan(yCopy / xCopy);
    }

    private double handleSW(double x, double y) {
        if (y == 50) {
            return 270;
        }
        double xCopy = 50 - y;
        double yCopy = (50 - x) * -1;
        return 180 - RADIAN_TO_DEGREE_FACTOR * Math.atan(yCopy / xCopy);
    }

    private double handleNW(double x, double y) {
        double xCopy = 50 - x;
        double yCopy = y - 50;
        return 270 + RADIAN_TO_DEGREE_FACTOR * Math.atan(yCopy / xCopy);
    }

    private void writeToFile(String line) throws IOException {
        outputWriter.append(line);
        outputWriter.newLine();
    }

    private static enum Quadrant {
        NE(50, 100, 50, 100),
        SE(50, 100, 0, 49),
        SW(0, 49, 0, 50),
        NW(0, 49, 51, 100);

        private final double minX;
        private final double maxX;
        private final double minY;
        private final double maxY;

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
}