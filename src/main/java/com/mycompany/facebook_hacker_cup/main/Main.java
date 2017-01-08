package com.mycompany.facebook_hacker_cup.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final double RADIAN_TO_DEGREE_FACTOR = 180 / Math.PI;

    private Scanner scanner;
    private File output;
    private BufferedWriter outputWriter;

    public Main() throws FileNotFoundException {
        scanner = new Scanner(this.getClass().getClassLoader().getResourceAsStream("input.txt"));
        scanner.useDelimiter("\n");
        output = new File("out.txt");
        outputWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        if(false) {
            run();
        } else {
            renewInput();
        }
    }

    private static void run() throws FileNotFoundException, IOException {
        Main main = new Main();
        main.start();
        main.close();
    }
    
    private static void renewInput() throws IOException {
        Random random = new Random();
        File input = new File("src/main/resources/input.txt");
        BufferedWriter inputWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(input)));
        writeToFile("1000", inputWriter);
        for (int i = 0; i < 1000; i++) {
            int p = random.nextInt(101);
            int x = random.nextInt(101);
            int y = random.nextInt(101);
            String line = p + " " + x + " " + y;
            writeToFile(line, inputWriter);
        }
        inputWriter.close();
    }

    public void close() throws IOException {
        outputWriter.close();
    }

    private void start() throws IOException {
        int numberOfCases = Integer.parseInt(scanner.next());
        for (int i = 1; i <= numberOfCases; i++) {
            System.out.println("Case #" + i);
            String line = scanner.next();
            System.out.println(line);
            String color = handleCase(line);
            System.out.println(color);
            System.out.println("\n");
            writeToFile("Case #" + i + ": " + color);
        }
    }

    private String handleCase(String line) {
        Double[] input = readInput(line);
        if(input[0] == 0) {
            return "white";
        }
        if (!inCircle(input[1], input[2])) {
            System.out.println("Not in circle");
            return "white";
        }
        double progressAngle = 360 * (input[0] / 100);
        Quadrant q = Quadrant.getFrom(input[1], input[2]);
        System.out.println(q.name());
        System.out.println("Progress angle: " + progressAngle);
        double angleForPoint = 0;
        switch (q) {
            case NE:
                angleForPoint = handleE(input[1], input[2]);
                break;
            case SE:
                angleForPoint = handleE(input[1], input[2]);
                break;
            case SW:
                angleForPoint = handleSW(input[1], input[2]);
                break;
            case NW:
                angleForPoint = handleNW(input[1], input[2]);
                break;
        }
        System.out.println("Angle to reach point: " + angleForPoint);
        return progressAngle >= angleForPoint ? "black" : "white";
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

    private static void writeToFile(String line, BufferedWriter inputWriter) throws IOException {
        inputWriter.append(line);
        inputWriter.newLine();
    }

}
