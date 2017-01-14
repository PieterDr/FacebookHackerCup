package com.mycompany.facebook_hacker_cup.main.round1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import static java.util.stream.Collectors.toList;

public class ProgressPie {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        new ProgressPie();
    }

    private Scanner scanner;
    private BufferedWriter outputWriter;

    public ProgressPie() throws FileNotFoundException, IOException {
        scanner = new Scanner(this.getClass().getClassLoader().getResourceAsStream("round1/pie_progress_example_input.txt"));
        scanner.useDelimiter("\n");
        outputWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("round1/output_progress_pie.txt"))));
        start();
        scanner.close();
    }

    private void start() throws IOException {
        int cases = readInt();
        for (int i = 1; i <= cases; i++) {
            Integer[] caseInfo = readIntLine();
            int days = caseInfo[0];
            int piesPerDay = caseInfo[1];
            List<DailyPieList> pieList = new ArrayList<>();
            for (int j = 1; j <= days; j++) {
                addPies(pieList, j);
            }
            handleCase(pieList, days, i);
        }
    }

    private void handleCase(List<DailyPieList> pieList, int days, int caseNumber) throws IOException {
        List<Pie> chosenPies = new ArrayList<>();
        for (int i = 1; i <= days; i++) {
            chosenPies.add(choosePie(pieList.subList(0, i), chosenPies));
        }
        int price = calculatePrice(chosenPies);
        String line = "Case #" + caseNumber + ": " + price;
        writeToFile(line);
        System.out.println(line);
    }

    private Pie choosePie(List<DailyPieList> pieList, List<Pie> chosenPies) {
        Map<Integer, Integer> piesPerDayMap = getPiesPerDay(chosenPies);
        Pie pie = new Pie(0, -1000);
        int lowestPrice = Integer.MAX_VALUE;
        for (int i = 0; i < pieList.size(); i++) {
            Optional<Pie> optionalPie = pieList.get(i).getCheapestPie();
            if (optionalPie.isPresent()) {
                int price = calculatePriceFor(optionalPie.get(), chosenPies, piesPerDayMap);
                if (price < lowestPrice) {
                    lowestPrice = price;
                    pie = optionalPie.get();
                }
            }
        }
        if (pie.day == -1000) {
            throw new RuntimeException("Day -1000 ;-)");
        }
        pie.choose();
        return pie;
    }

    private Map<Integer, Integer> getPiesPerDay(List<Pie> pies) {
        Map<Integer, Integer> result = new HashMap<>();
        for (Pie pie : pies) {
            addPieToMap(result, pie);
        }
        return result;
    }

    private void addPieToMap(Map<Integer, Integer> result, Pie pie) {
        if (result.get(pie.day) == null) {
            result.put(pie.day, 1);
        } else {
            result.put(pie.day, result.get(pie.day) + 1);
        }
    }

    private int calculatePrice(List<Pie> chosenPies) {
        return calculatePriceFor(null, chosenPies, getPiesPerDay(chosenPies));
    }

    private int calculatePriceFor(Pie potentialPie, List<Pie> chosenPies, Map<Integer, Integer> piesPerDayMap) {
        Map<Integer, Integer> mapCopy = new HashMap<>(piesPerDayMap);
        if (potentialPie != null) {
            addPieToMap(mapCopy, potentialPie);
        }
        int price = 0;
        if(potentialPie != null) {
            price = potentialPie.price;
        }
        for (Pie pie : chosenPies) {
            price += pie.price;
        }
        for (int i : mapCopy.keySet()) {
            int value = mapCopy.get(i);
            price += value * value;
        }
        return price;
    }

    private void addPies(List<DailyPieList> pieList, int j) {
        List<Pie> pies = Arrays.asList(readIntLine()).stream().map(i -> new Pie(i, j)).collect(toList());
        pieList.add(new DailyPieList(pies));
    }

    private int readInt() {
        return Integer.parseInt(scanner.next());
    }

    private Integer[] readIntLine() {
        String[] input = scanner.next().split("\\s");
        Integer[] output = new Integer[input.length];
        for (int i = 0; i < input.length; i++) {
            output[i] = Integer.parseInt(input[i]);
        }
        return output;
    }

    private void writeToFile(String line) throws IOException {
        outputWriter.append(line);
        outputWriter.newLine();
    }

    private static class Pie {

        public Integer price;
        public Integer day;
        public boolean chosen = false;

        public Pie(Integer price, Integer day) {
            this.price = price;
            this.day = day;
        }

        public void choose() {
            chosen = true;
        }

        @Override
        public String toString() {
            return "Pie: " + price + "@" + day;
        }
    }

    private static class DailyPieList {

        public List<Pie> pieList;

        public DailyPieList(List<Pie> pieList) {
            pieList.sort((p1, p2) -> p1.price.compareTo(p2.price));
            this.pieList = pieList;
        }

        public Optional<Pie> getCheapestPie() {
            return pieList.stream()
                    .filter(pie -> !pie.chosen)
                    .findFirst();
        }
    }
}