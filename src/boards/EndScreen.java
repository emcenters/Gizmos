package boards;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import main.MainBoard;

public class EndScreen extends JLayeredPane{
    private MainBoard main;

    public EndScreen(MainBoard m) {
        main = m;
        setBackground(Color.LIGHT_GRAY);
        setLayout(new FlowLayout(FlowLayout.CENTER, 2000, 40));
        setPreferredSize(new Dimension(1400, 800));

        JLabel winner = new JLabel("Winner is: Player "+main.winner.num);
        winner.setBounds(0, 0, 200, 200);
        add(winner);
    }
}
