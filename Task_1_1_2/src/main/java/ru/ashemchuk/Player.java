package ru.ashemchuk;

public abstract class Player {
    private Hand hand;

    public Player(Hand hand) {
        this.hand = hand;
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
