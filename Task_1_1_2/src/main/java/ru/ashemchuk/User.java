package ru.ashemchuk;

import java.util.Scanner;

public class User extends Player {
    private static Scanner in = new Scanner(System.in);

    public User(Hand hand) {
        super(hand);
    }

    public boolean turn(Deck deck) {
        output.printInputPrompt();

        try {
            int next = in.nextInt();
            in.nextLine();
            switch (next) {
                case 0:
                    return false;
                case 1:
                    output.printMove(this, takeCard(deck), hand);
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

    public String getTitle() {
        return "User";
    }
}
