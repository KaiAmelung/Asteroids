import java.io.*;
import sun.audio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
public class Bullet
{
   private double myX, myY, dx, dy;
   public int time = 0;
   public Bullet(double a, double b, int c)
   {
      myX = a;
      myY = b;
      int degrees;
      degrees = c;
      int temp = 0;
            if(degrees<90){
               temp = degrees;
               dy = -1*Math.cos(Math.toRadians(temp))*5;
               dx = Math.sin(Math.toRadians(temp))*5;
              
            }
            else if(degrees<180){
               temp = degrees-90;
               dy =Math.sin(Math.toRadians(temp))*5;
               dx =Math.cos(Math.toRadians(temp))*5;
            }
            else if(degrees<270){
               temp = degrees - 180;
               dy =Math.cos(Math.toRadians(temp))*5;
               dx =-1*Math.sin(Math.toRadians(temp))*5;
            }
            else{
               temp = degrees - 270;
               dy =-1*Math.sin(Math.toRadians(temp))*5;
               dx =-1*Math.cos(Math.toRadians(temp))*5;
            }
   }
   public void setX(double x)
   {
      myX = x;
   }
   public void setY(double y)
   {
      myY = y;
   }
   public double getX()
   {
      return myX;
   }
   public double getY()
   {
      return myY;
   }
   public int getTime()
   {
      return time;
   }
   public void move()
   {
      if(getX()>625)
         setX(-25);
      else if(getX()<(-25))
         setX(600);
      else
         setX(getX()+dx);
      if(getY()>625)
         setY(-25);
      else if(getY()<(-25))
         setY(625);
      else
         setY(getY()+dy);
      time++;
   }
   public void draw(Graphics g)
   {
      g.fillOval((int)myX, (int)myY, 2, 2);
   }
}
