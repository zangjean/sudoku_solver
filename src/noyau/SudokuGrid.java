
package noyau;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SudokuGrid {
    private CelluleSudoku[][] grid;

    public SudokuGrid() {
        this.grid = new CelluleSudoku[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                this.grid[row][col] = new CelluleSudoku();
            }
        }
    }

    public CelluleSudoku returnCellule(int row, int col) {
        return this.grid[row][col];
    }


    public void setValueOnGrid(int value, int row, int col) {
        this.grid[row][col].setValue(value);
        if(value!=0){
            this.grid[row][col].getCellNotes().getNotes().clear();
        }

        this.updateNotes();
    }

    public CelluleSudoku[][] getGrid () {
            return this.grid;
    }
    public void setGrid(CelluleSudoku[][] grid1){
        this.grid=grid1;
    }

    public void printGrid () {

        for (int row = 0; row < 9; row++) {
            System.out.print("\n");
            if ((row) % 3 == 0) {
                System.out.println("-------------------------------");
            }
            for (int col = 0; col < 9; col++) {
                if ((col) % 3 == 0) {
                    System.out.print(" |");
                }
                System.out.print(" " + this.grid[row][col].getValue());


            }
        }
        System.out.print("\n");
    }

    private boolean isValidGridZone ( int rowStart, int colStart, int rowEnd, int colEnd){
        Set<Integer> values = new HashSet<>();
        boolean res = true;
        for (int row = rowStart; row <= rowEnd; row++) {
            for (int col = colStart; col <= colEnd; col++) {
                if (this.grid[row][col].getValue() != 0) {
                    int value = this.grid[row][col].getValue();

                    if (values.contains(value)) {
                        res = false;
                    }
                    values.add(value);
                }
            }
        }
        return res;
    }

    private boolean isValidGridRow ( int numberRow){
        return this.isValidGridZone(numberRow, 0, numberRow, 8);
    }

    private boolean isValidGridCol ( int numberCol){
        return this.isValidGridZone(0, numberCol, 8, numberCol);
    }

    private boolean isValidGridQuare ( int numberQuare){
        int xmin = (numberQuare % 3) * 3;
        int ymin = (numberQuare / 3) * 3;
        int xmax = xmin + 2;
        int ymax = ymin + 2;
        return this.isValidGridZone(xmin, ymin, xmax, ymax);
    }

    public boolean isValidGrid () {
        boolean res = true;
        for (int ind = 0; ind <= 8; ind++) {
            if (res) {
                res = this.isValidGridRow(ind) && this.isValidGridCol(ind) && this.isValidGridQuare(ind);
            } else {
                return res;
            }
        }
        return res;
    }

    public void updateNotes(){
        for(int row=0;row<=8;row++){
            for(int col=0;col<=8;col++){
                int value=this.grid[row][col].getValue();

                //this.grid[row][col].getCellNotes().getNotes().clear();
                for (int i = 0; i < 9; i++) {
                    if (i != row) {
                        this.grid[i][col].getCellNotes().deletNote(value);
                    }
                    if (i != col) {
                        this.grid[row][i].getCellNotes().deletNote(value);
                    }
                }
                int BLOC_SIZE=3;
                int coordBlocX=(row/BLOC_SIZE)*BLOC_SIZE;
                int coordBlocY=(col/BLOC_SIZE)*BLOC_SIZE;

                for(int i=coordBlocX; i < coordBlocX+BLOC_SIZE; i++){
                    for(int j=coordBlocY; j < coordBlocY+BLOC_SIZE; j++){
                        if(!(i == row && j == col)) {
                            this.grid[i][j].getCellNotes().deletNote(value);
                        }
                    }
                }
            }
        }
    }

    public void suppValue(int rowD,int colD,int value){
       // System.out.println("Je veux supp:"+value);
        if(value!=0) {
            this.grid[rowD][colD].setValue(0);
            Notes n = new Notes();
            this.grid[rowD][colD].setNotes(n);

            for (int row = 0; row <= 8; row++) {
                for (int col = 0; col <= 8; col++) {
                    //this.grid[row][col].getCellNotes().getNotes().clear();

                    for (int i = 0; i < 9; i++) {
                        //this.grid[i][colD].getCellNotes().getNotes().add(Integer.valueOf(value));
                        //this.grid[rowD][i].getCellNotes().getNotes().add(Integer.valueOf(value));

                        if (i != row) {
                            if (!(this.grid[i][col].getCellNotes().containNote(value))) {
                                this.grid[i][col].getCellNotes().getNotes().add(Integer.valueOf(value));
                            }
                        }
                        if (i != col) {
                            if (!(this.grid[row][i].getCellNotes().containNote(value))) {
                                this.grid[row][i].getCellNotes().getNotes().add(Integer.valueOf(value));
                            }
                        }
                    }
                    int BLOC_SIZE = 3;
                    int coordBlocX = (row / BLOC_SIZE) * BLOC_SIZE;
                    int coordBlocY = (col / BLOC_SIZE) * BLOC_SIZE;

                    for (int i = coordBlocX; i < coordBlocX + BLOC_SIZE; i++) {
                        for (int j = coordBlocY; j < coordBlocY + BLOC_SIZE; j++) {
                            if (!(i == row && j == col)) {
                                if (!(this.grid[i][j].getCellNotes().containNote(value))) {
                                    this.grid[i][j].getCellNotes().getNotes().add(Integer.valueOf(value));
                                }
                            }
                        }
                    }
                }
            }
        }



    }

    public boolean noEmptyCells(){
        boolean result=true;
        for(int row=0;row<9;row++){
            for(int col=0;col<9;col++){
                if(result){
                    result=this.grid[row][col].celluleIsNot0();
                }else{
                    return result;
                }
            }
        }
        return result;
    }

    public boolean isSolved(){
        return (this.noEmptyCells()&&this.isValidGrid());
    }

    public HashMap returnNextPos(int posX, int posY){
        if((posX>=8)&&(posY>=8)){//si la prochaine case c'est 8,8 alors on etait sur la dernière case de la grille
            return null;
        }
        HashMap<String,Integer> pos=new HashMap<>();
        if(posY>=8){
            pos.put("nextRow",posX+1);
            pos.put("nextCol",0);
            return pos;
        }else{
            pos.put("nextRow",posX);
            pos.put("nextCol",posY+1);
            return pos;
        }
    }

    public SudokuGrid cloneGrid(){
        SudokuGrid clone = new SudokuGrid();
        clone.setGrid(this.grid.clone());
        return clone;
    }





}
