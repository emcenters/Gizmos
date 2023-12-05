package main;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

public class MouseHandler implements MouseInputListener{
    private Rectangle mouseBounds;
    private Color mouseColor;
    private int x, y;

    private boolean mouseHeld;
    private int pastClick, currentClick;

    public MouseHandler() {
        mouseBounds = new Rectangle(26, 34);
        mouseColor = Color.green;
        mouseHeld = false;

        pastClick = 0;
        currentClick = 0;
    }

    public void paint(Graphics2D g) {
        g.setColor(mouseColor);
        g.draw(mouseBounds);
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


    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseColor = Color.red;
        update(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseColor = Color.green;
        update(e);
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
