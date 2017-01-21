import javax.swing.JFrame;
public class AsteroidDriver
{
   public static void main(String[] args)
   {
      JFrame frame = new JFrame("Asteroids");
      frame.setSize(600, 600);
      frame.setLocation(350, 0);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setContentPane(new AsteroidPanel());
      frame.setVisible(true);
   }
}