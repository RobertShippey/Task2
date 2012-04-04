/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;

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

        // adding tab to tabbed gui

        tabbedGUI.addTab("CreateBooking", null, panel1, "First Panel");

        // pane 2
        
        JPanel panel2 = new JPanel();
        
        // adding Booking label

        JLabel labelBooking = new JLabel("Booking", SwingConstants.LEFT);
        panel2.add(labelBooking);
        
        // adding booking dropdown box
        
        JComboBox dropdownBooking;
        String booking[] = {"booking1", "booking2", "booking3", "booking4"};
        dropdownBooking = new JComboBox(booking);
        panel2.add(dropdownBooking);
        
        // adding Booking label

        JLabel labelAmendSeats = new JLabel("New No of Seats", SwingConstants.LEFT);
        panel2.add(labelAmendSeats);
        
        // No of seats spinner
        
        String[] noOfSeats = {"1","2","3","4","5","6","7"};
        SpinnerModel spinnerSeats = new SpinnerListModel(noOfSeats);
        JSpinner spnList = new JSpinner(spinnerSeats);
        panel2.add(spnList);
        
        // amend booking submit button
        
        JButton SUBMIT2 = new JButton("SUBMIT");
        SUBMIT2.addActionListener(this);
        panel2.add(SUBMIT2);
        
        tabbedGUI.addTab("Amend Booking", null, panel2, "Second Panel");

        // pane 3
        
        JPanel panel3 = new JPanel();
        
        // label for Delete Booking

        JLabel labelDeleteBooking = new JLabel("Booking", SwingConstants.CENTER);
        panel3.add(labelDeleteBooking);
        
         // adding delete booking dropdown box
        
        JComboBox dropdownDeleteBooking;
        String Dbooking[] = {"booking1", "booking2", "booking3", "booking4"};
        dropdownDeleteBooking = new JComboBox(Dbooking);
        panel3.add(dropdownDeleteBooking);
        
        // adding submit button
        
        JButton SUBMIT3 = new JButton("SUBMIT");
        SUBMIT3.addActionListener(this);
        panel3.add(SUBMIT);
        
        tabbedGUI.addTab("Delete Booking", null, panel3, "Third Panel");

        // pane 4
        
        JPanel panel4 = new JPanel();
        
        // adding deals label

        JLabel labelDeals = new JLabel("Deals", SwingConstants.CENTER);
        panel4.add(labelDeals);
        
        // adding text area
        
        JTextArea Deals  = new JTextArea(6, 30);
        panel4.add(Deals);
        
        // adding refresh button
        JButton Refresh = new JButton("Refresh");
        Refresh.addActionListener(this);
        panel4.add(Refresh);
        
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
