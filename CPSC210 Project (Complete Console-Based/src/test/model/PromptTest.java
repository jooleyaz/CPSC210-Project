package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PromptTest {

    Prompt p;

    @BeforeEach
    void setup() {
        p = new Prompt("p");
    }

    @Test
    void constructorTest() {
        assertEquals("p", p.getName());
        assertEquals(null, p.getSong());
    }

    @Test
    void addSongTest() {
        p.addSong("s");
        assertEquals("s", p.getSong());
    }

}
