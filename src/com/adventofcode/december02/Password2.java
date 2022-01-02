package com.adventofcode.december02;

public class Password2 {
    public static void main(String[] args) {
        Input input = new Input();
        int correct = 0;
        for (int i=0; i < input.size(); ++i) {
            String example = input.getField4(i);
            int index1 = input.getField1(i)-1;
            int index2 = input.getField2(i)-1;
            if (index1 < example.length() && index2 < example.length()) {
                char ch1 = example.charAt(index1);
                char ch2 = example.charAt(index2);
                char chMust = input.getField3(i).charAt(0);
                if ((ch1 == chMust && ch2 != chMust) || (ch1 != chMust && ch2 == chMust))
                    correct++;
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
