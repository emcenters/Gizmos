package control;
import main.*;
import object.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

import boards.*;

public class TurnManager {
    private MainBoard mb;
    private int researchDeckNum;
    private Random rand;

    public TurnManager(MainBoard m) {
        mb = m;

        researchDeckNum = -1;
        rand = new Random(40);
    }
    public void researchPopup() {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        JPanel panel = new JPanel();
        JCheckBox[] deckButtons = new JCheckBox[3];
        JButton confirm = new JButton("CONFIRM");
        panel.setPreferredSize(new Dimension(400, 150));

        confirm.setPreferredSize(new Dimension(100, 20));
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(JCheckBox c: deckButtons) {
                    c.setVisible(false);
                }
                confirm.setVisible(false);
                
                ArrayList<Card> researchCards = mb.gameBoard.getDeck(researchDeckNum).getCards(mb.playerBoard.player.research);
                for(Card c: researchCards) {
                    c.setImage(Card.CARDSIZE);
                    JButton temp = new JButton();
                    temp.add(c);
                    temp.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Card c = (Card) temp.getComponent(0);
                            researchCards.remove(c);
                            for(Card card: researchCards) {
                                card.removeImage();
                            }
                            mb.gameBoard.getDeck(researchDeckNum).addToBottom(researchCards);
                            
                            int result = JOptionPane.showOptionDialog(null, "BUILD OR ARCHIVE?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] {"ARCHIVE", "BUILD"}, null);                    
                            if(result == 0) {
                                mb.turnManager.file(c, true);
                            }
                            else if(result == 1) {
                                mb.turnManager.build(c);
                            }
                            c.removeImage();
                            frame.setVisible(false);                            
                        }
                    });
                    panel.add(temp);
                }
            }
        });
        
        for(int i = 0; i < deckButtons.length; i++) {
            deckButtons[i] = new JCheckBox(getIcon("/card/deck"+(i+1)+".png", 100));
            deckButtons[i].setActionCommand(i+1+"");
            deckButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    researchDeckNum = Integer.parseInt(e.getActionCommand());
                }
            });
            panel.add(deckButtons[i]);
        }
        panel.add(confirm);
        frame.add(panel);

        frame.pack();
        
        mb.add(frame);
    }

    public void build(Card pickedCard) {
        if(pickedCard.getCost() <= mb.playerBoard.player.marbles[pickedCard.getColor()+1]) {
            mb.playerBoard.player.build(pickedCard);
            removeAllListeners();
        } 
    }
    public Marble pick(Marble marble) {
        if(mb.playerBoard.player.notAtMarbleLimit()) {
            mb.playerBoard.player.pick(marble.getColor());
            Marble replaceMarble = new Marble(rand.nextInt(1, 5), marble.getX(), marble.getY());
            replaceMarble.setImage();
            mb.playerBoard.player.updateEnergy();
            removeAllListeners();

            return replaceMarble;
        }
        else {
            return null;
        }
    }
    public void file(Card filedCard, boolean fromResearch) {
        if(mb.playerBoard.player.notAtFileLimit()) {
            if(!fromResearch) {
                Card newDisplayCard = mb.gameBoard.drawFromDeck(filedCard.getCardNum());
                newDisplayCard.setBounds(filedCard.getX(), filedCard.getY(), filedCard.getWidth(), filedCard.getHeight());
                newDisplayCard.setImage(Card.CARDSIZE);
                mb.gameBoard.remove(filedCard);
                mb.gameBoard.add(newDisplayCard);
            }
            mb.playerBoard.player.file(filedCard);
            
            removeAllListeners();
        }
    }
    public void nextPlayer() {
        mb.playerBoard.nextPlayer();
    }

    public ImageIcon getIcon(String filePath, int w) {
        ImageIcon image = new ImageIcon(TurnManager.class.getResource(filePath));
        //resizing image
        Image im = image.getImage();
        Image newimg = im.getScaledInstance(w, w,  java.awt.Image.SCALE_SMOOTH);
        image = new ImageIcon(newimg);
        return image;
    }

    public void removeAllListeners() {
        mb.energyBoard.removeMouseListener(mb.energyBoard);
        mb.gameBoard.removeMouseListener(mb.gameBoard);
        mb.playerBoard.player.end.setEnabled(true);
    }
    public void addAllListeneres() {
        mb.energyBoard.addMouseListener(mb.energyBoard);
        mb.gameBoard.addMouseListener(mb.gameBoard);
    }
}
