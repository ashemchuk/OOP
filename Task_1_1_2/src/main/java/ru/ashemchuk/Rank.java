package ru.ashemchuk;

/**
 * Represents the ranks of playing cards in a standard deck.
 * Each rank has a display name and a point value used in game scoring.
 * Includes special handling for Ace which can have two different values.
 */
public enum Rank {
    TWO("Two", 2),
    THREE("Three", 3),
    FOUR("Four", 4),
    FIVE("Five", 5),
    SIX("Six", 6),
    SEVEN("Seven", 7),
    EIGHT("Eight", 8),
    NINE("Nine", 9),
    TEN("Ten", 10),
    JACK("Jack", 10),
    QUEEN("Queen", 10),
    KING("King", 10),
    ACE("Ace", 11),
    ACE_DOWNGRADED("Ace", 1);

    private final String display;
    private int worth;

    /**
     * Constructs a card rank with the specified display name and point value.
     *
     * @param display the human-readable name of the rank
     * @param worth   the point value of the rank in the game
     */
    Rank(String display, int worth) {
        this.display = display;
        this.worth = worth;
    }

    /**
     * Returns the point value of this rank.
     *
     * @return the worth value of the rank
     */
    public int getWorth() {
        return worth;
    }

    /**
     * Returns the display name of this rank.
     *
     * @return the human-readable name of the rank
     */
    public String getDisplay() {
        return display;
    }
}