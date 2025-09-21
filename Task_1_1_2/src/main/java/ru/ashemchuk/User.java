package ru.ashemchuk;

import java.util.Scanner;

public class User extends Player{
    private Scanner console = new Scanner(System.in);

    public User(Hand hand) {
        super(hand);
    }
    public Hand turn(Deck deck) {
        System.out.println("Your turn!");
        System.out.println("-------");
        while (true) {
            if (hand.getTotalWorth() >= 21) {
                return hand;
            }
            System.out.println("Enter \"1\" to take a card and \"0\" to stop...");
            int nextInput = console.nextInt();
            if (nextInput == 0) {
                System.out.println();
                break;
            }
            if (nextInput == 1) {
                System.out.println("You open card: " + takeCard(deck).toString());
            }
            System.out.println("\tYour cards: " + hand);
        }
        return hand;
    }
}
