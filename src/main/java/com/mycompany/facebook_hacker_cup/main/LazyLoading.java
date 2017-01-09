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

    public static void main(String[] args) throws IOException {
        LazyLoading main = new LazyLoading();
        main.start();
        main.close();
    }

    public LazyLoading() throws FileNotFoundException {
        scanner = new Scanner(this.getClass().getClassLoader().getResourceAsStream("lazy_loading.txt"));
        scanner.useDelimiter("\n");
        outputWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("lazy_loading.txt"))));
    }

    private void start() throws IOException {
        int numberOfDays = readNumber();
        for (int dayNumber = 1; dayNumber <= numberOfDays; dayNumber++) {
            int numerOfItems = readNumber();
            List<Integer> items = new ArrayList<>();
            for (int j = 1; j <= numerOfItems; j++) {
                items.add(readNumber());
            }
            int numberOfTrips = handleDay(dayNumber, numerOfItems, items);
            writeToFile("Case #" + dayNumber + ": " + numberOfTrips);
        }
    }

    private int readNumber() {
        return Integer.parseInt(scanner.next());
    }

    private void close() throws IOException {
        outputWriter.close();
    }

    private int handleDay(int day, int numberOfItems, List<Integer> items) {
        items.sort((a, b) -> b.compareTo(a));
        System.out.println("Day " + day + "... Moving " + numberOfItems + " items:");
        System.out.println(items.stream().map(i -> "" + i).collect(Collectors.joining(", ")));
        System.out.println("");

        int result = 0;
        while (!items.isEmpty() && itemsStillSupportValidTrip(items)) {
            int factor = 1;
            Integer largestItem = items.get(0);
            System.out.println("Removing item: " + largestItem);
            while (factor * largestItem < 50) {
                factor++;
                System.out.println("Incrementing factor: " + (factor - 1) + " -> " + factor);
            }
            items.remove(largestItem);
            System.out.println("Factor is: " + factor + " -> Removing " + (int) (factor - 1) + " small item(s)");
            while (factor > 1) {
                Integer smallestItem = items.get(items.size() - 1);
                System.out.println(smallestItem + " removed...");
                items.remove(smallestItem);
                factor--;
            }
            result++;
            System.out.println("result increased to: " + result);
        }
        System.out.println("");
        return result;
    }

    private boolean itemsStillSupportValidTrip(List<Integer> items) {
        return 50 <= items.get(0) * items.size();
    }

    private void writeToFile(String line) throws IOException {
        outputWriter.append(line);
        outputWriter.newLine();
    }

}
