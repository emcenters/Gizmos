package boards;
import main.*;
import object.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import java.util.*;

public class GameBoard extends JLayeredPane implements MouseListener{
    private MainBoard main;
    private int mouseClicked;
    // public MouseHandler mouseHandler;

    private Deck L1, L2, L3;

    private int LX = 50, L3Y = 50, L2Y = L3Y+Card.CARDSIZE+OFFSET, L1Y = L2Y+Card.CARDSIZE+OFFSET;
    public final static int OFFSET = 25;

    public GameBoard(MainBoard m) {
        setPreferredSize(new Dimension(1400, 300));
        setBorder(BorderFactory.createTitledBorder("GAME BOARD"));

        main = m;
        mouseClicked = 0;
        // mouseHandler = new MouseHandler(m);
    }

    public void setDefaultValues() {
        L1 = new Deck(main, "/card/L1.txt", 4, LX, L1Y);
        L2 = new Deck(main, "/card/L1.txt", 3, LX, L2Y);
        L3 = new Deck(main, "/card/L1.txt", 2, LX, L3Y);
    }

    public void update() {
        // testCard.update();
        if(ActionBoard.buildClicked || ActionBoard.fileClicked) {
            addMouseListener(this);
            mouseClicked++;
        }
        
        L1.update();
        L2.update();
        L3.update();
    }

    public Deck getDeck(int num) {
        switch(num) {
            case 1: return L1;
            case 2: return L2;
            case 3: return L3;
        }
        return null;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        int x = e.getX();
        int y = e.getY();

        if(L1.contains(x, y) != null) {
            Card card = L1.contains(x, y);
            performAction(card);
        }
        else if(L2.contains(x, y) != null) {
            Card card = L2.contains(x, y);
            performAction(card);
        }
        else if(L3.contains(x, y) != null) {
            Card card = L3.contains(x, y);
            performAction(card);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }
    public void performAction(Card c) {
        if(ActionBoard.buildClicked) {
            main.turnManager.build(c);
        }
        else if(ActionBoard.fileClicked) {
            main.turnManager.file(c);
        }
    }
}
