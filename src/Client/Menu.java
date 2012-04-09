/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import shared.Request;
import shared.Response;

/**
 * The main menu GUI
 * @author Robert and Nathan
 */
public class Menu extends JFrame implements WindowListener, ActionListener, ChangeListener, ItemListener{
    private static final long serialVersionUID = 1L;
    private final Comms server;
    private Urgent messages;
    private  JComboBox CBFilmDropdown;
    private JComboBox CBDateDropdown;
    private JComboBox CBTimeDropdown;
    private JComboBox CBSeatsDropdown;
    private JTextArea DealsText;
    private JComboBox ABBookingDropdown;
    private JSpinner ABSeatsSpinner;
    private JComboBox DBBookingDropdown;
    private final JTabbedPane tabbedGUI;

    /**
     * Constructs a menu.
     * @param s the instance that deals with this session on the server
     */
   public Menu(Comms s) {
        
      super("Client GUI");
      this.server = s;
      
        messages = new Urgent();
        messages.start();
   

       tabbedGUI = new JTabbedPane();
       tabbedGUI.addChangeListener(this);

       // Pane 1

        JPanel panel1 = new JPanel();
       
        // label for film

        JLabel labelFilm = new JLabel("Film", SwingConstants.LEFT);
        panel1.add(labelFilm);

        // dropdown box for films


        String CBfilm[] = server.getFilmNames();
        CBFilmDropdown = new JComboBox(CBfilm);
        CBFilmDropdown.addItemListener(this);
        panel1.add(CBFilmDropdown);

        // label for date

        JLabel labelDates = new JLabel("Dates", SwingConstants.LEFT);
        panel1.add(labelDates);

        // Dropdown box for date

        
        String CBdate[] = server.getFilmDates((String)CBFilmDropdown.getModel().getSelectedItem());
        CBDateDropdown = new JComboBox(CBdate);
        CBDateDropdown.addItemListener(this);
        panel1.add(CBDateDropdown);

        // label for time

        JLabel labelTime = new JLabel("Time", SwingConstants.LEFT);
        panel1.add(labelTime);

        // Dropdown box for time

        String time[] = server.getFilmDateTimes((String)CBFilmDropdown.getModel().getSelectedItem(), (String)CBDateDropdown.getModel().getSelectedItem());
        CBTimeDropdown = new JComboBox(time);
        CBTimeDropdown.addItemListener(this);
        panel1.add(CBTimeDropdown);

        // label for Seats

        JLabel labelSeats = new JLabel("No of Seats", SwingConstants.LEFT);
        panel1.add(labelSeats);

        // Dropdown box for No of Seats


        String seats[] = server.getFilmDateTimeSeats((String)CBFilmDropdown.getModel().getSelectedItem(), (String)CBDateDropdown.getModel().getSelectedItem(), (String)CBTimeDropdown.getModel().getSelectedItem());
        CBSeatsDropdown = new JComboBox(seats);
        panel1.add(CBSeatsDropdown);
        
        // button for submit
        
        JButton SUBMIT = new JButton("SUBMIT");
        SUBMIT.addActionListener(this);
        SUBMIT.setActionCommand("create");
        panel1.add(SUBMIT);

        // adding tab to tabbed gui

        tabbedGUI.addTab("Create Booking", null, panel1, "First Panel");

        // pane 2
        
        JPanel panel2 = new JPanel();
        
        // adding Booking label

        JLabel labelBooking = new JLabel("Booking", SwingConstants.LEFT);
        panel2.add(labelBooking);
        
        // adding booking dropdown box
        
        String booking[] = server.getAllReservationsAsStrings();
        ABBookingDropdown = new JComboBox(booking);
        panel2.add(ABBookingDropdown);
        
        // adding Booking label

        JLabel labelAmendSeats = new JLabel("New No of Seats", SwingConstants.LEFT);
        panel2.add(labelAmendSeats);
        
        // No of seats spinner
        
       ABSeatsSpinner = new JSpinner();

       String noOfSeats[] = {"0"};
       String ABseats = "0";
       ABSeatsSpinner.setModel(new SpinnerListModel(noOfSeats));
       ABSeatsSpinner.getModel().setValue(noOfSeats[0]);
       panel2.add(ABSeatsSpinner);

       String reservation = (String) ABBookingDropdown.getModel().getSelectedItem();
       if (!reservation.equals("")) {
           String data[] = reservation.split(",");
           String ABfilm = data[0].trim();
           String ABdate = data[1].trim();
           String ABtime = data[2].trim();
           ABseats = data[3].trim();

           ABSeatsSpinner.setModel(new SpinnerListModel(server.getFilmDateTimeSeats(ABfilm, ABdate, ABtime)));
           //ABSeatsSpinner.getModel().setValue(ABseats);
       }


        // amend booking submit button
        
        JButton SUBMIT2 = new JButton("SUBMIT");
        SUBMIT2.addActionListener(this);
        SUBMIT2.setActionCommand("amend");
        panel2.add(SUBMIT2);
        
        tabbedGUI.addTab("Amend Booking", null, panel2, "Second Panel");

        // pane 3
        
        JPanel panel3 = new JPanel();
        
        // label for Delete Booking

        JLabel labelDeleteBooking = new JLabel("Booking", SwingConstants.CENTER);
        panel3.add(labelDeleteBooking);
        
         // adding delete booking dropdown box
        
        
        String Dbooking[] = server.getAllReservationsAsStrings();
        DBBookingDropdown = new JComboBox(Dbooking);
        panel3.add(DBBookingDropdown);
        
        // adding submit button
        
        
        JButton SUBMIT3 = new JButton("SUBMIT");
        SUBMIT3.addActionListener(this);
        SUBMIT3.setActionCommand("delete");
        panel3.add(SUBMIT3);
        
        tabbedGUI.addTab("Delete Booking", null, panel3, "Third Panel");

        // pane 4
        
        JPanel panel4 = new JPanel();
        
        // adding deals label

        JLabel labelDeals = new JLabel("Deals", SwingConstants.CENTER);
        panel4.add(labelDeals);
        
        // adding text area
        
        DealsText  = new JTextArea(6, 30);
       DealsText.setEditable(false);
       panel4.add(DealsText);

       Request request = new Request(Request.REFRESH_OFFERS);
       Response response = server.sendRequest(request);
       if (response.getSuccess()) {
           DealsText.setText(response.getResponse());
       } else {
           DealsText.setText("");
       }

        // adding refresh button
        JButton Refresh = new JButton("Refresh");
        Refresh.addActionListener(this);
        Refresh.setActionCommand("refresh");
        panel4.add(Refresh);
        
        tabbedGUI.addTab("Deals", null, panel4, "Forth Panel");
        
        this.add(tabbedGUI);
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(this);
        setLocationRelativeTo(null);
        setSize(450, 220);
    }
   
