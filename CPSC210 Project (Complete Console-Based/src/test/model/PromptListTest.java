package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PromptListTest {

    PromptList pl;
    Prompt p1;
    Prompt p2;
    ArrayList<String> al;

    @BeforeEach
    void setup() {
        pl = new PromptList("pl");
        p1 = new Prompt("p1");
        p2 = new Prompt("p2");
    }

    @Test
    void constructorTest() {
        assertEquals("pl", pl.getName());
        assertEquals(0, pl.getSize());
    }

    @Test
    void addPromptTest() {
        pl.addPrompt(p1);
        assertEquals(1, pl.getSize());
        assertEquals("p1", pl.getPrompts().get(0));
        pl.addPrompt(p1);
        assertEquals(2, pl.getSize());
        assertEquals("p1", pl.getPrompts().get(0));
        assertEquals("p1", pl.getPrompts().get(1));
        pl.addPrompt(p2);
        assertEquals(3, pl.getSize());
        assertEquals("p1", pl.getPrompts().get(0));
        assertEquals("p1", pl.getPrompts().get(1));
        assertEquals("p2", pl.getPrompts().get(2));
    }

    @Test
    void getElementTest() {
        pl.addPrompt(p1);
        pl.addPrompt(p2);
        assertEquals(p1, pl.getElement(0));
        assertEquals(p2, pl.getElement(1));
    }

    @Test
    void shuffleTest() {
        pl.addPrompt(p1);
        pl.addPrompt(p2);
        assertEquals(2, pl.getSize());
        pl.shuffle();
        assertEquals(2, pl.getSize());
    }

    @Test
    void createMatchUpsTest() {
        p1.addSong("s1");
        p2.addSong("s2");
        pl.addPrompt(p1);
        pl.addPrompt(p2);
        al = new ArrayList<>();
        al.add("p1: s1");
        al.add("p2: s2");
        assertEquals(al, pl.createMatchUps());
    }

    @Test
    void getPromptListTest() {
        ArrayList<Prompt> pl2 = new ArrayList<>();
        assertEquals(pl2, pl.getPromptList());
        pl2.add(p1);
        pl.addPrompt(p1);
        assertEquals(pl2, pl.getPromptList());
    }
}
