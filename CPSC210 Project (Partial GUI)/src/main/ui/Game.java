package ui;

import model.Prompt;
import model.PromptList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

// Represents an instance of the game
public class Game {

    private PromptList p1PromptList;
    private PromptList p2PromptList;
    private String p1;
    private String p2;
    private int p2Score;
    private Scanner input;

    private static final String JSON_STORE = "./data/promptList.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS: runs the game
    public Game() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        p2Score = 0;
        runGame();
    }

    //MODIFIES: this
    //EFFECTS: processes user input
    //note: elements taken from TellerApp
    public void runGame() {
        boolean continueGame = true;
        String command;

        while (continueGame) {
            gameMenu();
            input = new Scanner(System.in);
            input.useDelimiter("\n");
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                continueGame = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("Game over, thanks for playing!");

    }

    //EFFECTS: displays game menu and options to choose from to user
    private void gameMenu() {
        System.out.println("\nWelcome to the Song Prompt Game!"
                + "\nPlease select from the following options:"
                + "\nStart Game = g"
                + "\nQuit game = q");
    }

    //MODIFIES: this
    //EFFECTS: processes user commands
    //note: elements taken from TellerApp
    private void processCommand(String command) {
        if (command.equals("g")) {
            playerSetup();
            playerOneTurn();
            playerTwoTurn();
            displayAnswersAndScores();
        } else {
            System.out.println("Invalid selection, please try again!\n");
        }
    }

    //MODIFIES: this
    //EFFECTS: takes the name for both players
    private void playerSetup() {
        System.out.println("\nPlease enter a name for Player 1 (the chooser):");
        p1 = input.next();

        System.out.println("Please enter a name for Player 2 (the guesser):");
        p2 = input.next();
    }

    //EFFECTS: displays text and runs methods associated with Player One's turn
    private void playerOneTurn() {
        p1PromptList = new PromptList("p1 promptList");
        System.out.println("\nHi " + p1 + "! Please choose a list of prompts to generate 10 random ones from:");
        System.out.println("\n1. Situational prompts -- e.g. Stargazing, Rainy Day, Breakup Blues"
                + "\n2. Mood prompts -- e.g. Happy, Bittersweet, Motivated"
                + "\n3. Theme prompts -- e.g. Nostalgia, Empowerment, Growth"
                + "\n4. Make your own prompt list!"
                + "\n5. Load last game's prompt list!");
        pickPrompts();
        System.out.println("\nNow, type in a song and artist for each prompt as it appears.");
        pickSongs();
    }

    //EFFECTS: displays text and runs methods associated with Player Two's turn
    private void playerTwoTurn() {
        p2PromptList = new PromptList("p2 promptList");
        System.out.println("\nNow pass the device to " + p2 + ". Press any key to continue.");
        input.next();
        displayPrompts();
        matchSongsToPrompts();
    }

    //EFFECTS: displays the correct and guessed answers, and Player Two's score
    private void displayAnswersAndScores() {
        calculateScore(p1PromptList, p2PromptList);
        System.out.println("\n" + p2 + " got " + p2Score + "/10 song-prompt pairings correct!");
        System.out.println(p1 + "'s pairings were: " + p1PromptList.createMatchUps());
        System.out.println(p2 + "'s guesses were: " + p2PromptList.createMatchUps());
    }

    //MODIFIES: p2Score
    //EFFECTS: increases Player Two's score by 1 with every matching prompt name between Player One and Two's lists
    private void calculateScore(PromptList p1, PromptList p2) {
        for (int i = 0; i < 10; i++) {
            if (p1.getElement(i).getName().equals(p2.getElement(i).getName())) {
                p2Score++;
            }
        }
    }

    //EFFECTS: determines which PromptList type the user wants
    private void pickPrompts() {
        String response = input.next();
        if (response.equals("1")) {
            chooseTenPrompts(generateSituationalPrompts());
            p1PromptList.getPrompts();
        } else if (response.equals("2")) {
            chooseTenPrompts(generateMoodPrompts());
            p1PromptList.getPrompts();
        } else if (response.equals("3")) {
            chooseTenPrompts(generateThemePrompts());
            p1PromptList.getPrompts();
        } else if (response.equals("4")) {
            createUserPromptList();
            p1PromptList.getPrompts();
        } else if (response.equals("5")) {
            loadPromptList();
        } else {
            System.out.println("Invalid selection, please try again!");
            pickPrompts();
        }
    }

    //EFFECTS: creates a custom PromptList with arbitrary prompts, then picks 10 random ones
    private void createUserPromptList() {
        boolean continueTakingInputs = true;
        ArrayList<String> userPromptList = new ArrayList<>();
        System.out.println("Please enter a minimum of 10 prompts below (when done, type q):");
        while (continueTakingInputs) {
            String response = input.next();
            if (response.equals("q")) {
                if (userPromptList.size() < 10) {
                    System.out.println("Please enter at least 10 prompts! Keep going, you got this :D");
                    continue;
                } else {
                    continueTakingInputs = false;
                    continue;
                }
            }
            userPromptList.add(response);
            System.out.println("Prompt added: " + response);
        }
        chooseTenPrompts(userPromptList);
    }

    //MODIFIES: p1PromptList
    //EFFECTS: adds songs to associated prompts, in p1PromptList
    private void pickSongs() {
        for (int i = 0; i < 10; i++) {
            System.out.println(p1PromptList.getElement(i).getName() + ":");
            p1PromptList.getElement(i).addSong(input.next());
        }
    }

    //EFFECTS: prints the list of Player One's prompts
    private void displayPrompts() {
        System.out.println("Here are the prompts that " + p1 + " chose:");
        System.out.println(p1PromptList.getPrompts());
    }

    //MODIFIES: p1PromptList, p2PromptList
    //EFFECTS: shuffles p1PromptList, gets user to guess the prompt that matches to corresponding song
    private void matchSongsToPrompts() {
        System.out.println("\nSongs that " + p1 + " assigned to these prompts will now randomly "
                + "appear. Type in the prompt (with correct spelling and capitalization) that you think "
                + p1 + " assigned to that song.");
        p1PromptList.shuffle();
        for (int i = 0; i < 10; i++) {
            System.out.println(p1PromptList.getElement(i).getSong());
            Prompt p = new Prompt(input.next());
            p2PromptList.addPrompt(p);
            p2PromptList.getElement(i).addSong(p1PromptList.getElement(i).getSong());
        }
    }

    //REQUIRES: prompts >= 10
    //MODIFIES: p1PromptList
    //EFFECTS: adds 10 random prompts from a list of more to p1PromptList
    private void chooseTenPrompts(ArrayList<String> prompts) {
        Collections.shuffle(prompts);
        Prompt p1 = new Prompt(prompts.get(0));
        Prompt p2 = new Prompt(prompts.get(1));
        Prompt p3 = new Prompt(prompts.get(2));
        Prompt p4 = new Prompt(prompts.get(3));
        Prompt p5 = new Prompt(prompts.get(4));
        Prompt p6 = new Prompt(prompts.get(5));
        Prompt p7 = new Prompt(prompts.get(6));
        Prompt p8 = new Prompt(prompts.get(7));
        Prompt p9 = new Prompt(prompts.get(8));
        Prompt p10 = new Prompt(prompts.get(9));
        p1PromptList.addPrompt(p1);
        p1PromptList.addPrompt(p2);
        p1PromptList.addPrompt(p3);
        p1PromptList.addPrompt(p4);
        p1PromptList.addPrompt(p5);
        p1PromptList.addPrompt(p6);
        p1PromptList.addPrompt(p7);
        p1PromptList.addPrompt(p8);
        p1PromptList.addPrompt(p9);
        p1PromptList.addPrompt(p10);
        savePromptList();
    }

    //EFFECTS: saves promptlist to file
    private void savePromptList() {
        System.out.println("Would you like to save your prompt list for the next game? y = yes, anything for no");
        if (input.next().equals("y")) {
            try {
                jsonWriter.open();
                jsonWriter.write(p1PromptList);
                jsonWriter.close();
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write file to " + JSON_STORE);
            }
            System.out.println("Saved your prompt list to " + JSON_STORE);
        }
    }

    //MODIFIES: this
    //EFFECTS: loads promptlist from file
    private void loadPromptList() {
        try {
            p1PromptList = jsonReader.read();
            System.out.println("Loaded last custom prompt list from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file " + JSON_STORE);
        }
        ArrayList<String> yeah = p1PromptList.getPrompts();
        System.out.println(yeah);
        savePromptList();
    }

    //EFFECTS: creates a list of 40 situational prompts, generated using ChatGPT
    @SuppressWarnings("methodlength")
    private ArrayList<String> generateSituationalPrompts() {
        ArrayList<String> situationPrompts = new ArrayList<>();
        situationPrompts.add("First Date");
        situationPrompts.add("Morning Commute");
        situationPrompts.add("Late Night Drive");
        situationPrompts.add("Lazy Sunday");
        situationPrompts.add("Rainy Day");
        situationPrompts.add("Party Time");
        situationPrompts.add("Bonfire Nights");
        situationPrompts.add("Beach Days");
        situationPrompts.add("Workout Sessions");
        situationPrompts.add("Study Breaks");
        situationPrompts.add("Bon Voyage");
        situationPrompts.add("Work from Home");
        situationPrompts.add("Moving On");
        situationPrompts.add("Summer Nights");
        situationPrompts.add("Snow Day");
        situationPrompts.add("Game Night");
        situationPrompts.add("Stargazing");
        situationPrompts.add("Traveling Alone");
        situationPrompts.add("Family Road Trip");
        situationPrompts.add("Breakup Blues");
        situationPrompts.add("Midsummer Madness");
        situationPrompts.add("Fall Foliage");
        situationPrompts.add("Fresh Start");
        situationPrompts.add("Afternoon Tea");
        situationPrompts.add("Night Walk");
        situationPrompts.add("Farmercore");
        situationPrompts.add("Winter Wonderland");
        situationPrompts.add("Spring Awakening");
        situationPrompts.add("End of an Era");
        situationPrompts.add("Rainy Day Blues");
        situationPrompts.add("Ocean Waves");
        situationPrompts.add("Wild Party");
        situationPrompts.add("At the Gym");
        situationPrompts.add("City Nights");
        situationPrompts.add("Lazy Afternoon");
        situationPrompts.add("Social Distance");
        situationPrompts.add("Road Less Traveled");
        situationPrompts.add("Family Gatherings");
        situationPrompts.add("Summer Love");
        situationPrompts.add("Reflective Walks");
        return situationPrompts;
    }

    //EFFECTS: creates a list of 40 theme prompts, generated using ChatGPT
    @SuppressWarnings("methodlength")
    private ArrayList<String> generateThemePrompts() {
        ArrayList<String> themePrompts = new ArrayList<>();
        themePrompts.add("Heartbreak");
        themePrompts.add("Love");
        themePrompts.add("Rebellion");
        themePrompts.add("Dreams");
        themePrompts.add("Friendship");
        themePrompts.add("Escape");
        themePrompts.add("Nostalgia");
        themePrompts.add("Freedom");
        themePrompts.add("Power");
        themePrompts.add("Hope");
        themePrompts.add("Adventure");
        themePrompts.add("Fantasy");
        themePrompts.add("Success");
        themePrompts.add("Inspiration");
        themePrompts.add("Coming of Age");
        themePrompts.add("Betrayal");
        themePrompts.add("Survival");
        themePrompts.add("Redemption");
        themePrompts.add("Reflection");
        themePrompts.add("Heartfelt");
        themePrompts.add("Growth");
        themePrompts.add("Sacrifice");
        themePrompts.add("Celebration");
        themePrompts.add("Longing");
        themePrompts.add("Gratitude");
        themePrompts.add("Diversity");
        themePrompts.add("Empowerment");
        themePrompts.add("Enchantment");
        themePrompts.add("Independence");
        themePrompts.add("Hopelessness");
        themePrompts.add("Overcoming");
        themePrompts.add("Empathy");
        themePrompts.add("Solidarity");
        themePrompts.add("Overthinking");
        themePrompts.add("Healing");
        themePrompts.add("Acceptance");
        themePrompts.add("Religious");
        themePrompts.add("Malicious");
        themePrompts.add("Medieval");
        themePrompts.add("Perseverance");
        return themePrompts;
    }

    //EFFECTS: creates a list of 40 mood prompts, generated using ChatGPT
    @SuppressWarnings("methodlength")
    private ArrayList<String> generateMoodPrompts() {
        ArrayList<String> moodPrompts = new ArrayList<>();
        moodPrompts.add("Happy");
        moodPrompts.add("Sad");
        moodPrompts.add("Angry");
        moodPrompts.add("Excited");
        moodPrompts.add("Melancholic");
        moodPrompts.add("Fearful");
        moodPrompts.add("Peaceful");
        moodPrompts.add("Moody");
        moodPrompts.add("Pensive");
        moodPrompts.add("Confused");
        moodPrompts.add("Anxious");
        moodPrompts.add("Bittersweet");
        moodPrompts.add("Blissful");
        moodPrompts.add("Cozy");
        moodPrompts.add("Depressed");
        moodPrompts.add("Energetic");
        moodPrompts.add("Flirty");
        moodPrompts.add("Gloomy");
        moodPrompts.add("Hyped");
        moodPrompts.add("Introspective");
        moodPrompts.add("Playful");
        moodPrompts.add("Sensual");
        moodPrompts.add("Thoughtful");
        moodPrompts.add("Caring");
        moodPrompts.add("Motivated");
        moodPrompts.add("Romantic");
        moodPrompts.add("Reflective");
        moodPrompts.add("Upbeat");
        moodPrompts.add("Wistful");
        moodPrompts.add("Yearning");
        moodPrompts.add("Whimsical");
        moodPrompts.add("Soulful");
        moodPrompts.add("Cheeky");
        moodPrompts.add("Blissed out");
        moodPrompts.add("Unsettling");
        moodPrompts.add("Numb");
        moodPrompts.add("Empowered");
        moodPrompts.add("Nostalgic");
        moodPrompts.add("Amorous");
        moodPrompts.add("Confused");
        return moodPrompts;
    }

}