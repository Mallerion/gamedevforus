package uweunddaniel;

import java.awt.image.BufferedImage;


public class ImageControl 
{
    BufferedImage[] tiles;
    BufferedImage[] shadow;
    private static ImageControl instance;
    
    private ImageControl()
    {
        tiles = null;
    }
    
    public static ImageControl getInstance()
    {
        if(instance == null)
        {
            instance = new ImageControl();
        }
        
        return instance;
    }
    
    public void setSourceImage(String path, int column, int row)
    {
        SpriteLib lib = SpriteLib.getInstance();
        tiles = lib.getSprite(path, column, row);
    }
    
    public void setShadowImage(String path, int column, int row)
    {
        SpriteLib lib = SpriteLib.getInstance();
        shadow = lib.getSprite(path, column, row);
    }
    
    public BufferedImage getImageAt(int number)
    {
        return tiles[number];
    }
    
    public BufferedImage getShadowImageAt(int number)
    {
        return shadow[number];
    }
    
    public int getTileWidht(int number)
    {
        return tiles[number].getWidth();
    }
    
    public int getTileHeight(int number)
    {
        return tiles[number].getHeight();
    }
}



