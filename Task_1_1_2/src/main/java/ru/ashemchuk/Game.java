package ru.ashemchuk;

public class Game {
    private Deck deck;
    private Dealer dealer;
    private User user;
    private int userScore = 0;
    private int dealerScore = 0;

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

        user.turn(deck);
        dealer.turn(deck);


        dealer.getHand().clear();
        user.getHand().clear();
    }
}
