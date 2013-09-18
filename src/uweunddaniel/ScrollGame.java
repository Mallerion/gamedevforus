package uweunddaniel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;


public class ScrollGame extends GamePanel 
{
    SpriteLib lib;
    Player player;
    MapDisplay maplayer0, maplayer1;
    boolean leftmove = false;
    boolean rightmove = false;
    boolean upmove = false;
    boolean downmove = false;
    int check = 0;
    
    
    int speed = 100;
    
    public static void main (String[] args)
    {
        new ScrollGame(480, 480);
    }
    
    public ScrollGame(int w, int h)
    {
        super(w,h);
    }
    
    @Override
    protected void doInitializations() 
    {
        super.doInitializations();

        lib = SpriteLib.getInstance();
        player = new Player(lib.getSprite("pics/player.png", 12, 1), this.width / 2, this.height / 2, 150, this);
        maplayer0 = new MapDisplay("level/uwe.txt", "pics/sandbox/sandbox.png", "pics/shadowmap.png", this);
        maplayer0.setVisibleRectangle(new Rectangle2D.Double(50, 50, this.getWidth(), this.getHeight()));
        load = null;
    }
    
    @Override
    protected void checkKeys() 
    {
        if(left)
        {
            maplayer0.setHorizontalSpeed(-speed);
        }
        else if(right)
        {

            maplayer0.setHorizontalSpeed(speed);
        }
        else if(up)
        {
            maplayer0.setVerticalSpeed(-speed);
        }
        else if(down)
        {
            maplayer0.setVerticalSpeed(speed);
        }

        if(!up && !down)
        {
            maplayer0.setVerticalSpeed(0);
        }

        if(!left && !right)
        {
            maplayer0.setHorizontalSpeed(0);
        }
        if(check == 0)
        {
            player.setLoop(1, 1);
        }
    }
    
    @Override
    protected void doLogic() 
    {
        player.doLogic(delta);
    }
    
    @Override
    protected void moveObjects()
    {
        maplayer0.moveVisibleRectangle(delta);
        //player.move(delta);
    }

    @Override
    public void paintAll(Graphics g)
    {
        maplayer0.drawVisibleMap(g);
        player.drawObjects(g);
        //g.drawImage(ImageControl.getInstance().getImageAt(3), 16, 16, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) 
    {
        if(e.getKeyCode() == KeyEvent.VK_W)
        {
            up = true;
            check = 1;
            
            if(!upmove)
            {
                upmove = true;
                player.setLoop(0, 2);
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_S)
        {
            down = true;
            check = 1;
            
            if(!downmove)
            {
                downmove = true;
                player.setLoop(6, 8);
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_A)
        {
            left = true;
            check = 1;
            
            if(!leftmove)
            {
                leftmove = true;
                player.setLoop(9, 11);
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_D)
        {
            right = true;
            check = 1;
            
            if(!rightmove)
            {
                rightmove = true;
                player.setLoop(3, 5);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
        if(e.getKeyCode() == KeyEvent.VK_W)
        {
            up = false;
            
            if(upmove)
            {
                upmove = false;
                player.setLoop(1, 1);
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_S)
        {
            down = false;
            
            if(downmove)
            {
                downmove = false;
                player.setLoop(7, 7);
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_A)
        {
            left = false;

            if(leftmove)
            {
                leftmove = false;
                player.setLoop(10, 10);
            }
            
        }
        if(e.getKeyCode() == KeyEvent.VK_D)
        {
            right = false;
            
            if(rightmove)
            {
                rightmove = false;
                player.setLoop(4, 4);
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            if(!isStarted())
            {
                setStarted(true);
                doInitializations();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            if(isStarted())
            {
                stopGame();
            }
            else
            {
                setStarted(false);
                System.exit(0);
            }
        }
    }
    
    public MapDisplay getMap()
    {
        return maplayer0;
    }
}
