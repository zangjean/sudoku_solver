package noyau;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

class Rectangle {

    public final int xmin;
    public final int xmax;
    public final int ymin;
    public final int ymax;

    public Rectangle (int xmin, int ymin, int xmax, int ymax) {
        this.xmin = xmin;
        this.xmax = xmax;
        this.ymin = ymin;
        this.ymax = ymax;
    }
}

public class Rules {

    private void lastEmptyRuleZone(SudokuGrid grid, int rowStrat, int colStart, int rowEnd, int colEnd) {
        ArrayList<Integer> valuesOnZone = new ArrayList<>();
        int rowSaved = -1;
        int colSaved = -1;
        for (int row = rowStrat; row <= rowEnd; row++) {
            for (int col = colStart; col <= colEnd; col++) {
                int val = grid.getGrid()[row][col].getValue();
                if (val == 0) {
                    rowSaved = row;
                    colSaved = col;
                } else {
                    valuesOnZone.add(val);
                }
            }
        }
        if (valuesOnZone.size() == 8) {
            grid.setValueOnGrid(trouverValeurAbsente(valuesOnZone), rowSaved, colSaved);
        }
    }

    private void lastEmptyRuleRow(SudokuGrid grid, int ind) {
        System.out.println("J'applique la règle dernière case vide sur la ligne: "+ind);

        this.lastEmptyRuleZone(grid, ind, 0, ind, 8);
    }

    private void lastEmptyRuleCol(SudokuGrid grid, int ind) {
        System.out.println("J'applique la règle dernière case vide sur la colonne: "+ind);

        this.lastEmptyRuleZone(grid, 0, ind, 8, ind);
    }

    private void lastEmptyRuleQuare(SudokuGrid grid, int ind) {
        int xmin = (ind % 3) * 3;
        int ymin = (ind / 3) * 3;
        int xmax = xmin + 2;
        int ymax = ymin + 2;
        System.out.println("J'applique la règle dernière case vide sur le carre: "+ind);

        this.lastEmptyRuleZone(grid, xmin, ymin, xmax, ymax);
    }

    public void lastEmptyRule(SudokuGrid grid) {
        for (int ind = 0; ind <= 8; ind++) {
            this.lastEmptyRuleCol(grid, ind);
            this.lastEmptyRuleRow(grid, ind);
            this.lastEmptyRuleQuare(grid, ind);
        }
    }

    private static int trouverValeurAbsente(ArrayList<Integer> liste) {
        for (int i = 1; i <= 9; i++) {
            if (!liste.contains(i)) {
                return i;
            }
        }
        return -1;
    }

    public void singletonNus(SudokuGrid grid) {
        System.out.println("regle singleton nus");
        for (int row = 0; row <= 8; row++) {
            for (int col = 0; col <= 8; col++) {
                int sizeNotes = grid.getGrid()[row][col].getCellNotes().getNotes().size();
                //System.out.println("tailles notes case:"+row+" "+col+" . "+sizeNotes);
                if (sizeNotes == 1) {
                    grid.setValueOnGrid(grid.getGrid()[row][col].getCellNotes().getNotes().getFirst(), row, col);
                }
            }
        }
    }

    private void suppAllNotesOnZone(SudokuGrid grid, int xmin, int ymin, int xmax, int ymax, int note,int exceptRow1,int exceptCol1,int exceptRow2,int exceptCol2) {
        //System.out.println("cases a exclure.  case1:"+exceptRow1+" "+exceptCol1+" case2:"+exceptRow2+" "+exceptCol2+"  note concerné: "+note );
        //System.out.println("xmin:"+xmin+ " ymin:"+ymin+ " xmax:"+xmax+" ymax:"+ymax);

        //boolean is_modif=false;
        for (int row = xmin; row <= xmax; row++) {
            for (int col = ymin; col <= ymax; col++) {
                if(!(((exceptRow1==row)&&(exceptCol1==col)) || ((exceptRow2==row)&&(exceptCol2==col)))){
                    Notes n = grid.getGrid()[row][col].getCellNotes();
                    //System.out.println("row:" + row + " col:" + col + "  notes. " );
                    //n.printNotes();
                    grid.getGrid()[row][col].getCellNotes().deletNote(note);
                }
            }
        }
    }

