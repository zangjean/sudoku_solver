package noyau;

import java.util.Random;

public class GridGenerator {
    private static final int SIZE=9;
    private int empty_cells; // Number of empty cells in the generated Sudoku grid
    private Random random;

    SudokuGrid sudokuGrid;

    public GridGenerator(int number_empty){
        empty_cells=number_empty;
        sudokuGrid=new SudokuGrid();
        random=new Random();
    }
    public SudokuGrid generatePartialGrid(){
        generateDiagonalBlocks();
        removeCells();
        return sudokuGrid;
    }

    private void removeCells() {
        int count = empty_cells;
        while (count > 0) {
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);/*
            if (grid[row][col] != 0) {
                grid[row][col] = 0;
                count--;
            }
            */
            int value=sudokuGrid.getGrid()[row][col].getValue();
            if( value != 0){
                sudokuGrid.suppValue(row,col,value);
                count=count-1;
            }
        }
    }

    private void generateDiagonalBlocks(){
        for (int i = 0; i < SIZE; i += 3) {
            generateDiagonalBlock(i, i);
        }
        sudokuGrid=new Backtracking(sudokuGrid).solve();
        //sudokuGrid.printGrid();
    }

    private void generateDiagonalBlock(int row, int col){
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        shuffleArray(numbers);
        int ind=0;
        for (int i = row; i < row + 3; i++) {
            for (int j = col; j < col + 3; j++) {
                sudokuGrid.setValueOnGrid(numbers[ind++],i,j);
            }
        }
    }

    private void shuffleArray(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
}
