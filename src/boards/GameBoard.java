package boards;
import main.*;
import object.*;

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class GameBoard extends JLayeredPane{
    private MainBoard main;
    private MouseHandler mh;

    private Deck L1, L2, L3;

    private int LX = 50, L3Y = 50, L2Y = L3Y+Card.CARDSIZE+OFFSET, L1Y = L2Y+Card.CARDSIZE+OFFSET;
    public final static int OFFSET = 25;

    public GameBoard(MainBoard m, MouseHandler mouseHandler) {
        setPreferredSize(new Dimension(1400, 500));
        setBorder(BorderFactory.createTitledBorder("GAME BOARD"));

        main = m;
    }

    public void setDefaultValues() {
        L1 = new Deck(main, "/card/L1.txt", 4, LX, L1Y);
        L2 = new Deck(main, "/card/L1.txt", 3, LX, L2Y);
        L3 = new Deck(main, "/card/L1.txt", 2, LX, L3Y);
    }

    public void update() {
        // testCard.update();
        if(ActionBoard.buildClicked) {
            ActionBoard.buildClicked = false;
            addMouseMotionListener(mh);
            addMouseListener(mh);
        }
        else if(ActionBoard.fileClicked) {
            ActionBoard.fileClicked = false;
            if(main.player.notAtFileLimit()) {
                addMouseMotionListener(mh);
                addMouseListener(mh);
            }
        }
        else if(ActionBoard.researchClicked) {
            ActionBoard.researchClicked = false;
            addMouseMotionListener(mh);
            addMouseListener(mh);
        }
        else if(getMouseListeners().length != 0) {
            removeMouseMotionListener(mh);
            removeMouseListener(mh);
        }
        L1.update();
        L2.update();
        L3.update();
    }
}
