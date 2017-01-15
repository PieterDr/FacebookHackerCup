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
        scanner = new Scanner(this.getClass().getClassLoader().getResourceAsStream("round1/pie_progress.txt"));
        scanner.useDelimiter("\n");
        outputWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("round1/output_pie_progress.txt"))));
        start();
        scanner.close();
        outputWriter.close();
    }

    private void start() throws IOException {
        long cases = readLong();
        for (int i = 1; i <= cases; i++) {
            Long[] caseInfo = readLongLine();
            long days = caseInfo[0];
            List<DailyPieList> pieList = new ArrayList<>();
            for (int j = 1; j <= days; j++) {
                addPies(pieList, j);
            }
            handleCase(pieList, days, i);
        }
    }

    private void handleCase(List<DailyPieList> pieList, long days, long caseNumber) throws IOException {
        List<Pie> chosenPies = new ArrayList<>();
        for (int i = 1; i <= days; i++) {
            chosenPies.add(choosePie(pieList.subList(0, i), chosenPies));
        }
        long price = calculatePrice(chosenPies);
        String line = "Case #" + caseNumber + ": " + price;
        writeToFile(line);
    }

    private Pie choosePie(List<DailyPieList> pieList, List<Pie> chosenPies) {
        Map<Long, Long> piesPerDayMap = getPiesPerDay(chosenPies);
        Pie pie = new Pie(0l, -1000l);
        long lowestPrice = Long.MAX_VALUE;
        for (int i = 0; i < pieList.size(); i++) {
            Optional<Pie> optionalPie = pieList.get(i).getCheapestPie();
            if (optionalPie.isPresent()) {
                long price = calculatePriceFor(optionalPie.get(), chosenPies, piesPerDayMap);
                if (price < lowestPrice) {
                    lowestPrice = price;
                    pie = optionalPie.get();
                }
            }
        }
        pie.choose();
        return pie;
    }

    private Map<Long, Long> getPiesPerDay(List<Pie> pies) {
        Map<Long, Long> result = new HashMap<>();
        for (Pie pie : pies) {
            addPieToMap(result, pie);
        }
        return result;
    }

    private void addPieToMap(Map<Long, Long> result, Pie pie) {
        if (result.get(pie.day) == null) {
            result.put(pie.day, 1l);
        } else {
            result.put(pie.day, result.get(pie.day) + 1);
        }
    }

    private long calculatePrice(List<Pie> chosenPies) {
        return calculatePriceFor(null, chosenPies, getPiesPerDay(chosenPies));
    }

    private long calculatePriceFor(Pie potentialPie, List<Pie> chosenPies, Map<Long, Long> piesPerDayMap) {
        Map<Long, Long> mapCopy = new HashMap<>(piesPerDayMap);
        if (potentialPie != null) {
            addPieToMap(mapCopy, potentialPie);
        }
        long price = 0;
        if(potentialPie != null) {
            price = potentialPie.price;
        }
        for (Pie pie : chosenPies) {
            price += pie.price;
        }
        for (long i : mapCopy.keySet()) {
            long value = mapCopy.get(i);
            price += value * value;
        }
        return price;
    }

    private void addPies(List<DailyPieList> pieList, long j) {
        List<Pie> pies = Arrays.asList(readLongLine()).stream().map(i -> new Pie(i, j)).collect(toList());
        pieList.add(new DailyPieList(pies));
    }

    private long readLong() {
        return Long.parseLong(scanner.next());
    }

    private Long[] readLongLine() {
        String[] input = scanner.next().split("\\s");
        Long[] output = new Long[input.length];
        for (int i = 0; i < input.length; i++) {
            output[i] = Long.parseLong(input[i]);
        }
        return output;
    }

    private void writeToFile(String line) throws IOException {
        outputWriter.append(line);
        outputWriter.newLine();
    }

    private static class Pie {

        public Long price;
        public Long day;
        public boolean chosen = false;

        public Pie(Long price, Long day) {
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