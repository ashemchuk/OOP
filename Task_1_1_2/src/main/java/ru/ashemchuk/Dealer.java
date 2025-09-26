package ru.ashemchuk;

public class Dealer extends Player {
    private Card holeCard;

    public Dealer(Hand hand) {
        super(hand);
    }

    public Card getHoleCard() {
        return holeCard;
    }

    public void takeCard(Deck deck, boolean isHole) {
        Card card = super.takeCard(deck);
        if (isHole) {
            card.withHole();
            holeCard = card;
        }
    }

    public boolean turn(Deck deck) {
        output.printMove(this, takeCard(deck));
        return hand.getTotalWorth() < 17;
    }

    public void openHoleCard() {
        holeCard.open();
    }

    public String getTitle() {
        return "Dealer";
    }
}
