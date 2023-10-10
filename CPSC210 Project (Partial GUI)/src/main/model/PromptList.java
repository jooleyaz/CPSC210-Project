package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;

// represents a list of prompts with a name
public class PromptList implements Writable {

    private ArrayList<Prompt> promptList;
    private String name;

    //EFFECTS: constructs a prompt list with a name and empty list of Prompts
    public PromptList(String n) {
        name = n;
        promptList = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: adds a prompt to the prompt list
    public void addPrompt(Prompt p) {
        promptList.add(p);
        EventLog.getInstance().logEvent(new Event("Prompt added."));
    }

    //EFFECTS: returns the list of strings that make up the prompts only in the prompt list, no associated songs
    public ArrayList<String> getPrompts() {
        ArrayList<String> prompts = new ArrayList<>();
        for (Prompt p : promptList) {
            prompts.add(p.getName());
        }
        return prompts;
    }

    //EFFECTS: returns the list of prompts that make up a prompt list
    public ArrayList<Prompt> getPromptList() {
        return this.promptList;
    }

    //EFFECTS: returns the xth element in the prompt list
    public Prompt getElement(int x) {
        return promptList.get(x);
    }

    //MODIFIES: this
    //EFFECTS: shuffles the order of the prompts in the prompt list
    public void shuffle() {
        Collections.shuffle(promptList);
        EventLog.getInstance().logEvent(new Event("Prompt list shuffled."));
    }

    //EFFECTS: returns the list of strings that combine prompts and songs together
    public ArrayList<String> createMatchUps() {
        ArrayList<String> matchUps = new ArrayList<>();
        for (Prompt p : promptList) {
            matchUps.add(p.getName() + ": " + p.getSong());
        }
        EventLog.getInstance().logEvent(new Event("Returned prompt-song list."));
        return matchUps;
    }

    //EFFECTS: returns name of prompt list
    public String getName() {
        return this.name;
    }

    //EFFECTS: returns size of prompt list
    public int getSize() {
        return this.promptList.size();
    }

    //EFFECTS: returns JSONObject for promptList
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("promptList", promptListToJson());
        return json;
    }

    //EFFECTS: returns JSONArray of prompts in promptList
    private JSONArray promptListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Prompt p : promptList) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }
}