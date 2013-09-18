package uweunddaniel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ListIterator;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public abstract class GamePanel extends JPanel implements KeyListener, Runnable, ActionListener
{

    JFrame frame;
    
    protected boolean gameRunning = true;
    protected boolean started = false;
    boolean once;
    
    protected long delta = 0;
    protected long last = 0;
    protected long fps = 0;
    public long gameover = 0;
    
    protected boolean up = false;
    protected boolean down = false;
    protected boolean left = false;
    protected boolean right = false;
    
    BufferedImage load;
    
    int width = 0;
    int height = 0;

    //Timer timer;
    
    public GamePanel(int w, int h)
    {
        this.setPreferredSize(new Dimension(w, h));
        frame = new JFrame("Game Demo");
        frame.setLocation(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.addKeyListener(this);
        frame.pack(); // setzt die größe des fenster auf prefered size, ansonsten ist das fenster trotz angegebener größe auf minimal größe
        frame.setVisible(true);
        
        this.width = w;
        this.height = h;
        
        doInitializations();
    }

    protected void doInitializations()
    {
        load = loadPics("pics/load.jpg", 1)[0];
        //timer = new Timer(3000, this);
        //timer.start();
        
        if(!once)
        {
            once = true;
            Thread th = new Thread(this);
            th.start();
        }
    }
    
    @Override
    public void run() 
    {
        while(gameRunning)
        {
            computeDelta();
        
            if(isStarted() && (load == null))
            {
                checkKeys();
                doLogic();
                moveObjects();
            }
            
            repaint();
            try {
                Thread.sleep(1000/30);
            } catch (InterruptedException ex) {}
        }
    }
    
    protected abstract void moveObjects(); 
    
    protected abstract void doLogic(); 
    
    protected void stopGame()
    {
        //timer.stop();
        setStarted(false);
//        soundLib.stopLoopingSound();
    }
    
    protected abstract void checkKeys(); 
    
    protected void computeDelta()
    {
        delta = System.nanoTime() - last;
        last = System.nanoTime();
        fps = ((long)(1e9))/delta;
    }
    
    @Override
    public abstract void paintAll(Graphics g);
    
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        g.setColor(Color.red);
        g.drawString("FPS: " + Long.toString(fps), 20, 10);
        
        if(load != null)
        {
            g.drawImage(load, (this.width - load.getWidth()) / 2, (this.height - load.getHeight()) / 2, this);
        }
        
        //g.drawImage(load, 0, 0, this);
        
        if(!started)
        {
            return;
        }
        
        paintAll(g);
    }
    
    protected BufferedImage[] loadPics(String path, int pics)
    {
        BufferedImage[] anim = new BufferedImage[pics];
        BufferedImage source = null;
        
        URL pic_url = getClass().getClassLoader().getResource(path);
        
        try 
        {
            source = ImageIO.read(pic_url);
        } 
        catch (IOException ex) { }
        
        for(int x = 0; x < pics; x++)
        {
            anim[x] = source.getSubimage(x*source.getWidth() / pics, 0, source.getWidth() / pics, source.getHeight());
        }
        
        return anim;
    }
    
    public boolean isStarted()
    {
        return started;
    }
    
    public void setStarted(boolean started)
    {
        this.started = started;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) 
    {
        
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
        
    }
}
