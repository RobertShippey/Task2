/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

public class Menu extends JFrame implements WindowListener, ActionListener{
    private static final long serialVersionUID = 1L;
    private final Comms server;
    private Urgent messages;

   public Menu(Comms s) {
        
      super("Client GUI");
      this.server = s;
      
        messages = new Urgent();
        messages.start();
      
       // Pane 1

        JPanel panel1 = new JPanel();
        
        JTabbedPane tabbedGUI = new JTabbedPane();

        // label for film

        JLabel labelFilm = new JLabel("Film", SwingConstants.LEFT);
        panel1.add(labelFilm);

        // dropdown box for films

        JComboBox dropdown;
        String film[] = {"Batman", "SuperMan", "Startrek", "HellBoy"};
        dropdown = new JComboBox(film);
        panel1.add(dropdown);

        // label for date

        JLabel labelDates = new JLabel("Dates", SwingConstants.LEFT);
        panel1.add(labelDates);

        // Dropdown box for date

        JComboBox dropdownDate;
        String date[] = {"28/3/2012", "12/12/2012", "12/13/1415", "80/08/1355"};
        dropdownDate = new JComboBox(date);
        panel1.add(dropdownDate);

        // label for time

        JLabel labelTime = new JLabel("Time", SwingConstants.LEFT);
        panel1.add(labelTime);

        // Dropdown box for time

        JComboBox dropdownTime;
        String time[] = {"28/3/2012", "12/12/2012", "12/13/1415", "80/08/1355"};
        dropdownTime = new JComboBox(time);
        panel1.add(dropdownTime);

        // label for Seats

        JLabel labelSeats = new JLabel("No of Seats", SwingConstants.LEFT);
        panel1.add(labelSeats);

        // Dropdown box for No of Seats

        JComboBox dropdownSeats;
        String seats[] = {"28/3/2012", "12/12/2012", "12/13/1415", "80/08/1355"};
        dropdownSeats = new JComboBox(seats);
        panel1.add(dropdownSeats);
        
        // button for submit
        
        JButton SUBMIT = new JButton("SUBMIT");
        SUBMIT.addActionListener(this);
        panel1.add(SUBMIT);

        //  panel1.add(spnDate);

        tabbedGUI.addTab("CreateBooking", null, panel1, "First Panel");

        // pane 2

        JLabel label2 = new JLabel("panel two", SwingConstants.LEFT);
        JPanel panel2 = new JPanel();
        panel2.add(label2);
        tabbedGUI.addTab("Amend Booking", null, panel2, "Second Panel");

        // pane 3

        JLabel label3 = new JLabel("panel three", SwingConstants.CENTER);
        JPanel panel3 = new JPanel();
        panel3.add(label3);
        tabbedGUI.addTab("Delete Booking", null, panel3, "Third Panel");

        // pane 4

        JLabel label4 = new JLabel("panel four", SwingConstants.CENTER);
        JPanel panel4 = new JPanel();
        panel4.add(label4);
        tabbedGUI.addTab("Deals", null, panel4, "Forth Panel");
        
        this.add(tabbedGUI);
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(this);
        setLocationRelativeTo(null);
        setSize(401, 200);
    }
   
   @Override
    public void windowOpened(WindowEvent we) {}

    @Override
    public void windowClosing(WindowEvent we) {
        server.logoff();
        messages.stop();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
   
}
