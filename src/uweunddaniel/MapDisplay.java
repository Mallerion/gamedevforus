package uweunddaniel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


public class MapDisplay extends Rectangle
{
    ImageControl control;
    Vector<Tile> tiles;
    ScrollGame parent;
    Rectangle2D display;
    
    double dx = 0;
    double dy = 0;
    int column = 0;
    int row = 0;
    int pixel = 16;

    public MapDisplay(String level, String picpath, String shadowpath, GamePanel p)
    {
        tiles = new Vector<Tile>();
        parent = (ScrollGame) p;
        
        loadLevelData(level);
        
        BufferedImage source = null;
        URL location = getClass().getClassLoader().getResource(picpath);
        try {
            source = ImageIO.read(location);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        this.column = source.getWidth() / pixel;
        this.row = source.getHeight() / pixel;
        
        control = ImageControl.getInstance();
        control.setSourceImage(picpath, column, row);
        
        location = getClass().getClassLoader().getResource(shadowpath);
        try {
            source = ImageIO.read(location);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.column = source.getWidth() / pixel;
        this.row = source.getHeight() / pixel;
        
        control.setShadowImage(shadowpath, column, row);
        display = new Rectangle2D.Double(0, 0, parent.getWidth(), parent.getHeight());
    }
    
    private void loadLevelData(String level)
    {
        try
        {
            InputStreamReader isr = new InputStreamReader(getClass().getClassLoader().getResourceAsStream(level));
            BufferedReader bufreader = new BufferedReader(isr);
        
            String line = null;
        
            do
            {
                line = bufreader.readLine();
            
                if(line == null)
                {
                    continue;
                }
            
                String split[] = line.split(",");
                int num = Integer.parseInt(split[0]);
                int posx = Integer.parseInt(split[1]);
                int posy = Integer.parseInt(split[2]);
                int width = Integer.parseInt(split[3]);
                int height = Integer.parseInt(split[4]);
                
                if((posx + width) > this.width)
                {
                    this.width = posx + width;
                }
                if((posy + height) > this.height)
                {
                    this.height = posy + height;
                }
            
                Tile t = new Tile(posx, posy, width, height, num);
                tiles.add(t);
            }
            while(line != null);
        
            bufreader.close();
            isr.close();    
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        
    }
    
    public void drawVisibleMap(Graphics g)
    {
        Tile t;
        
        for(int i = 0; i < tiles.size(); i++)//Tile t : tiles)
        {
            t = tiles.get(i);
            
            if(t.intersects(display))
            {
                double dx = t.x - display.getX();
                double dy = t.y - display.getY();
                
                g.drawImage((Image)control.getImageAt(t.getImageNumber()), (int) dx, (int) dy, null);
            }
        }
    }
    
    public void setVisibleRectangle(Rectangle2D rec)
    {
        display = rec;
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

    public void moveVisibleRectangle(long delta) 
    {
        double x = display.getX();
        double y = display.getY();
        
        if(dx != 0)
        {
            x += dx*(delta/1e9);
        }
        if(dy != 0)
        {
            y += dy*(delta/1e9);
        }
        if((x + display.getWidth()) > width)
        {
            x = width - display.getWidth();
        }
        if((y + display.getHeight()) > height)
        {
            y = height - display.getHeight();
        }
        if(x < 0)
        {
            x = 0;
        }
        if(y < 0)
        {
            y = 0;
        }
        display.setRect(x, y, display.getWidth(), display.getHeight());
    }
    
    public Color getColorForPoint(Point p)
    {
        for(int i = 0; i < tiles.size(); i++)
        {
            double dx = tiles.get(i).x - display.getX();
            double dy = tiles.get(i).y - display.getY();
            
            Rectangle temp2 = new Rectangle((int)dx, (int)dy, (int)tiles.get(i).getWidth(), (int)tiles.get(i).getHeight());
            if(temp2.contains(p))
            {
                int px = (int)(p.x - dx);
                int py = (int)(p.y - dy);
                
                Color c = new Color(ImageControl.getInstance().getShadowImageAt(i).getRGB(px, py));
                return c;
            }
        }
        
        return null;
    }
    
    public Rectangle2D getDisplay()
    {
        return display;
    }
}