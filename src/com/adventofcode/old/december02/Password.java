package com.adventofcode.old.december02;

public class Password {
    public static void main(String[] args) {
        Input input = new Input();
        int correct = 0;
        for (int i=0; i < input.size(); ++i) {
            int nr = countOccurences(input.getField4(i), input.getField3(i));
            if (nr < input.getField1(i) || nr > input.getField2(i)) {
                System.out.print(input.getField1(i));
                System.out.print(input.getField2(i));
                System.out.print(input.getField3(i));
                System.out.println(input.getField4(i));
            } else {
                ++correct;
            }
        }
        System.out.println("Correct ones: " + correct);
    }

    static int countOccurences(String hayStack, String needle) {
        int count = 0;
        int next = hayStack.indexOf(needle, 0);
        while (next >= 0) {
            ++count;
            next = hayStack.indexOf(needle, next+1);
        }
        return count;
    }

}
