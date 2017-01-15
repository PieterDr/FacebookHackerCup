package com.mycompany.facebook_hacker_cup.main.qualifier;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;

public class RandomInput {

    private static final Random random = new Random();

    public static void main(String[] args) throws IOException {
//        progressPie();
//        lazyLoading();
        pieProgress();
    }

    private static void progressPie() throws IOException {
        BufferedWriter inputWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("src/main/resources/input_progress_pie.txt"))));
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

    private static void lazyLoading() throws IOException {
        BufferedWriter inputWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("src/main/resources/input_lazy_loading.txt"))));
        writeToFile("500", inputWriter);
        for (int i = 1; i <= 500; i++) {
            writeToFile("10", inputWriter);
            for (int j = 1; j <= 10; j++) {
                int item = random.nextInt(31);
                writeToFile(Integer.toString(item), inputWriter);
            }
        }
        inputWriter.close();
    }

    private static void pieProgress() throws IOException {
        BufferedWriter inputWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("src/main/resources/round1/pie_progress_random.txt"))));
        writeToFile("100", inputWriter);
        for (int caseNumber = 1; caseNumber <= 100; caseNumber++) {
            int days = 300;
            int pies = 300;
            writeToFile(days + " " + pies, inputWriter);
            for (int day = 1; day <= days; day++) {
                String line = Integer.toString(1000000);
                for (int pie = 1; pie <= pies; pie++) {
                    line += " " + Integer.toString(1000000);
                }
                writeToFile(line, inputWriter);
            }
        }
        inputWriter.close();
    }

    private static void writeToFile(String line, BufferedWriter inputWriter) throws IOException {
        inputWriter.append(line);
        inputWriter.newLine();
    }

}
