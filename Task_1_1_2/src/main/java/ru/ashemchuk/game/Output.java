package ru.ashemchuk.game;

import ru.ashemchuk.deck.card.Card;
import ru.ashemchuk.player.Player;

/**
 * Handles all console output operations for the card game.
 * Provides methods for displaying game state, player actions, and results.
 */
public class Output {

    /**
     * Prints the round start announcement with the current round number.
     *
     * @param game the current game instance
     */
    public void printRoundStart(Game game) {
        System.out.println("Round " + game.getRound());
        System.out.println("Dealer dealt the cards");
    }

    /**
     * Prints a player's current hand of cards.
     * Displays the player's title and the string representation of their hand.
     *
     * @param player the player whose hand to display
     */
    public void printHand(Player player) {
        System.out.println("\t" + player.getTitle() + ", your cards: " + player.getHand());
    }

    /**
     * Prints the input prompt for the user's turn.
     * Instructs the user to enter "1" to take a card or "0" to stop.
     */
    public void printInputPrompt() {
        System.out.println("Enter \"1\" to take a card and \"0\" to stop...");
    }

    /**
     * Prints a player's move when they draw a card.
     * Shows the drawn card and updates the display of the player's hand.
     *
     * @param player the player who made the move
     * @param card   the card that was drawn
     */
    public void printMove(Player player, Card card) {
        System.out.println(player.getTitle() + " open card: " + card);
        printHand(player);
    }

    /**
     * Prints the turn announcement for a player.
     * Includes visual separation for clarity.
     *
     * @param player the player whose turn is starting
     */
    public void printTurn(Player player) {
        System.out.println(player.getTitle() + ", it's your turn!");
        System.out.println("----------------");
        System.out.println();
    }

    /**
     * Prints a warning message for invalid user input.
     */
    public void printBadInput() {
        System.out.println("Bad input:(");
    }

    /**
     * Determines and prints the winner of the current round.
     * Updates player scores based on the outcome according to blackjack rules:
     * - Blackjack (21 points) wins automatically
     * - Player with points over 21 loses
     * - Higher point value wins if both are 21 or under
     * - Draw if point values are equal
     *
     * @param p1 the first player (typically the user)
     * @param p2 the second player (typically the dealer)
     */
    public void printWinner(Player p1, Player p2) {
        int p1Worth = p1.getHand().getTotalWorth();
        int p2Worth = p2.getHand().getTotalWorth();

        if (p1Worth == p2Worth) {
            System.out.println("Ooops, it's draw");
        } else if (p1Worth == 21) {
            System.out.printf("BLACKJACK! %s, you won\n", p1.getTitle());
            p1.addScore(1);
        } else if (p2Worth == 21) {
            System.out.printf("BLACKJACK! %s, you won\n", p2.getTitle());
            p2.addScore(1);
        } else if (p2Worth > 21) {
            System.out.printf("%s, you won! %s has over than 21 points\n", p1.getTitle(),
                p2.getTitle());
            p1.addScore(1);
        } else if (p1Worth > 21) {
            System.out.printf("%s, you won! %s has over than 21 points\n", p2.getTitle(),
                p1.getTitle());
            p2.addScore(1);
        } else if (p1Worth > p2Worth) {
            System.out.printf("%s, you won!\n", p1.getTitle());
            p1.addScore(1);
        } else {
            System.out.printf("%s, you won!\n", p2.getTitle());
            p2.addScore(1);
        }
    }

    /**
     * Prints the current scores of both players.
     * Displays the score in the format: "Player1 Score1:Score2 Player2"
     *
     * @param p1 the first player
     * @param p2 the second player
     */
    public void printScores(Player p1, Player p2) {
        System.out.printf("%s %d:%d %s\n", p1.getTitle(), p1.getScore(), p2.getScore(),
            p2.getTitle());
    }
}