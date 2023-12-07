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
        setPreferredSize(new Dimension(1400, 500));
        setBorder(BorderFactory.createTitledBorder("GAME BOARD"));

        main = m;
        mouseClicked = 0;
        addMouseListener(this);
        // mouseHandler = new MouseHandler(m);
    }

    public void setDefaultValues() {
        L1 = new Deck(main, "/card/L1.txt", 4, LX, L1Y, 1);
        L2 = new Deck(main, "/card/L2.txt", 3, LX, L2Y, 37);
        L3 = new Deck(main, "/card/L3.txt", 2, LX, L3Y, 73);
    }

    public void update() {
        // testCard.update();
        // if(ActionBoard.buildClicked || ActionBoard.fileClicked) {
            
        // }
        
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
    public Card drawFromDeck(int cardNum) {
        if(cardNum > 72) {
            return L3.getCards(1).get(0);
        }
        else if(cardNum > 36) {
            return L2.getCards(1).get(0);
        }
        else {
            return L1.getCards(1).get(0);
        }
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

        if(L1.deckCard.getBounds().contains(x, y) || L2.deckCard.getBounds().contains(x, y) || L3.deckCard.getBounds().contains(x, y)) {
            main.turnManager.researchPopup();
        }   
        else if(L1.contains(x, y) != null) {
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
        String[] choices = new String[]{"ARCHIVE", "BUILD"};
        int result = JOptionPane.showOptionDialog(null, "BUILD OR ARCHIVE?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, null);
        System.out.println(result);

        if(result == 0) {
            main.turnManager.file(c, false);
        }
        else if(result == 1) {
            main.turnManager.build(c);
        }
    }
}
