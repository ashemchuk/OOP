package ru.ashemchuk;

/**
 * Classic card suits
 */
public enum Suit {
    SPADE("Spade"),
	HEART("Heart"),
	DIAMOND("Diamond"),
	CLUB("Club");

    private final String name; // name ??? :(

    /**
     * default constructor
     * @param name
     */
    Suit(String name) {
        this.name = name;
    }

    /**
     * @return cards display string
     */
    public String getDisplay() {
        return this.name;
    }
}

