import java.io.*;
import sun.audio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
public class Asteroid extends Hostile
{
   private int mySize;
   public Asteroid(double a, double b, double c, double d, int e)
   {
      super(a, b, c, d);
      mySize = e;
   }
   public int hit()
   {
      return mySize;
   }
   public void setSize(int a)
   {
      mySize = a;
   }
   public void destroyed()
   {
      try{
         InputStream in = (this.getClass().getResourceAsStream("Resources/explosion.wav"));
         AudioStream audioStream = new AudioStream(in);
         AudioPlayer.player.start(audioStream);
      }
      catch(Exception e){
      }
   }
   public void draw(Graphics g)
   {
      g.setColor(Color.WHITE);
      g.drawOval((int)myX, (int)myY, 25*mySize, 25*mySize);
   }
   public void move()
   {
      if(getX()>600)
         setX(0-25*mySize);
      else if(getX()<(0-25*mySize))
         setX(600);
      else
         setX(getX()+dx);
      if(getY()>600)
         setY(0-25*mySize);
      else if(getY()<(0-25*mySize))
         setY(600);
      else
         setY(getY()+dy);
   }
}