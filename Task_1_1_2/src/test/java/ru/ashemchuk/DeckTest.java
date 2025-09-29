package ru.ashemchuk;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import ru.ashemchuk.deck.Deck;

class DeckTest {
    @Test
    void pull_oneDeck() {
        Deck deck = new Deck();
        assertNotNull(deck.pull());
        for (int i = 0; i < 51; i++) {
            deck.pull();
        }
        assertNotNull(deck.pull());
    }

    @Test
    void pull_threeDecks() {
        Deck deck = new Deck(3);
        assertNotNull(deck.pull());
        for (int i = 0; i < 52 * 3 - 1; i++) {
            deck.pull();
        }
        assertNotNull(deck.pull());
    }
}