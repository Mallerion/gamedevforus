package uweunddaniel;

import java.awt.Graphics;
import java.awt.Rectangle;


public class Tile extends Rectangle
{
    int image_number;
    ImageControl control;
    
    public Tile(int x, int y, int width, int height, int number)
    {
        super(x, y, width, height);
        image_number = number;
        control = ImageControl.getInstance();
    }
    
    public int getImageNumber()
    {
        return image_number;
    }
    
    public void drawTile(Graphics g)
    {
        g.drawImage(control.getImageAt(image_number), x, y, null);
    }
}
