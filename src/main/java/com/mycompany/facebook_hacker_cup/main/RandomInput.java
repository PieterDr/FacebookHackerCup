package com.mycompany.facebook_hacker_cup.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;

public class RandomInput {

    public static void main(String[] args) throws IOException {
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

    private static void writeToFile(String line, BufferedWriter inputWriter) throws IOException {
        inputWriter.append(line);
        inputWriter.newLine();
    }
}