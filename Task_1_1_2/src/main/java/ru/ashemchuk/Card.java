package ru.ashemchuk;

import java.util.Random;

public class Card {

    private static final Random random = new Random();
    private boolean isHole = false; // 2nd dealer's card (hidden)
    private final Rank rank;
    private final Suit suit;
    Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public static Card randomOf() {
        Suit[] suits = Suit.values();
        Rank[] ranks = Rank.values();
        Rank rank = ranks[random.nextInt(ranks.length)];
        Suit suit = suits[random.nextInt(suits.length)];
        return new Card(suit, rank);
    }

    public Card withHole() {
        this.isHole = true;
        return this;
    }

    public Card open() {
        isHole = false;
        return this;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public boolean getIsHole() {
        return isHole;
    }

    @Override
    public String toString() {
        if (isHole) {
            return "<hole card>";
        }
        return suit.getDisplay() + " of " + rank.getDisplay() + "(" + getRank().getWorth() + ")";
    }

}
