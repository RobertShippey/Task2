/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

public class Menu extends JFrame implements WindowListener{
    private static final long serialVersionUID = 1L;
    private final Comms server;

   public Menu(Comms s) {
        
      super("Client GUI");
      this.server = s;
      
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
      
      
 
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(this);
        setLocationRelativeTo(null);
        setSize(400, 200);
    }
   
   @Override
    public void windowOpened(WindowEvent we) {}

    @Override
    public void windowClosing(WindowEvent we) {
        server.logoff();
        this.setVisible(false);
        try{
        Login l = new Login();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.exit(0);
        }
        this.dispose();
        
    }

    @Override
    public void windowClosed(WindowEvent we) {}

    @Override
    public void windowIconified(WindowEvent we) {}

    @Override
    public void windowDeiconified(WindowEvent we) {}

    @Override
    public void windowActivated(WindowEvent we) {}

    @Override
    public void windowDeactivated(WindowEvent we) {}
   
}