   /**
    * Not implemented.
    * @param we 
    */
   @Override
    public void windowOpened(WindowEvent we) {}

   /**
    * Logs off the server, disposes of this window and creates a new instance of Login.
    * @param we 
    */
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

    /**
     * Not implemented.
     * @param we 
     */
    @Override
    public void windowClosed(WindowEvent we) {}

    /**
     * Not implemented.
     * @param we 
     */
    @Override
    public void windowIconified(WindowEvent we) {}

    /**
     * Not implemented.
     * @param we 
     */
    @Override
    public void windowDeiconified(WindowEvent we) {}

    /**
     * Not implemented.
     * @param we 
     */
    @Override
    public void windowActivated(WindowEvent we) {}

    /**
     * Not implemented.
     * @param we 
     */
    @Override
    public void windowDeactivated(WindowEvent we) {}

    /**
     * Performs the actions for submit/refresh buttons on each tab.
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("create")) {
            String film = (String) CBFilmDropdown.getModel().getSelectedItem();
            String date = (String) CBDateDropdown.getModel().getSelectedItem();
            String time = (String) CBTimeDropdown.getModel().getSelectedItem();

            //cast dropdown object to String then parse String to int
            int seats = 0;
            try{
            seats = Integer.parseInt((String) CBSeatsDropdown.getModel().getSelectedItem());
            } catch (NumberFormatException ne){
                JOptionPane.showMessageDialog(null, "Looks like there are no eats available for this showing.");
                return;
            }

            Request request = new Request(Request.MAKE);

            request.setFilm(film);
            request.setDate(date);
            request.setTime(time);
            request.setSeats(seats);

            Response response = server.sendRequest(request);
            if (response.getSuccess()) {
                JOptionPane.showMessageDialog(null, "Success!");
            } else {
                JOptionPane.showMessageDialog(null, response.getReason());
            }
            return;


        } else if (command.equals("amend")) {

            Request request = new Request(Request.AMEND);

            String booking = (String) ABBookingDropdown.getModel().getSelectedItem();
            if(booking.equals("")){
                JOptionPane.showMessageDialog(null, "You have no bookings, make one first.");
                return;
            }
            String data[] = booking.split(",");
            String film = data[0].trim();
            String date = data[1].trim();
            String time = data[2].trim();
            int seats = Integer.parseInt(data[3].trim());
            int newSeats = Integer.parseInt((String) ABSeatsSpinner.getModel().getValue());

            request.setFilm(film);
            request.setDate(date);
            request.setTime(time);
            request.setSeats(seats);
            request.setNewSeats(newSeats);

            Response response = server.sendRequest(request);
            
            String[] r = server.getAllReservationsAsStrings(true);
            ABBookingDropdown.setModel(new DefaultComboBoxModel(r));
            ABBookingDropdown.validate();
            ABBookingDropdown.repaint();
            
            if (response.getSuccess()) {
                JOptionPane.showMessageDialog(null, "Success!");
            } else {
                JOptionPane.showMessageDialog(null, response.getReason());
            }
            return;

        } else if (command.equals("delete")) {
            Request request = new Request(Request.DELETE);

            String booking = (String) DBBookingDropdown.getModel().getSelectedItem();
            if(booking.equals("")){
                JOptionPane.showMessageDialog(null, "You have no bookings, make one first.");
                return;
            }
            String data[] = booking.split(",");
            String film = data[0].trim();
            String date = data[1].trim();
            String time = data[2].trim();
            int seats = Integer.parseInt(data[3].trim());

            request.setFilm(film);
            request.setDate(date);
            request.setTime(time);
            request.setSeats(seats);

            Response response = server.sendRequest(request);
            
            DBBookingDropdown.setModel(new DefaultComboBoxModel(server.getAllReservationsAsStrings(true)));
            DBBookingDropdown.validate();
            DBBookingDropdown.repaint();

            if (response.getSuccess()) {
                JOptionPane.showMessageDialog(null, "Success!");
            } else {
                JOptionPane.showMessageDialog(null, response.getReason());
            }
            return;

        } else if (command.equals("refresh")) {
            Request request = new Request(Request.REFRESH_OFFERS);

            Response response = server.sendRequest(request);
            if (response.getSuccess()) {
                DealsText.setText(response.getResponse());
                return;
            } else {
                JOptionPane.showMessageDialog(null, response.getReason());
                return;
            }

        } else {
            JOptionPane.showMessageDialog(null, "Something bad happened!");
            return;
        }
    }
    
    /**
     * Refreshes data on the selected tab after changing tab.
     * @param evt 
     */
    @Override
    public void stateChanged(ChangeEvent evt) {
        if (evt.getSource() instanceof JTabbedPane) {
            JTabbedPane pane = (JTabbedPane) evt.getSource();
            String name = pane.getTitleAt(pane.getSelectedIndex());

            if (name.equals("Create Booking")) {
                CBFilmDropdown.setModel(new DefaultComboBoxModel(server.getFilmNames()));
                itemStateChanged(new ItemEvent(CBFilmDropdown, 0, null, 0));
            } else if (name.equals("Amend Booking")) {
                ABBookingDropdown.setModel(new DefaultComboBoxModel(server.getAllReservationsAsStrings(true)));
                String noOfSeats[] = {"0"};
                ABSeatsSpinner.setModel(new SpinnerListModel(noOfSeats));
                ABSeatsSpinner.getModel().setValue(noOfSeats[0]);

                String reservation = (String) ABBookingDropdown.getModel().getSelectedItem();
                if (!reservation.equals("")) {
                    String data[] = reservation.split(",");
                    String ABfilm = data[0].trim();
                    String ABdate = data[1].trim();
                    String ABtime = data[2].trim();
                    String ABseats = data[3].trim();

                    ABSeatsSpinner.setModel(new SpinnerListModel(server.getFilmDateTimeSeats(ABfilm, ABdate, ABtime)));
                    ABSeatsSpinner.getModel().setValue(ABseats);
                }
            } else if (name.endsWith("Delete Booking")) {
                DBBookingDropdown.setModel(new DefaultComboBoxModel(server.getAllReservationsAsStrings(true)));
            }
            pane.repaint();
        }
    }

