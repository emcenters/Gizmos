package boards;
import main.*;
import object.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

public class PlayerBoard extends JLayeredPane implements MouseListener{
    private static int ACTION = 0;

    //marbles + file limit
    public int[] marbles, file; //marbles - 0: limit, 1: current amount, 2: #red, 3: #yellow, 4: #blue, 5: #black
    private ArrayList<Card> filedCards;
    //research limit
    public int research;

    //cards
    private ArrayList<Card> cards;

    //points of cards
    private int startingX, startinY;
    private final static int OFFSET = 30;

    //linked list of player boards for turns
    private PlayerBoard next;


    public PlayerBoard(int num, PlayerBoard n){
        setPreferredSize(new Dimension(1400, 300));
        setBorder(BorderFactory.createTitledBorder("PLAYER "+num+" DASHBOARD"));

        setDefaultValues();

        next = n;
    }

    public void setDefaultValues() {
        marbles = new int[] {5, 0, 0, 0, 0, 0};
        file = new int[] {1, 0};
        filedCards = new ArrayList<>();
        research = 3;
    }

    public void addComponents() {

    }

    public void update() {
        //implement chain
    }

    public PlayerBoard getNext() {
        return next;
    }
    public void setNext(PlayerBoard n) {
        next = n;
    }

    public boolean notAtMarbleLimit() {
        return marbles[0] != marbles[1];
    }
    public boolean notAtFileLimit() {
        return file[0] != file[1];
    }
    public int getTotalMarbles() {
        return marbles[1];
    }

    public void build(Card c) {
        int color = c.getColor();
        int cost = c.getCost();
        marbles[color+1] -= cost;

        if(c.isUpgrade()) {
            for(Integer i: c.upgrades.keySet()) {
                c.upgrades.get(i);
                
            }
        }
        else {

        }
    }
    public void pick(int color) {
        marbles[color] += 1;
        marbles[1] += 1;
    }
    public void file(Card c) {
        filedCards.add(c);
    }
    public void research() {
        
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
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }
}
