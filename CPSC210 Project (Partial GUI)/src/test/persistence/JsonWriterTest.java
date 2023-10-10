package persistence;

import model.Prompt;
import model.PromptList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            PromptList promptList = new PromptList("test");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptySongPromptList() {
        try {
            PromptList promptList = new PromptList("test");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptySongPromptList.json");
            writer.open();
            writer.write(promptList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptySongPromptList.json");
            promptList = reader.read();
            assertEquals(0, promptList.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralSongPromptList() {
        try {
            PromptList promptList = new PromptList("test");
            Prompt p1 = new Prompt("prompt1");
            Prompt p2 = new Prompt("prompt2");
            p1.addSong("song1");
            p2.addSong("song2");
            promptList.addPrompt(p1);
            promptList.addPrompt(p2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralSongPromptList.json");
            writer.open();
            writer.write(promptList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralSongPromptList.json");
            promptList = reader.read();
            List<Prompt> prompts = promptList.getPromptList();
            assertEquals(2, prompts.size());
            checkSongPrompt("song1", "prompt1", prompts.get(0));
            checkSongPrompt("song2", "prompt2", prompts.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}