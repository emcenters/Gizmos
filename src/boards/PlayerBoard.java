package boards;
import main.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class PlayerBoard extends JLayeredPane {
    //linked list of player boards
    private PlayerBoard next;

    //points of cards
    private int startingX, startinY;
    private final static int OFFSET = 30;

    public PlayerBoard(MouseHandler mouseHandler, PlayerBoard n){
        setPreferredSize(new Dimension(1400, 300));
        setBorder(BorderFactory.createTitledBorder("PLAYER DASHBOARD"));

        add(new BoardSection());

        addMouseMotionListener(mouseHandler);
        addMouseListener(mouseHandler);

        next = n;
    }

    public void update() {

    }

    public PlayerBoard getNext() {
        return next;
    }
    public void setNext(PlayerBoard n) {
        next = n;
    }
}
