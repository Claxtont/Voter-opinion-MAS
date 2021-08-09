package Simulator;

import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.GridGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;

public class _Graph {

    private DefaultGraph graph;
    private int size;

    public _Graph() throws IOException {

        graph = new DefaultGraph("_Graph");
        //viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);

        //View view = viewer.addDefaultView(false);

        String styleSheet = Files.readString(java.nio.file.Path.of("stylesheet.css"));

        graph.setAttribute("ui.stylesheet", styleSheet);
        graph.setAttribute("ui.quality");
        graph.setAttribute("ui.antialias");

        size = ModelConstants.SIZE;

        display();
        populate();


    }
    public DefaultGraph getGraph() {
        return graph;
    }

    void sleep(int t) {
        try { Thread.sleep(t); } catch (Exception e) {}
    }

    public void display(){

        Generator gen = null;
        if (ModelConstants.GRAPH == 1) {
            gen = new GridGenerator(true, false);
            size = (int) Math.sqrt(size);
        }

        if (ModelConstants.GRAPH == 2) {
            gen = new GridGenerator(true, true);
            size = (int) Math.sqrt(size);
        }

        if(ModelConstants.GRAPH == 3){
            gen = new DorogovtsevMendesGenerator();
        }

        gen.addSink(graph);
        gen.begin();

        for(int i=0; i<size-1; i++){
            gen.nextEvents();
            //sleep(5);
        }

        gen.end();

    }

    public void populate(){
        for(Node n: graph){
            n.setAttribute("Voter",new Voter(n));
            //n.setAttribute("ui.label", n.toString());
            System.out.println(n.toString());
            Voter v = (Voter)n.getAttribute("Voter");
            colour(v);
            //sleep(10);
        }
    }

    public void colour(Voter v){
        Node n = v.getNode();
        if(ModelConstants.OPINION_MODEL == 2){
            switch (v.getAgentOpinion()) {
                case 3 -> n.setAttribute("ui.class", "o3");
                case 2 -> n.setAttribute("ui.class", "o2");
                case 1 -> n.setAttribute("ui.class", "o1");
                case 0 -> n.setAttribute("ui.class", "o0");
                case -1 -> n.setAttribute("ui.class", "om1");
                case -2 -> n.setAttribute("ui.class", "om2");
                case -3 -> n.setAttribute("ui.class", "om3");
            }
        }
        else if (ModelConstants.OPINION_MODEL == 1) {
            if (v.getAgentOpinionBoolean()){
                n.setAttribute("ui.class", "o3");
            }
            else {
                n.setAttribute("ui.class", "om3");
            }
        }

    }

    public Node getNode(Voter v){
        Node result = null;
        for (int i = 0; i < graph.nodes().count(); i++){
            if (graph.getNode(i).getAttribute("Voter") == v){
                result = graph.getNode(i);
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

    public void graphDiscussants(ArrayList<Voter> voters){
        for (int i = 0; i < graph.getEdgeCount(); i++){
            graph.getEdge(i).setAttribute("ui.class", "blank");
        }
        //Voter v = voters.get(0);
        for (Voter v : voters) {
            for (Voter d : v.getDisscussants()){
                getNode(v).getEdgeBetween(getNode(d)).setAttribute("ui.class", "discussant");
            }
        }
    }

}