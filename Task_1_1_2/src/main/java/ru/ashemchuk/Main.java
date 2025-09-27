package ru.ashemchuk;

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
            new Game(new Deck(), new Dealer(new Hand()), new User(new Hand()), new Output());
        game.runGame();
    }
}