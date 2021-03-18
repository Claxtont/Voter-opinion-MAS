package Simulator;
import org.graphstream.algorithm.generator.*;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.graphicGraph.stylesheet.StyleSheet;
import org.graphstream.ui.swing_viewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class _Graph {

    private int size, graphType, model;
    private Graph graph;

    public _Graph(int s,int g, int m) throws IOException {
        model = m;

        System.setProperty("org.graphstream.ui", "swing");


        size = s; graphType = g;  model = m;

        graph = new SingleGraph("_Graph");



        String styleSheet = Files.readString(java.nio.file.Path.of("stylesheet.css"));

        graph.setAttribute("ui.stylesheet", styleSheet);



        display();
        populate();


    }

    void sleep() {
        try { Thread.sleep(10); } catch (Exception e) {}
    }

        public void display(){


            Generator gen = null;
            if (graphType == 1) {
                gen = new GridGenerator(true, false);
                size = (int) Math.sqrt(size);
            }
            else if(graphType == 2){
                gen = new DorogovtsevMendesGenerator();
            }

            gen.addSink(graph);
            gen.begin();

            graph.display();

            for(int i=0; i<size; i++){
                gen.nextEvents();
                sleep();
            }

            for (Node n: graph){
                n.setAttribute("ui.color", (float)0.5);
            }

            //graph.setAttribute("ui.color", (float)0.5);

            gen.end();

        }

        public void populate(){
            for(Node n: graph){
                n.setAttribute("Voter",new Voter(n));
                n.setAttribute("ui.label", n.toString());
                System.out.println(n.toString());
                Voter v = (Voter)n.getAttribute("Voter");
                colour(v);
                sleep();
            }
        }

        public void colour(Voter v){
            Node n = v.getNode();
            switch(v.getAgentOpinion()) {
                case 3:
                    n.setAttribute("ui.class", "o3");
                    break;
                case 2:
                    n.setAttribute("ui.class", "o2");
                    break;
                case 1:
                    n.setAttribute("ui.class", "o1");
                    break;
                case 0:
                    n.setAttribute("ui.class", "o0");
                    break;
                case -1:
                    n.setAttribute("ui.class", "om1");
                    break;
                case -2:
                    n.setAttribute("ui.class", "om2");
                    break;
                case -3:
                    n.setAttribute("ui.class", "om3");
                    break;
            }
        }

        public Node getNode(Voter v){
            Node result = null;
            int i = 0;
            while (result == null){
                if (graph.getNode(i).getAttribute("Voter") == v){
                    result = graph.getNode(i);
                    i++;
                }
            }
            return result;
        }

        public ArrayList<Voter> getVoters(){
            ArrayList<Voter> result = new ArrayList<>();
            for(Node n: graph) {
                result.add((Voter)n.getAttribute("Voter"));
            }
            return result;
        }

}

