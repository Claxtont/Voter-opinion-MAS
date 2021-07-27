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

    private DefaultGraph graph;
    private DefaultView view;
    private Viewer viewer;

    public GUIGraph(){
        generateGraph();
    }

    private void generateGraph()
    {

        DefaultGraph graph = new DefaultGraph("g1");
        gen(graph);
        setGraph(graph);
        //setGraph(graph2);
    }

     public void setGraph(DefaultGraph graph)
     {
        //for each pipe
        ThreadProxyPipe pipe1 = new ThreadProxyPipe();
        pipe1.init(graph);

        //for each view and viewer
        viewer = new SwingViewer(pipe1);

        view = new DefaultView(viewer, "view1", new SwingGraphRenderer());
        viewer.addView(view);
        viewer.enableAutoLayout();

        graph.setAttribute("ui.quality");
        graph.setAttribute("ui.antialias");
        graph.setAttribute("ui.stylesheet", styleSheet1);

        jPanels(view);
    }

    public static void gen(DefaultGraph graph){

        DorogovtsevMendesGenerator gen = new DorogovtsevMendesGenerator();

        gen.addSink(graph);
        gen.begin();
        for(int i = 0 ; i < 100; i++)
            gen.nextEvents();
        gen.end();

        gen.removeSink(graph);
    }

    private void jPanels(DefaultView view){
        this.removeAll();
        JPanel panelView = new JPanel(new BorderLayout());
        panelView.add(view, BorderLayout.CENTER);
        this.add(panelView);
        this.updateUI();
    }

    protected String styleSheet1 =
            "graph { padding: 40px; }" +
                    "node { fill-color: red; stroke-mode: plain; stroke-color: black; }";

    protected String styleSheet2 =
            "graph { padding: 40px; }" +
                    "node { fill-color: blue; stroke-mode: plain; stroke-color: black; }";


}
