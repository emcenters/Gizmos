package object;

import main.*;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class SuperObject extends JLabel{
    private static ImageIcon image;

    private MainBoard lp;
    private MouseHandler mh;
    private int distanceX, distanceY;
    private boolean canMove;

    public SuperObject(MainBoard l) {
        lp = l;
        mh = lp.mouseHandler;
        try {
            image = new ImageIcon(ImageIO.read(SuperObject.class.getResource("/card/pinkSquare.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        } 
        setIcon(image);
        setBounds(15, 225, 200, 200);

        setDefaultValues();
    }
    public SuperObject(int x, int y) {
        setDefaultValues();
        setLocation(x, y);
    }

    public void setDefaultValues() {
        distanceX = 0;
        distanceY = 0;

        canMove = false;
    }

    public void update() {
        if(mh.isHeld() && mh.getBounds().intersects(getBounds()) && canMove) {
            setLocation(mh.getX()-distanceX, mh.getY()-distanceY);
        }
        else {
            canMove = (mh.getPastClick()==mh.getCurrentClick());
            distanceX = mh.getX()-getX();
            distanceY = mh.getY()-getY();
        }
    }
}
