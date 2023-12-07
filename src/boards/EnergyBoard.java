package boards;
import main.*;
import object.Marble;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class EnergyBoard extends JLayeredPane implements MouseListener{
    private MainBoard main;
    // private MouseHandler mouseHandler;
    public int grabCap = 1;
    
    private Marble[] marbleRow;
    private Random rand;
    private int startX = 25, startY = 25;
    private int OFFSET = 25;

    public EnergyBoard(MainBoard m) {
        setPreferredSize(new Dimension(1400, 100));
        setBorder(BorderFactory.createTitledBorder("ENERGY ROW"));

        main = m;
        rand = new Random((int)Math.random());
        // mouseHandler = new MouseHandler(m);
        addMarbles();
        addMouseListener(this);
    }

    public void addMarbles() {
        marbleRow = new Marble[6];

        for(int i = 0; i < marbleRow.length; i++) {
            // marbleRow[i] = new Marble(mouseHandler, rand.nextInt(1, 5), startX, startY);
            marbleRow[i] = new Marble(rand.nextInt(1, 5), startX, startY);

            marbleRow[i].setImage();
            add(marbleRow[i]);
            startX += OFFSET+Marble.MARBLESIZE;
        }
    }

    public void update() {
        for(Marble m: marbleRow) {
            m.update();
        }
        // if(ActionBoard.pickClicked) {
        //     ActionBoard.pickClicked = false;
            // if(main.playerBoard.player.notAtMarbleLimit() && addedListener == 0) {
            //     addedListener++;
            //     // addMouseMotionListener(mouseHandler);
            // } 
            // else if(!main.playerBoard.player.notAtMarbleLimit()){
                // main.actionBoard.reset();
            // }
        // }
        // else if(getMouseListeners().length != 0){
        //     // removeMouseListener(mouseHandler);
        //     // removeMouseMotionListener(mouseHandler);
        // }
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
        // TODO Auto-generated method stub
        int x = e.getX();
        int y = e.getY();
        Rectangle bounds = new Rectangle(x, y, 20, 34);

        for(int i = 0; i < marbleRow.length; i++) {
            if(bounds.intersects(marbleRow[i].getBounds()) && grabCap > 0) {
                Marble replace = main.turnManager.pick(marbleRow[i]);
                if(replace != null) {
                    grabCap--;
                    remove(marbleRow[i]);
                    marbleRow[i] = replace;
                    add(replace);
                }
                break;
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
}
