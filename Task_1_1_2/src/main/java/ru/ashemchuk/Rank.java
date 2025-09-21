package ru.ashemchuk;

public enum Rank {
    TWO("Two", 2), THREE("Three", 3), FOUR("Four", 4), FIVE("Five", 5), SIX("Six", 6), SEVEN("Seven", 7), EIGHT("Eight", 8), NINE("Nine", 9), TEN("Ten", 10), JACK("Jack", 10), QUEEN("Queen", 10), KING("King", 10), ACE("Ace", 11);

    private final String display;
    private int worth;

    Rank(String display, int worth) {
        this.display = display;
        this.worth = worth;
    }

    public int getWorth() {
        return worth;
    }

    public String getDisplay() { // rank or display???
        return display;
    }

    public void downgrade() {
        if (this == ACE) {
            this.worth = 1;
        }
    }
}

