/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

public class ClientGUI extends JFrame {

   public ClientGUI() {
        
      super("Client GUI");
      JTabbedPane tabbedGUI = new JTabbedPane();
      
      // Pane 1
      JLabel label1 = new JLabel("panel one", SwingConstants.CENTER);
      JPanel panel1 = new JPanel();
      panel1.add(label1);
      tabbedGUI.addTab( "Tab One", null, panel1, "First Panel");
      
      // pane 2
      
       JLabel label2 = new JLabel("panel two", SwingConstants.CENTER);
      JPanel panel2 = new JPanel();
      panel2.add(label2);
      tabbedGUI.addTab( "Tab Two", null, panel2, "Second Panel");
      
      
 
      // pane 3
      
      JLabel label3 = new JLabel("panel three", SwingConstants.CENTER);
      JPanel panel3 = new JPanel();
      panel3.add(label3);
      tabbedGUI.addTab( "Tab three", null, panel3, "Third Panel");
      
  
      add(tabbedGUI);  
      
      
 
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setSize(400, 200);
    }
}
