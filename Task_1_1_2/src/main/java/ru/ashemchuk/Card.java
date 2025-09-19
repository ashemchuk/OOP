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
        return suit.getDisplay() + "of" + rank.getDisplay();
    }

    private enum Rank {
        TWO("Two", 2), THREE("Three", 3), FOUR("Four", 4), FIVE("Five", 5), SIX("Six", 6), SEVEN("Seven", 7), EIGHT("Eight", 8), NIGHT("Night", 9), TEN("Ten", 10), JACK("Jack", 10), QUEEN("Queen", 10), KING("King", 10), ACE("Ace", 11);

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

    private enum Suit {
        SPADE("Spade"), HEART("Heart"), DIAMOND("Diamond"), CLUB("Club");

        private final String name; // name ??? :(

        Suit(String name) {
            this.name = name;
        }

        public String getDisplay() {
            return this.name;
        }
    }
}
