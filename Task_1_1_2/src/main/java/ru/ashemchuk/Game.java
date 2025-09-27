package ru.ashemchuk;

/**
 * Manages the core game logic for a card game (likely Blackjack).
 * Controls the game flow, rounds, and interactions between dealer and user.
 * The game continues until either the user or dealer reaches 3 wins.
 */
public class Game {
    private Output out;
    private final Deck deck;
    private final Dealer dealer;
    private final User user;
    private int round = 1;

    /**
     * Constructs a new Game with the specified components.
     *
     * @param deck   the deck of cards to use for the game
     * @param dealer the dealer participant
     * @param user   the user participant
     * @param out    the output handler for displaying game information
     */
    public Game(Deck deck, Dealer dealer, User user, Output out) {
        this.deck = deck;
        this.dealer = dealer;
        this.user = user;
        this.out = out;
    }

    /**
     * Returns the current round number.
     *
     * @return the current round number (starting from 1)
     */
    public int getRound() {
        return round;
    }

    /**
     * Runs the main game loop until either the user or dealer reaches 3 wins.
     * Each iteration represents a complete round of the game.
     */
    public void runGame() {
        while (user.getScore() < 3 && dealer.getScore() < 3) {
            round();
        }
    }

    /**
     * Executes one complete round of the game.
     * Includes dealing initial cards, player turns, determining the winner,
     * updating scores, and resetting for the next round.
     */
    public void round() {
        out.printRoundStart(this);

        // Deal initial cards - dealer's second card is hidden (hole card)
        dealer.takeCard(deck);
        dealer.takeCard(deck, true);

        user.takeCard(deck);
        user.takeCard(deck);

        // Display initial hands
        out.printHand(user);
        out.printHand(dealer);

        // User's turn
        out.printTurn(user);
        while (user.turn(deck)) {
            // Continue user's turn until they stop drawing cards
        }

        // Reveal dealer's hole card
        dealer.openHoleCard();

        // Dealer's turn (only if user hasn't busted)
        if (user.getHand().getTotalWorth() < 21) {
            out.printTurn(dealer);
            while (dealer.turn(deck)) {
                // Continue dealer's turn according to game rules
            }
        }

        // Determine and display winner, update scores
        out.printWinner(user, dealer);
        out.printScores(user, dealer);

        // Display final hands
        out.printHand(user);
        out.printHand(dealer);

        // Prepare for next round
        round++;
        dealer.getHand().clear();
        user.getHand().clear();
    }
}