import java.io.*;
import sun.audio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
public class Enemy extends Hostile
{
   public Enemy(double a, double b, double c, double d)
   {
      super(a, b, c, d);
   }
   public int hit()
   {
      return 1;
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
      g.drawOval((int)myX+6, (int)myY, 18, 18);
      g.fillOval((int)myX, (int)myY+10, 30, 20);
   }
   public void move()
   {
      if(getX()>600)
         setX(0-30);
      else if(getX()<(0-30))
         setX(600);
      else
         setX(getX()+dx);
      if(getY()>600)
         setY(0-30);
      else if(getY()<(0-30))
         setY(600);
      else
         setY(getY()+dy);
  }
}
