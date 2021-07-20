package Simulator;
import org.graphstream.graph.Graph;

import java.io.IOException;
import java.util.ArrayList;

public class Simulator {

    private _Graph graph;
    private int step;


    public Simulator() throws IOException {

        //field = new Field(ModelConstants.WIDTH, ModelConstants.HEIGHT, ModelConstants.MODEL);
        //System.out.println("Model constructed with height "+ ModelConstants.HEIGHT+" and width "+ ModelConstants.WIDTH);
        //view = new View(ModelConstants.WIDTH, ModelConstants.HEIGHT, this);
        graph = new _Graph();
        reset();

    }

    private void reset() {
        step = 0;
        //view.showStatus(step, field);
    }

//    public void closeView()
//    {
//        this.view.setVisible(false);
//    }

    public void simulateOneStep() {
        step++;

        ArrayList<Voter> voters = graph.getVoters();

        act7PointScale(voters);
    }

    private void actBinaryModel(){

    }

    private void act7PointScale(ArrayList<Voter> voters){
        //watch media
        for (Voter voter: voters){
            voter.consumeMedia();
        }

        //select discussants
        for (Voter voter: voters){
            voter.selectDiscussants();
        }

        //discuss
        for (Voter voter: voters){
            voter.discuss();
        }

        //update opinions
        for (Voter voter: voters){
            voter.updateOpinion();
            graph.colour(voter);
        }
    }
}
