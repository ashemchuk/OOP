package ru.ashemchuk.player;

import java.util.Scanner;
import ru.ashemchuk.deck.Deck;

/**
 * Represents the human user player in the card game.
 * Handles user input for game decisions and extends the base Player functionality.
 */
public class User extends Player {
    private final static Scanner in = new Scanner(System.in);

    /**
     * Constructs a user player with the specified hand.
     *
     * @param hand the initial hand for the user
     */
    public User(Hand hand) {
        super(hand);
    }

    /**
     * Executes the user's turn by prompting for input and processing the decision.
     * The user can choose to take a card (1) or stop (0). Validates input and
     * handles exceptions. The turn continues if the user chooses to take a card
     * and their hand total is less than 21.
     *
     * @param deck the deck to draw from if the user chooses to take a card
     * @return true if the user can continue their turn (hand worth less 21),
     * false if the user stops or has 21 or more points
     */
    public boolean turn(Deck deck) {
        output.printInputPrompt();

        try {
            int next = in.nextInt();
            in.nextLine();
            switch (next) {
                case 0:
                    return false;
                case 1:
                    output.printMove(this, takeCard(deck));
                    break;
                default:
                    output.printBadInput();
            }
        } catch (Exception ex) {
            in.nextLine();
            output.printBadInput();
        }
        return hand.getTotalWorth() < 21;
    }

    /**
     * Returns the display title for the user.
     *
     * @return the string "User"
     */
    public String getTitle() {
        return "User";
    }
}