package object;

import main.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import boards.GameBoard;

public class SuperObject extends JLabel{
    protected static ImageIcon image;
    protected int color;

    private int distanceX, distanceY;
    private boolean canMove;

    public SuperObject(int x, int y) {       
        setBounds(x, y, Card.CARDSIZE, Card.CARDSIZE);

        setDefaultValues();
    }

    public void setDefaultValues() {
        distanceX = 0;
        distanceY = 0;

        color = 0;

        canMove = false;
    }
    public int getColor() {
        return color;
    }
    public void setImage(String filePath, int w) {
        image = new ImageIcon(SuperObject.class.getResource(filePath));
        //resizing image
        Image im = image.getImage();
        Image newimg = im.getScaledInstance(w, w,  java.awt.Image.SCALE_SMOOTH);
        image = new ImageIcon(newimg);
        setIcon(image);
    }
    public void setColor(int c) {
        color = c;
    }

    public void update() {
        //for drag if time permits
        // if(mh.isHeld() && mh.getBounds().intersects(getBounds()) && canMove) {
        //     setLocation(mh.getX()-distanceX, mh.getY()-distanceY);
        // }
        // else {
        //     canMove = (mh.getPastClick()==mh.getCurrentClick());
        //     distanceX = mh.getX()-getX();
        //     distanceY = mh.getY()-getY();
        // }
    }

    public boolean contains(int x, int y) {
        if(x <= getX()+getWidth() && x >= getX()) {
            if(y <= getY()+getHeight() && y >= getY()) {
                return true;
            }
        }
        return false;
    }
}
