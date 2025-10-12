package ru.ashemchuk.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class SignificationTest {

    @Test
    void parseSignification() {
        Signification oneSingleLetter = new Signification();
        oneSingleLetter.parseSignification("x = 1");
        assertEquals(1, oneSingleLetter.getValue("x"));

        Signification severalSingleLetter = new Signification();
        severalSingleLetter.parseSignification("a = 1; b = 3; c = 2");
        assertEquals(1, severalSingleLetter.getValue("a"));
        assertEquals(3, severalSingleLetter.getValue("b"));
        assertEquals(2, severalSingleLetter.getValue("c"));

        Signification several = new Signification();
        several.parseSignification("aboba = 52; T = 4");
        assertEquals(52, several.getValue("aboba"));
        assertEquals(4, several.getValue("T"));

        Signification spaces = new Signification();
        spaces.parseSignification("        a = 4;        b =     0");
        assertEquals(4, spaces.getValue("a"));
        assertEquals(0, spaces.getValue("b"));

        Signification invalid = new Signification();
        invalid.parseSignification("a = 0; dsdf == 3; b = 1");
        assertEquals(0, invalid.getValue("a"));
        assertNull(invalid.getValue("dsdf"));
        assertEquals(1, invalid.getValue("b"));

        Signification valueNotInt = new Signification();
        valueNotInt.parseSignification("a = b");
        assertNull(valueNotInt.getValue("a"));
    }

}