package persistence;

import model.Prompt;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkSongPrompt(String song, String prompt, Prompt p) {
        assertEquals(song, p.getSong());
        assertEquals(prompt, p.getName());
    }
}