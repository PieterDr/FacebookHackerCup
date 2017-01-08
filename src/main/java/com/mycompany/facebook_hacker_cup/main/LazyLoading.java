package com.mycompany.facebook_hacker_cup.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LazyLoading {

    private final Scanner scanner;
    private final BufferedWriter outputWriter;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        LazyLoading main = new LazyLoading();
        main.start();
        main.close();
    }

    public LazyLoading() throws FileNotFoundException {
        scanner = new Scanner(this.getClass().getClassLoader().getResourceAsStream("lazy_loading_input.txt"));
        scanner.useDelimiter("\n");
        outputWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("lazy_loading_output.txt"))));
    }

    private void start() throws IOException {
        double numberOfDays = readNumber();
        for (int dayNumber = 1; dayNumber <= numberOfDays; dayNumber++) {
            double numerOfItems = readNumber();
            List<Double> items = new ArrayList<>();
            for (double j = 1; j <= numerOfItems; j++) {
                items.add(readNumber());
            }
            int numberOfTrips = handleDay(dayNumber, numerOfItems, items);
            writeToFile("Case #" + dayNumber + ": " + numberOfTrips);
        }
    }

    private double readNumber() throws NumberFormatException {
        return Double.parseDouble(scanner.next());
    }

    private void close() throws IOException {
        outputWriter.close();
    }

    private int handleDay(double day, double numberOfItems, List<Double> items) {
        items.sort((a, b) -> b.compareTo(a));
        System.out.println("Day " + day + "... Moving " + numberOfItems + " items:");
        System.out.println(items.stream().map(i -> "" + i).collect(Collectors.joining(", ")));
        System.out.println("");

        int result = 0;
        while (items.size() != 0 && itemsStillSupportValidTrip(items)) {
            double factor = 1;
            double largestItem = items.get(0);
            System.out.println("Removing item: " + largestItem);
            while (50 / factor > largestItem) {
                factor++;
                System.out.println("Incrementing factor: " + (factor - 1) + " -> " + factor);
            }
            items.remove(largestItem);
            while (factor > 1) {
                double smallestItem = items.get(items.size() - 1);
                System.out.println("Factor is: " + factor + " -> Removing smallest item: " + smallestItem);
                items.remove(smallestItem);
                factor--;
            }
            result++;
            System.out.println("result increased to: " + result);
        }
        System.out.println("");
        return result;
    }

    private boolean itemsStillSupportValidTrip(List<Double> items) {
        return 50 <= items.get(0) * items.size();
    }

    private void writeToFile(String line) throws IOException {
        outputWriter.append(line);
        outputWriter.newLine();
    }

}
