package ru.ashemchuk;

/**
 * Main application class
 */
public class Main {
    /**
     * Application entrypoint
     * @param args
     */
    public static void main(String[] args) {
        Game game = new Game(new Deck(), new Dealer(new Hand()), new User(new Hand()), new Output());
        game.runGame();
    }
}
