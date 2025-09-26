package ru.ashemchuk;

/**
 * Classic card ranks
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
     * default constructor
     * @param display
     * @param worth
     */
    Rank(String display, int worth) {
        this.display = display;
        this.worth = worth;
    }

    /**
     * @return rank's worth
     */
    public int getWorth() {
        return worth;
    }

    /**
     * @return rank's display string
     */
    public String getDisplay() { // rank or display???
        return display;
    }
}

