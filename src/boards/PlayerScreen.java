package boards;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.*;

import main.MainBoard;

public class PlayerScreen {
    private ArrayList<PlayerBoard> others;
    private JFrame frame = new JFrame();

    
    public PlayerScreen(PlayerBoard p) {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));  


        others = new ArrayList<>();

        for(int i = 0; i < 4; i++) {
            if(i != p.currentPlayer) {
                PlayerBoard temp = new PlayerBoard(p, i+1);
                temp.player.revealAll();
                temp.player.viewOthers.setVisible(false);
                temp.player.end.setVisible(false);
                panel.add(temp);
            }
        }

        frame.add(panel);
        frame.pack();

		frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public JFrame getFrame() {
        return frame;
    }
}