    /**
     * Refreshes the next dropdown box when one has been changed.
     * @param ie 
     */
    @Override
    public void itemStateChanged(ItemEvent ie) {
        JComboBox box = (JComboBox) ie.getSource();
        if(box == CBFilmDropdown){
            String film = (String)box.getModel().getSelectedItem();
            String[] dates = server.getFilmDates(film);
            CBDateDropdown.setModel(new DefaultComboBoxModel(dates));
            itemStateChanged(new ItemEvent(CBDateDropdown, 0, null, 0));
        } else if (box == CBDateDropdown) {
            String film = (String) CBFilmDropdown.getModel().getSelectedItem();
            String date = (String)box.getModel().getSelectedItem();
            String[] times = server.getFilmDateTimes(film, date);
            CBTimeDropdown.setModel(new DefaultComboBoxModel(times));
            itemStateChanged(new ItemEvent(CBTimeDropdown, 0, null, 0));
        } else if(box == CBTimeDropdown){
            String film = (String) CBFilmDropdown.getModel().getSelectedItem();
            String date = (String)CBDateDropdown.getModel().getSelectedItem();
            String time = (String)box.getModel().getSelectedItem();
            String[] seats = server.getFilmDateTimeSeats(film, date, time);
            CBSeatsDropdown.setModel(new DefaultComboBoxModel(seats));
            
        }
    }
}
