package ru.ashemchuk;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> cards;

    public Hand() {
        this.cards = new ArrayList<Card>();
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
        return this.getCards().toString() + " => " + this.getTotalWorth();
    }

    public String toString(boolean isHole) {
        String out = this.getCards().toString();
        if (!isHole) {
            out += " => " + this.getTotalWorth();
        }
        return out;
    }
}
