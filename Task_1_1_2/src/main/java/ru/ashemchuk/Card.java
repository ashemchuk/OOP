package ru.ashemchuk;

/**
 * Represents a playing card from a standard 52-card deck.
 * Each card has a suit, rank, and can be marked as a hole card (face down).
 * This class is commonly used in card games like blackjack.
 */
public class Card {
    private final Suit suit;
    private Rank rank;
    private boolean isHole = false; // 2nd dealer's card (hidden)

    /**
     * Constructs a new Card with the specified suit and rank.
     *
     * @param suit the suit of the card
     * @param rank the rank of the card
     */
    Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * Marks this card as a hole card (face down).
     * Typically used for the dealer's second card in blackjack.
     *
     * @return this card instance with hole status set to true
     */
    public Card withHole() {
        this.isHole = true;
        return this;
    }

    /**
     * Reveals a hole card by setting its hole status to false.
     *
     * @return this card instance with hole status set to false
     */
    public Card open() {
        isHole = false;
        return this;
    }

    /**
     * Returns the rank of this card.
     *
     * @return the rank of this card
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Downgrades an Ace card to its lower value.
     * If this card is an Ace, its rank is changed to ACE_DOWNGRADED.
     * This is typically used in blackjack when an Ace should be worth 1 instead of 11.
     */
    public void downgrade() {
        if (getRank() == Rank.ACE) {
            this.rank = Rank.ACE_DOWNGRADED;
        }
    }

    /**
     * Checks if this card is a hole card (face down).
     *
     * @return true if this card is a hole card, false otherwise
     */
    public boolean getIsHole() {
        return isHole;
    }

    /**
     * Returns a string representation of this card.
     * If the card is a hole card, returns "hole card".
     * Otherwise, returns the card's display format including suit, rank, and point value.
     *
     * @return string representation of this card
     */
    @Override
    public String toString() {
        if (isHole) {
            return "<hole card>";
        }
        return suit.getDisplay() + " of " + rank.getDisplay() + "(" + getRank().getWorth() + ")";
    }
}