package ui;

import model.Event;
import model.EventLog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

// Represents player one's turn
public class PlayerOneGUI extends JFrame implements AllGUI {

    private JLabel message;
    private AddPromptsGUI addPromptsGUI;

    @SuppressWarnings("methodlength") // GUI is longer for functionality
    public PlayerOneGUI() {
        super("Player One's Turn");
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

        JPanel panel = new JPanel(new FlowLayout());

        message = new JLabel("Welcome Player One!");
        add(message, BorderLayout.NORTH);

        //EFFECTS: brings player to the prompt list adding menu
        JButton createPromptList = new JButton("Create prompt list");
        createPromptList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PlayerOneGUI.super.dispose();
                new AddPromptsGUI();
            }
        });

        //EFFECTS: brings player to the loaded version of the prompt list adding menu
        JButton loadPromptList = new JButton("Load last prompt list");
        loadPromptList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PlayerOneGUI.super.dispose();
                new LoadedPrompts();
            }
        });

        panel.add(createPromptList);
        panel.add(loadPromptList);
        add(panel);
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
