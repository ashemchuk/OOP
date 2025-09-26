package ru.ashemchuk;

import java.util.Scanner;

/**
 * User
 */
public class User extends Player {
    private final static Scanner in = new Scanner(System.in);

    /**
     * default constructor
     * @param hand
     */
    public User(Hand hand) {
        super(hand);
    }

    /**
     * do user turn:
     * - promting user to input
     * - awaiting user's input, then validating
     * - checking hand worth
     * @param deck
     * @return user's hand worth
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
     * @return user's name display string
     */
    public String getTitle() {
        return "User";
    }
}