    private void pairesNuesZone(SudokuGrid grid,int rowMin, int colMin, int rowMax, int colMax) {
        //System.out.println("je suis dans la fonction Zone");
        int nb;
        int[][] indexs = new int[2][2];
        for(int n1 = 1; n1 < 10; n1++) {
            for (int n2 = n1 + 1; n2 < 10; n2++) {
                nb=0;
                ArrayList<Integer> n = null;
                boolean here=false;
                for (int row = rowMin; row <= rowMax; row++) {
                    for (int col = colMin; col <= colMax; col++) {
                        Notes notes=grid.getGrid()[row][col].getCellNotes();
                        ArrayList<Integer> notesForDel=new ArrayList<>();
                        if (grid.getGrid()[row][col].getCellNotes().howManyNotes() == 2) {
                            if(notes.containNote(n1)&&notes.containNote(n2)){
                                if(nb<2){
                                    notesForDel.add(n1);
                                    notesForDel.add(n2);
                                    here=true;
                                    n=notesForDel;
                                    indexs[nb][0]=row;
                                    indexs[nb][1]=col;
                                }
                                //System.out.println("notes sont: "+notesForDel);
                                nb++;
                            }
                        }
                    }
                }
                if(nb==2){
                    //System.out.println("nb==2");
                    suppAllNotesOnZone(grid,rowMin,colMin,rowMax,colMax,n.get(0),indexs[0][0],indexs[0][1],indexs[1][0],indexs[1][1]);
                    suppAllNotesOnZone(grid,rowMin,colMin,rowMax,colMax,n.get(1),indexs[0][0],indexs[0][1],indexs[1][0],indexs[1][1]);
                }
            }
        }
    }

    private void pairesNuesRow(SudokuGrid grid,int ind) {
        System.out.println("J'applique la règle paire nu sur la ligne: "+ind);

        this.pairesNuesZone(grid,ind, 0, ind, 8);
    }
    private void pairesNuesCol(SudokuGrid grid,int ind){
        System.out.println("J'applique la règle paire nu sur la colonne: "+ind);

        this.pairesNuesZone(grid,0,ind,8,ind);
    }
    private void pairesNuesQuare(SudokuGrid grid, int ind) {

        int xmin = (ind % 3) * 3;
        int ymin = (ind / 3) * 3;
        int xmax = xmin + 2;
        int ymax = ymin + 2;
        System.out.println("J'applique la règle paire nu sur le carre: "+ind);

        //System.out.println("carre: "+xmin+" "+ymin+ " à "+xmax+" "+ymax);
        this.pairesNuesZone(grid, xmin, ymin, xmax, ymax);
    }
    public void pairesNues(SudokuGrid grid){
        for (int ind = 0; ind <= 8; ind++) {
            this.pairesNuesCol(grid, ind);
            this.pairesNuesRow(grid, ind);
            this.pairesNuesQuare(grid, ind);
        }
    }
    private void singletonCacheZone(SudokuGrid grid,int xmin, int ymin, int xmax, int ymax){
        int nb,value = 0;
        int[] indexs=new int[2];
        for(int x=1;x<=9;x++){
            nb = 0;
            for(int row=xmin;row<=xmax;row++) {
                for (int col = ymin; col <= ymax; col++) {
                    Notes notes=grid.getGrid()[row][col].getCellNotes();
                    if(notes.containNote(x)){
                        if(nb==0){
                            value=x;
                            indexs[0]=row;
                            indexs[1]=col;
                        }
                        nb++;
                    }
                }
            }
            if(nb==1){
                grid.setValueOnGrid(value, indexs[0],indexs[1]);
            }
        }
    }

