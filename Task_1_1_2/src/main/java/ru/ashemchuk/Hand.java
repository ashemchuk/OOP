package ru.ashemchuk;

import java.util.ArrayList;
import java.util.List;

/**
 * Player's hand with cards
 */
public class Hand {
    private final List<Card> cards;

    /**
     * default constructor
     */
    public Hand() {
        this.cards = new ArrayList<>();
    }

    /**
     * @return cards in hand
     */
    public List<Card> getCards() {
        return cards;
    }

    /**
     * @param card to add in hand
     * @return added card
     */
    public Hand addCard(Card card) {
        cards.add(card);
        return this;
    }

    /**
     * clears hand
     */
    public void clear() {
        cards.clear();
    }

    /**
     * @return worth of cards in hand
     */
    public int getTotalWorth() {
        //I'm proud of this :)
        return cards
                .stream()
                .map(card -> card.getRank().getWorth())
                .reduce(0, Integer::sum);
    }

    /**
     * @return display string of cards in hand with worthes and holes
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
