package Simulator;

import org.graphstream.graph.Node;

import java.util.*;

public class Voter {

    private int opinion;
    private Boolean opinionBoolean;
    private int mediaExposure;
    private int networkOpinionDistribution;
    private float networkHomogeneity;
    private double politicalInterest;
    private ArrayList<Voter> discussants = new ArrayList<>();
    private float socialExposure;
    private int networkDisagreement;
    private Node node;

    public Voter(Node n) {
        node = n;
        politicalInterest = ModelConstants.RANDOM.nextGaussian();
        if (politicalInterest > 1) {
            if (ModelConstants.RANDOM.nextInt(2) == 0) {
                opinion = (int) ((ModelConstants.RANDOM.nextFloat() * 2) + 2);
            } else {
                opinion = (int) ((ModelConstants.RANDOM.nextFloat() * -2) - 2);
            }
        } else if (politicalInterest < 1 && politicalInterest > -1) {
            if (ModelConstants.RANDOM.nextInt(2) == 0) {
                opinion = (int) ((ModelConstants.RANDOM.nextFloat() * 2) + 1);
            } else {
                opinion = (int) ((ModelConstants.RANDOM.nextFloat() * -2) - 1);
            }
        } else {
            if (ModelConstants.RANDOM.nextInt(2) == 0) {
                opinion = (int) ((ModelConstants.RANDOM.nextFloat() * 2));
            } else {
                opinion = (int) ((ModelConstants.RANDOM.nextFloat() * -2));
            }
        }
        if (ModelConstants.RANDOM.nextInt(2) == 1) {
            opinionBoolean = true;
        } else {
            opinionBoolean = false;
        }

        networkHomogeneity = (float)0.5;
    }

    public Node getNode() {
        return node;
    }

    public int getAgentOpinion() {
        return opinion;
    }

    public double getAgentPoliticalInterest() {
        return politicalInterest;
    }

    public boolean getAgentOpinionBoolean() {
        return opinionBoolean;
    }

    public void consumeMedia() {
        mediaExposure = 0;
        if (ModelConstants.NETWORK_HOMOGENEITY_IMPACT == 0) {
            if (ModelConstants.OPINION_MODEL == 2) {
                if (ModelConstants.RANDOM.nextInt(2) < Math.abs(opinion)) {
                    mediaExposure += Integer.signum(opinion);
                }
                if (ModelConstants.RANDOM.nextInt(3) < Math.abs(opinion)) {
                    mediaExposure += Integer.signum(opinion);
                }
                if (ModelConstants.RANDOM.nextInt(4) < Math.abs(opinion)) {
                    mediaExposure += Integer.signum(opinion);
                }
                if (opinion == 0) {
                    mediaExposure = ModelConstants.RANDOM.nextInt(7) - 3;
                }
            }
        }
        else{
            int proMediaExposure = 2;
            int counterMediaExposure = 1;
            if (ModelConstants.OPINION_MODEL == 2) {


                if (ModelConstants.RANDOM.nextInt(2) < Math.abs(opinion)) {
                    proMediaExposure += Integer.signum(opinion);
                }
                if (ModelConstants.RANDOM.nextInt(3) < Math.abs(opinion)) {
                    proMediaExposure += Integer.signum(opinion);
                }
                if (ModelConstants.RANDOM.nextInt(4) < Math.abs(opinion)) {
                    proMediaExposure += Integer.signum(opinion);
                }

                proMediaExposure *= networkHomogeneity;

                if (ModelConstants.RANDOM.nextInt(2) >= Math.abs(opinion)) {
                    counterMediaExposure += Integer.signum(opinion);
                }
                if (ModelConstants.RANDOM.nextInt(3) >= Math.abs(opinion)) {
                    counterMediaExposure += Integer.signum(opinion);
                }
                if (ModelConstants.RANDOM.nextInt(4) >= Math.abs(opinion)) {
                    counterMediaExposure += Integer.signum(opinion);
                }

                counterMediaExposure *= 1 - networkHomogeneity * ModelConstants.NETWORK_HOMOGENEITY_IMPACT;

                mediaExposure = proMediaExposure - counterMediaExposure;

                if (opinion == 0) {
                    mediaExposure = ModelConstants.RANDOM.nextInt(7) - 3;
                }
            }
        }
    }

