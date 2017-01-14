package com.mycompany.facebook_hacker_cup.main.qualifier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PieTim {

    private Printer printer;

    public PieTim(Printer printer) {
        this.printer = printer;
    }

    public static void main(String[] args) {
        List<String> lines = readFile();

        new PieTim(new SoutPrinter()).execute(lines);

    }

    public void execute(List<String> lines) {
        Integer numberOfCases = Integer.valueOf(lines.get(0));
        for (int i = 1; i <= numberOfCases; i++) {
            int[] ints = splitToIntegers(lines.get(i));

            if (ints[0] == 0) {
                System.out.println("Case #" + i + ": " + "white");

            } else {
                double angleToWhichCovered = 360.0 * ints[0] / 100;

                boolean inside;
                double angle;
                if (ints[1] == 50 && ints[2] >= 50) {
                    inside = ints[0] > 0;
                } else if (ints[1] == 50 && ints[2] < 50) {
                    inside = ints[0] >= 50;
                } else {
                    angle = getAngle(ints);
                    if (angle - angleToWhichCovered <= 0.000001) {
                        inside = !(Math.pow(ints[1] - 50, 2) + Math.pow(ints[2] - 50, 2) > Math.pow(50, 2));
                    } else {
                        inside = false;
                    }
                }
                printer.print(i, inside);
            }
        }
    }

    public double getAngle(int[] ints) {
        double angle;
        int projX = ints[1] - 50;
        int projy = ints[2] - 50;
        int minY = Math.min(0, projy);
        int maxY = Math.max(0, projy);
        angle = Math.acos(
                (maxY - minY) / Math.sqrt(Math.pow(0 - projX, 2) + Math.pow(maxY - minY, 2))
        ) * (180 / Math.PI);
        if (projX >= 0 && projy >= 0) {
            return angle;
        } else if (projX >= 0 && projy < 0) {
            return angle + 90;
        } else if (projX < 0 && projy <= 0) {
            return angle + 90 + 90;
        } else {
            return angle + 270;
        }
    }

    public interface Printer {

        void print(int caseNumber, boolean inside);
    }

    public static class SoutPrinter implements Printer {

        @Override
        public void print(int caseNumber, boolean inside) {
            System.out.println("Case #" + caseNumber + ": " + (inside ? "black" : "white"));
        }
    }

    private int[] splitToIntegers(String line) {
        String[] items = line.split(" ");
        int[] results = new int[items.length];
        for (int i = 0; i < items.length; i++) {
            try {
                results[i] = Integer.parseInt(items[i]);
            } catch (NumberFormatException ignored) {
            }
        }
        return results;
    }

    public static List<String> readFile() {
        try {
            return Files.readAllLines(Paths.get("input_progress_pie.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
