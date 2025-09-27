package ru.ashemchuk;

/**
 * Represents the four classic suits of playing cards in a standard deck.
 * Each suit has a display name used for card representation.
 */
public enum Suit {
    SPADE("Spade"),
    HEART("Heart"),
    DIAMOND("Diamond"),
    CLUB("Club");

    private final String name;

    /**
     * Constructs a suit with the specified display name.
     *
     * @param name the human-readable name of the suit
     */
    Suit(String name) {
        this.name = name;
    }

    /**
     * Returns the display name of this suit.
     *
     * @return the human-readable name of the suit
     */
    public String getDisplay() {
        return this.name;
    }
}