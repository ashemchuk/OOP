package ru.ashemchuk;

public abstract class Player {
    protected static Output output = new Output();
    protected Hand hand;
    protected int score;

    public Player(Hand hand) {
        this.hand = hand;
    }

    public Hand getHand() {
        return this.hand;
    }

    public abstract boolean turn(Deck deck);

    public abstract String getTitle();

    public int addScore(int amount) {
        return score += amount;
    }

    public int getScore() {
        return score;
    }

    public Card takeCard(Deck deck) {
        Card card = deck.pull();
        int worth = hand.getTotalWorth();
        if (worth > 21 && card.getRank() == Rank.ACE) {
            card.getRank().downgrade();
        }
        hand.addCard(card);
        return card;
    }
}
