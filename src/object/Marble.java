package object;

import java.awt.event.MouseListener;

import boards.*;
import main.*;

public class Marble extends SuperObject { 
    public final static int MARBLESIZE = 50;
    private MouseListener mh;

    public Marble(int c, int x, int y) {
        super(x, y);
        // this.mh = mh;

        setColor(c);
        setBounds(x, y, MARBLESIZE, MARBLESIZE);
        // color = c;
    }
    public void setImage() {
        setImage("/marble/marble"+color+".png", MARBLESIZE);
    }

    // public void update() {
        // if(mh.getBounds().intersects(getBounds()) && ActionBoard.pickClicked) {
        //     ActionBoard.pickClicked = false;
        //     MouseHandler.currentObject = this;
        //     System.out.println("PICK");
        // }
    // }
}
