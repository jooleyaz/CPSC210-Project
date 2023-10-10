package persistence;

import model.Prompt;
import model.PromptList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            PromptList promptList = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptySongPromptList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptySongPromptList.json");
        try {
            PromptList promptList = reader.read();
            assertEquals(0, promptList.getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralSongPromptList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralSongPromptList.json");
        try {
            PromptList promptList = reader.read();
            ArrayList<Prompt> prompts = promptList.getPromptList();
            assertEquals(2, promptList.getSize());
            checkSongPrompt("song1", "prompt1", promptList.getElement(0));
            checkSongPrompt("song2", "prompt2", promptList.getElement(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}