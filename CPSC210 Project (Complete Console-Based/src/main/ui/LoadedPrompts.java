package ui;

import model.Event;
import model.EventLog;
import model.Prompt;
import model.PromptList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// Pulls up an instance of a GUI after a prompt list has been loaded
public class LoadedPrompts extends JFrame implements AllGUI {

    private PromptList promptList;
    private Prompt p1;
    private String prompt;
    private String song;
    private JTextField promptTextField;
    private JTextField songTextField;

    private static final String JSON_STORE = "./data/promptList.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS: constructs the GUI frame
    @SuppressWarnings("methodlength") // GUI is longer for functionality so supressed methodLength
    public LoadedPrompts() {
        super("Add prompts to prompt list");

        new Notification("Prompts are loaded in! Click 'Show past loaded pairings' to see them!");

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

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        JPanel godPanel = new JPanel(new FlowLayout());
        JPanel panel = new JPanel(new FlowLayout());
        JPanel panel2 = new JPanel(new FlowLayout());

        promptTextField = new JTextField(20);
        songTextField = new JTextField(20);
        promptList = new PromptList("test");

        JLabel l1 = new JLabel("Enter a prompt:");
        JLabel l2 = new JLabel("Enter an associated song:");
        JLabel l3 = new JLabel("Here are the prompts and associated songs you have chosen:");
        JLabel l4 = new JLabel("No prompts or songs yet!");

        // MODIFIES: this
        // EFFECTS: button adds song and prompt from text field to prompt list
        JButton submit = new JButton("Add song-prompt");
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                prompt = promptTextField.getText();
                song = songTextField.getText();

                p1 = new Prompt(prompt);
                p1.addSong(song);
                promptList.addPrompt(p1);
            }
        });

        // MODIFIES: l4
        // EFFECTS: button displays songs and associated prompts
        JButton show = new JButton("Show current pairing list");
        show.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String listString = String.join(", ", promptList.createMatchUps());
                l4.setText(listString);
            }
        });

        // MODIFIES: this, l4
        // EFFECTS: button shuffles order of prompt list
        JButton shuffle = new JButton("Shuffle current pairing list");
        shuffle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                promptList.shuffle();
                String listString = String.join(", ", promptList.createMatchUps());
                l4.setText(listString);
            }
        });

        // MODIFIES: this
        // EFFECTS: button saves prompt list to data file
        JButton save = new JButton("Save current pairing list");
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    jsonWriter.open();
                    jsonWriter.write(promptList);
                    jsonWriter.close();
                } catch (FileNotFoundException ex) {
                    new Notification("Did not save. :(");
                }
                new Notification("You've saved your prompt list :)");
            }
        });

        // MODIFIES: this, l4
        // EFFECTS: button loads data and creates prompt list to show on l4
        JButton load = new JButton("Show past loaded pairings");
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    promptList = jsonReader.read();
                } catch (IOException ex) {
                    new Notification("Unable to read from file :(");
                }
                String listString = String.join(", ", promptList.createMatchUps());
                l4.setText(listString);
            }
        });

        panel.add(l1);
        panel.add(promptTextField);
        panel.add(l2);
        panel.add(songTextField);
        panel.add(submit);
        panel.add(show);
        panel.add(shuffle);
        panel.add(save);
        panel.add(load);

        panel2.add(l3);
        panel2.add(l4);

        godPanel.add(panel);
        godPanel.add(panel2);

        add(godPanel);
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
