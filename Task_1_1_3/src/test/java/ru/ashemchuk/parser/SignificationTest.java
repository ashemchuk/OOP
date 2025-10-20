package ru.ashemchuk.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class SignificationTest {

    @Test
    void parseSignification_oneSingleLetter() {
        Signification oneSingleLetter = new Signification();
        oneSingleLetter.parseSignification("x = 1");
        assertEquals(1, oneSingleLetter.getValue("x"));
    }

    @Test
    void parseSignification_severalSingleLetter() {
        Signification severalSingleLetter = new Signification();
        severalSingleLetter.parseSignification("a = 1; b = 3; c = 2");
        assertEquals(1, severalSingleLetter.getValue("a"));
        assertEquals(3, severalSingleLetter.getValue("b"));
        assertEquals(2, severalSingleLetter.getValue("c"));
    }

    @Test
    void parseSignification_several() {
        Signification several = new Signification();
        several.parseSignification("aboba = 52; T = 4");
        assertEquals(52, several.getValue("aboba"));
        assertEquals(4, several.getValue("T"));
    }

    @Test
    void parseSignification_spaces() {
        Signification spaces = new Signification();
        spaces.parseSignification("        a = 4;        b =     0");
        assertEquals(4, spaces.getValue("a"));
        assertEquals(0, spaces.getValue("b"));
    }

    @Test
    void parseSignification_invalid() {
        Signification invalid = new Signification();
        invalid.parseSignification("a = 0; dsdf == 3; b = 1");
        assertEquals(0, invalid.getValue("a"));
        assertNull(invalid.getValue("dsdf"));
        assertEquals(1, invalid.getValue("b"));
    }

    @Test
    void parseSignification_valueNotInt() {
        Signification valueNotInt = new Signification();
        valueNotInt.parseSignification("a = b");
        assertNull(valueNotInt.getValue("a"));
    }

    @Test
    void parseSignification_nullString() {
        Signification nullString = new Signification();
        nullString.parseSignification(null);
        assertNull(nullString.getValue("x"));
    }

    @Test
    void parseSignification_extraDelim() {
        Signification extraDelim = new Signification();
        extraDelim.parseSignification("x = 3; y = 4; ;");
        assertEquals(3, extraDelim.getValue("x"));
        assertEquals(4, extraDelim.getValue("y"));
    }

    @Test
    void parseSignification_incorrectDelim() {
        Signification incorrectDelim = new Signification();
        incorrectDelim.parseSignification("f = 0; x = 3, y = 4; xz = 5");
        assertEquals(0, incorrectDelim.getValue("f"));
        assertEquals(5, incorrectDelim.getValue("xz"));
        assertNull(incorrectDelim.getValue("x"));
        assertNull(incorrectDelim.getValue("y"));
    }

}