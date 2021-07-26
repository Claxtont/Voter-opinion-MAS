package UI;

import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.thread.ThreadProxyPipe;
import org.graphstream.ui.swing.SwingGraphRenderer;
import org.graphstream.ui.swing_viewer.DefaultView;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import java.awt.*;

public class GUIGraph extends JPanel {

    private DefaultGraph graph1, graph2;
    private DefaultView view1, view2;
    private Viewer viewer1, viewer2;

    public GUIGraph(){
        generateGraph();
    }

    private void generateGraph(){

        graph1 = new DefaultGraph("g1");
        graph2 = new DefaultGraph("g2");

        //for each pipe
        ThreadProxyPipe pipe1 = new ThreadProxyPipe();
        pipe1.init(graph1);
        ThreadProxyPipe pipe2 = new ThreadProxyPipe();
        pipe2.init(graph2);

        //for each view and viewer
        viewer1 = new SwingViewer(pipe1);
        viewer2 = new SwingViewer(pipe2);

        gen();

        view1 = new DefaultView(viewer1, "view1", new SwingGraphRenderer());
        view2 = new DefaultView(viewer2, "view2", new SwingGraphRenderer());
        viewer1.addView(view1);
        viewer2.addView(view2);
        viewer1.enableAutoLayout();
        viewer2.enableAutoLayout();


        //for each graph
        graph1.setAttribute("ui.quality");
        graph2.setAttribute("ui.quality");
        graph1.setAttribute("ui.antialias");
        graph2.setAttribute("ui.antialias");
        graph1.setAttribute("ui.stylesheet", styleSheet1);
        graph2.setAttribute("ui.stylesheet", styleSheet2);

        jPanels();


    }

    public void clearGraph(){
        graph1.clear();
        graph2.clear();
    }

    public void setGraph(DefaultGraph graph){ //this is currently for testing
        graph1 = graph;
    }

    public void gen(){
        DorogovtsevMendesGenerator gen = new DorogovtsevMendesGenerator();

        gen.addSink(graph1);
        gen.addSink(graph2);
        gen.begin();
        for(int i = 0 ; i < 100; i++)
            gen.nextEvents();
        gen.end();

        gen.removeSink(graph1);
        gen.removeSink(graph2);
    }

    private void jPanels(){

        JPanel panelView1 = new JPanel(new BorderLayout());
        panelView1.add(view1, BorderLayout.CENTER);
        this.add(panelView1);

        JPanel panelView2 = new JPanel(new BorderLayout());
        panelView2.add(view2, BorderLayout.CENTER);

        this.add(panelView2);
    }

    protected String styleSheet1 =
            "graph { padding: 40px; }" +
                    "node { fill-color: red; stroke-mode: plain; stroke-color: black; }";

    protected String styleSheet2 =
            "graph { padding: 40px; }" +
                    "node { fill-color: blue; stroke-mode: plain; stroke-color: black; }";


}
