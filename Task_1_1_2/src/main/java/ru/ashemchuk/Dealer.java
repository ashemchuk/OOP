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
        System.out.println("-------");

        System.out.println("Dealer open hole card: " + holeCard.open());
        System.out.println("\tDealer's cards" + hand);
        while (hand.getTotalWorth() < 17) {
            System.out.println("Dealer open card: " + takeCard(deck));
            System.out.println("\tDealer's cards: " + hand);
        }
        return hand;
    }
}
