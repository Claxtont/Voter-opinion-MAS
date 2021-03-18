package Simulator;
import UI.View;
import org.graphstream.graph.Graph;

import java.util.ArrayList;

public class Simulator {


    private Field field;
    private _Graph graph;
    private View view;
    private int step;
    //private ArrayList<Voter> voter;

    public Simulator() {

        //field = new Field(ModelConstants.WIDTH, ModelConstants.HEIGHT, ModelConstants.MODEL);
        //System.out.println("Model constructed with height "+ ModelConstants.HEIGHT+" and width "+ ModelConstants.WIDTH);
        //view = new View(ModelConstants.WIDTH, ModelConstants.HEIGHT, this);
        graph = new _Graph(ModelConstants.SIZE, ModelConstants.GRAPH, ModelConstants.SIZE);
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

        ArrayList<Voter> voters = field.getVoters();

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
        }

        view.showStatus(step, field);

    }
}
