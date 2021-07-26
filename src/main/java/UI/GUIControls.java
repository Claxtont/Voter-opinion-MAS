package UI;

import javax.swing.*;

public class GUIControls extends JPanel {

    private JButton initialiseButton, oneStepButton, longStepButton, quitButton, fullScreenButton;


    public GUIControls(GUIMain mainWindow){
        initialiseButton = new JButton();
        oneStepButton = new JButton();
        longStepButton = new JButton();
        quitButton = new JButton();

        initialiseButton.setText("Initialise simulation");
        initialiseButton.setEnabled(true);

        oneStepButton.setText("Step once");
        oneStepButton.setEnabled(true);

        longStepButton.setText("50 steps");
        longStepButton.setEnabled(true);

        quitButton.setText("Quit");
        quitButton.setEnabled(true);

        add(initialiseButton);
        add(oneStepButton);
        add(longStepButton);
        add(quitButton);

        listenerSetUp(mainWindow);

    }

    private void listenerSetUp(GUIMain window){

        quitButton.addActionListener(e -> window.exitApp());

        initialiseButton.addActionListener(e -> {

            Thread thread = new Thread(() -> {
                window.initialise();
            });
            thread.start();
        });

        longStepButton.addActionListener(e -> {
            Thread thread = new Thread(() -> {
                window.runSimulation();
            });
            thread.start();
        });

        oneStepButton.addActionListener(e -> {
            Thread thread = new Thread(() -> {
                window.runSimulationOnce();
            });
            thread.start();
        });

    }


}
