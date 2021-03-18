//package UI;
//
//import Simulator.Simulator;
//import Simulator.Field;
//import Simulator.Voter;
//import Simulator.ModelConstants;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//
//public class View extends JFrame {
//    private static final Color TRUE_COLOR = Color.GREEN;
//    private static final Color FALSE_COLOR = Color.BLUE;
//
//    private JLabel steplabel;
//    private FieldView fieldView;
//    private Simulator s;
//
//    public View(int height, int width, Simulator s){
//        this.s = s;
//
//        setTitle("MAS");
//        steplabel = new JLabel();
//
//        setLocation(400, 200);
//
//        fieldView = new FieldView(height, width);
//        Container contents = getContentPane();
//        contents.add(steplabel, BorderLayout.NORTH);
//        contents.add(fieldView, BorderLayout.CENTER);
//
//        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//        this.addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                exitApp();
//            }
//        });
//
//        pack();
//        setVisible(true);
//    }
//
//    private void exitApp(){
//        System.exit(0);
//    }
//
//
//
//    public void showStatus(int step, Field field){
//        if(!isVisible()){
//            setVisible(true);
//        }
//
//        steplabel.setText(String.valueOf(step));
//
//        fieldView.preparePaint();
//
//        if (ModelConstants.MODEL == 1) {
//            for (int row = 0; row < field.getHeight(); row++) {
//                for (int col = 0; col < field.getWidth(); col++) {
//                    Voter voter = field.getVoterAt(col, row);
//                    if (voter.getAgentOpinionBoolean()) {
//                        fieldView.drawMark(col, row, TRUE_COLOR);
//                    } else {
//                        fieldView.drawMark(col, row, FALSE_COLOR);
//                    }
//                }
//            }
//        }
//        else if (ModelConstants.MODEL ==2){
//            for (int row = 0; row < field.getHeight(); row++) {
//                for (int col = 0; col < field.getWidth(); col++) {
//                    Voter voter = field.getVoterAt(col, row);
//                    switch(voter.getAgentOpinion()){
//                        case 3:
//                            fieldView.drawMark(col, row, new Color(0,0,204));
//                            break;
//                        case 2:
//                            fieldView.drawMark(col, row, new Color(68,68,204));
//                            break;
//                        case 1:
//                            fieldView.drawMark(col, row, new Color(136,136,204));
//                            break;
//                        case 0:
//                            fieldView.drawMark(col, row, new Color(204,204,204));
//                            break;
//                        case -1:
//                            fieldView.drawMark(col, row, new Color(204,136,136));
//                            break;
//                        case -2:
//                            fieldView.drawMark(col, row, new Color(204,68,68));
//                            break;
//                        case -3:
//                            fieldView.drawMark(col, row, new Color(204,0,0));
//                            break;
//                    }
//
//                }
//            }
//        }
//
//        fieldView.repaint();
//        pack();
//    }
//
//    private class FieldView extends JPanel {
//
//        private final int GRID_VIEW_SCALING_FACTOR = 6;
//        Dimension size;
//        private Image fieldImage;
//        private Graphics graphics;
//        private int gridWidth, gridHeight;
//        private int xScale, yScale;
//
//
//        public FieldView(int width, int height){
//            gridHeight = height;
//            gridWidth = width;
//            size = new Dimension(0, 0);
//        }
//
//        public Dimension getPreferredSize()
//        {
//            return new Dimension(gridWidth * GRID_VIEW_SCALING_FACTOR,
//                    gridHeight * GRID_VIEW_SCALING_FACTOR);
//        }
//
//        public void preparePaint()
//        {
//            if(! size.equals(getSize())) {  // if the size has changed...
//                size = getSize();
//                fieldImage = fieldView.createImage(size.width, size.height);
//                graphics = fieldImage.getGraphics();
//
//                xScale = size.width / gridWidth;
//                if(xScale < 1) {
//                    xScale = GRID_VIEW_SCALING_FACTOR;
//                }
//                yScale = size.height / gridHeight;
//                if(yScale < 1) {
//                    yScale = GRID_VIEW_SCALING_FACTOR;
//                }
//            }
//        }
//        public void drawMark(int x, int y, Color color)
//        {
//            graphics.setColor(color);
//            graphics.fillRect(x * xScale, y * yScale, xScale-1, yScale-1);
//        }
//
//        public void paint(Graphics g)
//        {
//            if(fieldImage != null) {
//                g.drawImage(fieldImage, 0, 0, null);
//            }
//        }
//
//    }
//
//}