    private void singletonCacheRow(SudokuGrid grid,int ind) {
        System.out.println("J'applique la règle singleton cache sur la ligne: "+ind);

        this.singletonCacheZone(grid,ind, 0, ind, 8);
    }
    private void singletonCacheCol(SudokuGrid grid,int ind) {
        System.out.println("J'applique la règle singleton cache sur la colonne: "+ind);

        this.singletonCacheZone(grid,0,ind,8,ind);
    }
    private void singletonCacheQuare(SudokuGrid grid, int ind) {
        int xmin = (ind % 3) * 3;
        int ymin = (ind / 3) * 3;
        int xmax = xmin + 2;
        int ymax = ymin + 2;
        System.out.println("J'applique la règle singleton cache sur le carre: "+ind);

        this.singletonCacheZone(grid, xmin, ymin, xmax, ymax);
    }
    public void singletonCache(SudokuGrid grid){
        for (int ind = 0; ind <= 8; ind++) {
            this.singletonCacheCol(grid, ind);
            this.singletonCacheRow(grid, ind);
            this.singletonCacheQuare(grid, ind);
        }
    }

    private void tripletsNusZone(SudokuGrid grid,int rowMin, int colMin, int rowMax, int colMax) {
        //System.out.println("je suis dans la fonction Zone");
        int nb;
        int[][] indexs = new int[3][2];
        for(int n1 = 1; n1 < 10; n1++) {
            for (int n2 = n1 + 1; n2 < 10; n2++) {
                for (int n3 = n2 + 1; n3 < 10; n3++) {
                    nb = 0;
                    ArrayList<Integer> n = null;
                    boolean here = false;
                    for (int row = rowMin; row <= rowMax; row++) {
                        for (int col = colMin; col <= colMax; col++) {
                            Notes notes = grid.getGrid()[row][col].getCellNotes();
                            ArrayList<Integer> notesForDel = new ArrayList<>();
                            if (grid.getGrid()[row][col].getCellNotes().howManyNotes() == 3) {
                                if (notes.containNote(n1) && notes.containNote(n2)) {
                                    if (nb < 3) {
                                        notesForDel.add(n1);
                                        notesForDel.add(n2);
                                        notesForDel.add(n3);
                                        here = true;
                                        n = notesForDel;
                                        indexs[nb][0] = row;
                                        indexs[nb][1] = col;
                                    }
                                    //System.out.println("notes sont: "+notesForDel);
                                    nb++;
                                }
                            }
                        }
                    }
                    if (nb == 3) {
                        //System.out.println("nb==2");
                        suppAllNotesOnZone(grid, rowMin, colMin, rowMax, colMax, n.get(0), indexs[0][0], indexs[0][1], indexs[1][0], indexs[1][1]);
                        suppAllNotesOnZone(grid, rowMin, colMin, rowMax, colMax, n.get(1), indexs[0][0], indexs[0][1], indexs[1][0], indexs[1][1]);
                        suppAllNotesOnZone(grid, rowMin, colMin, rowMax, colMax, n.get(2), indexs[2][0], indexs[2][1], indexs[2][0], indexs[2][1]);
                    }
                }
            }
        }
    }

    private void tripletsNusRow(SudokuGrid grid,int ind) {
        System.out.println("J'applique la règle triplet nu sur la ligne: "+ind);

        this.tripletsNusZone(grid,ind, 0, ind, 8);

    }
    private void tripletsNusCol(SudokuGrid grid,int ind){
        System.out.println("J'applique la règle triplet nu sur la colonne: "+ind);

        this.tripletsNusZone(grid,0,ind,8,ind);
    }
    private void tripletsNusQuare(SudokuGrid grid, int ind) {

        int xmin = (ind % 3) * 3;
        int ymin = (ind / 3) * 3;
        int xmax = xmin + 2;
        int ymax = ymin + 2;
        System.out.println("J'applique la règle triplet nu sur le carree: "+ind);

        this.tripletsNusZone(grid, xmin, ymin, xmax, ymax);
    }
    public void tripletsNus(SudokuGrid grid){
        for (int ind = 0; ind <= 8; ind++) {
            this.tripletsNusCol(grid, ind);
            this.tripletsNusRow(grid, ind);
            this.tripletsNusQuare(grid, ind);
        }
    }

