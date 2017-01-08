package com.mycompany.facebook_hacker_cup.main;

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
        lazyLoading();
    }

    private static void progressPie() throws IOException {
        BufferedWriter inputWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("src/main/resources/progress_pie_input.txt"))));
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
        BufferedWriter inputWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("src/main/resources/lazy_loading_input.txt"))));
        writeToFile("500", inputWriter);
        for(int i = 1; i <= 500; i++) {
            writeToFile("10", inputWriter);
            for(int j = 1; j <= 100; j++) {
                int item = random.nextInt(31);
                writeToFile(Integer.toString(item), inputWriter);
            }
        }
        inputWriter.close();
    }

    private static void writeToFile(String line, BufferedWriter inputWriter) throws IOException {
        inputWriter.append(line);
        inputWriter.newLine();
    }
}