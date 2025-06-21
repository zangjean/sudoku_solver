package noyau;

public class CelluleSudoku {
    private int value; //valeur final de la cellule
    private Notes notes; //notes presente dans la cellule -> au format binaire

    public CelluleSudoku(){
        this.value=0;
        this.notes=new Notes();
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
    }

    public void setValue(int value) {
        this.value = value;
    }
    public void cleanValue(){
        this.setValue(0);
    }

    public Notes getCellNotes() {
        return this.notes;
    }

    public int getValue() {
        return value;
    }

    public void printCell(){
        System.out.println("Value: "+this.getValue());
        this.getCellNotes().printNotes();
    }

    public boolean celluleIsNot0(){
        return this.value!=0;
    }
}
