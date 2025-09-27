package ru.ashemchuk;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

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