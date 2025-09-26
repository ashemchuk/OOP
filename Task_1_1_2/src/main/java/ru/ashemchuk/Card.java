package ru.ashemchuk;

public class Card {
    private boolean isHole = false; // 2nd dealer's card (hidden)
    private final Rank rank;
    private final Suit suit;
    Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
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
