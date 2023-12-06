package boards;
import main.*;
import object.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class PlayerBoard extends JLayeredPane {
    //marbles + file limit
    public int[] marbles, file; //marbles - 0: limit, 1: current amount, 2: #red, 3: #yellow, 4: #blue, 5: #black
    private ArrayList<Card> filedCards;
    //research limit
    private int research;

    //cards
    private ArrayList<Card> cards;

    //points of cards
    private int startingX, startinY;
    private final static int OFFSET = 30;

    //linked list of player boards for turns
    private PlayerBoard next;


    public PlayerBoard(MouseHandler mouseHandler, PlayerBoard n){
        setPreferredSize(new Dimension(1400, 300));
        setBorder(BorderFactory.createTitledBorder("PLAYER DASHBOARD"));

        setDefaultValues();

        next = n;
    }

    public void setDefaultValues() {
        marbles = new int[6];
        file = new int[2];
        filedCards = new ArrayList<>();
        research = 0;

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

    public void build(Card c) {
        int color = c.getColor();
        int cost = c.getCost();
        marbles[color+1] -= cost;

    }
    public void pick(int color) {
        marbles[color] += 1;
    }
    public void file(Card c) {
        filedCards.add(c);
    }
    public void research() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
