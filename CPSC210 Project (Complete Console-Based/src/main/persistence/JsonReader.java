package persistence;

import model.Prompt;
import model.PromptList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads songpromptlist from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public PromptList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePromptList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses promptList from JSON object and returns it
    private PromptList parsePromptList(JSONObject jsonObject) {
        PromptList promptList = new PromptList("test");
        addPrompts(promptList, jsonObject);
        return promptList;
    }

    // MODIFIES: songPromptList
    // EFFECTS: parses usersongprompts from JSON object and adds them to songPromptList
    private void addPrompts(PromptList promptList, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("promptList");
        for (Object json : jsonArray) {
            JSONObject nextSongPrompt = (JSONObject) json;
            addPrompt(promptList, nextSongPrompt);
        }
    }

    // MODIFIES: songPromptList
    // EFFECTS: parses songPrompt from JSON object and adds it to songPromptList
    private void addPrompt(PromptList promptList, JSONObject jsonObject) {
        String prompt = jsonObject.getString("name");
        String song = jsonObject.getString("song");
        Prompt p = new Prompt(prompt);
        p.addSong(song);
        promptList.addPrompt(p);
    }
}