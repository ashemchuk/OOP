package ru.ashemchuk;

public class Game {
    private Deck deck;
    private Dealer dealer;
    private User user;
    private static int userScore = 0;
    private static int dealerScore = 0;

    public Game() {
        this.deck = new Deck();
        this.dealer = new Dealer(new Hand());
        this.user = new User(new Hand());
    }

    public void round() {
        dealer.takeCard(deck);
        dealer.takeCard(deck, true);

        user.takeCard(deck);
        user.takeCard(deck);

        System.out.println("Dealer dealt the cards");
        System.out.println("\tYour cards: " + user.hand);
        System.out.println("\tDealer's cards: " + dealer.hand.toString(true));

        user.turn(deck);
        if (user.hand.getTotalWorth() < 21) dealer.turn(deck);

        System.out.println();

        int userWorth = user.hand.getTotalWorth();
        int dealerWorth = dealer.hand.getTotalWorth();

        if (userWorth == dealerWorth) {
            System.out.println("Ooops, it's draw");
        } else if (userWorth == 21) {
            System.out.println("BLACKJACK! You won");
            userScore++;
        } else if (dealerWorth == 21) {
            System.out.println("BLACKJACK! Dealer won");
            dealerScore++;
        } else if (dealerWorth > 21) {
            System.out.println("You won! Dealer has over than 21 points");
            userScore++;
        } else if (userWorth > 21) {
            System.out.println("Dealer won! You has over than 21 points");
            dealerScore++;
        } else if (userWorth > dealerWorth) {
            System.out.println("You won!");
            userScore++;
        } else {
            System.out.println("Dealer won!");
            dealerScore++;
        }

        System.out.println("Score: " + userScore + ":" + dealerScore + "\n\n");

        dealer.getHand().clear();
        user.getHand().clear();
    }
}
