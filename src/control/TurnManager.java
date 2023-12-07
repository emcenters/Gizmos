package control;
import main.*;
import object.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
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
                                mb.turnManager.file(c, true, 0);
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

    public boolean build(Card pickedCard) {
        if(pickedCard.getCost() <= mb.playerBoard.player.marbles[pickedCard.getColor()+1]) {            
            mb.playerBoard.player.build(pickedCard);
            mb.gameBoard.remove(pickedCard);
            mb.playerBoard.player.updateEnergy();
            removeAllListeners();
            return true;
        } 
        else {
            return false;
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
    public void file(Card filedCard, boolean fromResearch, int num) {
        if(mb.playerBoard.player.notAtFileLimit()) {
            Deck cardDeck = null;
            switch(num) {
                case 1: cardDeck = mb.gameBoard.L1; break;
                case 2: cardDeck = mb.gameBoard.L2; break;
                case 3: cardDeck = mb.gameBoard.L3; break;
            }
            if(!fromResearch) {
                Card newDisplayCard = mb.gameBoard.drawFromDeck(filedCard.getCardNum());
                newDisplayCard.setBounds(filedCard.getX(), filedCard.getY(), filedCard.getWidth(), filedCard.getHeight());
                newDisplayCard.setImage(Card.CARDSIZE);
                mb.gameBoard.remove(filedCard);
                mb.gameBoard.add(newDisplayCard, 0);
                if(num != 0) {
                    cardDeck.shown[cardDeck.getIndex(filedCard)] = newDisplayCard;
                }
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
    
    public void startEffect(HashMap<Integer, Integer> effects) {
    	String message = "Effect: ";
        int num = 0;
    	for(Integer k: effects.keySet()) {
            num = effects.get(k);
    		switch(num) {
    		case 1: message += "Archive Any Card"; break;
    		case 2: message += "Research"; break;
    		case 3: message += "Build a L1 Gizmos for No Cost"; break;
    		case 4: message += "Pick One Energy from the Energy Row"; break;
    		case 5: message += "Pick Two Energies from the Energy Row"; break;
    		case 6: message += "Draw One Random Energy"; break;
    		case 7: message += "Draw Two Random Energies"; break;
    		case 8: message += "Convert to Any Color"; break;
    		case 9: message += "Convert to Two Reds"; break;
    		case 10: message += "Convert to Two Yellows"; break;
    		case 11: message += "Convert One Red to Any Color"; break;
    		case 12: message += "Convert One Yellow to Any Color"; break;
    		case 13: message += "Convert One Blue to Any Color"; break;
    		case 14: message += "Convert One Black to Any Color"; break;
    		case 15: message += "Convert to Two Reds"; break;
    		case 16: message += "Convert to Two Yellows"; break;
    		case 17: message += "Convert to Two Blues"; break;
    		case 18: message += "Convert to Two Blacks"; break;
    		case 19: message += "Add One Victory Point"; break;
    		case 20: message += "Add Two Victory Point"; break;
    		}
        }
        int result = JOptionPane.showOptionDialog(null, message, "EFFECT", JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"YES"}, null);

        if(result == 0) {
            runEffect(num);
        }
        
    }
    
    public void runEffect(int effect) {
    	switch(effect) {
    		case 1: mb.gameBoard.canFile = true; addMouseListenerToGB(); break;
    		case 2: mb.gameBoard.canResearch = true; addMouseListenerToGB(); break;
    		case 3: mb.gameBoard.canBuildL1 = true; addMouseListenerToGB(); break;
    		case 4: mb.energyBoard.grabCap = 1; addMouseListenerToER(); break;
    		case 5: mb.energyBoard.grabCap = 2; addMouseListenerToER(); break;
    		case 6: drawEnergy(1); break;
    		case 7: drawEnergy(2); break;
    		// case 8: message += "Convert to Any Color"; break;
    		// case 9: message += "Convert to Two Reds"; break;
    		// case 10: message += "Convert to Two Yellows"; break;
    		// case 11: message += "Convert One Red to Any Color"; break;
    		// case 12: message += "Convert One Yellow to Any Color"; break;
    		// case 13: message += "Convert One Blue to Any Color"; break;
    		// case 14: message += "Convert One Black to Any Color"; break;
    		// case 15: message += "Convert to Two Reds"; break;
    		// case 16: message += "Convert to Two Yellows"; break;
    		// case 17: message += "Convert to Two Blues"; break;
    		// case 18: message += "Convert to Two Blacks"; break;
    		case 19: addVP(1); break;
    		case 20: addVP(2); break;
    		}
    }
    public void addMouseListenerToGB() {
        mb.gameBoard.addMouseListener(mb.gameBoard);
    }
    public void addMouseListenerToER() {
        mb.energyBoard.addMouseListener(mb.energyBoard);
    }
    public void addVP(int num) {
    	mb.playerBoard.player.VP += num;
    }
    public void drawEnergy(int num) {
        mb.playerBoard.player.drawEnergy(num);
    }
}
