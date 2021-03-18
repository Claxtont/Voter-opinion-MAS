package Simulator;
import org.graphstream.algorithm.generator.*;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

import java.util.ArrayList;

public class _Graph {

    private int size, graphType, model;
    private Graph graph;

    public _Graph(int s,int g, int m){
        model = m;

        System.setProperty("org.graphstream.ui", "swing");

        size = s; graphType = g;  model = m;

        graph = new SingleGraph("_Graph");

        graph.setAttribute("ui.stylesheet", styleSheet);

        display();
        populate();


    }

    private String styleSheet =
        "node {" +
        "	fill-color: black;" +
        "}" +
        "node.om3 {" +
        "	fill-color: rgb(255,0,0);" +
        "}" +
        "node.om2 {" +
        "	fill-color: rgb(170,0,0);" +
        "}" +
        "node.om1 {" +
        "	fill-color: rgb(85,0,0);" +
        "}" +
        "node.o0 {" +
        "   fill-color: rgb(0,0,0);" +
        "}" +
        "node.o1 {" +
        "	fill-color: rgb(0,0,85);" +
        "}" +
        "node.o2 {" +
        "	fill-color: rgb(0,0,170);" +
        "}" +
        "node.o3 {" +
        "	fill-color: rgb(0,0,255);" +
        "}";

        public void display(){
            Generator gen = null;
            if (graphType == 1) {
                gen = new GridGenerator(false, true);
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
            }

            gen.end();
        }

        public void populate(){
            for(Node n: graph){
                n.setAttribute("Voter",new Voter(n));

                Voter v = (Voter)n.getAttribute("Voter");
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

//        public ArrayList<Voter> adjacentVoters(Voter v){
//            getNode(v).neighborNodes().toList();
//        }

}