    private void pairesCacheZone(SudokuGrid grid,int xmin, int ymin, int xmax, int ymax){
        //System.out.println("pairesCacheZone");
        for(int x=1;x<=9;x++) {
            for (int y = x + 1 ; y <= 9; y++) {
                ArrayList<Integer> values = new ArrayList<>(Set.of(x,y));
                int index[][] = new int[2][2];
                int validCells = 0;
                    for(int row=xmin;row<=xmax;row++) {
                        for (int col = ymin; col <= ymax; col++) {
                            ArrayList<Integer> notes = grid.getGrid()[row][col].getCellNotes().getNotes();

                            if(notes.contains(x) && notes.contains(y)) {
                                if (validCells < 2) {
                                    index[validCells][0] = row;
                                    index[validCells][1] = col;
                                }
                                validCells++;
                            }
                        }
                    }
                    if(validCells == 2){
                        grid.getGrid()[index[0][0]][index[0][1]].getCellNotes().setNotes(values);
                        grid.getGrid()[index[1][0]][index[1][1]].getCellNotes().setNotes(values);
                    }
            }
        }
    }

    private void pairesCacheRow(SudokuGrid grid,int ind) {
        System.out.println("J'applique la règle paires caché sur la ligne: "+ind);

        this.pairesCacheZone(grid,ind, 0, ind, 8);

    }
    private void pairesCacheCol(SudokuGrid grid,int ind) {
        System.out.println("J'applique la règle paires caché sur la colonne: "+ind);

        this.pairesCacheZone(grid,0,ind,8,ind);
    }
    private void pairesCacheQuare(SudokuGrid grid, int ind) {
        int xmin = (ind % 3) * 3;
        int ymin = (ind / 3) * 3;
        int xmax = xmin + 2;
        int ymax = ymin + 2;
        System.out.println("J'applique la règle paires caché sur le carre: "+ind);

        this.pairesCacheZone(grid, xmin, ymin, xmax, ymax);
    }
    public void pairesCache(SudokuGrid grid){
        for (int ind = 0; ind <= 8; ind++) {
            this.pairesCacheCol(grid, ind);
            this.pairesCacheRow(grid, ind);
            this.pairesCacheQuare(grid, ind);
        }
    }

    private void tripletsCacheZone(SudokuGrid grid,int xmin, int ymin, int xmax, int ymax){
        System.out.println("tripletsCacheZone");
        for(int x=1;x<=9;x++) {
            for (int y = x + 1 ; y <= 9; y++) {
                for (int z = y + 1 ; z <= 9; z++) {
                    ArrayList<Integer> values = new ArrayList<>(Set.of(x, y, z));
                    int index[][] = new int[3][2];
                    int validCells = 0;
                    for (int row = xmin; row <= xmax; row++) {
                        for (int col = ymin; col <= ymax; col++) {
                            ArrayList<Integer> notes = grid.getGrid()[row][col].getCellNotes().getNotes();
                            if (notes.contains(x) && notes.contains(y) && notes.contains(z)) {
                                if (validCells < 3) {
                                    index[validCells][0] = row;
                                    index[validCells][1] = col;
                                }
                                validCells++;
                            }
                        }
                    }
                    if (validCells == 3) {
                        grid.getGrid()[index[0][0]][index[0][1]].getCellNotes().setNotes(values);
                        grid.getGrid()[index[1][0]][index[1][1]].getCellNotes().setNotes(values);
                        grid.getGrid()[index[2][0]][index[2][1]].getCellNotes().setNotes(values);
                    }
                }
            }
        }
    }

    private void tripletsCacheRow(SudokuGrid grid,int ind) {
        System.out.println("J'applique la règle triplet caché sur la ligne: "+ind);

        this.tripletsCacheZone(grid,ind, 0, ind, 8);
    }
    private void tripletsCacheCol(SudokuGrid grid,int ind) {
        System.out.println("J'applique la règle triplet caché sur la colonne: "+ind);

        this.tripletsCacheZone(grid,0,ind,8,ind);
    }
    private void tripletsCacheQuare(SudokuGrid grid, int ind) {
        int xmin = (ind % 3) * 3;
        int ymin = (ind / 3) * 3;
        int xmax = xmin + 2;
        int ymax = ymin + 2;
        System.out.println("J'applique la règle triplet caché sur le carre: "+ind);

        this.tripletsCacheZone(grid, xmin, ymin, xmax, ymax);
    }
    public void tripletsCache(SudokuGrid grid){
        for (int ind = 0; ind <= 8; ind++) {
            this.tripletsCacheCol(grid, ind);
            this.tripletsCacheRow(grid, ind);
            this.tripletsCacheQuare(grid, ind);
        }
    }

