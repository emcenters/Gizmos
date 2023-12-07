package boards;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import object.Card;
import object.SuperObject;

public class Player {
    private PlayerBoard pb;
    private Color dashColor;
    public JButton end = new JButton("END TURN");
    private JLabel energyText;

    //marbles + file limit
    public int[] marbles, file; //marbles - 0: limit, 1: current amount, 2: #red, 3: #yellow, 4: #blue, 5: #black
    public ArrayList<Card> filedCards;
    //research limit
    public int research;
    private int[] specialUpgrades;
    /*0. Remove 1 Cost on Build from Archive		
     1. Remove 1 Cost on Build from Research		
     2. Remove 1 Cost on Build L2 Gizmos		
     3. Convert 1 Victory Point to 3		
     4. Convert 1 Energy to 1 Point		 */

    //cards
    // private ArrayList<Card> cards;

    //cards
    private HashMap<Integer, HashMap<Integer, JLabel>> cards;
    //0: upgrade, 1: converter, 2: file, 3: pick, 4: build, 5: file spot, 6: energy ring
    //num of card in the row and then the card
    private final static int OFFSET = 30;

    //linked list of player boards for turns
    private Player next;


    public Player(PlayerBoard pb, int num){
        setDefaultValues();

        // next = n;
        this.pb = pb;
        
        if(num == 0) {
            dashColor = new Color(138, 128, 76);
        }
        else {
            dashColor = Color.gray;
        }

        setPoints();   
    }

    public void setDefaultValues() {
        marbles = new int[] {5, 0, 0, 0, 0, 0};
        file = new int[] {1, 0};
        filedCards = new ArrayList<>();
        research = 3;
        specialUpgrades = new int[5];
    }
    public void setPoints() {
        int x = 25, y = 20;
        String[] names = new String[] {"UPGRADE", "CONVERTER", "FILE", "PICK", "BUILD", "FILE SPOT"};

        cards = new HashMap<>();
        for(int i = 0; i < 6; i++) {
            HashMap<Integer, JLabel> temp = new HashMap<>();
            JLabel label = new JLabel(names[i], SwingConstants.CENTER);
            label.setBounds(x, y, 150, 40);
            label.setBorder(BorderFactory.createLineBorder(Color.black));
            label.setOpaque(true);
            label.setBackground(dashColor);
            x += label.getWidth()+15;
            temp.put(0, label);

            cards.put(i, temp);
        }
        JLabel starterCard = new JLabel(setImage("/objects/startercard.png", 150));
        HashMap<Integer, JLabel> cardRow = cards.get(2);
        starterCard.setBounds(cardRow.get(0).getX(), y+OFFSET, 150, 150);
        cardRow.put(1, starterCard);
        cards.put(2, cardRow);

        JLabel energyRing = new JLabel(setImage("/objects/energyring.png", 150));
        energyRing.setBounds(x, y, 150, 150);

        energyText = new JLabel("<html>R: "+marbles[2]+"<br/>Y: "+marbles[3]+"<br/>B: "+marbles[4]+"<br/>Bl: "+marbles[5]+"</html>", SwingConstants.CENTER);
        energyText.setBounds(x, y, 150, 150);
        x += 200;
        HashMap<Integer, JLabel> energyMap = new HashMap<>();
        energyMap.put(0, energyRing);
        cards.put(6, energyMap);

        end.setBounds(x, y, 100, 40);
        end.setEnabled(false);
        end.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pb.main.turnManager.addAllListeneres();
                pb.main.turnManager.nextPlayer();
                end.setEnabled(false);
            }
        });
    }

    public void addComponents() {
        for(Integer i: cards.keySet()) {
            HashMap<Integer, JLabel> cardSet = cards.get(i);
            for(Integer k: cardSet.keySet()) {
                pb.add(cardSet.get(k));
            }
        }
        pb.add(end);
        pb.add(energyText);
    }

    public void revealAll() {
        updateEnergy();
        for(Card c: filedCards) {
            c.setVisible(true);
        }
        for(Integer i: cards.keySet()) {
            HashMap<Integer, JLabel> cardSet = cards.get(i);
            for(Integer k: cardSet.keySet()) {
                cardSet.get(k).setVisible(true);
            }
        }
        end.setVisible(true);
    }
    public void removeAll() {
        for(Card c: filedCards) {
            c.setVisible(false);
        }
        for(Integer i: cards.keySet()) {
            HashMap<Integer, JLabel> cardSet = cards.get(i);
            for(Integer k: cardSet.keySet()) {
                cardSet.get(k).setVisible(false);
            }
        }
        end.setVisible(false);
        energyText.setVisible(false);
    }

    public void update() {
        //implement chain
    }

    public Player getNext() {
        return next;
    }
    public void setNext(Player n) {
        next = n;
    }
    public void updateEnergy() {
        pb.remove(energyText);
        int x = energyText.getX(), y = energyText.getY();
        energyText = new JLabel(("<html>R: "+marbles[2]+"<br/>Y: "+marbles[3]+"<br/>B: "
            +marbles[4]+"<br/>Bl: "+marbles[5]+"</html>"), SwingConstants.CENTER);
        energyText.setBounds(x, y, 150, 150);
        pb.add(energyText);
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

        if(filedCards.contains(c)) {
            filedCards.remove(c);
            pb.remove(c);
            build(c);
        }
        else if(c.isUpgrade()) {
            for(Integer i: c.upgrades.keySet()) {
                int upgNum = c.upgrades.get(i);
                switch(upgNum) {
                    case 1: marbles[0] += 1;
                        break;
                    case 2: marbles[0] += 2;
                        break;
                    case 3: marbles[0] += 4;
                        break;
                    case 4: research += 1;
                        break;
                    case 5: research += 2;
                        break;
                    case 6: file[0] += 1;
                        break;
                    case 7: specialUpgrades[0] += 1;
                        break;
                    case 8: specialUpgrades[0] += 1;
                        break;
                    case 9: specialUpgrades[0] += 1;
                        break;
                    case 10: file[0] = 0;
                        break;
                    case 11: research = 0;
                        break;
                }
            }
        }
        else {

        }
    }
    public void pick(int color) {
        marbles[color+1] += 1;
        System.out.println(color+" "+marbles[color+1]);
        marbles[1] += 1;
    }
    public void file(Card c) {
        HashMap<Integer, JLabel> cardRow = cards.get(5);
        int x = cardRow.get(cardRow.size()-1).getX();
        int y = cardRow.get(cardRow.size()-1).getY();

        y += OFFSET;
        file[1] += 1;

        c.setBounds(x, y, 150, 150);
        c.setImage(150);
        pb.add(c);
        filedCards.add(c);
    }

    public ImageIcon setImage(String filePath, int w) {
        ImageIcon image = new ImageIcon(SuperObject.class.getResource(filePath));
        //resizing image
        Image im = image.getImage();
        Image newimg = im.getScaledInstance(w, w,  java.awt.Image.SCALE_SMOOTH);
        image = new ImageIcon(newimg);
        return image;
    }
}
