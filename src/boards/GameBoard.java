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
    public boolean canFile = true, canBuild = true, canResearch = true, canBuildL1 = true;
    // public MouseHandler mouseHandler;

    public Deck L1, L2, L3;

    private int LX = 50, L3Y = 50, L2Y = L3Y+Card.CARDSIZE+OFFSET, L1Y = L2Y+Card.CARDSIZE+OFFSET;
    public final static int OFFSET = 25;

    public GameBoard(MainBoard m) {
        setPreferredSize(new Dimension(1400, 400));
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
        L3.cutDeck();;
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
            if(canResearch)
                main.turnManager.researchPopup();
        }   
        else if(L1.getIndex(x, y) != -1) {
            Card card = L1.shown[L1.getIndex(x, y)];
            performAction(card, 1);
        }
        else if(L2.contains(x, y) != null) {
            Card card = L2.shown[L2.getIndex(x, y)];
            performAction(card, 2);
        }
        else if(L3.contains(x, y) != null) {
            Card card = L3.shown[L3.getIndex(x, y)];
            performAction(card, 3);
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
    public void performAction(Card c, int num) {
        String[] choices = new String[]{"ARCHIVE", "BUILD"};
        int result = JOptionPane.showOptionDialog(null, "BUILD OR ARCHIVE?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, null);
        System.out.println(result);

        if(result == 0) {
            if(canFile) {
                main.turnManager.file(c, false, num);
                deactivateAction();
            }
        }
        else if(result == 1) {
            if(canBuild) {
                if(main.turnManager.build(c)) {
                    deactivateAction();
                }
            }
            else if(canBuildL1 && c.getLevel() == 1) {
                c.setCost(0);
                if(main.turnManager.build(c)) {
                    deactivateAction();
                }
            }
        }
    }

    public void deactivateAction() {
        main.gameBoard.canBuild = false;
        main.gameBoard.canFile = false;
        main.gameBoard.canResearch = false;
        main.gameBoard.canBuildL1 = false;
    }
}
