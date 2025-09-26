package ru.ashemchuk;

/**
 * User console output use-cases
 */
public class Output {

    /**
     * Prints round's start preamble
     * @param game
     */
    public void printRoundStart(Game game) {
        System.out.println("Round " + game.getRound());
        System.out.println("Dealer dealt the cards");
    }

    /**
     * Prints player's cards in hand
     * @param player
     */
    public void printHand(Player player) {
        System.out.println("\t" + player.getTitle() + ", your cards: " + player.getHand());
    }

    /**
     * Prints user action prompt
     */
    public void printInputPrompt() {
        System.out.println("Enter \"1\" to take a card and \"0\" to stop...");
    }

    /**
     * Prints player's move
     * @param player
     * @param card
     */
    public void printMove(Player player, Card card) {
        System.out.println(player.getTitle() + " open card: " + card);
        printHand(player);
    }

    /**
     * Prints players turn preamble
     * @param player
     */
    public void printTurn(Player player) {
        System.out.println(player.getTitle() + ", it's your turn!");
        System.out.println("----------------");
        System.out.println();
    }

    /**
     * Prints warning about bad input
     */
    public void printBadInput() {
        System.out.println("Bad input:(");
    }

    /**
     * Calculates and printing turn's winner
     * @param p1
     * @param p2
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
            System.out.printf("%s, you won! %s has over than 21 points\n", p1.getTitle(), p2.getTitle());
            p1.addScore(1);
        } else if (p1Worth > 21) {
            System.out.printf("%s, you won! %s has over than 21 points\n", p2.getTitle(), p1.getTitle());
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
     * Print both players scores
     * @param p1
     * @param p2
     */
    public void printScores(Player p1, Player p2) {
        System.out.printf("%s %d:%d %s\n", p1.getTitle(), p1.getScore(), p2.getScore(), p2.getTitle());
    }

}
