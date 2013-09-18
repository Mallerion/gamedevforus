
package uweunddaniel;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class Sprite extends Rectangle2D.Double implements Movable, Drawable
{
    long delay;
    long animation = 0;
    GamePanel parent;
    BufferedImage[] pics;
    int currentpic = 0;
    
    protected double dx;
    protected double dy;
    
    int loopFrom;
    int loopTo;
    
    boolean remove;
    
    public Sprite(BufferedImage[] i, double x, double y, long delay, GamePanel p)
    {
        pics = i;
        this.x = x;
        this.y = y;
        this.delay = delay;
        this.width = pics[0].getWidth();
        this.height = pics[0].getHeight();
        parent = p;
        loopFrom = 0;
        loopTo = pics.length - 1;
    }
    
    @Override
    public void drawObjects(Graphics g) 
    {
        g.drawImage(pics[currentpic], (int) x, (int) y, null);
    }
    
    @Override
    public void doLogic(long delta) 
    {
        animation += (delta/1000000);
        
        if(animation > delay)
        {
            animation = 0;
            computeAnimation();
        }
    }
    
    private void computeAnimation()
    {
        currentpic++;
        
        if(currentpic > loopTo)
        {
            currentpic = loopFrom;
        }
    }
    
    public void setLoop(int from, int to)
    {
        loopFrom = from;
        loopTo = to;
        currentpic = from;
    }
    
    @Override
    public void move(long delta) 
    {
        if(dx != 0)
        {
            x += dx*(delta/1e9);
        }
        
        if(dy != 0)
        {
            y += dy*(delta/1e9);
        }
    }
    
    public double getHorizontalSpeed()
    {
        return dx;
    }
    
    public void setHorizontalSpeed(double dx)
    {
        this.dx = dx;
    }
    
    public double getVerticalSpeed()
    {
        return dy;
    }
    
    public void setVerticalSpeed(double dy)
    {
        this.dy = dy;
    }
    
    public abstract boolean collidedWith(Sprite s);
    
    public boolean checkOpaqueColorCollisions(Sprite s)
    {
        Rectangle2D.Double cut = (Double) this.createIntersection(s);
        
        if((cut.width < 1) || (cut.height < 1))
        {
            return false;
        }
        
        Rectangle2D.Double subMe = getSubRec(this, cut);
        Rectangle2D.Double subHim = getSubRec(s, cut);
        
        BufferedImage imgMe = pics[currentpic].getSubimage((int) subMe.x, (int) subMe.y, (int) subMe.width, (int) subMe.height);
        BufferedImage imgHim = s.pics[currentpic].getSubimage((int) subHim.x, (int) subHim.y, (int) subHim.width, (int) subHim.height);
        
        for(int i = 0; i < imgMe.getWidth(); i++)
        {
            for(int n = 0; n < imgHim.getHeight(); n++)
            {
                int rgb1 = imgMe.getRGB(i, n);
                int rgb2 = imgHim.getRGB(i, n);
                
                if(isOpaque(rgb1) && isOpaque(rgb2))
                {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    protected Rectangle2D.Double getSubRec(Rectangle2D.Double source, Rectangle2D.Double part)
    {
        Rectangle2D.Double sub = new Rectangle2D.Double();
        
        if(source.x > part.x)
        {
            sub.x = 0;
        }
        else
        {
            sub.x = part.x - sub.x;
        }
        
        if(source.y > part.y)
        {
            sub.y = 0;
        }
        else
        {
            sub.y = part.y - sub.y;
        }
        
        sub.width = part.width;
        sub.height = part.height;
        
        return sub;
    }
    
    protected boolean isOpaque(int rgb)
    {
        int alpha = (rgb >> 24) & 0xff;
        
        if(alpha == 0)
        {
            return false;
        }
        
        return true;
    }
    
}
