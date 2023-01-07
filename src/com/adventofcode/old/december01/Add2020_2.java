package com.adventofcode.old.december01;

public class Add2020_2 {
    public static void main(String[] args) {
        for (int i=0; i<Input.list.length-2; ++i) {
            for (int j=i+1; j<Input.list.length-1; ++j) {
                for (int k=j+1; k<Input.list.length; ++k) {
                    if (Input.list[i] + Input.list[j] + Input.list[k] == 2020) {
                        System.out.printf("Found! %d + %d + %d = 2020. Factor = %d",
                                Input.list[i],
                                Input.list[j],
                                Input.list[k],
                                Input.list[i] * Input.list[j] * Input.list[k]);
                    }
                }
            }
        }
    }
}
