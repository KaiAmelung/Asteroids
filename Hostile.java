import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
public abstract class Hostile
{
   public double myX, myY;
   public double dx, dy;
   public int mySize;
   public Hostile(double x, double y, double x1, double y1)
   {
      myX = x;
      myY = y;
      dx = x1;
      dy = y1;
   }
   public void setX(double x)
   {
      myX = x;
   }
   public void setY(double y)
   {
      myY = y;
   }
   public void setdx(double x)
  {
      dx = x;
   }
   public void setdy(double y)
   {
      dy = y;
   }
   ////////////////////////
   public double getX()
   {
      return myX;
   }
   public double getY()
   {
      return myY;
   }
   public double getdx()
   {
      return dx;
   }
   public double getdy()
   {
      return dy;
   }
   public abstract int hit();
   public abstract void destroyed();
   public abstract void move();
   public abstract void draw(Graphics g);
}