package ru.ashemchuk;

/**
 * Classic 52-pcs games deck card
 */
public class Card {
    private final Rank rank;
    private final Suit suit;
    private boolean isHole = false; // 2nd dealer's card (hidden)

    /**
     * @param suit of card
     * @param rank of card
     */
    Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * hide card (for 2nd dealer's card)
     * @return card with .isHole = true
     */
    public Card withHole() {
        this.isHole = true;
        return this;
    }

    /**
     * open hole card
     * @return opened card
     */
    public Card open() {
        isHole = false;
        return this;
    }

    /**
     * @return rank of card
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * @return is card hole
     */
    public boolean getIsHole() {
        return isHole;
    }

    /**
     * @return cards display string
     */
    @Override
    public String toString() {
        if (isHole) {
            return "<hole card>";
        }
        return suit.getDisplay() + " of " + rank.getDisplay() + "(" + getRank().getWorth() + ")";
    }

}
