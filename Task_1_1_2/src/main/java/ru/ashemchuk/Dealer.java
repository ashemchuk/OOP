package ru.ashemchuk;

public class Dealer extends Player{
    private Card holeCard;
    public Dealer(Hand hand) {
        super(hand);
    }

    public Card takeCard(Deck deck, boolean isHole) {
        Card card = super.takeCard(deck);
        if (isHole) {
            card.withHole();
            holeCard = card;
        }
        return card;
    }

    public Hand turn(Deck deck) {
        System.out.println("Dealer's turn");

        System.out.print("Dealer open hole card: " + holeCard.open().toString());
        while (hand.getTotalWorth() < 17) {
            System.out.println("Dealer open card: " + takeCard(deck).toString());
        }
        return hand;
    }
}
