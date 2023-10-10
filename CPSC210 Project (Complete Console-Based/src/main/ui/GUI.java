package ui;

import model.Event;
import model.EventLog;
import model.PromptList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;

// Runs the game by starting a GUI instance
public class GUI extends JFrame implements AllGUI {

    // represents an instance of the main GUI of the game
    @SuppressWarnings("methodlength") // GUI is longer for functionality so suppressed methodLength
    public GUI() {
        super("Song-Prompt Guessing Game");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ArrayList<String> log = printLog(EventLog.getInstance());
                for (String s : log) {
                    System.out.println(s);
                }
                System.exit(0);
            }
        });
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ImageIcon welcomeImage = new ImageIcon("data/welcomescreen.png");

        JLabel welcomeScreen = new JLabel(welcomeImage);
        welcomeScreen.setIcon(welcomeImage);
        welcomeScreen.setOpaque(true);
        welcomeScreen.setText("Welcome to the Song-Prompt Guessing Game!");
        welcomeScreen.setHorizontalTextPosition(JLabel.CENTER);
        welcomeScreen.setVerticalTextPosition(JLabel.TOP);

        //EFFECTS: creates button to start game
        JButton beginButton = new JButton("Start Game");
        beginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GUI.super.dispose();
                new PlayerOneGUI();
            }
        });

        add(welcomeScreen);
        add(beginButton, BorderLayout.SOUTH);

        pack();
        setVisible(true);

    }

    public ArrayList<String> printLog(EventLog el) {
        ArrayList<String> log = new ArrayList<>();
        for (Event next : el) {
            log.add(next.getDate() + ": " + next.getDescription());
        }
        return log;
    }


}