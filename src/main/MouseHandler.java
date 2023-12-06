package main;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

import object.SuperObject;

public class MouseHandler implements MouseInputListener{
    public static SuperObject currentObject = null;

    private MainBoard mb;

    private Rectangle mouseBounds;
    private Color mouseColor;
    private int x, y;

    private boolean mouseHeld, mouseClick;
    private int pastClick, currentClick;

    public MouseHandler(MainBoard m) {
        mouseBounds = new Rectangle(26, 34);
        mouseColor = Color.green;
        mouseHeld = false;

        pastClick = 0;
        currentClick = 0;

        mb = m;
    }

    public Rectangle getBounds() {
        return mouseBounds;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getPastClick() {
        return pastClick;
    }
    public int getCurrentClick() {
        return currentClick;
    }

    public boolean isHeld() {
        return mouseHeld;
    }
    public boolean isMouseClick() {
        return mouseClick;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseColor = Color.red;
        mouseClick = true;
        update(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseColor = Color.green;
        mouseClick = false;
        update(e);
        mb.update();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseHeld = true;
        update(e);
        pastClick = currentClick;
        currentClick++;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseHeld = false;
        update(e);

        pastClick = 0;
        currentClick = 0;
    }
    
    public void update(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        mouseBounds.setLocation(x-10, y-12);
    }
}
