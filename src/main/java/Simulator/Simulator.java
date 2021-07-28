package Simulator;

import java.io.IOException;
import java.util.ArrayList;

public class Simulator {

    private _Graph graph;
    private int step;

    public Simulator() throws IOException {
        reset();
        graph = new _Graph();
    }

    public _Graph getGraph(){
        return graph;
    }

    private void reset() {
        step = 0;
        //view.showStatus(step, field);
    }

    public void simulateOneStep() {
        step++;

        ArrayList<Voter> voters = graph.getVoters();

        act(voters);

    }

    private void act(ArrayList<Voter> voters){
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
