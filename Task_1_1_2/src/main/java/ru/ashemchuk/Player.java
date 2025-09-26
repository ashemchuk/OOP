package ru.ashemchuk;

/**
 * Player
 */
public abstract class Player {
    protected static final Output output = new Output();
    protected final Hand hand;
    protected int score;

    /**
     * default constructor
     * @param hand
     */
    public Player(Hand hand) {
        this.hand = hand;
    }

    /**
     * @return players' hand
     */
    public Hand getHand() {
        return this.hand;
    }

    /**
     * @return player's name display string
     */
    public abstract String getTitle();

    /**
     * add points to player's score
     * @param amount - points to add
     */
    public void addScore(int amount) {
        score += amount;
    }

    /**
     * @return player's score
     */
    public int getScore() {
        return score;
    }

    /**
     * takes card from deck, downgrade if it needs, and pushing into hand
     * @param deck
     * @return taken card
     */
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
