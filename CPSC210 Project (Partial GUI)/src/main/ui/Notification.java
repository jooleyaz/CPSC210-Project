package ui;

import javax.swing.*;

// Pulls up a pop-up window with a text notification
public class Notification extends JFrame {

    //EFFECTS: constructs a notification window
    public Notification(String s) {
        super("Notification!");
        JLabel label = new JLabel(s);
        add(label);
        pack();
        setVisible(true);
    }

}