    public void updateOpinion() {

        float tally;
        float oldOpinion;
        if (ModelConstants.OPINION_MODEL == 2) {
            if(ModelConstants.OPINION_UPDATE_MODEL == 1) {
                oldOpinion = opinion * ((1 - ModelConstants.OPINION_DECAY) + ModelConstants.RANDOM.nextFloat() * ModelConstants.OPINION_DECAY);

                tally = oldOpinion + ModelConstants.SOCIAL_INFLUENCE * (socialExposure - opinion) + ModelConstants.MEDIA_INFLUENCE * (mediaExposure - opinion);


                if ((Math.round(tally)) < opinion) {
                    opinion -= 1;
                } else if ((Math.round(tally)) > opinion) {
                    opinion += 1;
                }

                System.out.println(this.node + " old Opinion: " + oldOpinion + ", social Exposure: " + socialExposure + ", media Exposure: " + mediaExposure);
                System.out.println("Tally: " + tally + ", new opinion: " + opinion);

                while (Math.abs(opinion) > 3) {
                    opinion = opinion - Integer.signum(opinion);
                }
            }
            else if(ModelConstants.OPINION_UPDATE_MODEL == 2){
                socialExposure = (socialExposure - opinion) * ModelConstants.SOCIAL_INFLUENCE;
                float mediaExposureFloat = (mediaExposure - opinion) * ModelConstants.MEDIA_INFLUENCE;

                float opinionInfluence = (socialExposure + mediaExposureFloat)/2 * (ModelConstants.OPINION_DECAY + ModelConstants.OPINION_DECAY * ModelConstants.RANDOM.nextFloat());

                if(Math.round(opinionInfluence) > 0){
                    opinion++;
                }
                else if (Math.round(opinionInfluence) < 0){
                    opinion--;
                }

                while (Math.abs(opinion) > 3) {
                    opinion = opinion - Integer.signum(opinion);
                }
            }


        } else {
            oldOpinion = (opinionBoolean ? 1 : -1) * ((1 - ModelConstants.OPINION_DECAY) + ModelConstants.RANDOM.nextFloat() * ModelConstants.OPINION_DECAY);

            tally = oldOpinion + ModelConstants.SOCIAL_INFLUENCE * socialExposure + ModelConstants.MEDIA_INFLUENCE * mediaExposure;

            if (tally > 0) {
                opinionBoolean = true;
            } else {
                opinionBoolean = false;
            }
        }


    }

    public ArrayList<Voter> getAdjacent() {
        Node adj[] = node.neighborNodes().toArray(Node[]::new);
        ArrayList<Voter> result = new ArrayList<>();
        for (Node n : adj) {
            result.add((Voter) n.getAttribute("Voter"));
        }
        return result;
    }

    public ArrayList<Voter> getShuffledAdjacent() {
        Node adj[] = node.neighborNodes().toArray(Node[]::new);
        ArrayList<Voter> result = new ArrayList<>();
        for (Node n : adj) {
            result.add((Voter) n.getAttribute("Voter"));
        }
        Collections.shuffle(result, ModelConstants.RANDOM);
        return result;
    }

    public float opinionSimilarity(Voter v) {
        return ((6 - Math.abs(v.getAgentOpinion() - this.getAgentOpinion())) / 6);
    }


