import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.PrintStream;
public class AsteroidPanel extends JPanel
{
   private static final int FRAME = 600;
  
   private BufferedImage myImage;
   private Graphics myBuffer;
   private Timer timer;
  
   ArrayList<Hostile> hostiles = new ArrayList<Hostile>();
   ArrayList<Bullet> bullets = new ArrayList<Bullet>();
   ArrayList<Enemy> enemies = new ArrayList<Enemy>();
   Ship ship = new Ship();
  
   private int lives = 3, shiftFactor = 1, nukes = 0, gatling = 0;
   private boolean holdingFire, impervious = true;
   private boolean left, right, forward, shift = false, shiftBreak = false, nameEntered, isGatling = false;
   private double speed = 0.04;
   private int level = 3, shiftTime = 0;
   int reps = 0, reps1 = 0;
   private int score = 0, time = 10, money = 0, imperviouscount=0;
   private boolean game = false, title = true, leaderboard = false, pause = false;
   private boolean upleft = true, upright = false, downleft = false, downright = false;
  
   public AsteroidPanel()
   {
      myImage =  new BufferedImage(FRAME, FRAME, BufferedImage.TYPE_INT_RGB);
      myBuffer = myImage.getGraphics();
      myBuffer.setColor(Color.BLACK);
      myBuffer.fillRect(0,0,FRAME,FRAME);
      addKeyListener(new Key());
      setFocusable(true);
      timer = new Timer(time, new Listener());
      timer.start();
   }
   public void paintComponent(Graphics g)
   {
      g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
   }
   public void newSpawn(int a)
   {
      for(int x = 1; x<=a; x++)
      {
         double x1, x2;
         x1 = x2 = 0;
         for(int b = 0; b<2; b++)
         {
            if(Math.random()>0.5)
            {
               switch(b){
                  case(0): x1= Math.random()*0.5+0.5;
                  break;
                  case(1): x2= Math.random()*0.5+0.5;
                  break;
               }
            }
            else
            {
               switch(b){
                  case(0): x1= Math.random()*-0.5-0.5;
                  break;
                  case(1): x2= Math.random()*-0.5-0.5;
                  break;
               }
            }
         }
         if(Math.random()<0.5)
            hostiles.add(new Asteroid(0, Math.random()*600, x1, x2, 3));
         else
            hostiles.add(new Asteroid(Math.random()*600, 0, x1, x2, 3));
      }
   }
   public void split(Hostile a)
   {
      if(a.hit() != 1)
      {
         for(int x = 1; x<=2; x++)
         {
            double x1, x2;
            x1 = x2 = 0;
            for(int b = 0; b<2; b++)
            {
               if(Math.random()>0.5)
               {
                  switch(b){
                     case(0): x1= Math.random()*1.0/2.0+1.0/2.0;
                     break;
                     case(1): x2= Math.random()*1.0/2.0+1.0/2.0;
                     break;
                  }
               }
               else
               {
                  switch(b){
                     case(0): x1= Math.random()*(-1.0/2.0)-1.0/2.0;
                     break;
                     case(1): x2= Math.random()*(-1.0/2.0)-1.0/2.0;
                     break;
                  }
               }
            }
         hostiles.add(new Asteroid(a.getX() + 1.0/4.0*a.hit()*25, a.getY() + 1.0/4.0*a.hit()*25, x1, x2, a.hit()-1));
         }
      }
   }
   public void levelUp()
   {
      bullets.clear();
      shiftTime = 0;
      ship.setX(300);
      ship.setY(300);
      ship.setDegrees(0);
      ship.setdx(0);
      ship.setdy(0);
      level++;
      if(level==10)
      {
         hostiles.add(new Asteroid(0, 0, 0.5, 0.5, 7));
      }
      else
      {
         newSpawn(level);
      }
   }
   public void shipCrash(Ship ship, ArrayList<Hostile> hostiles)
   {
      for(int a = 0; a<3; a++)
      {
         for(int b=0; b<hostiles.size(); b++)
         {
            double xdistance = (ship.getxpoints()[a])-(hostiles.get(b).getX() + hostiles.get(b).hit()*12.5);
            double ydistance = (ship.getypoints()[a])-(hostiles.get(b).getY() + hostiles.get(b).hit()*12.5);
            double distance = Math.hypot(xdistance, ydistance);
            if(distance <= hostiles.get(b).hit()*12.5&&!impervious)
            {
               ship.setX(300);
               ship.setY(300);
               impervious = true;
               ship.setdy(0);
               ship.setdx(0);
               ship.setDegrees(0);
               hostiles.get(b).destroyed();
               split(hostiles.get(b));
               hostiles.remove(b);
               if(hostiles.isEmpty())
               {
                  levelUp();
               }
               lives--;
               if(lives ==0)
               {
                  left = false;
                  right = false;
                  forward = false;
                  shiftTime = 0;
                  myBuffer.setColor(Color.BLACK);
                  myBuffer.fillOval(150, 23, 25, 25);
                  nameEntered = false;
                  try{
                     String name = JOptionPane.showInputDialog("Game over your score was " + score + ".\nEnter your name if you want your score to be on the leaderboards.");
                     name = name.replaceAll(" ", "");
                     if(!name.equals(""))
                     {
                        nameEntered = true;
                        while(name.length()>7)
                           name = JOptionPane.showInputDialog("Your name was too long, please enter one max 7 characters.");
                        try{
                           
                           Scanner infile = new Scanner(this.getClass().getResourceAsStream("Resources/leaderboard.txt"));
                           ArrayList<String> old = new ArrayList<String>();
                           while(infile.hasNext())
                              old.add(infile.nextLine());
                           infile.close();
                           PrintStream outfile = new PrintStream("Resources/leaderboard.txt");
                           for(int z = 0; z<old.size(); z++)
                              outfile.println(old.get(z));
                           outfile.println(name);
                           outfile.println("" + score);
                           outfile.close();
                        }
                        catch(Exception e){}
                     }
                  }
                  catch(Exception e){}
                  score = 0;
                  level = 3;
                  lives = 3;
                  hostiles.clear();
                  game = false;
                  title = false;
                  leaderboard = true;
               }
            }
         }
      }
   }
   public void enemyShot(ArrayList<Hostile> hostiles, ArrayList<Bullet> bullets)
   {
      for(int b = 0; b<hostiles.size(); b++)
      {
         for(int a=0; a<bullets.size()&&!bullets.isEmpty(); a++)
         {
            if(a == bullets.size())
            {
               a--;
            }
            if(b == hostiles.size())
            {
               b--;
            }
            double xdistance = (bullets.get(a).getX()+1.5)-(hostiles.get(b).getX() + hostiles.get(b).hit()*12.5);
            double ydistance = (bullets.get(a).getY()+1.5)-(hostiles.get(b).getY() + hostiles.get(b).hit()*12.5);
            double distance = Math.hypot(xdistance, ydistance);
            if(distance<=1.5+hostiles.get(b).hit()*12.5&&a<bullets.size()&&b<hostiles.size())
            {
               hostiles.get(b).destroyed();
               score+=(100-hostiles.get(b).hit()*25);
               money+=(100-hostiles.get(b).hit()*25);
               split(hostiles.get(b));
               hostiles.remove(b);
               bullets.remove(a);
               if(hostiles.isEmpty())
               {
                  levelUp();
               }
            }
         }
      }
   }
   private void update()
   {
                  //stage setup
                  myBuffer.setColor(Color.BLACK);
                  myBuffer.fillRect(0,0,FRAME,FRAME);
                  //checks
                  shipCrash(ship, hostiles);
                  enemyShot(hostiles, bullets);
                  //conditional object movement
                  if(right&&!left)
                  {
                     ship.setDegrees(ship.getDegrees()+3);
                  }
                  else if(left)
                  {
                     ship.setDegrees(ship.getDegrees()-3);
                  }
                  if(forward)
                  {
                     int degrees = ship.getDegrees();
                     int temp = 0;
                     if(degrees<90){
                        temp = degrees;
                        ship.setdy(ship.getdy() + -1*Math.cos(Math.toRadians(temp))*speed);
                        ship.setdx(ship.getdx() + Math.sin(Math.toRadians(temp))*speed);
                       
                     }
                     else if(degrees<180){
                        temp = degrees-90;
                        ship.setdy(ship.getdy() + Math.sin(Math.toRadians(temp))*speed);
                        ship.setdx(ship.getdx() + Math.cos(Math.toRadians(temp))*speed);
                     }
                     else if(degrees<270){
                        temp = degrees - 180;
                        ship.setdy(ship.getdy() + Math.cos(Math.toRadians(temp))*speed);
                        ship.setdx(ship.getdx() + -1*Math.sin(Math.toRadians(temp))*speed);
                     }
                     else{
                        temp = degrees - 270;
                        ship.setdy(ship.getdy() + -1*Math.sin(Math.toRadians(temp))*speed);
                        ship.setdx(ship.getdx() + -1*Math.cos(Math.toRadians(temp))*speed);
                     }
                  }
                  //movement and drawing
                  for(int a = 0; a <hostiles.size(); a++)
                  {
                     hostiles.get(a).move();
                     hostiles.get(a).draw(myBuffer);
                  }
                  for(int a = 0; a <bullets.size(); a++)
                  {
                     bullets.get(a).move();
                     bullets.get(a).draw(myBuffer);
                     if(bullets.get(a).getTime()==80)
                     {
                        bullets.remove(a);
                     }
                  }
                  if(!forward)
                  {
                     ship.setdx(ship.getdx()/1.015);
                     ship.setdy(ship.getdy()/1.015);
                  }
                  myBuffer.setColor(Color.WHITE);
                  myBuffer.setFont(new Font("Verdana", Font.BOLD, 35));
                  myBuffer.drawString("" + score, 20, 50);
                  myBuffer.drawString("WAVE " + (level-3), 430, 50);
                  for(int a = 0; a<lives; a++)
                  {
                     myBuffer.fillOval(180+a*30, 23, 25, 25);
                  }
                  ship.move();
                  if(!impervious||(imperviouscount%10)<5)
                  {
                     ship.draw(myBuffer);
                  }
                  if(shiftTime==0)
                     myBuffer.setColor(Color.GREEN);
                  if(shiftBreak)
                     myBuffer.setColor(Color.RED);
                  myBuffer.drawRect(490, 560, 100-shiftTime/2, 25);
   }
   private class Listener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         if(game)
         {
            if(impervious&&imperviouscount<=160)
               imperviouscount++;
            else if(imperviouscount > 160)
            {
               impervious = false;
               imperviouscount=0;
            }
            if(hostiles.isEmpty())
            {
               levelUp();
            }
            reps++;
            reps = reps%(6000/level);
            if(reps == 0)
            {
               Enemy temp = new Enemy((int)Math.random()*600, (int)Math.random()*600, Math.random()*4-2, Math.random()*4-2);
               hostiles.add(temp);
               enemies.add(temp);
            }
            if(reps%250==0)
            {
               for(int a = 0; a<enemies.size(); a++)
               {
                  enemies.get(a).setdx(Math.random()*4-2);
                  enemies.get(a).setdy(Math.random()*4-2);
               }
            }
            if(shift)
            {
               shiftTime++;
               if(shiftTime>=200)
               {
                  shift = false;
                  shiftBreak = true;
               }
               if(reps%2==0)
               {
                  update();
               }
            }
            else
            {
               if(shiftTime > 0)
                  shiftTime -=shiftFactor;
               else
                  shiftBreak = false;
               update();
            }
            repaint();
         }
         else if(title)
         {
            if(hostiles.isEmpty())
               newSpawn(8);
            myBuffer.setColor(Color.BLACK);
            myBuffer.fillRect(0, 0, FRAME, FRAME);
            reps1++;
            reps1 = reps1%180;
            if(reps1>89)
            {
               myBuffer.setColor(Color.WHITE);
               myBuffer.setFont(new Font("Verdana", Font.ITALIC, 35));
               myBuffer.drawString("Space to start", 170, 400);
            }
            Image image = new ImageIcon(this.getClass().getResource("Resources/Title.png")).getImage();
            myBuffer.drawImage(image, 30, 200, null);
            for(int a = 0; a <hostiles.size(); a++)
            {
               hostiles.get(a).move();
               hostiles.get(a).draw(myBuffer);
            }
            repaint();
         }
         else if(leaderboard)
         {
            myBuffer.setColor(Color.BLACK);
            myBuffer.fillRect(0, 0, FRAME, FRAME);
            for(int a = 0; a <hostiles.size(); a++)
            {
               hostiles.get(a).move();
               hostiles.get(a).draw(myBuffer);
            }
            if(hostiles.isEmpty())
               newSpawn(8);
            ArrayList<String> leaders = new ArrayList<String>();
            ArrayList<Integer> leaders1 = new ArrayList<Integer>();
            try{
               Scanner infile = new Scanner(this.getClass().getResourceAsStream("Resources/leaderboard.txt"));
               while(infile.hasNext())
               {
                  leaders.add(infile.nextLine());
                  String score = infile.nextLine();
                  leaders.add(score);
                  leaders1.add(Integer.parseInt(score));
               }
               infile.close();
            }
            catch(Exception r){
            }
            int maxPos = 0;
            ArrayList<Integer> top = new ArrayList<Integer>();
            
            for(int a = 0; a<leaders1.size(); a++)
            {
               for(int b = 0; b<leaders1.size(); b++)
               {
                  if(leaders1.get(b)>leaders1.get(maxPos))
                  {
                     maxPos = b;
                  }
               }
               leaders1.set(maxPos, -1);
               top.add(maxPos);
            }
            for(int a = 0; a<10&&a<leaders1.size(); a++)
            {
               if(top.get(a)*2+2 == leaders.size()&&nameEntered)
                  myBuffer.setColor(Color.YELLOW);
               else
                  myBuffer.setColor(Color.WHITE);
               myBuffer.setFont(new Font("Verdana", Font.ITALIC, 32));
               myBuffer.drawString((a+1)+ ".\t" + leaders.get((top.get(a)*2))+"\t"+leaders.get((top.get(a)*2+1)), 190, 55+a*55);
            }
         repaint();
         }
         else if(pause)
         {
            myBuffer.setColor(Color.BLACK);
            myBuffer.fillRect(0, 0, FRAME, FRAME);
            myBuffer.setColor(Color.WHITE);
            myBuffer.setColor(Color.WHITE);
            myBuffer.setFont(new Font("Verdana", Font.BOLD, 35));
            myBuffer.drawString("$" + money, 20, 50);
            myBuffer.drawString("WAVE " + (level-3), 430, 50);
            for(int a = 0; a<lives; a++)
            {
               myBuffer.fillOval(180+a*30, 23, 25, 25);
            }
            if(!upleft)
            {
               myBuffer.setColor(Color.WHITE);
               myBuffer.drawRect(75, 100, 200, 200);
               myBuffer.setFont(new Font("Verdana", Font.BOLD, 20));
               myBuffer.drawString("SLO-MO", 90, 150);
               myBuffer.drawString("$5000", 90, 200);
            }
            else
            {
               myBuffer.setFont(new Font("Verdana", Font.BOLD, 35));
               myBuffer.setColor(Color.WHITE);
               myBuffer.fillRect(75, 100, 200, 200);
               myBuffer.setColor(Color.BLACK);
               myBuffer.drawString("SLO-MO", 90, 150);
               myBuffer.drawString("$5000", 90, 200);
            }
            if(!upright)
            {
               myBuffer.setColor(Color.WHITE);
               myBuffer.drawRect(FRAME-275, 100, 200, 200);
               myBuffer.setFont(new Font("Verdana", Font.BOLD, 20));
               myBuffer.drawString("SPEED", FRAME-260, 150);
               myBuffer.drawString("$5000", FRAME-260, 200);
            }
            else
            {
               myBuffer.setFont(new Font("Verdana", Font.BOLD, 35));
               myBuffer.setColor(Color.WHITE);
               myBuffer.fillRect(FRAME-275, 100, 200, 200);
               myBuffer.setColor(Color.BLACK);
               myBuffer.drawString("SPEED", FRAME-260, 150);
               myBuffer.drawString("$5000", FRAME-260, 200);
            }
            if(!downleft)
            {
               myBuffer.setColor(Color.WHITE);
               myBuffer.drawRect(75, FRAME-250, 200, 200);
               myBuffer.setFont(new Font("Verdana", Font.BOLD, 20));
               myBuffer.drawString("GATLING", 90, FRAME-200);
               myBuffer.drawString("$10000", 90, FRAME-150);
            }
            else
            {
               myBuffer.setFont(new Font("Verdana", Font.BOLD, 35));
               myBuffer.setColor(Color.WHITE);
               myBuffer.fillRect(75, FRAME-250, 200, 200);
               myBuffer.setColor(Color.BLACK);
               myBuffer.drawString("GATLING", 90, FRAME-200);
               myBuffer.drawString("$10000", 90, FRAME-150);
            }
            if(!downright)
            {
               myBuffer.setColor(Color.WHITE);
               myBuffer.drawRect(FRAME-275, FRAME-250, 200, 200);
               myBuffer.setFont(new Font("Verdana", Font.BOLD, 20));
               myBuffer.drawString("LIFE", FRAME-260, FRAME-200);
               myBuffer.drawString("$7500", FRAME-260, FRAME-150);
            }
            else
            {
               myBuffer.setFont(new Font("Verdana", Font.BOLD, 35));
               myBuffer.setColor(Color.WHITE);
               myBuffer.fillRect(FRAME-275, FRAME-250, 200, 200);
               myBuffer.setColor(Color.BLACK);
               myBuffer.drawString("LIFE", FRAME-260, FRAME-200);
               myBuffer.drawString("$7500", FRAME-260, FRAME-150);
            }
            repaint();
         }
         
      }
   }
   private class Key extends KeyAdapter
   {
      public void keyPressed(KeyEvent e)
      {
         if(e.getKeyCode() == KeyEvent.VK_SPACE&&!holdingFire)
         {
            if(game)
            {
               if(!isGatling)
                  holdingFire = true;
               bullets.add(new Bullet(ship.getX(), ship.getY(), ship.getDegrees()));
            }
            if(title)
            {
               title = false;
               game = true;
               leaderboard = false;
               money = 0;
               shiftTime = 0;
               speed = 0.04;
               gatling = 0;
               shiftFactor = 1;
               bullets.clear();
               hostiles.clear();
            }
            if(leaderboard)
            {
               title = true;
               game = false;
               leaderboard = false;
               hostiles.clear();
            }
            if(pause)
            {
                  if(upleft&&money>=5000)
                  {
                     money-=5000;
                     shiftFactor++;
                  }
                  if(upright&&money>=5000)
                  {
                     money-=5000;
                     speed+=0.01;
                  }
                  if(downleft&&money>=10000)
                  {
                     money-=10000;
                     isGatling = true;
                  }
                  if(downright&&money>=7500)
                  {
                     money-=7500;
                     lives++;
                  }
            }
         }
         if(e.getKeyCode() == KeyEvent.VK_RIGHT)
         {
            right = true;
            if(pause)
            {
               if(downleft)
               {
                  downright = true;
                  downleft = false;
               }
               if(upleft)
               {
                  upright = true;
                  upleft = false;
               }
            }
         }
         if(e.getKeyCode() == KeyEvent.VK_LEFT)
         {
            left = true;
            if(pause)
            {
               if(downright)
               {
                  downleft = true;
                  downright = false;
               }
               if(upright)
               {
                  upleft = true;
                  upright = false;
               }
            }
         }
         if(e.getKeyCode() == KeyEvent.VK_UP)
         {
            forward = true;
            if(pause)
            {
               if(downright)
               {
                  upright = true;
                  downright = false;
               }
               if(downleft)
               {
                  upleft = true;
                  downleft = false;
               }
            }
         }
         if(e.getKeyCode() == KeyEvent.VK_DOWN)
         {
            if(!shiftBreak)
               shift = true;
            if(pause)
            {
               if(upright)
               {
                  downright = true;
                  upright = false;
               }
               if(upleft)
               {
                  downleft = true;
                  upleft = false;
               }
            }
         }
         if(e.getKeyCode() == KeyEvent.VK_P)
         {
            if(game)
            {
               game = false;
               pause = true;
            }
            else if(pause)
            {
               game = true;
               pause = false;
            }
         }
         e.consume();
      }
      public void keyReleased(KeyEvent e)
      {
         if(e.getKeyCode() == KeyEvent.VK_SPACE)
         {
            holdingFire = false;
         }
         if(e.getKeyCode() == KeyEvent.VK_RIGHT)
         {
            right = false;
         }
         if(e.getKeyCode() == KeyEvent.VK_LEFT)
         {
            left = false;
         }
         if(e.getKeyCode() == KeyEvent.VK_UP)
         {
            forward = false;
         }
         if(e.getKeyCode() == KeyEvent.VK_DOWN)
         {
            shift = false;
         }
         e.consume();
      }
   }
}