package ru.ashemchuk;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player's hand containing playing cards.
 * Provides functionality to manage cards, calculate hand value,
 * and display the hand's contents.
 */
public class Hand {
    private final List<Card> cards;

    /**
     * Constructs an empty hand.
     */
    public Hand() {
        this.cards = new ArrayList<>();
    }

    /**
     * Returns the list of cards currently in the hand.
     *
     * @return an unmodifiable list of cards in this hand
     */
    public List<Card> getCards() {
        return cards;
    }

    /**
     * Adds a card to the hand.
     *
     * @param card the card to add to the hand
     * @return this hand instance for method chaining
     */
    public Hand addCard(Card card) {
        cards.add(card);
        return this;
    }

    /**
     * Removes all cards from the hand, making it empty.
     */
    public void clear() {
        cards.clear();
    }

    /**
     * Calculates the total point value of all cards in the hand.
     * Uses the worth value of each card's rank and sums them together.
     *
     * @return the total point value of the hand
     */
    public int getTotalWorth() {
        //I'm proud of this :)
        return cards
            .stream()
            .map(card -> card.getRank().getWorth())
            .reduce(0, Integer::sum);
    }

    /**
     * Returns a string representation of the hand.
     * If any card in the hand is a hole card (face down), only the card list is shown.
     * If all cards are face up, the total point value is also displayed.
     *
     * @return string representation of the hand and its total value (if visible)
     */
    @Override
    public String toString() {
        String out = this.getCards().toString();
        if (!cards.stream().map(Card::getIsHole).reduce(false, (acc, x) -> x || acc)) {
            out += " => " + this.getTotalWorth();
        }
        return out;
    }
}