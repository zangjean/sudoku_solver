package noyau;

import java.util.*;

public class Backtracking {
    private static final int SIZE = 9;
    int[][] tab = new int[SIZE][SIZE];
    SudokuGrid sudoku;
    public Backtracking(SudokuGrid sudokuGrid) {
        switchGridTab(sudokuGrid);
        //sudoku=new SudokuGrid();
    }

    public void switchTabGrid(){
        SudokuGrid sudokuGrid=new SudokuGrid();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                sudokuGrid.getGrid()[row][col].setValue(tab[row][col]);
            }
        }
        sudoku=sudokuGrid;
    }

    public SudokuGrid getSudoku(){
        return sudoku;
    }


    public void switchGridTab(SudokuGrid sudokuGrid){
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                tab[row][col]=sudokuGrid.getGrid()[row][col].getValue();
            }
        }
    }

    public SudokuGrid solve() {
        System.out.println("J'applique l'algo de backtracking");
        if (this.solveSudoku()) {
            switchTabGrid();
            return sudoku;
        } else {
            System.out.println("PAS DE SOLUTION");
            return null;
        }

    }

    private boolean solveSudoku() {
        LinkedList<Integer> emptyCells = new LinkedList<>();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (tab[row][col] == 0) {
                    emptyCells.add(row * SIZE + col);
                }
            }
        }

        int index = 0;
        int[] stack = new int[emptyCells.size()];

        while (index >= 0 && index < emptyCells.size()) {
            int cell = emptyCells.get(index);
            int row = cell / SIZE;
            int col = cell % SIZE;

            boolean found = false;
            for (int num = tab[row][col] + 1; num <= SIZE; num++) {
                if (isSafe(row, col, num)) {
                    tab[row][col] = num;
                    stack[index] = num;
                    found = true;
                    break;
                }
            }

            if (found) {
                index++;
            } else {
                tab[row][col] = 0;
                index--;
            }
        }

        return index >= 0;
    }

    private boolean isSafe(int row, int col, int num) {
        return !usedInRow(row, num) && !usedInCol(col, num) && !usedInBox(row - row % 3, col - col % 3, num);
    }
    private boolean usedInRow(int row, int num) {
        for (int col = 0; col < SIZE; col++) {
            if (tab[row][col] == num) {
                return true;
            }
        }
        return false;
    }
    private boolean usedInCol(int col, int num) {
        for (int row = 0; row < SIZE; row++) {
            if (tab[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean usedInBox(int boxStartRow, int boxStartCol, int num) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (tab[row + boxStartRow][col + boxStartCol] == num) {
                    return true;
                }
            }
        }
        return false;
    }
}