    boolean intersection(SudokuGrid g, int x, Rectangle carre, Rectangle lc){
        int k = 0;
        if(lc.xmin == lc.xmax){// Il s'agit d'une ligne
            int i = lc.xmin;
            for(int j = carre. ymin; j <= carre. ymax; j++){ // On parcourt les 3 cases
                if(g.getGrid()[i][j].getCellNotes().containNote(x))
                    k++;
                }
            }else { // Il s'agit d'une colonne
            int j = lc.ymin;
            for (int i = carre.xmin; i <= carre.xmax; i++) { // On parcourt les 3 cases
                if (g.getGrid()[i][j].getCellNotes().containNote(x))
                    k++;
            }
        }
        return k >= 2;
    }

    boolean pointante(SudokuGrid g,int x, Rectangle carre, Rectangle lc){
        boolean valid = true;
        for(int i = carre. xmin; i <= carre. xmax; i++){
            for(int j = carre. ymin; j <= carre. ymax; j++){ // On parcourt le carré
                if((i != lc. xmin && lc. xmin == lc. xmax)|| (j != lc. ymin && lc. ymin == lc. ymax)){ // On exclut la ligne/colonne de la zone de recherche
                    valid = g.getGrid()[i][j].getCellNotes().containNote(x) && valid; // On vérifie que la note n'est jamais présente
                }
            }
        }
        return valid;
    }

    boolean boxreduction(SudokuGrid g,int x, Rectangle carre, Rectangle lc){
        boolean valid = true;
        for(int i = lc.xmin; i <= lc.xmax; i++){
            for(int j = lc.ymin; j <= lc.ymax; j++){ // On parcourt la ligne/colonne
                if(lc.ymin == lc.ymax){ // On exclut le carré de la zone de recherche
                    if((carre.xmin > i) || (carre.xmax < i)){
                        valid = valid && !(g.getGrid()[i][j].getCellNotes().containNote(x));// On vérifie que la note n'est jamais présente
                    }
                }else{
                    if((carre.ymin > j) || (carre.ymax < j)){
                        valid = valid && !(g.getGrid()[i][j].getCellNotes().containNote(x));// On vérifie que la note n'est jamais présente
                    }
                }
            }
        }
        return valid;
    }

    void supnote(SudokuGrid g, int x, Rectangle zone, Rectangle except){
        // On découpe notre zone en deux parties,la partie à supprimer et la partie à garder
        // Ce découpage est dispensable dans les cas où une ligne/colonne coupe un carré au milieu
        System.out.println("TEST sur " + x);
        for(int i = zone.xmin; i <= zone.xmax; i++){
            for(int j = zone.ymin; j <= zone.ymax; j++){
                if(zone. xmin == zone.xmax || except.ymin == except.ymax){ //La zone est une ligne ou l'exception est une colonne
                    if((j < except.ymin || j > except.ymax)){
                        g.getGrid()[i][j].getCellNotes().deletNote(x);
                    }
                }else if(zone.ymin == zone.ymax || except.xmin == except.xmax){//La zone est une colonne ou l'exception est une ligne
                    if((i < except.xmin || i > except.xmax)){
                        g.getGrid()[i][j].getCellNotes().deletNote(x);
                    }
                }
            }
        }
    }

