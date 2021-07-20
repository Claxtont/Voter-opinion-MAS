package UI;

import Simulator.GraphTest;

import javax.swing.*;


public class GUIGraph {


    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "org.graphstream.ui.swing.util.Display");
        GraphTest test = new GraphTest();
        test.run( args , new JFrame());
    }


}
