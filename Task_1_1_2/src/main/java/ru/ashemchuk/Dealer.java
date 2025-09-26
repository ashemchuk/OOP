package ru.ashemchuk;

/**
 * Dealer
 */
public class Dealer extends Player {
    private Card holeCard;

    /**
     * @param hand
     */
    public Dealer(Hand hand) {
        super(hand);
    }

    /**
     * @return hole card
     */
    public Card getHoleCard() {
        return holeCard;
    }

    /**
     * takes card from deck, hole if specified
     * @param deck
     * @param isHole
     */
    public void takeCard(Deck deck, boolean isHole) {
        Card card = super.takeCard(deck);
        if (isHole) {
            card.withHole();
            holeCard = card;
        }
    }

    /**
     * do dealers turn
     * @param deck
     * @return should dealer do more moves?
     */
    public boolean turn(Deck deck) {
        output.printMove(this, takeCard(deck));
        return hand.getTotalWorth() < 17;
    }

    /**
     * opens hole card
     */
    public void openHoleCard() {
        holeCard.open();
    }

    /**
     * @return Dealer's display string
     */
    public String getTitle() {
        return "Dealer";
    }
}
