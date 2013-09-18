package uweunddaniel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class TiledTextFile 
{
    HashMap <URL, BufferedImage> tiles;
    BufferedImage sandbox;
    BufferedImage level;
    int pixel; // auflösung pixel * pixel zB 16*16
    int xtiles;
    int ytiles; 
    int width;
    int height;
    
    public static void main(String[] args)
    {
        TiledTextFile t = new TiledTextFile(1264, 2048, 16);
        
        t.mergeSandbox(t.writeSandboxTile(t.getLevelTiles("pics/Zweite_Testmap.png")));
        t.writeLevelData("level/uwe.txt", t.getTiles("pics/sandbox/sandbox.png"), t.getLevelTiles("pics/Zweite_Testmap.png"));
    }
    
    
    public TiledTextFile(int width, int height, int pixel)
    {
        this.width = width;
        this.height = height;
        this.xtiles = width / pixel;
        this.ytiles = height / pixel;
        this.pixel = pixel;
    }
    
    public Color[] getTileRGB(BufferedImage image)
    {
        Color[] rgb = new Color[image.getWidth() * image.getHeight()];
        
        int count = 0;
        //ColorModel model = image.getColorModel();
        
        //WritableRaster raster = image.getRaster();

        for(int i=0; i<image.getWidth(); i++)
        {
            for(int j=0; j<image.getHeight(); j++)
            {
                //Object dataAlt = raster.getDataElements(i, j, null);
                //int argbAlt = model.getRGB(dataAlt);
                
                //Color c = new Color(image.getRGB(i, j));      //(argbAlt, true);
                
                
                rgb[count] = new Color(image.getRGB(i, j));
                
                count++;
            }
        }
        
        return rgb;
    }
    
    public BufferedImage loadPic(String path)
    {
        URL location = getClass().getClassLoader().getResource(path);
        
        BufferedImage source =  null;
        try {
            source = ImageIO.read(location);
        } catch (IOException ex) {
            System.out.println("image konnte nicht gelesen werden");
        }
        
        return source;
    }
    
    
    public BufferedImage[] getTiles(String path)
    {
        int count = 0;
        
        BufferedImage source = loadPic(path);
        
        /*URL location = getClass().getClassLoader().getResource(path);
        
        BufferedImage source =  null;
        try {
            source = ImageIO.read(location);
        } catch (IOException ex) {
            Logger.getLogger(TiledTextFile.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        int column = source.getWidth() / pixel;
        int row = source.getHeight() / pixel;
        
        /*if(source ==  null)
        {
            try {
                source = ImageIO.read(location);
            } catch (IOException ex) {
                System.out.println("tiles konnten nicht richitg geladen werden " + ex);
                return null;
            }
            
            tiles.put(location, source);
        }*/
        
        int width = pixel;
        int height = pixel;
        
        BufferedImage[] pics = new BufferedImage[column * row];

        for(int n = 0; n < column; n++)
        {
            for(int i = 0; i < row; i++)
            {
                pics[count] = source.getSubimage(n * pixel, i * pixel, pixel, pixel);
                count++;
            }
        }
        
        return pics;
    }
    
    public BufferedImage[] getLevelTiles(String path/*, int width, int height*/)
    {
        //int pixel = this.pixel;
        
        int column = width / pixel;
        int row = height / pixel;
        int count = 0;
        
        BufferedImage source = loadPic(path);
        
        /*URL location = getClass().getClassLoader().getResource(path);
        
        BufferedImage source = null;
        
        try {
            source = ImageIO.read(location);
        } catch (IOException ex) {
            Logger.getLogger(TiledTextFile.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        BufferedImage[] levelTiles = new BufferedImage[column * row];

        for(int i = 0; i < row; i++)
        {
            for(int k = 0; k < column; k++)
            {
                levelTiles[count] = source.getSubimage(k * pixel, i * pixel, pixel, pixel);
                
                count++;
                
                //System.out.println(count);
            }
        }
            //System.out.println(count);
        
        return levelTiles;
    }
    
    public void writeLevelData(String levelDataPath, BufferedImage[] tiles, BufferedImage[] level)
    {
        //System.out.println(xtiles);
        //System.out.println(ytiles);
        
        Color[] color1;
        Color[] color2;
        int count = 0;
        String divide = ",";
        //int check1 = 0;
        
        try {
            //URL location = getClass().getClassLoader().getResource(levelDataPath);
            FileWriter fw = new FileWriter("C:\\Dokumente und Einstellungen\\Mighty Me\\Eigene Dateien\\UweUndDaniel\\src\\level\\uwe.txt");
            //FileWriter fw = new FileWriter;
            BufferedWriter writer =  new BufferedWriter(fw);
            
            for(int i = 0; i < level.length; i++)
            {
                for(int k = 0; k < tiles.length; k++)
                {
                    count = 0;
                    color1 = getTileRGB(level[i]);
                    color2 = getTileRGB(tiles[k]);
                    
                    for(int l = 0; l < pixel * pixel; l++)
                    {
                        if(color1[l].equals(color2[l]))
                        {
                            count++;
                        }
                        else
                        {
                            break;
                        }
                        if(count == 256)
                        {
                            //check1++;
                            //System.out.println(check1);
                            //System.out.println("kachel wird geschrieben");
                            count = 0;
                            //System.out.println(i);
                            writer.write("" + k  + divide + ((i % xtiles) * pixel) + divide + ((i / xtiles) * pixel) + divide + pixel + divide + pixel);
                            writer.newLine();
                            k = 999999999;  // alternative ist i++ k = 0
                        }
                    }
                }
            }
            writer.close();
            
        } catch (IOException ex) {
            System.out.println("file not found ex");
        }
        
        //System.out.println(count);
    }
    
    public ArrayList<BufferedImage> writeSandboxTile(BufferedImage[] level)
    {
        int count = 0;
        Color[] color1, color2;
        BufferedImage[] sandbox;
        ArrayList<BufferedImage> biAsList = new ArrayList<BufferedImage>();
        
        for(int k = 0; k < level.length; k++)
        {
            //System.out.println("kachel nr. " + k);
            
            count = 0;
            
            if(k != 0)
            {
                color1 = getTileRGB(level[k]);
                
                for(int y = 0; y < biAsList.size(); y++)
                {
                    count = 0;
                    color2 = getTileRGB(biAsList.get(y));    

                    for(int l = 0; l < pixel * pixel; l++)
                    {
                        //System.out.println(color1[l] + " < - > " + color2[2]);
                        
                        if(color1[l].equals(color2[l]))
                        {
                            //System.out.println("gleich");

                            count++;
                            
                            if(count == 256)
                            {
                                break;
                            }
                        }
                        else
                        {
                            //System.out.println("stimmt nicht");
                            //System.out.println(count);
                            //listcounter++;
                            if( y == biAsList.size() - 1)
                            {
                                //System.out.println("add");
                                biAsList.add(level[k]);
                                y = biAsList.size() + 5; // abbruch provozieren
                                //listcounter = 0;
                            }
                            break;
                        }
                    }
                    if(count == 256)
                    {   
                        //listcounter = 0;
                        count = 0;
                        break;
                    }
                }
            }
            else
            {
                biAsList.add(level[k]);
            }
        }
        //System.out.println(biAsList.size());
        
        return biAsList;
    }
    
    public void mergeSandbox(ArrayList<BufferedImage> sandboxTiles)
    {
        String dataName = "sandbox";
        String typ = "png";
        //Graphics2D g2d;
        
        BufferedImage sandbox = new BufferedImage(sandboxTiles.size() * pixel, pixel, BufferedImage.TYPE_INT_RGB);
        
        for(int i = 0; i < sandboxTiles.size(); i++)
        {
            sandbox.getGraphics().drawImage(sandboxTiles.get(i), i * pixel, 0, null);
        }
        
        //g2d = sandbox.createGraphics();

        File data = new File("C:\\Dokumente und Einstellungen\\Mighty Me\\Eigene Dateien\\UweUndDaniel\\src\\pics\\sandbox\\" + dataName + ".".concat(typ));
        //File data = new File("pics/sandbox/sandbox.png");
        try {
            ImageIO.write((RenderedImage)sandbox, typ, data);
        } catch (IOException ex) {
            System.out.println("IO exception");
        }
    }
    
    public void mergeSandbox(BufferedImage[] sandboxTiles)
    {
        String dataName = "sandbox";
        String typ = "png";
        //Graphics2D g2d;
        
        BufferedImage sandbox = new BufferedImage(sandboxTiles.length * pixel, pixel, BufferedImage.TYPE_INT_RGB);
        
        for(int i = 0; i < sandboxTiles.length; i++)
        {
            sandbox.getGraphics().drawImage(sandboxTiles[i], i * pixel, 0, null);
        }
        
        //g2d = sandbox.createGraphics();

        File data = new File("C:\\Dokumente und Einstellungen\\Mighty Me\\Eigene Dateien\\UweUndDaniel\\src\\pics\\sandbox\\" + dataName + ".".concat(typ));
        //File data = new File("pics/sandbox/sandbox.png");
        try {
            ImageIO.write((RenderedImage)sandbox, typ, data);
        } catch (IOException ex) {
            System.out.println("IO exception");
        }
    }
}