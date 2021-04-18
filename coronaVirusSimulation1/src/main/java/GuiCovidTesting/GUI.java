package GuiCovidTesting;
import GuiLockDown.ChartLockDown;
import GuiRegular.*;
import javafx.application.Application;

import javax.swing.*;
import java.awt.*;


public class GUI {
     public static void main(String[] args) {
      int height = 600;
      int width = 800;
      JFrame f = new JFrame("covid-19");
      f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      AnimationPanel ap = new AnimationPanel(height, width);
      f.getContentPane().add(ap);
      f.pack();
      f.show();
      JButton stopJButton = new JButton("STOP");
//      stopJButton.addActionListener(this)
      ap.add(stopJButton);
      Application.launch (ChartCovidTesting.class);
   }
     
     private void stopJButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // TODO add your handling code here:
        String[] args = {""};
        Driver.DriverJFrame.main(args);
    }  
     
     
}
