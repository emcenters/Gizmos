package control;
import main.*;
import object.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import boards.*;

public class TurnManager {
    private MainBoard mb;
    private int researchDeckNum;

    public TurnManager(MainBoard m) {
        mb = m;

        researchDeckNum = -1;
    }
    public void researchPopup() {

        JFrame researchFrame = new JFrame();
        JPanel researchPanel = new JPanel();
        JCheckBox[] deckButtons = new JCheckBox[3];
        JButton confirm = new JButton("CONFIRM");
        researchFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        researchFrame.setVisible(true);
        researchFrame.setLocationRelativeTo(null);

        researchPanel.setPreferredSize(new Dimension(400, 150));

        confirm.setPreferredSize(new Dimension(100, 20));
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(JCheckBox c: deckButtons) {
                    c.setVisible(false);
                }
                confirm.setVisible(false);
                
                ArrayList<Card> researchCards = mb.gameBoard.getDeck(researchDeckNum).getCards(mb.player.research);
                for(Card c: researchCards) {
                    c.setImage();
                    JButton temp = new JButton();
                    temp.add(c);
                    temp.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Card c = (Card) temp.getComponent(0);
                            build(c);
                            researchFrame.setVisible(false);
                            // JOptionPane.showConfirmDialog(c, e)
                            mb.actionBoard.actionButtons[4].setEnabled(true);
                        }
                    });
                    researchPanel.add(temp);
                }
            }
        });
        
        for(int i = 0; i < deckButtons.length; i++) {
            deckButtons[i] = new JCheckBox(getIcon("/card/deck"+(i+1)+".jpg", 100));
            deckButtons[i].setActionCommand(i+1+"");
            deckButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    researchDeckNum = Integer.parseInt(e.getActionCommand());
                }
            });
            researchPanel.add(deckButtons[i]);
        }
        researchPanel.add(confirm);

        researchFrame.add(researchPanel);
        researchFrame.pack();
        mb.add(researchFrame);
    }

    public void build(Card pickedCard) {
        if(pickedCard.getCost() > mb.player.marbles[pickedCard.getColor()+1]) {
            mb.actionBoard.reset();
        } 
        else {
            mb.player.build(pickedCard);
            mb.actionBoard.actionButtons[4].setEnabled(true);
        }
    }
    public Marble pick(Marble marble) {
        if(mb.player.notAtMarbleLimit()) {
            mb.player.pick(marble.getColor());
            Marble replaceMarble = new Marble(((int)(Math.random()*4)+1), marble.getX(), marble.getY());
            replaceMarble.setImage();
            mb.actionBoard.actionButtons[4].setEnabled(true);
            return replaceMarble;
        }
        else {
            mb.actionBoard.reset();
            return null;
        }
    }
    public void file(Card filedCard) {
        if(mb.player.notAtFileLimit()) {
            mb.player.file(filedCard);
            mb.gameBoard.remove(filedCard);
            mb.actionBoard.actionButtons[4].setEnabled(true);
        }
        else {
            mb.actionBoard.reset();
        }
    }

    public void research(Deck deck) {
        
    }
    public void nextPlayer() {
        mb.remove(mb.player);
        mb.player = mb.player.getNext();
        mb.add(mb.player);
    }

    public ImageIcon getIcon(String filePath, int w) {
        ImageIcon image = new ImageIcon(SuperObject.class.getResource(filePath));
        //resizing image
        Image im = image.getImage();
        Image newimg = im.getScaledInstance(w, w,  java.awt.Image.SCALE_SMOOTH);
        image = new ImageIcon(newimg);
        return image;
    }
}
