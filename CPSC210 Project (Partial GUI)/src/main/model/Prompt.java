package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// represents a prompt and an associated song
public class Prompt implements Writable {

    private String name;
    private String song;

    //EFFECTS: constructs a prompt with a name and no song
    public Prompt(String n) {
        name = n;
        song = null;
    }

    //EFFECTS: returns the name of the prompt
    public String getName() {
        return name;
    }

    //EFFECTS: returns the name of the song associated with the prompt
    public String getSong() {
        return song;
    }

    //MODIFIES: this
    //EFFECTS: associates a song with the prompt
    public void addSong(String s) {
        this.song = s;
        EventLog.getInstance().logEvent(new Event("Song added."));
    }

    //EFFECTS: returns a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("song", song);
        return json;
    }

}

