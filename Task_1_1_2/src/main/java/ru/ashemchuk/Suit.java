package ru.ashemchuk;

public enum Suit {
    SPADE("Spade"),
	HEART("Heart"),
	DIAMOND("Diamond"),
	CLUB("Club");

    private final String name; // name ??? :(

    Suit(String name) {
        this.name = name;
    }

    public String getDisplay() {
        return this.name;
    }
}

