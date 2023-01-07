package com.adventofcode.old.december03;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Input {
    private List<String> list;

    public Input(String fileName) {
        list = readFile("data/" + fileName);
    }

    List<String> getTreeLines() {
        return list;
    }

    //-----------------------------------------------------------------------------------------------------------

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

    //-----------------------------------------------------------------------------------------------------------

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
