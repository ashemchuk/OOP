package ru.ashemchuk;

public abstract class Player {
    protected Hand hand;

    public Player(Hand hand) {
        this.hand = hand;
    }

    public Hand getHand() {
        return this.hand;
    }

    public abstract Hand turn(Deck deck);

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
