package noyau;

import java.util.ArrayList;

public class Notes {
    private ArrayList<Integer> notes=new ArrayList<>(9);

    public Notes(){
        for (int i = 1; i <= 9; i++) {
            this.notes.add(i);
        }
    }
    public ArrayList<Integer> getNotes(){
        return this.notes;
    }
    public int howManyNotes(){
        return this.notes.size();
    }
    public boolean containNote(int n){
        return this.notes.contains(Integer.valueOf(n));
    }

    public void deletNote(int note){
        this.notes.remove(Integer.valueOf(note));
    }

    public void setNotes(ArrayList<Integer> notes) {
        this.notes = new ArrayList<>(notes);
    }

    public void printNotes(){
        System.out.println("Notes: "+this.getNotes());
    }


}
