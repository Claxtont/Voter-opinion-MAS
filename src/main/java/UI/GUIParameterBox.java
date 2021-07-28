package UI;

import Simulator.ModelConstants;

import javax.swing.*;

public class GUIParameterBox extends JPanel {

    private LabelledTextArea opinionDecay, mediaInfluence, graphSize, socialInfluence;
    private JComboBox model, graph, socialInfluenceModel, discussantSelectionModel, opinionUpdateModel;

    private String[] models = {"opinion model 1, binary opinion", "opinion model 2, 7 point opinion scale"};
    private String[] graphs = {"graph 1, grid", "graph 2, torus", "graph 2, Dorogovtsev Mendes graph"};
    private String[] socialInfluenceModels = {"Social Influence Model 1, Mean Average", "Social Influence Model 2, Majority", "Social Influence Model 3, Deffuant-Weisbauch BC model"};
    private String[] discussantSelectionModels = {"Discussant Selection Model 1, all neighbours", "Discussant Selection Model 2, Homophily-based"};
    private String[] opinionUpdateModels = {"Opinion Update Model 1, Weighted mean average"};

    public GUIParameterBox(){
        graphSize = new LabelledTextArea("Number of Agents", String.valueOf(100));
        opinionDecay = new LabelledTextArea("Voter opinion decay", String.valueOf(ModelConstants.OPINION_DECAY));
        mediaInfluence = new LabelledTextArea("Media Influence on voters", String.valueOf(ModelConstants.MEDIA_INFLUENCE));
        socialInfluence = new LabelledTextArea("Social Influence on voters", String.valueOf(ModelConstants.SOCIAL_INFLUENCE));

        model = new JComboBox(models);
        graph = new JComboBox(graphs);
        socialInfluenceModel = new JComboBox(socialInfluenceModels);
        discussantSelectionModel = new JComboBox(discussantSelectionModels);
        opinionUpdateModel = new JComboBox(opinionUpdateModels);

        model.setSelectedIndex(1);
        graph.setSelectedIndex(0);

        add(model);
        add(graph);
        add(socialInfluenceModel);
        add(discussantSelectionModel);
        add(opinionUpdateModel);

        add(graphSize);
        add(opinionDecay);
        add(mediaInfluence);
        add(socialInfluence);


    }

    public void setModelConstants() {

        ModelConstants.SIZE = (int) graphSize.getValue();
        ModelConstants.GRAPH = graph.getSelectedIndex() + 1;
        ModelConstants.OPINION_MODEL = model.getSelectedIndex() + 1;
        ModelConstants.DISCUSSANTS_MODEL = discussantSelectionModel.getSelectedIndex() + 1;
        ModelConstants.OPINION_UPDATE_MODEL = opinionUpdateModel.getSelectedIndex() + 1;
        ModelConstants.OPINION_DECAY = (float) opinionDecay.getValue();
        ModelConstants.MEDIA_INFLUENCE = (float) mediaInfluence.getValue();
        ModelConstants.SOCIAL_INFLUENCE = (float) socialInfluence.getValue();

    }
}
