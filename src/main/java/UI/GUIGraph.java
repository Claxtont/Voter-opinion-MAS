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

    private DefaultGraph graph1;
    private DefaultView view1;
    private Viewer viewer1;

    public GUIGraph(){
        generateGraph();
    }

    private void generateGraph()
    {

        DefaultGraph graph1 = new DefaultGraph("g1");
        gen(graph1);
        setGraph(graph1);
        //setGraph(graph2);
    }

     public void setGraph(DefaultGraph graph1)
     {
        //for each pipe
        ThreadProxyPipe pipe1 = new ThreadProxyPipe();
        pipe1.init(graph1);

        //for each view and viewer
        viewer1 = new SwingViewer(pipe1);

        //gen(graph1);

        view1 = new DefaultView(viewer1, "view1", new SwingGraphRenderer());
        viewer1.addView(view1);
        viewer1.enableAutoLayout();

        //for each graph
        graph1.setAttribute("ui.quality");
        graph1.setAttribute("ui.antialias");
        graph1.setAttribute("ui.stylesheet", styleSheet1);

        jPanels(view1);
         //pipe1.pump();
    }

    public void clearGraph(){
        graph1.clear();
    }

 //   public void setGraph(DefaultGraph graph){ //this is currently for testing
 //       graph1 = graph;
 //   }

    public static void gen(DefaultGraph graph){

        DorogovtsevMendesGenerator gen = new DorogovtsevMendesGenerator();

        gen.addSink(graph);
        //gen.addSink(graph2);
        gen.begin();
        for(int i = 0 ; i < 100; i++)
            gen.nextEvents();
        gen.end();

        gen.removeSink(graph);
        //gen.removeSink(graph2);
    }

    private void jPanels(DefaultView view){

        JPanel panelView1 = new JPanel(new BorderLayout());
        panelView1.add(view, BorderLayout.CENTER);
        this.add(panelView1);
        this.updateUI();
        //JPanel panelView2 = new JPanel(new BorderLayout());
        //panelView2.add(view2, BorderLayout.CENTER);

        //this.add(panelView2);
    }

    protected String styleSheet1 =
            "graph { padding: 40px; }" +
                    "node { fill-color: red; stroke-mode: plain; stroke-color: black; }";

    protected String styleSheet2 =
            "graph { padding: 40px; }" +
                    "node { fill-color: blue; stroke-mode: plain; stroke-color: black; }";


}
