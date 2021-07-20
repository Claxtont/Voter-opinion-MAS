package Simulator;

public class ModelConstants {

    public static int STEP_LENGTH = 50;

    //public static int HEIGHT = 100;
    public static int SIZE = 10;

    public static int SEED = 999;

    public static int OPINION_MODEL = 1;
    public static int GRAPH = 2;
    public static int DISCUSSANTS_MODEL = 1; //0 = all adjacent voters are the voter's discussants
    public static int SOCIAL_INFLUENCE_MODEL = 1;
    public static int OPINION_UPDATE_MODEL = 0;

    public static float OPINION_DECAY = (float)0.7;
    public static float MEDIA_INFLUENCE = (float)0.7;
    public static float SOCIAL_INFLUENCE = 1;
}
