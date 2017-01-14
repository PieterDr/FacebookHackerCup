package com.mycompany.facebook_hacker_cup.main.qualifier;

import java.util.Scanner;

public class CalculateDiff {

    public static void main(String[] args) {
        Scanner pieter = new Scanner(CalculateDiff.class.getClassLoader().getResourceAsStream("output_progress_pie_pieter.txt"));
        Scanner tim = new Scanner(CalculateDiff.class.getClassLoader().getResourceAsStream("output_progress_pie_tim.txt"));
        pieter.useDelimiter("\n");
        tim.useDelimiter("\n");
        for(int i = 1; i <= 500; i++) {
            String pLine = pieter.next();
            String tLine = tim.next();
            if(!pLine.equals(tLine)) {
                System.out.println("pieter: " + pLine);
                System.out.println("tim: " + tLine);
            }
        }
    }
    
}
