package UI;

import Simulator.Simulator;
import Simulator.ModelConstants;
import Simulator.GraphTest;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class GUIMain_Old {

    // Convenient to have base windows available everywhere within this class
    private JFrame mainFrame;

    //private LabelledTextArea simSeed;
    private LabelledTextArea graphSize;
    private LabelledTextArea opinionDecay;
    private LabelledTextArea mediaInfluence;
    private LabelledTextArea socialInfluence;

    private JButton initialiseButton, oneStepButton, longStepButton, quitButton, fullScreenButton;
    private JComboBox model, graph, socialInfluenceModel, discussantSelectionModel, opinionUpdateModel;

    private String[] models = {"opinion model 1, binary opinion", "opinion model 2, 7 point opinion scale"};
    private String[] graphs = {"graph 1, grid", "graph 2, torus", "graph 2, Dorogovtsev Mendes graph"};
    private String[] socialInfluenceModels = {"Social Influence Model 1, Mean Average", "Social Influence Model 2, Majority", "Social Influence Model 3, Deffuant-Weisbauch BC model"};
    private String[] discussantSelectionModels = {"Discussant Selection Model 1, all neighbours", "Discussant Selection Model 2, Homophily-based"};
    private String[] opinionUpdateModels = {"Opinion Update Model 1, Weighted mean average"};

    private Simulator s;

    public static void main(String[] args) {
        //final Model gm = new Model();
        System.setProperty("org.graphstream.ui", "swing");
        GUIMain_Old gm = new GUIMain_Old(args);
    }

    private GUIMain_Old(String[] args) {
        // create components
        initialiseButton = new JButton();
        oneStepButton = new JButton();
        longStepButton = new JButton();
        quitButton = new JButton();


        graphSize = new LabelledTextArea("Number of Agents", String.valueOf(100));
        //fieldHeight = new LabelledTextArea("Height of simulation", String.valueOf(ModelConstants.HEIGHT));
        //simSeed = new LabelledTextArea("Random Seed", "not implemented");
        opinionDecay = new LabelledTextArea("Voter opinion decay", String.valueOf(ModelConstants.OPINION_DECAY));
        mediaInfluence = new LabelledTextArea("Media Influence on voters", String.valueOf(ModelConstants.MEDIA_INFLUENCE));
        socialInfluence = new LabelledTextArea("Social Influence on voters", String.valueOf(ModelConstants.SOCIAL_INFLUENCE));

        model = new JComboBox(models);
        graph = new JComboBox(graphs);
        socialInfluenceModel = new JComboBox(socialInfluenceModels);
        discussantSelectionModel = new JComboBox(discussantSelectionModels);
        opinionUpdateModel = new JComboBox(opinionUpdateModels);

        //properties of components
        initialiseButton.setText("Initialise simulation");
        initialiseButton.setEnabled(true);

        oneStepButton.setText("Step once");
        oneStepButton.setEnabled(true);

        longStepButton.setText("50 steps");
        longStepButton.setEnabled(true);

        quitButton.setText("Quit");
        quitButton.setEnabled(true);

        //create containers
        mainFrame = new JFrame("Simulator setup");

        JPanel commandBox = new JPanel();
        JPanel parameterBox = new JPanel();
        JPanel lowerBox = new JPanel();


        lowerBox.setBorder(BorderFactory.createEtchedBorder());
        parameterBox.setBorder(new TitledBorder("Simulation Parameters"));

        //LayoutManagers
        mainFrame.getContentPane().setLayout(new BorderLayout());

        parameterBox.setLayout(new GridLayout(4,3));
        commandBox.setLayout(new GridLayout(2,2));
        lowerBox.setLayout(new BorderLayout());

        //add components to containers
        commandBox.add(initialiseButton);
        commandBox.add(oneStepButton);
        commandBox.add(longStepButton);
        //commandBox.add(new JLabel());
        commandBox.add(quitButton);


        model.setSelectedIndex(1);
        graph.setSelectedIndex(0);

        parameterBox.add(model);
        parameterBox.add(graph);
        parameterBox.add(socialInfluenceModel);
        parameterBox.add(discussantSelectionModel);
        parameterBox.add(opinionUpdateModel);


        //parameterBox.add(fieldHeight);
        //parameterBox.add(simSeed);
        parameterBox.add(graphSize);
        parameterBox.add(opinionDecay);
        parameterBox.add(mediaInfluence);
        parameterBox.add(socialInfluence);

        lowerBox.add(commandBox, BorderLayout.SOUTH);
        lowerBox.add(parameterBox, BorderLayout.NORTH);

        mainFrame.getContentPane().add(lowerBox, BorderLayout.SOUTH);


        //set up listeners
        mainFrame.setDefaultCloseOperation(
                WindowConstants.DO_NOTHING_ON_CLOSE);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                exitApp();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exitApp();
            }
        });

        initialiseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Thread thread = new Thread(new Runnable(){
                    public void run(){
                        initialise(args);
                        mainFrame.setVisible(true);
                    }
                });
                thread.start();
                //mainFrame.setVisible(false);
            };
        });

        longStepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread thread = new Thread(new Runnable(){
                    public void run(){
                        runSimulation();
                        mainFrame.setVisible(true);
                    }
                });
                thread.start();
                mainFrame.setVisible(false);
            }
        });

        oneStepButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                Thread thread = new Thread(new Runnable(){
                    public void run(){
                        runSimulationOnce();
                        mainFrame.setVisible(true);
                    }
                });
                thread.start();
                mainFrame.setVisible(false);
            }
        });


        //display the GUI
        mainFrame.pack();
        //mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //mainFrame.setUndecorated(true);
        mainFrame.setVisible(true);

    }

    private void initialise(String[] args){
        try {
            //ModelConstants.WIDTH = (int)graphSize.getValue();
            ModelConstants.SIZE = (int) graphSize.getValue();
            //ModelConstants.HEIGHT = (int)fieldHeight.getValue();
            ModelConstants.GRAPH = graph.getSelectedIndex() + 1;
            ModelConstants.OPINION_MODEL = model.getSelectedIndex() + 1;
            ModelConstants.OPINION_DECAY = (float) opinionDecay.getValue();
            ModelConstants.MEDIA_INFLUENCE = (float) mediaInfluence.getValue();
            ModelConstants.SOCIAL_INFLUENCE = (float) socialInfluence.getValue();
            GraphTest test = new GraphTest();
            test.run( args , mainFrame);
            //this.s = new Simulator();

        }
        catch (Exception e){
                JOptionPane.showMessageDialog(mainFrame, "Problem initialising graph. " + e.getMessage());
            }
    }


    private void exitApp() {
        // Display confirmation dialog before exiting application
        int response = JOptionPane.showConfirmDialog(mainFrame,
                "Do you really want to quit?",
                "Quit?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            System.exit(0);
        }


    }

    private void runSimulation(){
        int i = 1;
        try{
            for (i = 1; i <= ModelConstants.STEP_LENGTH; i++){
                s.simulateOneStep();
                Thread.sleep(200);
            }

        }
        catch(Exception e){
            JOptionPane.showMessageDialog( mainFrame, "Problem simulating at step: " + i + " Error: " + e.getMessage());
        }
    }

    private void runSimulationOnce(){

        //try{
            s.simulateOneStep();
        //}
        //catch (Exception e){
        //    JOptionPane.showMessageDialog(mainFrame, "Problem simulating the next step. " + e.getMessage());
        //}
    }
}
