package Simulator;

import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.ProxyPipe;
import org.graphstream.stream.Source;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.swing.SwingGraphRenderer;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.swing_viewer.ViewPanel;
import org.graphstream.ui.view.*;
import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.view.ViewerListener;

import javax.swing.*;
import java.awt.*;


public class GraphTest implements ViewerListener {
    private boolean loop = true;

    public void run(String[] args, JFrame frame) {
        JPanel panel = new JPanel(new GridLayout()){
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(640, 480);
            }
        };

        panel.setBorder(BorderFactory.createLineBorder(Color.blue, 5));

        Graph graph = new SingleGraph( "embeddedGraph", false, true );

        //SwingViewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        GraphRenderer renderer = new SwingGraphRenderer();
        //ViewPanel viewPanel = viewer.addDefaultView(false);

        //frame.add(viewPanel);


        //ViewerPipe pipeIn = viewer.newViewerPipe();
        DorogovtsevMendesGenerator gen = new DorogovtsevMendesGenerator();

        //pipeIn.addAttributeSink( graph );
        //pipeIn.addViewerListener( this );
        //pipeIn.pump();

        graph.setAttribute( "ui.default.title", "Layout Test" );
        graph.setAttribute( "ui.antialias" );
        graph.setAttribute( "ui.stylesheet", styleSheet );

        gen.addSink( graph );
        gen.setDirectedEdges( true, true );
        gen.begin();
        int i = 0;
        while ( i < 100 ) {
            gen.nextEvents();
            i += 1;
        }
        gen.end();

        graph.forEach( n -> n.setAttribute( "ui.label", "truc" ));

        while( loop ) {
            //pipeIn.pump();
            sleep( 10 );
        }

        System.exit(0);
    }

    protected void sleep(long ms){
        try { Thread.sleep(ms);}
        catch (InterruptedException e){e.printStackTrace();}
    }


    private String styleSheet =
            "graph {"+
                    "fill-mode: gradient-radial;"+
                    "fill-color: white, gray;"+
                    "padding: 60px;"+
                    "}"+
                    "node {"+
                    "shape: circle;"+
                    "size: 10px;"+
                    "fill-mode: gradient-vertical;"+
                    "fill-color: white, rgb(200,200,200);"+
                    "stroke-mode: plain;"+
                    "stroke-color: rgba(255,255,0,255);"+
                    "stroke-width: 2px;"+
                    "shadow-mode: plain;"+
                    "shadow-width: 0px;"+
                    "shadow-offset: 3px, -3px;"+
                    "shadow-color: rgba(0,0,0,100);"+
                    "text-visibility-mode: zoom-range;"+
                    "text-visibility: 0, 0.9;"+
                    //icon-mode: at-left;
                    //icon: url('file:///home/antoine/GSLogo11d24.png');
                    "}"+
                    "node:clicked {"+
                    "stroke-mode: plain;"+
                    "stroke-color: red;"+
                    "}"+
                    "node:selected {"+
                    "stroke-mode: plain;"+
                    "stroke-width: 4px;"+
                    "stroke-color: blue;"+
                    "}"+
                    "edge {"+
                    "size: 1px;"+
                    "shape: cubic-curve;"+
                    "fill-color: rgb(128,128,128);"+
                    "fill-mode: plain;"+
                    "stroke-mode: plain;"+
                    "stroke-color: rgb(80,80,80);"+
                    "stroke-width: 1px;"+
                    "shadow-mode: none;"+
                    "shadow-color: rgba(0,0,0,50);"+
                    "shadow-offset: 3px, -3px;"+
                    "shadow-width: 0px;"+
                    "arrow-shape: diamond;"+
                    "arrow-size: 14px, 7px;"+
                    "}";

    @Override
    public void viewClosed(String id) {loop = false;}

    @Override
    public void buttonPushed(String id) {    }

    @Override
    public void buttonReleased(String id) {    }

    @Override
    public void mouseOver(String id) {    }

    @Override
    public void mouseLeft(String id) {    }
}
