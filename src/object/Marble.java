package object;

import main.MainBoard;

public class Marble extends SuperObject { 
    public final static int MARBLESIZE = 50;
    private int color;

    public Marble(MainBoard m, int c, int x, int y) {
        super(m, x, y);

        setColor(c);
        setBounds(x, y, MARBLESIZE, MARBLESIZE);
        color = c;
    }
    public void setImage() {
        setImage("/marble/marble"+color+".png", MARBLESIZE);
    }

    public void update() {
        super.update();
    }
}