    public void KIntersections(SudokuGrid g){
        System.out.println("J'applique K-intersection");

        for(int i = 0; i < 9; i+=3){
            for(int j = 0; j < 9; j+=3){// Parcours des 9 carrés, soit 9 itérations
                for(int x = 1; x <= 9; x++){//Parcours des 9 valeurs
                    Rectangle carre = new Rectangle(i,j,i+2,j+2);// On créé le dit carré
                    for(int lc = 0; lc < 3; lc++){
                        Rectangle l = new Rectangle(i+lc, 0, i+lc, 8);// On créé les 3 lignes d'intersection
                        if(intersection(g,x,carre,l)){
                            if(pointante(g,x,carre,l)){
                                supnote(g,x,l,carre); // On supprime la note du premier Rectangle , tout en gardant le contenu du second
                            }
                            if(boxreduction(g,x,carre,l)){
                                supnote(g,x,carre,l);
                            }
                        }
                        Rectangle c = new Rectangle(0, j+lc, 8, j+lc);// On créé les 3 colonnes d'intersection
                        if(intersection(g,x,carre,c)){
                            if(pointante(g,x,carre,l)){
                                supnote(g,x,l,carre); // On supprime la note du premier Rectangle , tout en gardant le contenu du second
                            }
                            if(boxreduction(g,x,carre,c)){
                                supnote(g,x,carre,l);
                            }
                        }
                    }
                }
            }
        }
    }

    /*  ------- XWING --------- */


    public void xWing(SudokuGrid grid){
        System.out.println("J'applique la règle X-Wing");
        int[] case1 = new int[2];
        int[] case2 = new int[2];
        boolean case1Find=false;
        boolean case2Find=false;

        for (int row=0;row<9;row++){
            //System.out.print("la ligne: "+ row );
            Rectangle rectangle=new Rectangle(row,0,row,8);
            int[] tabPresenceNotesRow= witchNotesinZone(grid,rectangle);
            System.out.print(Arrays.toString(tabPresenceNotesRow));
            System.out.println(" ");
            case1Find=false;
            case2Find=false;
            for (int nbr=1; nbr<10;nbr++){
                //System.out.println("La notes est: "+nbr);

                for (int col=0;col<9;col++){

                    if(tabPresenceNotesRow[nbr-1]==2){
                        if(grid.getGrid()[row][col].getCellNotes().containNote(nbr)){
                            //System.out.println("La case "+row+col +" contient la note "+nbr);
                            if(!case1Find){
                                case1= new int[]{row, col};
                                case1Find=true;
                            } else if (!case2Find) {
                                case2= new int[]{row, col};
                                case2Find=true;
                            }
                        }
                        if(case1Find&&case2Find){
                            //System.out.println();
                            int col1=case1[1];
                            int col2=case2[1];
                            int rowSave=case1[0]+1;

                            while (rowSave<9){
                                //System.out.println("NOTES");
                                //grid.getGrid()[rowSave][col1].printCell();
                                //grid.getGrid()[rowSave][col2].printCell();

                                if((grid.getGrid()[rowSave][col1].getCellNotes().containNote(nbr)) && (grid.getGrid()[rowSave][col2].getCellNotes().containNote(nbr))) {
                                    //System.out.println("J'ai trouvéles 2 autres cases !!!!!!!");
                                    Rectangle rectangle1=new Rectangle(rowSave,0,rowSave,8);
                                    int [] tab=witchNotesinZone(grid,rectangle1);
                                    if(tab[nbr-1]==2){
                                        //System.out.println("La ligne actuel est la BONNE");
                                        suppAllNotesOnZone(grid,0,col1,8,col1,nbr,case1[0],col1,rowSave,col1);
                                        suppAllNotesOnZone(grid,0,col2,8,col2,nbr,case2[0],col2,rowSave,col2);
                                    }
                                }
                                rowSave++;
                            }
                        }
                    }
                }
            }
        }
    }


    public int[]witchNotesinZone(SudokuGrid grid,Rectangle zone){
        int[]tab=new int[]{0,0,0,0,0,0,0,0,0};
        for(int row=zone.xmin;row<=zone.xmax;row++){
            for(int col=zone.ymin;col<=zone.ymax;col++){
                Notes notes= grid.getGrid()[row][col].getCellNotes();
                for(int note:notes.getNotes()){
                    tab[note-1]++;
                }
            }
        }

        return tab;
    }

}
