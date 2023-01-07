package com.adventofcode.old.december01;

public class Add2020 {
    public static void main(String[] args) {
        for (int i=0; i<Input.list.length-1; ++i) {
            for (int j=i+1; j<Input.list.length; ++j) {
                if (Input.list[i] + Input.list[j] == 2020) {
                    System.out.printf("Found! %d + %d = 2020. Factor = %d", Input.list[i], Input.list[j], Input.list[i]*Input.list[j]);
                }
            }
        }
    }
}
