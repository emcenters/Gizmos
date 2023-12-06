package boards;
import main.*;
import object.Marble;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class EnergyBoard extends JLayeredPane{
    private MainBoard main;
    private MouseHandler mh;
    
    private Marble[] marbleRow;
    private int startX = 25, startY = 25;
    private int OFFSET = 25;

    public EnergyBoard(MainBoard m, MouseHandler mh) {
        setPreferredSize(new Dimension(1400, 100));
        setBorder(BorderFactory.createTitledBorder("ENERGY ROW"));

        main = m;
        this.mh = mh;
        addMarbles();
    }

    public void addMarbles() {
        marbleRow = new Marble[6];
        Random rand = new Random((int)Math.random());

        for(int i = 0; i < marbleRow.length; i++) {
            marbleRow[i] = new Marble(main, rand.nextInt(1, 5), startX, startY);
            marbleRow[i].setImage();
            add(marbleRow[i]);
            startX += OFFSET+Marble.MARBLESIZE;
        }
    }

    public void update() {
        for(Marble m: marbleRow) {
            m.update();
        }
        if(ActionBoard.pickClicked) {
            ActionBoard.pickClicked = false;
            if(main.player.notAtMarbleLimit()) {
                addMouseListener(mh);
                addMouseMotionListener(mh);
            } 
            else {
                main.actionBoard.reset();
            }
        }
        else if(getMouseListeners().length != 0){
            removeMouseListener(mh);
            removeMouseMotionListener(mh);
        }
    }
}
