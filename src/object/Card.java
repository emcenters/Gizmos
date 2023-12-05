package object;
import main.*;

import java.util.*;

public class Card extends SuperObject{
    private HashMap<Integer, Integer> triggers, effects, upgrades;
    private int color, cost;
    private int victoryPoints;

    public Card(MainBoard m, String line) {
        super(m);

        triggers = new HashMap<>();
        effects = new HashMap<>();
        upgrades = null;
        color = 0;

        setValues(line);
    }

    public void setValues(String readLine) {
        String[] line = readLine.split(" ");
        //for normal
        if(line[0].equals("0")) {
            //sets color + cost
            color = Integer.parseInt(line[3]);
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
            color = Integer.parseInt(line[2]);
            cost = Integer.parseInt(line[3]);

            //sets upgrades
            int key = 1;
            int index = 4;
            for(int i = Integer.parseInt(line[1]); i > 0; i--) {
                upgrades.put(key++, Integer.parseInt(line[index++]));
            }

            //sets victory points
            victoryPoints = Integer.parseInt(line[index]);
        }
    }
    public boolean isUpgrade() {
        return upgrades==null;
    }
}
