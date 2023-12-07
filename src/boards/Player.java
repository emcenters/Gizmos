package boards;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import object.Card;
import object.Marble;
import object.SuperObject;

public class Player {
    private PlayerBoard pb;
    private Color dashColor;
    public int num;
    public JButton end = new JButton("END TURN"), viewOthers = new JButton("VIEW OTHER PLAYERS");
    private JLabel energyText, VPText;
    
    public int totalCards = 0;
    public int L3Built = 0;
    public int VP = 0;

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
    public HashMap<Integer, HashMap<Integer, JLabel>> cards;
    //0: upgrade, 1: converter, 2: file, 3: pick, 4: build, 5: file spot, 6: energy ring
    //num of card in the row and then the card
    private final static int OFFSET = 30;

    //linked list of player boards for turns
    private Player next;


    public Player(PlayerBoard pb, int num){
        setDefaultValues();

        // next = n;
        this.pb = pb;
        this.num = num;
        
        if(num == 0) {
            dashColor = new Color(138, 128, 76);
        }
        else {
            dashColor = Color.gray;
        }

        setPoints();   
    }
    public Player(PlayerBoard pb, Player p) {
        this.pb = pb.getPlayerBoard();
        this.dashColor = p.dashColor;
        this.marbles = p.marbles.clone();
        this.end = p.end;
        this.energyText = p.energyText;

        this.totalCards = p.totalCards;
        this.L3Built = p.L3Built;
        this.VP = p.VP;
        this.VPText = p.VPText;

        this.file = p.file.clone();
        this.filedCards = p.filedCards;
        this.research = p.research;
        this.specialUpgrades = p.specialUpgrades.clone();
        this.cards = (HashMap<Integer, HashMap<Integer, JLabel>>) p.cards.clone();
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
        Card starterCard = new Card("0 1 1 0 0 1 6 0", 0, 0, 0);
        starterCard.setImage("/objects/startercard.png", 150);
        HashMap<Integer, JLabel> cardRow = cards.get(2);
        starterCard.setBounds(cardRow.get(0).getX(), y+OFFSET, 150, 150);
        System.out.println(starterCard.contains(400, 100));
        cardRow.put(1, starterCard);
        cards.put(2, cardRow);

        JLabel energyRing = new JLabel(setImage("/objects/energyring.png", 150));
        energyRing.setBounds(x, y, 150, 150);

        energyText = new JLabel("<html>R: "+marbles[2]+"<br/>Y: "+marbles[3]+"<br/>B: "+marbles[4]+"<br/>Bl: "+marbles[5]+"</html>", SwingConstants.CENTER);
        energyText.setBounds(x, y, 150, 150);
        int tempY = y+100;
        VPText = new JLabel("Total Victory Points: "+VP, SwingConstants.CENTER);
        VPText.setBounds(x, tempY, 150, 150);
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

        viewOthers.setBounds(x-50, y+60, 200, 40);
        viewOthers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pb.main.add(new PlayerScreen(pb).getFrame(), 0);
                viewOthers.setEnabled(false);
            }
        });
    }

    public void addComponents() {
        for(Integer i: cards.keySet()) {
            HashMap<Integer, JLabel> cardSet = cards.get(i);
            for(Integer k: cardSet.keySet()) {
                pb.add(cardSet.get(k), 0);
            }
        }
        pb.add(end, 0);
        pb.add(viewOthers, 0);
        pb.add(energyText, 0);
        pb.add(VPText, 0);
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
        viewOthers.setVisible(true);
    }
    public void revealAllForPS() {
        updateEnergy();
        for(Card c: filedCards) {
            pb.add(c);
            c.setVisible(true);
        }
        for(Integer i: cards.keySet()) {
            HashMap<Integer, JLabel> cardSet = cards.get(i);
            for(Integer k: cardSet.keySet()) {
                pb.add(cardSet.get(k));
                cardSet.get(k).setVisible(true);
            }
        }
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
        viewOthers.setVisible(false);
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
        pb.remove(VPText);
        int x = energyText.getX(), y = energyText.getY();
        energyText = new JLabel(("<html>R: "+marbles[2]+"<br/>Y: "+marbles[3]+"<br/>B: "
            +marbles[4]+"<br/>Bl: "+marbles[5]+"</html>"), SwingConstants.CENTER);
        energyText.setBounds(x, y, 150, 150);
        int tempY = VPText.getY();
        VPText = new JLabel("Total Victory Points: "+VP, SwingConstants.CENTER);
        VPText.setBounds(x, tempY, 150, 150);
        pb.add(energyText, -1);
        pb.add(VPText, -1);
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
        if(PlayerBoard.ACTION == 0) {
        	PlayerBoard.ACTION = 1+color;
        	if(c.getLevel() == 2) {
        		PlayerBoard.BUILDL2 = 6;
        	}
        }

        if(filedCards.contains(c)) {
        	PlayerBoard.ACTION = 6;
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
            int loc = c.getType();
            
            JLabel label = cards.get(loc).get(cards.get(loc).size()-1);
            c.setImage(150);
            JLabel temp = c;
            temp.setBounds(label.getX(), label.getY()+OFFSET, 150, 150);
            HashMap<Integer, JLabel> map = cards.get(loc);
            map.put(map.size(), temp);
            cards.put(loc, map);
            pb.add(temp, 0);
        }
    }
    public void pick(int color) {
    	PlayerBoard.ACTION = 7+color;
        marbles[color+1] += 1;
        System.out.println(color+" "+marbles[color+1]);
        marbles[1] += 1;
    }
    public void file(Card c) {
    	PlayerBoard.ACTION = 1;
        HashMap<Integer, JLabel> cardRow = cards.get(5);
        int x = cardRow.get(cardRow.size()-1).getX();
        int y = cardRow.get(cardRow.size()-1).getY();

        if(file[1] > 1) {
            y += 150;
        }
        else {
            y += OFFSET;
        }
        file[1] += 1;

        c.beenUsed = true;
        c.setBounds(x, y, 150, 150);
        c.setImage(150);
        pb.add(c, 0);
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

    public void drawEnergy(int num) {
        Random rand = new Random();
        while(num > 0) {
            Marble marb = new Marble(rand.nextInt(1, 5), 0, 0);
            JOptionPane.showMessageDialog(null, "You drew a "+marb.toString()+" marble!");
            marbles[marb.getColor()+1] += 1;
            num--;
        }
        updateEnergy();
    }
}