    public void selectDiscussants() {
        discussants.clear();



        if (ModelConstants.DISCUSSANTS_MODEL == 1) {

                discussants = getAdjacent();
        }


        else{

            int count = 0;
            ArrayList<Voter> neighbours = getShuffledAdjacent();
            ArrayList<Voter> neighboursToRemove = new ArrayList<>();

            /*Iterator<Voter> itr = neighbours.iterator();
            while ( itr.hasNext()) {
                Voter neighbour = itr.next();
                if
            }*/

            for (int i = (int)(ModelConstants.BASE_DISCUSSANTS * 10)-1; i >= 0; i--){
                discussants.add(neighbours.get(i));
                neighboursToRemove.add(neighbours.get(i));

                if (opinionSimilarity(neighbours.get(i)) >= ModelConstants.HOMOPHILY_PROPENSITY) {
                    count++;
                }

            }
            if (neighboursToRemove != null){
                neighbours.removeAll(neighboursToRemove);
            }

            neighboursToRemove.clear();

            //this adds discussants of the nearest opinion value
            int i  = 0;
            while (count / getAdjacent().size() <= ModelConstants.BASE_DISCUSSANTS*10 && neighbours.size() > neighboursToRemove.size()) {
                    for (Voter n : neighbours) {
                        if (opinionSimilarity(n) >= 1 - (i / 6)) {
                            discussants.add(n);
                            neighboursToRemove.add(n);
                            count++;
                        }
                }
                i++;
            }

            if (neighboursToRemove != null){
                neighbours.removeAll(neighboursToRemove);
            }

            /* this adds randomly weighted for opinion simularity
            while (count / getAdjacent().size() <= ModelConstants.HOMOPHILY_PROPENSITY && neighbours.size() != 0) {
                if (ModelConstants.RANDOM.nextFloat() < opinionSimularity(neighbours.get(i))){
                    discussants.add(neighbours.get(i));
                    count ++;
                }

                i++;
            }
            */

            /* This is the java implementation of the code used in the original paper
            for (Voter adjVoter : getShuffledAdjacent()) {

                int count = 0;
                for (Voter discussant : discussants) {
                    if (opinionSimularity(discussant) >= ModelConstants.HOMOPHILY_PROPENSITY) {
                        count++;
                    }
                }

                if (count / getAdjacent().size() <= ModelConstants.HOMOPHILY_PROPENSITY) {
                    if (ModelConstants.RANDOM.nextFloat() < ModelConstants.BASE_DISCUSSANTS) {
                        discussants.add(adjVoter);
                    }
                }
                else {
                    if (opinionSimularity(adjVoter) > ModelConstants.HOMOPHILY_PROPENSITY) {
                        discussants.add(adjVoter);
                    }
                }
            }

            repeat count neighbors  ;; for every neighbors
       [ let neighbor one-of turtles-on neighbors ;; define one of neighboring agents as "neighbor"
         let q random-float 1
         ifelse q < propensity-for-homophily  ;; if random number is less than propensity-for-homophily, create link with that neighbor with similarity of +-0.2 range of propensity-for-homophily
         [ if ((propensity-for-homophily - 0.2) <= (similarity-between neighbor self)) and ((similarity-between neighbor self) <= (propensity-for-homophily + 0.2)) [create-link-with neighbor] ]
         [ if random-float 1 < 0.5 [create-link-with neighbor]] ;; if greater, creat link at random basis

         ;; this results in a situation where
         ;; (a) for greater value of "propensity-for-homophily" (e.g., 0.8), selection of discussants based on homophily is more prevalent while there's still random connections
         ;; (b) for smaller value of "propensity-for-homophily" (e.g., 0.2), random selection is more prevalent
       ] ; end repeat

  set my-connected-neighbors link-neighbors  ;; define the agent-set "my-connected-neighbors" from linked neighbors
  ask links [die] ]  ;; clear links for next tick

            */
        }

        calculateNetworkHomogeneity();

        if (ModelConstants.SELF_DISCUSS){
            discussants.add(this);
        }


    }

    private void calculateNetworkHomogeneity(){
        int count = 0;
        for (Voter d: discussants){
            if (d.opinion >= opinion -1 && d.opinion <= opinion+1){
                count++;
            }
        }
        networkHomogeneity = count/discussants.size();
    }

    public void discuss() {
        if (ModelConstants.SOCIAL_INFLUENCE_MODEL == 1) {
            //mean average
            int sum = 0;
            for (Voter voter : discussants) {
                if (ModelConstants.OPINION_MODEL == 2) {
                    sum += voter.opinion;
                    if (voter.opinion * this.opinion < 0 || (this.opinion == 0 && voter.opinion != 0)) {
                        networkDisagreement++;
                    }
                } else {
                    sum += voter.opinionBoolean ? 1 : -1;
                    if (voter.opinionBoolean != this.opinionBoolean) {
                        networkDisagreement++;
                    }
                }
            }
            socialExposure = (float) sum / discussants.size();
        }
        else if (ModelConstants.SOCIAL_INFLUENCE_MODEL == 2) {

            //majority model

            int maxValue = 0, maxCount = 0;

            if (ModelConstants.OPINION_MODEL == 2) {
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
            }

            else {
                for (int i = 0; i < discussants.size(); ++i) {
                    int count = 0;
                    for (int j = 0; j < discussants.size(); ++j) {
                        if (discussants.get(j).getAgentOpinionBoolean() == discussants.get(i).getAgentOpinionBoolean())
                            ++count;
                    }
                    if (count > maxCount) {
                        maxCount = count;
                        maxValue = discussants.get(i).getAgentOpinionBoolean() ? 1 : -1;
                    }
                }
            }

            socialExposure = maxValue;

        }

        else {
            //Bounded confidence model

            int sum = 0, count = 0;
            for (Voter d: discussants){
                if (d.opinion >= opinion - 1 && d.opinion <= opinion + 1){
                    sum += d.opinion;
                    count++;
                }
            }
            socialExposure = sum/count;
        }
    }

    public ArrayList<Voter> getDiscussants() {
        return discussants;
    }
}