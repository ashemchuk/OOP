package ru.ashemchuk;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private final List<Card> cards;

    public Hand() {
        this.cards = new ArrayList<>();
    }

    public List<Card> getCards() {
        return cards;
    }

    public Hand addCard(Card card) {
        cards.add(card);
        return this;
    }

    public void clear() {
        cards.clear();
    }

    public int getTotalWorth() {
        //I'm proud of this :)
        return cards
                .stream()
                .map(card -> card.getRank().getWorth())
                .reduce(0, Integer::sum);
    }

    @Override
    public String toString() {
        String out = this.getCards().toString();
        if (!cards.stream().map(Card::getIsHole).reduce(false, (acc, x) -> x || acc)) {
            out += " => " + this.getTotalWorth();
        }
        return out;
    }
}
