package Simulator;

import java.util.Random;

public class ModelConstants {

    public static int STEP_LENGTH = 50;

    public static int SIZE = 100;

    public static int SEED = 999;

    public static int OPINION_MODEL = 1;
    // 1 = binary, 2 = 7 point scale
    public static int GRAPH = 2;
    //1 = grid, 2 = torus, 3 = DM-graph
    public static int SOCIAL_INFLUENCE_MODEL = 1;
    //1 = mean average, 2 = majority model, 3 = bounded confidence
    public static int DISCUSSANTS_MODEL = 1;
    //1 = all adjacent voters are the voter's discussants, 2 = homophilly based
    public static int OPINION_UPDATE_MODEL = 1;
    //1 = weighted mean average, 2 = current social only, 3 = social and media TODO: check this fully
    public static int CHART = 1;
    //1 = BarChart

    public static float HOMOPHILY_PROPENSITY = (float)0.6;
    public static float BASE_DISCUSSANTS = (float)0.3;
    public static float OPINION_DECAY = (float)0.7;
    public static float MEDIA_INFLUENCE = (float)0.7;
    public static float SOCIAL_INFLUENCE = 1;

    public static Random RANDOM = new Random();
}
