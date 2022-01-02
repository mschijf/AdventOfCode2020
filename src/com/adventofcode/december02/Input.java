package com.adventofcode.december02;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Input {
    private ArrayList<Integer> field1 = new ArrayList();
    private ArrayList<Integer> field2 = new ArrayList();
    private ArrayList<String>  field3 = new ArrayList();
    private ArrayList<String>  field4 = new ArrayList();

    public Input() {
        List<String> list = readFile("data/input02");
        doSplit(list);
    }

    public int getField1(int i) {
        return field1.get(i);
    }
    public int getField2(int i) {
        return field2.get(i);
    }
    public String getField3(int i) {
        return field3.get(i);
    }
    public String getField4(int i) {
        return field4.get(i);
    }

    public int size() {
        return field1.size();
    }


    private void doSplit(List<String> list) {
        for (String line: list) {
            List<String> splitted = split(line, "- :");
            field1.add(Integer.valueOf(splitted.get(0)));
            field2.add(Integer.valueOf(splitted.get(1)));
            field3.add(splitted.get(2));
            field4.add(splitted.get(3));
        }
    }

    private List<String> split(String input, String delimeters) {
        List<String> result = new ArrayList<String>();
        int j=0;
        for (;;) {
            int i = nextNonDelimeter(input, j, delimeters);
            if (i < 0)
                return result;

            j = nextDelimeter(input, i + 1, delimeters);
            if (j > i) {
                result.add(input.substring(i, j));
            } else {
                result.add(input.substring(i));
                return result;
            }
        }
    }

    private int nextDelimeter(String input, int startIndex, String delimeters) {
        for (int i=startIndex; i<input.length(); ++i) {
            if (delimeters.indexOf(input.charAt(i)) >= 0)
                return i;
        }
        return -1;
    }
    private int nextNonDelimeter(String input, int startIndex, String delimeters) {
        for (int i=startIndex; i<input.length(); ++i) {
            if (delimeters.indexOf(input.charAt(i)) < 0)
                return i;
        }
        return -1;
    }

    private List<String> readFile(String fileName) {
        int lineCount = 0;
        List<String> inputList = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = br.readLine()) != null) {
                inputList.add(line);
            }
        } catch ( Exception e) {

        }
        return inputList;
    }
}
