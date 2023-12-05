package boards;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;

public class BoardSection extends JLayeredPane{


    public BoardSection() {
        setPreferredSize(new Dimension(350, 300));
        setBorder(BorderFactory.createTitledBorder("BUILD"));

    }
}
