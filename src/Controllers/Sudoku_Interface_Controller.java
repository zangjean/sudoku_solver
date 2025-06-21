package Controllers;

import javafx.scene.control.ChoiceBox;
import noyau.*;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;


public class Sudoku_Interface_Controller {
    private int level;
    public ChoiceBox choiseBox_fxid;
    public Label affichage_fxid;
    private boolean gridAlreadyCheck=false;
    @FXML
    public GridPane sudokuGrid_fxid;

    @FXML
    public Label note1;
    @FXML
    public Label note2;
    @FXML
    public Label note3;
    @FXML
    public Label note4;
    @FXML
    public Label note5;
    @FXML
    public Label note6;
    @FXML
    public Label note7;
    @FXML
    public Label note8;
    @FXML
    public Label note9;
    private BorderPane selectedCell=null;
    @FXML
    public GridPane padGrid_fxId;

    SudokuGrid grid=new SudokuGrid();

    Rules rules=new Rules();






    @FXML
    public void OnMouseEnteredCell(MouseEvent mouseEvent) {
        if(selectedCell==null) {
            ((BorderPane) mouseEvent.getSource()).setStyle("-fx-background-color: #34b6e0;");
        }
    }

    @FXML
    public void OnMouseExitedCell(MouseEvent mouseEvent) {
        if(selectedCell==null){
            ((BorderPane) mouseEvent.getSource()).setStyle("-fx-background-color: #ffffff;");
        }
    }

    @FXML
    public void OnMouseClickedCell(MouseEvent mouseEvent) {
        BorderPane cellPane = (BorderPane) mouseEvent.getSource();

        if (selectedCell == cellPane) {
            // Si je clique à nouveau sur la même cellule, deselection
            Text cellText = (Text) cellPane.getCenter();
            cellText.setFill(Color.BLACK);
            cellPane.setStyle("-fx-background-color: #ffffff;");
            selectedCell = null;
        } else {
            // deselection cellule precedente
            if (selectedCell != null) {
                selectedCell.setStyle("-fx-background-color: #ffffff;");
                ((Text) selectedCell.getCenter()).setFill(Color.BLACK);
            }

            // selection nouvelle cellule
            Text cellText = (Text) cellPane.getCenter();
            cellText.setFill(Color.RED);
            cellPane.setStyle("-fx-background-color: #1518A1FF;");
            selectedCell = cellPane;
        }

        if (gridAlreadyCheck) {
            if(selectedCell!=null){
                Text cellText = (Text) cellPane.getCenter();
                if (sudokuGrid_fxid.getChildren().contains(cellPane)) {
                    Integer rowIndex = GridPane.getRowIndex(cellPane);
                    Integer colIndex = GridPane.getColumnIndex(cellPane);
                    if(rowIndex==null){
                        rowIndex=0;
                    }
                    if(colIndex==null){
                        colIndex=0;
                    }

                    if (rowIndex != null && colIndex != null) {
                        ArrayList<Integer> notes = grid.getGrid()[rowIndex][colIndex].getCellNotes().getNotes();
                        System.out.println("CASE:"+rowIndex+" "+colIndex+ " -> "+notes);
                        for (Integer note : notes) {
                            switch (note) {
                                case 1:
                                    note1.setOpacity(1);
                                    //System.out.println("opacity= " + note1.getOpacity());
                                    break;
                                case 2:
                                    note2.setOpacity(1);
                                    //System.out.println("opacity= " + note2.getOpacity());
                                    break;
                                case 3:
                                    note3.setOpacity(1);
                                    //System.out.println("opacity= " + note3.getOpacity());
                                    break;
                                case 4:
                                    note4.setOpacity(1);
                                    //System.out.println("opacity= " + note4.getOpacity());
                                    break;
                                case 5:
                                    note5.setOpacity(1);
                                    //System.out.println("opacity= " + note5.getOpacity());
                                    break;
                                case 6:
                                    note6.setOpacity(1);
                                    //System.out.println("opacity= " + note6.getOpacity());
                                    break;
                                case 7:
                                    note7.setOpacity(1);
                                    //System.out.println("opacity= " + note7.getOpacity());
                                    break;
                                case 8:
                                    note8.setOpacity(1);
                                    //System.out.println("opacity= " + note8.getOpacity());
                                    break;
                                case 9:
                                    note9.setOpacity(1);
                                    //System.out.println("opacity= " + note9.getOpacity());
                                    break;
                                default:
                                    throw new IllegalStateException("Unexpected value: " + note);
                            }

                        }
                        for (int i=1;i<=9;i++){
                            if(!(notes.contains(Integer.valueOf(i)))){
                                switch (i) {
                                    case 1:
                                        note1.setOpacity(0.3);
                                        //System.out.println("opacity= " + note1.getOpacity());
                                        break;
                                    case 2:
                                        note2.setOpacity(0.3);
                                        //System.out.println("opacity= " + note2.getOpacity());
                                        break;
                                    case 3:
                                        note3.setOpacity(0.3);
                                        //System.out.println("opacity= " + note3.getOpacity());
                                        break;
                                    case 4:
                                        note4.setOpacity(0.3);
                                        //System.out.println("opacity= " + note4.getOpacity());
                                        break;
                                    case 5:
                                        note5.setOpacity(0.3);
                                        //System.out.println("opacity= " + note5.getOpacity());
                                        break;
                                    case 6:
                                        note6.setOpacity(0.3);
                                        //System.out.println("opacity= " + note6.getOpacity());
                                        break;
                                    case 7:
                                        note7.setOpacity(0.3);
                                        //System.out.println("opacity= " + note7.getOpacity());
                                        break;
                                    case 8:
                                        note8.setOpacity(0.3);
                                        //System.out.println("opacity= " + note8.getOpacity());
                                        break;
                                    case 9:
                                        note9.setOpacity(0.3);
                                        //System.out.println("opacity= " + note9.getOpacity());
                                        break;
                                    default:
                                        throw new IllegalStateException("Unexpected value: " + i);
                                }
                            }
                        }
                    }

                }
            }
        }
    }
    @FXML
    public void OnActionButtonPad(ActionEvent actionEvent) {
        if (selectedCell != null) {
            Button sourceButton = (Button) actionEvent.getSource();
            String buttonText = sourceButton.getText();

            Text cellText = (Text) selectedCell.getCenter();
            cellText.setText(buttonText);
        }
    }

