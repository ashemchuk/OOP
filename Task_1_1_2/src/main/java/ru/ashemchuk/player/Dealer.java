package ru.ashemchuk.player;

import ru.ashemchuk.deck.Deck;
import ru.ashemchuk.deck.card.Card;

/**
 * Represents a dealer in a card game, extending the Player class.
 * The dealer has special rules and capabilities, including managing a hole card
 * (face-down card) and following specific drawing rules during their turn.
 */
public class Dealer extends Player {
    private Card holeCard;

    /**
     * Constructs a Dealer with the specified hand.
     *
     * @param hand the initial hand for the dealer
     */
    public Dealer(Hand hand) {
        super(hand);
    }

    /**
     * Returns the dealer's hole card (face-down card).
     *
     * @return the hole card, or null if no hole card has been set
     */
    public Card getHoleCard() {
        return holeCard;
    }

    /**
     * Takes a card from the deck, optionally marking it as a hole card.
     * If the card is marked as a hole card, it will be stored separately
     * and displayed face down until revealed.
     *
     * @param deck   the deck to draw from
     * @param isHole true if the card should be marked as a hole card, false otherwise
     */
    public void takeCard(Deck deck, boolean isHole) {
        Card card = super.takeCard(deck);
        if (isHole) {
            card.withHole();
            holeCard = card;
        }
    }

    /**
     * Executes the dealer's turn according to standard casino rules.
     * The dealer must draw cards until their hand total reaches 17 or higher.
     *
     * @param deck the deck to draw from
     * @return true if the dealer should continue drawing (hand total less than 17),
     * false if the dealer should stop (hand total 17 or higher)
     */
    public boolean turn(Deck deck) {
        output.printMove(this, takeCard(deck));
        return hand.getTotalWorth() < 17;
    }

    /**
     * Reveals the dealer's hole card by turning it face up.
     * This is typically done at the end of all player turns.
     */
    public void openHoleCard() {
        holeCard.open();
    }

    /**
     * Returns the title used for displaying the dealer.
     *
     * @return the string "Dealer"
     */
    public String getTitle() {
        return "Dealer";
    }
}