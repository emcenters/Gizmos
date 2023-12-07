package boards;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLayeredPane;

public class EndScreen extends JLayeredPane{

    public EndScreen() {
        setBackground(Color.LIGHT_GRAY);
        setLayout(new FlowLayout(FlowLayout.CENTER, 2000, 40));
        setPreferredSize(new Dimension(1400, 800));
    }
}
