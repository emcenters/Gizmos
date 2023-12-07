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
    public static int ACTION = 0;
    public static int BUILDL2 = 0;

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

        addMouseListener(this);
    }
    public PlayerBoard(PlayerBoard p, int num) {
        setPreferredSize(new Dimension(1200, 300));
        setBorder(BorderFactory.createTitledBorder("PLAYER "+(num)));

        player = new Player(this, p.players[num-1]);
        currentPlayer = num-1;
    }

    public void nextPlayer() {
        player.removeAll();
        currentPlayer += 1;
        if(currentPlayer == players.length) {
            currentPlayer = 0;
            checkForWinner();
        }
        player = players[currentPlayer];
        player.revealAll();
        ACTION = 0;
        BUILDL2 = 0;

        main.gameBoard.canBuild = true;
        main.gameBoard.canFile = true;
        main.gameBoard.canResearch = true;
        main.gameBoard.canBuildL1 = true;
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
        System.out.println(x+" "+y);

        if(player.filedCards.size() > 0) {
            Card fileToBuild = null;
            for(Card c: player.filedCards) {
                if(c.contains(x, y)) {
                    fileToBuild = c;
                    String[] choices = new String[]{"BUILD"};
                    int result = JOptionPane.showOptionDialog(null, "BUILD OR ARCHIVE?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
                
                    if(result == 0) {
                        main.turnManager.build(fileToBuild);
                    }
                    break;
                }
            }
        }
        Card card = null;
        for(int i = 0; i < 5; i++) {
            HashMap<Integer, JLabel> cardSet = player.cards.get(i);
            for(Integer k: cardSet.keySet()) {
                if(cardSet.get(k).contains(x, y)) {
                    System.out.println("trig eff");
                	card = (Card) cardSet.get(k);
                    if(!card.beenUsed && (card.triggers.containsValue(PlayerBoard.ACTION) || card.triggers.containsValue(PlayerBoard.BUILDL2))) {
                        card.beenUsed = true;
                        main.turnManager.startEffect(card.effects);
                    }
                	break;
                }
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

    public void checkForWinner() {
        for(int i = 0; i < players.length; i++) {
            if(players[i].L3Built >= 4 || players[i].totalCards >= 16) {
                main.nextGameState();
                main.setWinner(players[i]);
            }
        }
    }
    public PlayerBoard getPlayerBoard() {
        return this;
    }
}
