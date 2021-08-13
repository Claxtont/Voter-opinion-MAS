package UI;

import Simulator.ModelConstants;
import Simulator.Simulator;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static UI.GUIChart.*;


public class GUIMain extends JFrame {


    public static void main(String[] args) {
        new GUIMain().run();
    }

    GUIParameterBox parameterBox;
    GUIControls controls;
    GUIGraph graphPanel;
    ChartPanel chartPanel;
    JFreeChart chart;
    Simulator simulator;

    private void run(){

        setDefaultCloseOperation(
                WindowConstants.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                exitApp();
            }
        });


        setLayout(new GridLayout(2, 1));

        JPanel panelViewTop = new JPanel(new GridLayout(0,1));
        parameterBox = new GUIParameterBox();
        panelViewTop.add(parameterBox);
        controls = new GUIControls(this);
        panelViewTop.add(controls);

        JPanel panelViewBottom = new JPanel(new GridLayout(1,0));
        graphPanel = new GUIGraph();
        graphPanel.setLayout(new GridLayout(1,0));
        panelViewBottom.add(graphPanel);

        chartPanel = new ChartPanel(createChart(), false);
        panelViewBottom.add(chartPanel);

        add(panelViewTop);
        add(panelViewBottom);
        setSize(800, 600);
        //this.pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void exitApp(){
        int response = JOptionPane.showConfirmDialog(this,
                "Do you really want to quit?",
                "Quit?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    void initialise(){
        try {
            parameterBox.setModelConstants();
            simulator = new Simulator();
            graphPanel.setGraph(simulator.getGraph().getGraph());
            chartPanel.setChart(new GUIChart().createChart());
            simulator.updateChartData();
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(this, "Problem initialising graph. " + e.getMessage());
        }
    }

    void runSimulation(){
        int i = 1;
        try{
            for (i = 1; i <= ModelConstants.STEP_LENGTH; i++){
                simulator.simulateOneStep();
                Thread.sleep(20);
            }

        }
        catch(Exception e){
            JOptionPane.showMessageDialog( this, "Problem simulating at step: " + i + " Error: " + e.getMessage());
        }
    }

    void runSimulationOnce(){

        try{
            simulator.simulateOneStep();
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(this, "Problem simulating the next step. " + e.getMessage());
        }
    }

}
