package object;
import main.*;
import boards.*;

import java.util.*;

import javax.imageio.ImageIO;

public class Card extends SuperObject{
    public final static int CARDSIZE = 100;

    public HashMap<Integer, Integer> triggers, effects, upgrades;
    private int cost;
    private int cardNum;
    private int victoryPoints;

    // private MouseHandler mh;

    public Card(String line, int x, int y, int num) {
        super(x, y);
        // this.mh = mh;

        triggers = new HashMap<>();
        effects = new HashMap<>();
        upgrades = null;

        setValues(line);
        cardNum = num;
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
    }
    public boolean isUpgrade() {
        return upgrades==null;
    }
    public int getCost() {
        return cost;
    }
    public void setImage() {
        setImage("/card/"+cardNum+".png", CARDSIZE);
    }

    public void update() {
        // if(mh.getBounds().intersects(getBounds()) && ActionBoard.pickClicked) {
        //     ActionBoard.pickClicked = false;
        //     MouseHandler.currentObject = this;
        // }
    }
}
