//package Simulator;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//public class Field {
//
//    private Voter field[][];
//    private int width, height, model;
//
//
//
//    private ArrayList<Voter> voters;
//
//    public Field(int inputWidth, int inputHeight, int model) {
//        this.model = model;
//        height = inputHeight;
//        width = inputWidth;
//        field = new Voter[width][height]; //w&h
//        System.out.println("Field constructed with height "+height+" and width "+width);
//        if (model == 1) {
//            voters = new ArrayList<>();
//            populateField();
//        }
//        else {
//            voters = new ArrayList<>();
//            populateField();
//        }
//    }
//
//    public void populateField(){
//        int w = 0;
//        int h = 0;
//        for(w = 0; w < width; w++){
//            for(h = 0; h < height; h++){
//                field[w][h] = new Voter(w,h, this);
//                voters.add(field[w][h]);
//
//            }
//        }
//
//
//    }
//
//    public int getHeight(){
//        return height;
//    }
//
//    public int getWidth(){
//        return width;
//    }
//
//    public ArrayList<Voter> getVoters() {
//        //Collections.shuffle(voters);
//        return voters;
//    }
//
//    public Voter getVoterAt(int width, int height){ //w&h
//        return field[width][height]; //w&h
//    }
//
//    public Field cloneField()
//    {
//        Field clone = new Field(this.getWidth(), this.getHeight(), model);
//        for(int col = 0; col < width; col++){
//            for(int row = 0; row < height; row++){
//                clone.field[col][row] = this.field[col][row];
//
//            }
//        }
//        return clone;
//    }
//
//    public ArrayList<Voter> adjacentVoters(Voter v){
//        return adjacentVoters(v.getWidth(), v.getHeight());
//    }
//
//    public ArrayList<Voter> adjacentVoters(int w, int h) {
//        int fieldWidth = getWidth();
//        int fieldHeight = getHeight();
//        ArrayList<Voter> result = new ArrayList<>();
//        for (int dx = (w > 0 ? -1 : 0); dx <= (w < fieldWidth - 1 ? 1 : 0); ++dx) {
//            for (int dy = (h > 0 ? -1 : 0); dy <= (h < fieldHeight - 1 ? 1 : 0); ++dy) {
//                if (dx != 0 || dy != 0) {
//                    result.add(getVoterAt(w + dx, h + dy)); //w&h
//                }
//            }
//        }
//        return result;
//    }
//}
