package ru.ashemchuk;

import java.util.Scanner;
import ru.ashemchuk.deck.Deck;
import ru.ashemchuk.game.Game;
import ru.ashemchuk.game.Output;
import ru.ashemchuk.player.Dealer;
import ru.ashemchuk.player.Hand;
import ru.ashemchuk.player.User;

/**
 * Main application class for the card game.
 * Serves as the entry point for the application and initializes the game components.
 */
public class Main {
    /**
     * Application entry point that initializes and starts the card game.
     * Creates all necessary components: deck, dealer, user, and output handler,
     * then starts the main game loop.
     *
     * @param args command-line arguments (not used in this application)
     */
    public static void main(String[] args) {
        Game game =
            new Game(new Deck(getDeckCount()),
                new Dealer(new Hand()), new User(new Hand()), new Output());
        game.runGame();
    }

    /**
     * Prompts the user to enter the number of decks to be used in the game.
     * Continuously requests input until a valid number between 1 and 20 is provided.
     *
     * @return the number of decks to be used in the game,
     * guaranteed to be between 1 and 20 inclusive
     */
    public static int getDeckCount() {
        int count;
        Scanner in = new Scanner(System.in);

        while (true) {
            System.out.println("Enter count of decks(from 1 to 20)...");
            try {
                count = in.nextInt();
                if (1 <= count && count <= 20) {
                    return count;
                }
            } catch (Exception ex) {
                in.nextLine();
                System.out.println("Please, enter a number");
            }
        }
    }
}