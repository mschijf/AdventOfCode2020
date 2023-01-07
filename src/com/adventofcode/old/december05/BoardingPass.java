package com.adventofcode.old.december05;


import java.util.List;
import java.util.stream.Collectors;

public class BoardingPass {

    public static void main(String[] args) {
        BoardingPass pp = new BoardingPass();
        int output = pp.run2();
        System.out.println("Puzzle output : " + output);

    }

    //-----------------------------------------------------------

    private Input input;

    public BoardingPass() {
//        input = new Input("input05_example");
        input = new Input("input05_1");
    }

    public int run() {
        int max = -1;
        for (String line : input.getInputList()) {
            int id = checkPass(line);
            if (id > max)
                max = id;
        }
        return max;
    }

    public int run2() {
        List<Integer> list = input.getInputList()
                .stream()
                .map(s -> checkPass(s))
                .sorted()
                .collect(Collectors.toList());
        for (int i=0; i < list.size()-1; ++i) {
            if (list.get(i)+2 == list.get(i+1)) {
                return list.get(i) + 1;
            }
        }
        return -1;
    }

    private int checkPass(String line) {
        String frontBack = line.substring(0, 7).replace('F', '0').replace('B', '1');
        int row = Integer.parseInt(frontBack, 2);
        String leftRight = line.substring(7, 10).replace('L', '0').replace('R', '1');
        int col = Integer.parseInt(leftRight, 2);
        return row * 8 + col;
    }
}
