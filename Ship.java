import java.io.*;
import sun.audio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
public class Ship
{
   public double myX, myY, dx, dy;
   public int degrees;
   public Ship()
   {
      myX = 300;
      myY = 300;
      dx = 0;
      dy = 0;
      degrees = 0;
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
   public void setDegrees(int x)
   {
      degrees = x;
      if(degrees >= 360)
      {
         degrees = 0;
      }
      else if(degrees <= -1)
      {
         degrees = 359;
      }
   }
   /**/
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
   public int getDegrees()
   {
      return degrees;
   }
   /**/
   public void destroyed()
   {
      try{
         setX(300);
         setY(300);
      }
      catch(Exception e){
      }
   }
   public void draw(Graphics g)
   {
      int[] xpoints = getxpoints();
      int[] ypoints = getypoints();
      g.drawPolygon(xpoints, ypoints, 3);
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
   }
   public int[] getxpoints()
   {
      int[] xpoints = new int[3];
      /*****************************************/
      int temp = (degrees + 150)%360;
      if(temp<90)
      {
         xpoints[1]=(int)(myX+(20*Math.sin(Math.toRadians(temp))));
      }
      else if(temp<180)
      {
        xpoints[1]=(int)(myX+(20*Math.cos(Math.toRadians(temp-90))));
      }
      else if(temp<270)
      {
         xpoints[1]=(int)(myX+(20*-1*Math.sin(Math.toRadians(temp-180))));
      }
      else
      {
         xpoints[1]=(int)(myX+(20*-1*Math.cos(Math.toRadians(temp-270))));
      }
      /**********************************************/
      temp = (degrees + 210)%360;
      if(temp<90)
      {
         xpoints[2]=(int)(myX+(20*Math.sin(Math.toRadians(temp))));
      }
      else if(temp<180)
      {
         xpoints[2]=(int)(myX+(20*Math.cos(Math.toRadians(temp-90))));
      }
      else if(temp<270)
      {
         xpoints[2]=(int)(myX+(20*-1*Math.sin(Math.toRadians(temp-180))));
      }
      else
      {
         xpoints[2]=(int)(myX+(20*-1*Math.cos(Math.toRadians(temp-270))));
      }
      /**************************************/
      temp = degrees;
      if(temp<90)
      {
         xpoints[0]=(int)(myX+(20*Math.sin(Math.toRadians(temp))));
      }
      else if(temp<180)
      {
         xpoints[0]=(int)(myX+(20*Math.cos(Math.toRadians(temp-90))));
      }
      else if(temp<270)
      {
         xpoints[0]=(int)(myX+(20*-1*Math.sin(Math.toRadians(temp-180))));
      }
      else
      {
         xpoints[0]=(int)(myX+(20*-1*Math.cos(Math.toRadians(temp-270))));
      }
      return xpoints;
   }
   public int[] getypoints()
   {
      int[] ypoints = new int[3];
      if(degrees<90)
      {
         ypoints[0]=(int)(myY+(20*-1*Math.cos(Math.toRadians(degrees))));
      }
      else if(degrees<180)
      {
         ypoints[0]=(int)(myY+(20*Math.sin(Math.toRadians(degrees-90))));
      }
      else if(degrees<270)
      {
         ypoints[0]=(int)(myY+(20*Math.cos(Math.toRadians(degrees-180))));
      }
     else
      {
         ypoints[0]=(int)(myY+(20*-1*Math.sin(Math.toRadians(degrees-270))));
      }
      /*****************************************/
      int temp = (degrees + 150)%360;
      if(temp<90)
      {
         ypoints[1]=(int)(myY+(20*-1*Math.cos(Math.toRadians(temp))));
      }
      else if(temp<180)
      {
         ypoints[1]=(int)(myY+(20*Math.sin(Math.toRadians(temp-90))));
      }
      else if(temp<270)
      {
         ypoints[1]=(int)(myY+(20*Math.cos(Math.toRadians(temp-180))));
      }
      else
      {
         ypoints[1]=(int)(myY+(20*-1*Math.sin(Math.toRadians(temp-270))));
      }
      /**********************************************/
      temp = (degrees + 210)%360;
      if(temp<90)
      {
         ypoints[2]=(int)(myY+(20*-1*Math.cos(Math.toRadians(temp))));
      }
      else if(temp<180)
      {
         ypoints[2]=(int)(myY+(20*Math.sin(Math.toRadians(temp-90))));
      }
      else if(temp<270)
      {
         ypoints[2]=(int)(myY+(20*Math.cos(Math.toRadians(temp-180))));
      }
      else
      {
         ypoints[2]=(int)(myY+(20*-1*Math.sin(Math.toRadians(temp-270))));
      }
      return ypoints;
   }
}
