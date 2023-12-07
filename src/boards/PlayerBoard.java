package boards;
import main.*;
import object.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

public class PlayerBoard extends JLayeredPane implements MouseListener{
    public MainBoard main;
    private static int ACTION = 0;

    public Player[] players;
    public int currentPlayer = 0;
    public Player player;

    public PlayerBoard(MainBoard m){
        main = m;
        setPreferredSize(new Dimension(1400, 300));
        setBorder(BorderFactory.createTitledBorder("PLAYER DASHBOARD"));

        // Player first = new Player(this, 1, new Player(this, 2, null));
        // player = first;
        // player = player.getNext();
        // for(int i = 2; i < 4; i++) {
        //     player.setNext(new Player(this, (i+1), null));
        //     player = player.getNext();
        // }
        // player.setNext(first);
        // player = player.getNext();

        players = new Player[4];
        for(int i = 0; i < players.length; i++) {
            players[i] = new Player(this, i);
            player = players[i];
            setBoard();
        }
        player = players[currentPlayer];
        player.revealAll();
    }

    public void nextPlayer() {
        currentPlayer++;
        if(currentPlayer == players.length) {
            currentPlayer = 0;
        }
        player.removeAll();
        player = players[currentPlayer];
        player.revealAll();
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
        int x = e.getX();
        int y = e.getY();

        if(player.filedCards.size() > 0) {
            Card fileToBuild = null;
            for(Card c: player.filedCards) {
                if(c.contains(x, y)) {
                    fileToBuild = c;
                    break;
                }
            }

            String[] choices = new String[]{"BUILD"};
            int result = JOptionPane.showOptionDialog(null, "BUILD OR ARCHIVE?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
        
            if(result == 0) {
                main.turnManager.build(fileToBuild);
            }
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

    public void setBoard() {
        player.addComponents();
        player.removeAll();
    }
}
