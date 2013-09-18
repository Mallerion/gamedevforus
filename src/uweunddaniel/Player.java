package uweunddaniel;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;


public class Player extends Sprite
{
    ScrollGame parent;
    Rectangle2D rec = null;
    
    public Player(BufferedImage[] pics, double x, double y, long delay, GamePanel p)
    {
        super(pics, x, y, delay, p);
        parent = (ScrollGame) p;
    }
    
    public void doLogic(long delta)
    {
        super.doLogic(delta);

        rec = parent.getMap().getDisplay();
        
        Point p1 = new Point((int)(getX() + (getWidth() / 2)), (int)getY() + 16); // oben
        Point p2 = new Point((int)(getX() + (getWidth() / 2)), (int)(getY() + getHeight())); // unten
        Point p3 = new Point((int)getX(), (int)(getY() + (getHeight()-4))); // links
        Point p4 = new Point((int)(getX() + getWidth()), (int)(getY() + (getHeight()-4))); // rechts
        
        Color c1 = parent.getMap().getColorForPoint(p1);
        Color c2 = parent.getMap().getColorForPoint(p2);
        Color c3 = parent.getMap().getColorForPoint(p3);
        Color c4 = parent.getMap().getColorForPoint(p4);
        
        if(checkColor(c1))
        {
            parent.getMap().setVisibleRectangle(new Rectangle((int)rec.getX(),(int)(rec.getY() + 1), 480, 480));
            parent.getMap().setVerticalSpeed(0);
        }
        if(checkColor(c2))
        {
            parent.getMap().setVisibleRectangle(new Rectangle((int)rec.getX(),(int)(rec.getY() - 1), 480, 480));
            parent.getMap().setVerticalSpeed(0);
        }
        if(checkColor(c3))
        {
            parent.getMap().setVisibleRectangle(new Rectangle((int)rec.getX() + 1,(int)(rec.getY()), 480, 480));
            parent.getMap().setHorizontalSpeed(0);
        }
        if(checkColor(c4))
        {
            parent.getMap().setVisibleRectangle(new Rectangle((int)rec.getX() - 1,(int)(rec.getY()), 480, 480));
            parent.getMap().setHorizontalSpeed(0);
        }
    }
    
    private boolean checkColor(Color c)
    {
        if(c.equals(Color.BLACK))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    @Override
    public boolean collidedWith(Sprite s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
