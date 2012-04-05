/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import shared.Request;
import shared.Response;

public class Menu extends JFrame implements WindowListener, ActionListener, ChangeListener{
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

   public Menu(Comms s) {
        
      super("Client GUI");
      this.server = s;
      
        messages = new Urgent();
        messages.start();
   

       JTabbedPane tabbedGUI = new JTabbedPane();
       tabbedGUI.addChangeListener(this);

       // Pane 1

        JPanel panel1 = new JPanel();
       
        // label for film

        JLabel labelFilm = new JLabel("Film", SwingConstants.LEFT);
        panel1.add(labelFilm);

        // dropdown box for films


        String CBfilm[] = server.getFilmNames();
        CBFilmDropdown = new JComboBox(CBfilm);
        panel1.add(CBFilmDropdown);

        // label for date

        JLabel labelDates = new JLabel("Dates", SwingConstants.LEFT);
        panel1.add(labelDates);

        // Dropdown box for date

        
        String CBdate[] = server.getFilmDates((String)CBFilmDropdown.getModel().getSelectedItem());
        CBDateDropdown = new JComboBox(CBdate);
        panel1.add(CBDateDropdown);

        // label for time

        JLabel labelTime = new JLabel("Time", SwingConstants.LEFT);
        panel1.add(labelTime);

        // Dropdown box for time

        String time[] = server.getFilmDateTimes((String)CBFilmDropdown.getModel().getSelectedItem(), (String)CBDateDropdown.getModel().getSelectedItem());
        CBTimeDropdown = new JComboBox(time);
        panel1.add(CBTimeDropdown);

        // label for Seats

        JLabel labelSeats = new JLabel("No of Seats", SwingConstants.LEFT);
        panel1.add(labelSeats);

        // Dropdown box for No of Seats


        String seats[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
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
        
        String booking[] = new String[server.getReservationsLength()];
        booking = server.getAllReservationsAsStrings();
        ABBookingDropdown = new JComboBox(booking);
        panel2.add(ABBookingDropdown);
        
        // adding Booking label

        JLabel labelAmendSeats = new JLabel("New No of Seats", SwingConstants.LEFT);
        panel2.add(labelAmendSeats);
        
        // No of seats spinner
        
        String[] noOfSeats = {"1","2","3","4","5","6","7"};
        SpinnerModel spinnerSeats = new SpinnerListModel(noOfSeats);
        ABSeatsSpinner = new JSpinner(spinnerSeats);
        panel2.add(ABSeatsSpinner);
       
        
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
        setSize(420, 220);
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
        String command = e.getActionCommand();
        if (command.equals("create")) {
            String film = (String) CBFilmDropdown.getModel().getSelectedItem();
            String date = (String) CBDateDropdown.getModel().getSelectedItem();
            String time = (String) CBTimeDropdown.getModel().getSelectedItem();

            //cast dropdown object to String then parse String to int
            int seats = Integer.parseInt((String) CBSeatsDropdown.getModel().getSelectedItem());

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

            if (response.getSuccess()) {
                JOptionPane.showMessageDialog(null, "Success!");
            } else {
                JOptionPane.showMessageDialog(null, response.getReason());
            }
            return;

        } else if (command.equals("delete")) {
            Request request = new Request(Request.DELETE);

            String booking = (String) DBBookingDropdown.getModel().getSelectedItem();
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
    
    
    @Override
    public void stateChanged(ChangeEvent evt) {

        JTabbedPane pane = (JTabbedPane) evt.getSource();
        String name = pane.getTitleAt(pane.getSelectedIndex());

        if (name.equals("Create Booking")) {
            CBFilmDropdown.setModel(new DefaultComboBoxModel(server.getFilmNames()));
        } else if (name.equals("Amend Booking")) {
            ABBookingDropdown.setModel(new DefaultComboBoxModel(server.getAllReservationsAsStrings(true)));
        } else if (name.endsWith("Delete Booking")) {
            DBBookingDropdown.setModel(new DefaultComboBoxModel(server.getAllReservationsAsStrings(true)));
        }
        pane.repaint();
    }
   
}