    @FXML
    public void OnActionOkSudoku(ActionEvent actionEvent) {
        for (Node node : sudokuGrid_fxid.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer colIndex = GridPane.getColumnIndex(node);
            if(rowIndex==null){
                rowIndex=0;
            }
            if(colIndex==null){
                colIndex=0;
            }

            if (node instanceof BorderPane cell) {
                Node center= cell.getCenter();
                if (center instanceof Text textNode) {
                    String textContent = textNode.getText();
                    int number = Integer.parseInt(textContent);
                    grid.setValueOnGrid(number,rowIndex,colIndex);
                    //System.out.println("row= "+rowIndex+"  col= " +colIndex+ "  number= "+number);
                }
            }
        }
        if(!grid.isValidGrid()){
            Dialog.showErrorDialog("FAIL","Your Sudoku Grid is not Valid!! Please restart");
            gridAlreadyCheck=false;
            grid=new SudokuGrid();
        }else{
            gridAlreadyCheck=true;
        }
        grid.printGrid();
        System.out.println("Is valid: "+grid.isValidGrid());
    }

    @FXML
    public void applyLastEmptyRule(ActionEvent actionEvent) {
        if(gridAlreadyCheck){
            //Rules rules=new Rules();
            rules.lastEmptyRule(grid);
            grid.printGrid();
            this.updateVisualGrid();
        }

    }

    public void updateVisualGrid() {
        if (gridAlreadyCheck) {
            for (int row = 0; row <= 8; row++) {
                for (int col = 0; col <= 8; col++) {
                    int value = grid.getGrid()[row][col].getValue();
                    BorderPane borderPane = getBorderPaneByRowColumnIndex(row, col, this.sudokuGrid_fxid);

                    // Faire quelque chose avec le BorderPane récupéré
                    // Par exemple, mettre à jour le texte d'un Text au centre du BorderPane
                    if (borderPane != null) {
                        Text cellText = (Text) borderPane.getCenter();
                        cellText.setText(String.valueOf(value));
                    }
                }
            }
        }
    }

