package com.adventofcode.december03;

public class TreeWalk {

    public static void main(String[] args) {
        TreeWalk treeWalk = new TreeWalk();
        int treeCount = treeWalk.run(1, 1);
        treeCount *= treeWalk.run(3, 1);
        treeCount *= treeWalk.run(5, 1);
        treeCount *= treeWalk.run(7, 1);
        treeCount *= treeWalk.run(1, 2);
        System.out.println("Puzzle output : " + treeCount);

    }

    //-----------------------------------------------------------

    private Input input;

    public TreeWalk() {
        //input = new Input("input03_example");
        input = new Input("input03_1");
    }

    public int run(int right, int down) {
        int treeCount = 0;
        int fieldLength = input.getTreeLines().get(0).length();
        int fieldHeight = input.getTreeLines().size();
        int col = 0 + right;
        int row = 0 + down;
        while (row < fieldHeight) {
            if (input.getTreeLines().get(row).charAt(col) == '#')
                treeCount++;
            col = (col + right) % fieldLength;
            row += down;
        }
        return treeCount;
    }

}
