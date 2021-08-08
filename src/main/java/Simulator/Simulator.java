package Simulator;

import UI.GUIChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

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

    public CategoryDataset updateGraphData(){
        if (ModelConstants.CHART == 1){
            ArrayList<Voter> voters = graph.getVoters();
            //DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            int on3 = 0, on2 =0, on1 = 0, o0 = 0, o1 = 0, o2 = 0, o3 =0;

            for (Voter v: voters) {
                if (v.getAgentOpinion() == -3){on3++;}
                else if (v.getAgentOpinion() == -2){on2++;}
                else if (v.getAgentOpinion() == -1){on1++;}
                else if (v.getAgentOpinion() == 0){o0++;}
                else if (v.getAgentOpinion() == 1){o1++;}
                else if (v.getAgentOpinion() == 2){o2++;}
                else if (v.getAgentOpinion() == 3){o3++;}
            }

            GUIChart.dataset.addValue(on3,"-3","Opinion");
            GUIChart.dataset.addValue(on2,"-2","Opinion");
            GUIChart.dataset.addValue(on1,"-1","Opinion");
            GUIChart.dataset.addValue(o0,"0","Opinion");
            GUIChart.dataset.addValue(o1,"1","Opinion");
            GUIChart.dataset.addValue(o2,"2","Opinion");
            GUIChart.dataset.addValue(o3,"3","Opinion");
        }
        return null;
    }

    private void reset() {
        step = 0;
        //view.showStatus(step, field);
    }

    public void simulateOneStep() {
        step++;

        ArrayList<Voter> voters = graph.getVoters();

        act(voters);

        updateGraphData();
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

        graph.graphDiscussants(voters);

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
