package object;
import main.*;
import boards.*;

import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Card extends SuperObject{
    public final static int CARDSIZE = 100;

    public HashMap<Integer, Integer> triggers, effects, upgrades;
    private int cost;
    private int level;
    private int type;
    //0: upgrade, 1: converter, 2: file, 3: pick, 4: build
    public boolean beenUsed;
    
    private int cardNum;
    private int victoryPoints;

    // private MouseHandler mh;

    public Card(String line, int x, int y, int num) {
        super(x, y);
        // this.mh = mh;

        triggers = new HashMap<>();
        effects = new HashMap<>();
        upgrades = null;
        level = 0;
        cardNum = num;
        beenUsed = false;

        if(line != null) {
            setValues(line);
        }
    }

    public void setValues(String readLine) {
        String[] line = readLine.split(" ");
        //for normal
        if(line[0].equals("0")) {
            //sets color + cost
            setColor(Integer.parseInt(line[3]));
            cost = Integer.parseInt(line[4]);

            //sets triggers
            int key = 1;
            int index = 5;
            for(int i = Integer.parseInt(line[1]); i > 0; i--) {
                triggers.put(key++, Integer.parseInt(line[index++]));
            }

            //sets effects
            key = 1;
            for(int i = Integer.parseInt(line[2]); i > 0; i--) {
                effects.put(key++, Integer.parseInt(line[index++]));
            }

            //sets victory points
            victoryPoints = Integer.parseInt(line[index]);
        }

        //for upgrades
        else {
            upgrades = new HashMap<>();
            //sets color + cost
            setColor(Integer.parseInt(line[2]));
            cost = Integer.parseInt(line[3]);

            //sets upgrades
            int key = 1;
            int index = 4;
            for(int i = Integer.parseInt(line[1]); i > 0; i--) {
                upgrades.put(key++, Integer.parseInt(line[index++]));
            }

            //sets victory points
            if(index != line.length) {
                victoryPoints = Integer.parseInt(line[index]);
            }
            else {
                victoryPoints = -1;
            }
        }
        
        if(cardNum > 72) {
        	level = 3;
        }
        else if(cardNum > 36) {
        	level = 2;
        }
        else {
        	level = 1;
        }
        
        setType();
    }
    public boolean isUpgrade() {
        return upgrades != null;
    }
    public int getCost() {
        return cost;
    }
    public void setCost(int c) {
        cost = c;
    }
    public int getCardNum() {
        return cardNum;
    }
    public int getLevel() {
    	return level;
    }
    public int getType() {
    	return type;
    }
    public void setImage(int w) {
        setImage("/card/"+cardNum+".png", w);
    }
    public void removeImage() {
        image = null;
    }

    public void update() {
        // if(mh.getBounds().intersects(getBounds()) && ActionBoard.pickClicked) {
        //     ActionBoard.pickClicked = false;
        //     MouseHandler.currentObject = this;
        // }
    }
    
    public void setType() {
    	if(isUpgrade()) {
    		type = 0;
    	}
        else {
            int trig = triggers.get(1);
            if(trig > 11) {
                type = 1;
            }
            else if(trig > 7) {
                type = 3;
            }
            else if(trig > 1) {
                type = 4;
            }
            else {
                type = 2;
            }
        }
    }
}
