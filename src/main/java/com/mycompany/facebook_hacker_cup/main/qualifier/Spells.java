package com.mycompany.facebook_hacker_cup.main.qualifier;

public class Spells {

    public static void main(String[] args) {
        calculateProbabilityFor(10.0, 2.0, 6.0);
    }

    private static void calculateProbabilityFor(double sum, double dies, double sides) {
        int result = 0;
        double upper = Math.floor((sum-dies)/sides);
    }
    
}
