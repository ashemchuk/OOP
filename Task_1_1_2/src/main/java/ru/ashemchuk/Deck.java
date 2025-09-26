package ru.ashemchuk;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class Deck {
    static final Random random = new Random();
    final List<Card> cards = new LinkedList<>();

    public Card pull() {
        if (cards.isEmpty()) {
            fill();
        }
        Card card = cards.get(random.nextInt(cards.size()));
        cards.remove(card);
        return card;
    }

    private void fill() {
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                cards.add(new Card(suit, rank));
            }
        }
    }
}