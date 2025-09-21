package ru.ashemchuk;

public class Dealer extends Player{
    private Hand hand;

    public Dealer(Hand hand) {
        super(hand);
    }

    public Card takeCard(Deck deck, boolean isHole) {
        Card card = super.takeCard(deck);
        if (isHole) {
            card.withHole();
        }
        return card;
    }
}