    private BorderPane getBorderPaneByRowColumnIndex(int row, int col, GridPane gridPane) {
        ObservableList<Node> children = gridPane.getChildren();

        for (Node node : children) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer colIndex = GridPane.getColumnIndex(node);
            if(rowIndex==null){
                rowIndex=0;
            }
            if(colIndex==null){
                colIndex=0;
            }
            if (rowIndex == row && colIndex == col && node instanceof BorderPane) {
                return (BorderPane) node;
            }
        }

        return null;
    }

    @FXML
    public void OnActionSuppValue(ActionEvent actionEvent){
        if (selectedCell != null) {
            Text cellText = (Text) selectedCell.getCenter();
            cellText.setText("");
            Integer rowIndex = GridPane.getRowIndex(selectedCell);
            Integer colIndex = GridPane.getColumnIndex(selectedCell);
            if(rowIndex==null){
                rowIndex=0;
            }
            if(colIndex==null){
                colIndex=0;
            }
            int value=grid.getGrid()[rowIndex][colIndex].getValue();
            grid.suppValue(rowIndex,colIndex,value);
        }
    }


    @FXML
    public void applySingletonNus(ActionEvent actionEvent) {
        if(gridAlreadyCheck){

            rules.singletonNus(grid);
            grid.printGrid();
            this.updateVisualGrid();
        }
    }

    @FXML
    public void applyPairesNue(ActionEvent actionEvent) {
        if(gridAlreadyCheck){
            System.out.println("regle apply paires nues");
            rules.pairesNues(grid);
            grid.printGrid();
            this.updateVisualGrid();
        }
    }





    @FXML

    public void applySingletonCache(ActionEvent actionEvent) {
        if(gridAlreadyCheck){
            rules.singletonCache(grid);
            grid.printGrid();
            this.updateVisualGrid();
        }
    }
    @FXML

    public void applyPairesCache(ActionEvent actionEvent) {
        if(gridAlreadyCheck){
            rules.pairesCache(grid);
            grid.printGrid();
            this.updateVisualGrid();
        }
    }
    @FXML

    public void applyTripletsNus(ActionEvent actionEvent) {
        if(gridAlreadyCheck){
            System.out.println("regle apply triplets nus");
            rules.tripletsNus(grid);
            grid.printGrid();
            this.updateVisualGrid();
        }
    }
    @FXML

    public void applyTripletsCachés(ActionEvent actionEvent) {
        if(gridAlreadyCheck){
            System.out.println("regle apply triplets nus");
            rules.tripletsCache(grid);
            grid.printGrid();
            this.updateVisualGrid();
        }
    }
    @FXML

    public void applyKintersections(ActionEvent actionEvent) {
        if(gridAlreadyCheck){
            System.out.println("regle apply triplets nus");
            rules.KIntersections(grid);
            grid.printGrid();
            this.updateVisualGrid();
        }
    }
    @FXML

    public void onActionIsSolved(ActionEvent actionEvent) {
        if(gridAlreadyCheck){
            boolean check=grid.isSolved();
            System.out.println("SUDOKU SOLVED: "+check);
            if(check){
                affichage_fxid.setText("RESOLU!");
            }else{
                affichage_fxid.setText("NON RESOLU!");
            }
        }
    }
    @FXML


    public void onActionBacktrack(ActionEvent actionEvent) {
        if(gridAlreadyCheck){
            Backtracking backtrack=new Backtracking(grid);
            grid.printGrid();
            backtrack.solve();

            System.out.println("affichage grille final");
            grid=backtrack.getSudoku();
            if (grid==null){
                Dialog.showErrorDialog("ERROR","Your initial grid has no solution.\n Please RESTART");
            }
            grid.printGrid();
            updateVisualGrid();


        }
    }
    @FXML
    public void onActionRestart(ActionEvent actionEvent) {
        grid=new SudokuGrid();
        updateVisualGrid();
    }

    @FXML
    public void onActionXWing(ActionEvent actionEvent) {
        if(gridAlreadyCheck) {
            rules.xWing(grid);
            updateVisualGrid();

        }
    }

    public void onActionAutoResolution(ActionEvent actionEvent) throws InterruptedException {
        if(gridAlreadyCheck){
            int compteur = 0;
            boolean canBeSolved=true;
            boolean grisIsModif=false;


            SudokuGrid tempGrid = grid.cloneGrid(); // Cloner la grille pour éviter de modifier l'originale
            while (!grid.isSolved()&&canBeSolved){
                grisIsModif=false;
                if(compteur > 2){
                    System.out.println("On a obtenu 2 fois la meme grille, on passe au backtracking");
                    Backtracking backtrack=new Backtracking(grid);
                    grid.printGrid();
                    grid=backtrack.solve();
                    System.out.println("affichage grille final");
                    //grid=backtrack.getSudoku();
                    if (grid!=null){
                        grid.printGrid();

                    }
                    else{
                        Dialog.showErrorDialog("ERROR","Your initial grid has no solution.\n Please RESTART");
                        canBeSolved=false;
                    }
                    updateVisualGrid();
                }

                tempGrid=grid;

                rules.lastEmptyRule(grid);
                if(tempGrid!= grid){
                    grisIsModif=true;
                }
                if(!grisIsModif){
                    tempGrid=grid;
                    rules.singletonNus(grid);
                    if(tempGrid!=grid){
                        grisIsModif=true;
                    }
                }
                if(!grisIsModif){
                    tempGrid=grid;
                    rules.pairesNues(grid);
                    if(tempGrid!=grid){
                        grisIsModif=true;
                    }
                }
                if(!grisIsModif){
                    tempGrid=grid;
                    rules.singletonCache(grid);
                    if(tempGrid!=grid){
                        grisIsModif=true;
                    }
                }
                if(!grisIsModif){
                    tempGrid=grid;
                    rules.pairesCache(grid);
                    if(tempGrid!=grid){
                        grisIsModif=true;
                    }
                }
                if(!grisIsModif){
                    tempGrid=grid;
                    rules.tripletsNus(grid);
                    if(tempGrid!=grid){
                        grisIsModif=true;
                    }
                }
                if(!grisIsModif){
                    tempGrid=grid;
                    rules.tripletsCache(grid);
                    if(tempGrid!=grid){
                        grisIsModif=true;
                    }
                }
                if(!grisIsModif){
                    tempGrid=grid;
                    rules.KIntersections(grid);
                    if(tempGrid!=grid){
                        grisIsModif=true;
                    }
                }
                if(!grisIsModif){
                    tempGrid=grid;
                    rules.xWing(grid);
                    if(tempGrid!=grid){
                        grisIsModif=true;
                    }
                }

                updateVisualGrid();

                tempGrid=grid;
                if (tempGrid==grid){
                    System.out.println("Je n'ai rien changé ce tour: "+compteur);
                    compteur++;
                }
            }
        }
    }


    @FXML
    public void onActionTest(ActionEvent actionEvent) {
        if(gridAlreadyCheck){
            System.out.println("TEST");
            grid = new SudokuGrid();

            int [][] testGrid= {
                {0, 0, 3, 8, 0, 0, 5, 1, 0},
                {0, 0, 8, 7, 0, 0, 9, 3, 0},
                {1, 0, 0, 3, 0, 5, 7, 2, 8},
                {0, 0, 0, 2, 0, 0, 8, 4, 9},
                {8, 0, 1, 9, 0, 6, 2, 5, 7},
                {0, 0, 0, 5, 0, 0, 1, 6, 3},
                {9, 6, 4, 1, 2, 7, 3, 8, 5},
                {3, 8, 2, 6, 5, 9, 4, 7, 1},
                {0, 1, 0, 4, 0, 0, 6, 9, 2}
            };

            for(int row=0;row<9;row++){
                for(int col=0;col<9;col++){
                    grid.getGrid()[row][col].setValue(testGrid[row][col]);
                }
            }

            updateVisualGrid();
        }

    }

    @FXML
    public void onActionGenerated(ActionEvent actionEvent) {
        String selectedItem = (String) choiseBox_fxid.getSelectionModel().getSelectedItem();
        System.out.println(selectedItem);
        if(selectedItem!=null){
            switch (selectedItem){
                case "easy":level=20;break;
                case "medium":level=40;break;
                case "hard":level=55;break;
                case "extreme":level=65;break;
            }
            GridGenerator gridGenerator = new GridGenerator(level);
            grid = gridGenerator.generatePartialGrid();
            System.out.println("Grille genere");
            grid.printGrid();
            gridAlreadyCheck = true;
            updateVisualGrid();

        }else {
            System.out.println("pas de niveau selectionné");
            Dialog dialog=new Dialog();
            Dialog.showErrorDialog("ERROR","Please Select a level befor");
        }
    }

}




