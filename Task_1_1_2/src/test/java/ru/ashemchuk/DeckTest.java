package ru.ashemchuk;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DeckTest {
    @Test
    void pull() {
        Deck deck = new Deck();
        assertNotNull(deck.pull());
        for (int i = 0; i < 51; i++) {
            deck.pull();
        }
        assertNotNull(deck.pull());
    }
}