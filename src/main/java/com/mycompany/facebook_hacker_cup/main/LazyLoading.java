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

    private void start() {
        int numberOfDays = readNumber();
        for(int i = 1; i <= numberOfDays; i++) {
            int numerOfItems = readNumber();
            List<Integer> items = new ArrayList<>();
            for(int j = 1; j <= numerOfItems; j++) {
                items.add(readNumber());
            }
            printDay(i, numerOfItems, items);
        }
    }

    private int readNumber() throws NumberFormatException {
        return Integer.parseInt(scanner.next());
    }

    private void close() throws IOException {
        outputWriter.close();
    }

    private void printDay(int day, int numberOfItems, List<Integer> items) {
        System.out.println("Day " + day + "... Moving " + numberOfItems + " items:");
        System.out.println(items.stream().map(i -> "" + i).collect(Collectors.joining(", ")));
        System.out.println("");
    }

}
