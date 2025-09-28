package ru.ashemchuk.deck;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import ru.ashemchuk.deck.card.Card;
import ru.ashemchuk.deck.card.Rank;
import ru.ashemchuk.deck.card.Suit;

/**
 * Represents a deck of playing cards from a standard 52-card deck.
 * The deck automatically refills itself when empty by creating a new set of cards.
 * Cards are drawn randomly from the deck using a random number generator.
 */
public class Deck {
    static final Random random = new Random();
    final List<Card> cards = new LinkedList<>();

    /**
     * Draws a random card from the deck. If the deck is empty, it will be
     * automatically refilled with a new set of 52 cards before drawing.
     * The drawn card is removed from the deck.
     *
     * @return a randomly selected card from the deck
     */
    public Card pull() {
        if (cards.isEmpty()) {
            fill();
        }
        Card card = cards.get(random.nextInt(cards.size()));
        cards.remove(card);
        return card;
    }

    /**
     * Fills the deck with a complete set of 52 playing cards.
     * Creates one card for each combination of suit and rank.
     * This method is called automatically when the deck becomes empty.
     */
    private void fill() {
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                cards.add(new Card(suit, rank));
            }
        }
    }
}