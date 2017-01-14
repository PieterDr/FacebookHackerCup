package com.mycompany.facebook_hacker_cup.main.qualifier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class LoadingTim {

    private Printer printer = new SoutPrinter();

    public static void main(String[] args) {
        List<String> lines = readFile();

        new LoadingTim().execute(lines);

    }

    public void execute(List<String> lines) {
        Integer days = Integer.valueOf(lines.get(0));
        int j = 1;
        for (int i = 1; i <= days; i++) {
            int numberOfItems = Integer.valueOf(lines.get(j++));
            int[] items = new int[numberOfItems];
            for (int k = 0; k < numberOfItems; k++) {
                items[k] = Integer.parseInt(lines.get(j++));
            }
            int trips = calculateTrips(items);
            printer.print(i, trips);
        }
    }

    public int calculateTrips(int[] items) {
        if (items.length == 1) {
            return 1;
        }
        Arrays.sort(items);
        int upper = items.length - 1;
        int lower = 0;
        int trips = 0;

        while (upper > lower) {
            if (items[upper] >= 50) {
                upper--;
                trips++;
            } else {
                int multiplier = (int) Math.ceil(50.0 / items[upper]);
                lower = lower + multiplier - 1;
                if (lower > upper) {
                    //not enough
                    break;
                } else if (lower == upper) {
                    trips++;
                    break;
                } else {
                    trips++;
                    upper--;
                }
            }
        }
        return trips;
    }

    public interface Printer {

        void print(int caseNumber, int numberOfTrips);
    }

    public static class SoutPrinter implements Printer {

        @Override
        public void print(int caseNumber, int numberOfTrips) {
            System.out.println("Case #" + caseNumber + ": " + numberOfTrips);
        }
    }

    public static List<String> readFile() {
        List<String> lines = new ArrayList<>();
        Scanner scanner = new Scanner(LoadingTim.class.getClassLoader().getResourceAsStream("input_lazy_loading.txt"));
        while (scanner.hasNext()) {
            lines.add(scanner.next());
        }
        scanner.close();
        return lines;
    }

}
