package Simulator;

import org.graphstream.graph.Element;
import org.graphstream.graph.Node;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Voter {

    private int opinion;
    private Boolean opinionBoolean;
    private int mediaExposure;
    private int networkOpinionDistribution;
    private int networkHomogeneity;
    private double politicalInterest;
    private ArrayList<Voter> discussants;
    private float socialExposure;
    private int networkDisagreement;
    private Random rand = new Random();
    private Node node;

    public Voter(Node n){ //TODO: binary opinion model refactor
        node = n;
        Random rand = new Random();
        politicalInterest = rand.nextGaussian();
        if (politicalInterest > 1) {
            if (rand.nextInt(2) == 0) {
                opinion = (int) ((Math.random() * 2) + 2);
            } else {
                opinion = (int) ((Math.random() * -2) - 2);
            }
        }
        else if (politicalInterest <1 && politicalInterest >-1){
            if (rand.nextInt(2) == 0) {
                opinion = (int) ((Math.random() * 2) + 1);
            } else {
                opinion = (int) ((Math.random() * -2) - 1);
            }
        }
        else {
            if (rand.nextInt(2) == 0) {
                opinion = (int) ((Math.random() * 2));
            } else {
                opinion = (int) ((Math.random() * -2));
            }
        }
        if(rand.nextInt(2) == 1){
            opinionBoolean = true;
        }
        else {
            opinionBoolean = false;
        }
    }

    public Node getNode(){
        return node;
    }

    public int getAgentOpinion() {
        return opinion;
    }

    public double getAgentPoliticalInterest() {
        return politicalInterest;
    }

    public boolean getAgentOpinionBoolean(){
        return opinionBoolean;
    }

    public void consumeMedia() {
        mediaExposure = 0;
        if (rand.nextInt(2) < Math.abs(opinion)){
            mediaExposure += Integer.signum(opinion);
        }
        if (rand.nextInt(3) < Math.abs(opinion)){
            mediaExposure += Integer.signum(opinion);
        }
        if (rand.nextInt(4) < Math.abs(opinion)){
            mediaExposure += Integer.signum(opinion);
        }
        if (opinion == 0){
            mediaExposure = rand.nextInt(7) - 3;
        }
    }

 /*    public void act(){


        switch (ModelConstants.MODEL) {
            case 1:
                actModel1();
                break;
            case 2:
                actModel2();
                break;

        }
    }

    public void actModel1(){ //binary opinions
        ArrayList<Voter> adj = f.adjacentVoters(width, height);
        int count = 0;

        for(Voter v: adj){
            count += (v.getAgentOpinionBoolean() ? 1 : 0);
        }
        if(count > adj.size()/2){
            opinionBoolean = true;
        }
        else if(count < adj.size()/2){
            opinionBoolean = false;
        }

        //else leave the same
        //System.out.println("boolean flipped" + this + opinionBoolean);
    }

    public void actModel2(){
        //create a running tally of 3 factors
        //1: previous attitude * decay factor
        //2: opinions of those in political discussion group
        //3: pro vs counter media exposure * proportion of discussants who have the same bias, (positive vs negative)

        //1

        float decayedOpinion = opinion * ModelConstants.OPINIONDECAY;

        //2

        ArrayList<Voter> adj = f.adjacentVoters(width, height);//TODO: change this. for now I will use all adjacent voters

        float count = 0;
        for(Voter v: adj){
            count += v.getAgentOpinion();
        }

        float opinionDistribution = count / adj.size();

        //3
        float pro = 0, counter = 0;
        for(Voter v: adj){
            if (v.getAgentOpinion() * this.getAgentOpinion() >= 0){
                pro++;
            }
            else{
                counter++;
            }
        }
        float ratio = pro/adj.size();

        float tally3 = (float) ((Math.signum(opinion)) * 0.6 * ratio);

        //calculate based off 1, 2, & 3
        if (Math.round(decayedOpinion + opinionDistribution + tally3) > opinion){
            opinion ++;
            if (opinion > 3){
                opinion = 3;
            }
        }
        else if (Math.round(decayedOpinion + opinionDistribution + tally3) < opinion){
            opinion --;
            if (opinion < -3){
                opinion = -3;
            }
        }


    }*/

    public void updateOpinion(){

        float tally;
        //old Opinion is old opinion multiplied by random value between 1-opinion decay and 1
        float oldOpinion = opinion * ((1 - ModelConstants.OPINION_DECAY) + rand.nextFloat() * ModelConstants.OPINION_DECAY);

        tally = oldOpinion + ModelConstants.SOCIAL_INFLUENCE * socialExposure + ModelConstants.MEDIA_INFLUENCE * mediaExposure;

        if ((Math.round(tally)) < opinion){
            opinion -= 1;
        }
        else if ((Math.round(tally)) > opinion){
            opinion += 1;
        }


        while(Math.abs(opinion) > 3){
            opinion = opinion - Integer.signum(opinion);
        }
    }

    public ArrayList<Voter> getAdjacent(){
        Node adj[] = node.neighborNodes().toArray(Node[]::new);
        ArrayList<Voter> result = new ArrayList<>();
        for(Node n: adj) {
            result.add((Voter)n.getAttribute("Voter"));
        }
        return result;
    }

    public void selectDiscussants(){
        switch (ModelConstants.DISCUSSANTS_MODEL){

            case (1) : discussants = getAdjacent();

            case (2) : //homophily
        }
    }

    public void discuss(){
        switch (ModelConstants.SOCIAL_INFLUENCE_MODEL){

            case (1) :
                //mean average
                int sum = 0;
                for (Voter voter : discussants) {
                    sum += voter.opinion;
                    if (voter.opinion * this.opinion < 0 || (this.opinion == 0 && voter.opinion != 0)) {
                        networkDisagreement++;
                    }
                }
                socialExposure = (float) sum / discussants.size();

            case (2) :
                //majority model

                int maxValue = 0, maxCount = 0;

                for (int i = 0; i < discussants.size(); ++i) {
                    int count = 0;
                    for (int j = 0; j < discussants.size(); ++j) {
                        if (discussants.get(j).getAgentOpinion() == discussants.get(i).getAgentOpinion()) ++count;
                    }
                    if (count > maxCount) {
                        maxCount = count;
                        maxValue = discussants.get(i).getAgentOpinion();
                    }
                }

                socialExposure = maxValue;


            case (3) :
                //Deffuant-Weisbuch

        }

        //TODO Deffuant-Weisbuch bounded confidence model
    }
}
