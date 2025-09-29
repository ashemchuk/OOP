package ru.ashemchuk.player;

import ru.ashemchuk.deck.Deck;
import ru.ashemchuk.deck.card.Card;
import ru.ashemchuk.deck.card.Rank;
import ru.ashemchuk.game.Output;

/**
 * Abstract base class representing a player in the card game.
 * Provides common functionality for both user and dealer players,
 * including hand management, score tracking, and card drawing logic.
 */
public abstract class Player {
    protected static final Output output = new Output();
    protected final Hand hand;
    protected int score;

    /**
     * Constructs a player with the specified hand.
     *
     * @param hand the initial hand for the player
     */
    public Player(Hand hand) {
        this.hand = hand;
    }

    /**
     * Returns the player's current hand of cards.
     *
     * @return the player's hand
     */
    public Hand getHand() {
        return this.hand;
    }

    /**
     * Returns the display title/name of the player.
     * Must be implemented by concrete subclasses.
     *
     * @return the player's title as a string
     */
    public abstract String getTitle();

    /**
     * Adds points to the player's score.
     *
     * @param amount the number of points to add to the player's score
     */
    public void addScore(int amount) {
        score += amount;
    }

    /**
     * Returns the player's current score.
     *
     * @return the player's score
     */
    public int getScore() {
        return score;
    }

    /**
     * Takes a card from the deck, applies special rules if needed, and adds it to the hand.
     * If the player's hand total is over 21 and the drawn card is an Ace,
     * the Ace will be downgraded to its lower value (1 instead of 11).
     *
     * @param deck the deck to draw from
     * @return the card that was taken from the deck
     */
    public Card takeCard(Deck deck) {
        Card card = deck.pull();
        int worth = hand.getTotalWorth();
        if (worth > 21 && card.getRank() == Rank.ACE) {
            card.downgrade();
        }
        hand.addCard(card);
        return card;
    }
}