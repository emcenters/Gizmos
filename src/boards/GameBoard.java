package boards;
import main.*;
import object.*;

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class GameBoard extends JLayeredPane{
    private MainBoard main;

    private Deck L1, L2, L3;

    private HashMap<String, Point> deckPoints;

    public GameBoard(MainBoard m, MouseHandler mouseHandler) {
        setPreferredSize(new Dimension(1400, 600));
        setBorder(BorderFactory.createTitledBorder("GAME BOARD"));

        // setDefaultValues();

        main = m;
        addMouseMotionListener(mouseHandler);
        addMouseListener(mouseHandler);
    }

    public void setDefaultValues() {
        L1 = new Deck(main, "/card/L1.txt");
        L2 = new Deck(main, "/card/L2.txt");
        L3 = new Deck(main, "/card/L3.txt");

        deckPoints = new HashMap<>();
    }

    public void setCards() {
        
    }

    public void update() {

    }
}
