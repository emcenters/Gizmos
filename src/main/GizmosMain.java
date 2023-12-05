package main;

import javax.swing.JFrame;

public class GizmosMain {
	public static void main(String args[]) {
        JFrame frame = new JFrame("GIZMOS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
 
        //create and set up the content pane
        MainBoard layeredPane = new MainBoard();
        layeredPane.setOpaque(true);
        frame.setContentPane(layeredPane);
 
        //display the window
        frame.pack();
		frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        layeredPane.startGameThread();
	}
}
