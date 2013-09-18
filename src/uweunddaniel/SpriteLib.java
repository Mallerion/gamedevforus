package uweunddaniel;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class SpriteLib 
{
    private static SpriteLib single;
    private static HashMap <URL, BufferedImage> sprites;
    
    public static SpriteLib getInstance()
    {
        if(single == null)
        {
            single = new SpriteLib();
        }
        return single;
    }
    
    private SpriteLib()
    {
        sprites = new HashMap <URL, BufferedImage>();
    }
    
    public BufferedImage getSprite(URL location)
    {
        BufferedImage pic = null;
        
        pic = (BufferedImage) sprites.get(location);
        
        if(pic != null)
        {
            return pic;
        }
        
        try {
            pic = ImageIO.read(location);
        } catch (IOException ex) {
            System.out.println("Image konnte nicht geladen werden " + ex);
            return null;
        }
        
        sprites.put(location, pic);
        
        return pic;
    }
    
    public BufferedImage getSprite(String path)
    {
        BufferedImage pic = null;
        
        URL location = getURLfromStringPath(path);
        
        pic = (BufferedImage) sprites.get(location);
        
        if(pic != null)
        {
            return pic;
        }
        
        try {
            pic = ImageIO.read(location);
        } catch (IOException ex) {
            System.out.println("Image konnte nicht geladen werden " + ex);
            return null;
        }
        
        sprites.put(location, pic);
        
        return pic;
    }
    
    public URL getURLfromStringPath(String path)
    {
        return getClass().getClassLoader().getResource(path);
    }
    
    public BufferedImage[] getSprite(String path, int column, int row)
    {
        URL location = getURLfromStringPath(path);
        
        BufferedImage source =  null;
        
        source = (BufferedImage) sprites.get(location);
        
        if(source ==  null)
        {
            try {
                source = ImageIO.read(location);
            } catch (IOException ex) {
                System.out.println("animation konnte nicht richitg geladen werden " + ex);
                return null;
            }
            
            sprites.put(location, source);
        }
        
        //int width = source.getWidth() / column;
        //int height = source.getHeight() / row;
        
        BufferedImage[] pics = new BufferedImage[column * row];
        int count = 0;
        
        for(int i = 0; i < row; i++)
        {
            for(int n = 0; n < column; n++)
            {
                pics[count] = source.getSubimage(n * (source.getWidth() / column), i * (source.getHeight() / row), source.getWidth() / column, source.getHeight() / row);
                count++;
            }
        }
        
        return pics;
    }
    
    public BufferedImage[] getSprite(URL location, int column, int row)
    {
        BufferedImage source =  null;
        
        source = (BufferedImage) sprites.get(location);
        
        if(source ==  null)
        {
            try {
                source = ImageIO.read(location);
            } catch (IOException ex) {
                System.out.println("animation konnte nicht richitg geladen werden " + ex);
                return null;
            }
            
            sprites.put(location, source);
        }
        
        int width = source.getWidth() / column;
        int height = source.getHeight() / row;
        
        BufferedImage[] pics = new BufferedImage[column * row];
        int count = 0;
        
        for(int i = 0; i < row; i++)
        {
            for(int n = 0; n < column; n++)
            {
                pics[count] = source.getSubimage(n * (source.getWidth() / column), i * (source.getHeight() / row), source.getWidth() / column, source.getHeight() / row);
                count++;
            }
        }
        
        return pics;
    }
    
    
    
    
    
    
    
    
    
    
    
}
