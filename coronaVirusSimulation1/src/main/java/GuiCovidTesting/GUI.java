package GuiCovidTesting;
import GuiRegular.*;
import javax.swing.*;
import java.awt.*;


public class GUI {
     public static void main(String[] args) {
      int height = 600;
      int width = 800;
      JFrame f = new JFrame("covid-19");
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      AnimationPanel ap = new AnimationPanel(height, width);
      f.getContentPane().add(ap);
      f.pack();
      f.show();
   }
}
