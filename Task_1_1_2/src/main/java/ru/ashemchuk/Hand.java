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

    public int getTotalWorth() {
        //I'm proud of this :)
        return cards
                .stream()
                .map(card -> card.getRank().getWorth())
                .reduce(0, Integer::sum);
    }

}
