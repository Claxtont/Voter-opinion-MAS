package Simulator;

import org.graphstream.graph.Element;
import org.graphstream.graph.Node;
import org.graphstream.stream.binary.ByteProxy;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Voter {

    private int opinion;
    private Boolean opinionBoolean;
    private int mediaExposure;
    private int networkOpinionDistribution;
    private int networkHomogeneity;
    private double politicalInterest;
    private ArrayList<Voter> discussants = new ArrayList<>();
    private float socialExposure;
    private int networkDisagreement;
    private Node node;

    public Voter(Node n) { //TODO: binary opinion model refactor
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
        //todo: binary opinion media exposure
    }

    public void updateOpinion() {

        float tally;
        float oldOpinion;
        if (ModelConstants.OPINION_MODEL == 2) {
            oldOpinion = opinion * ((1 - ModelConstants.OPINION_DECAY) + ModelConstants.RANDOM.nextFloat() * ModelConstants.OPINION_DECAY);

            tally = oldOpinion + ModelConstants.SOCIAL_INFLUENCE * socialExposure + ModelConstants.MEDIA_INFLUENCE * mediaExposure;

            if ((Math.round(tally)) < opinion) {
                opinion -= 1;
            } else if ((Math.round(tally)) > opinion) {
                opinion += 1;
            }

            if ((Math.round(tally)) < opinion) {
                opinion -= 1;
            } else if ((Math.round(tally)) > opinion) {
                opinion += 1;
            }

            while (Math.abs(opinion) > 3) {
                opinion = opinion - Integer.signum(opinion);
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

    public float opinionSimularity(Voter v) {
        return 6 - (Math.abs(v.getAgentOpinion() - this.getAgentOpinion()) / 6);
    }


    public void selectDiscussants() {
        switch (ModelConstants.DISCUSSANTS_MODEL) {

            case (1):
                discussants = getAdjacent();

            case (2): //homophily
                for (Voter adjVoter : getAdjacent()) {

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
                    } else {
                        if (opinionSimularity(adjVoter) > ModelConstants.HOMOPHILY_PROPENSITY) {
                            discussants.add(adjVoter);
                        }
                    }
                }
        }
    }

    public void discuss() {
        switch (ModelConstants.SOCIAL_INFLUENCE_MODEL) {

            case (1):
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

            case (2):
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
                } else {
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

    }
}