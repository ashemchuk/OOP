package ru.ashemchuk;

public class Game {
    private static final Output out = new Output();
    private final Deck deck;
    private final Dealer dealer;
    private final User user;
    private int round = 1;

    public Game() {
        this.deck = new Deck();
        this.dealer = new Dealer(new Hand());
        this.user = new User(new Hand());
    }

    public int getRound() {
        return round;
    }

    public void runGame() {
        while (user.getScore() < 3 && dealer.getScore() < 3) {
            round();
        }
    }

    public void round() {
        out.printRoundStart(this);

        dealer.takeCard(deck);
        dealer.takeCard(deck, true);

        user.takeCard(deck);
        user.takeCard(deck);

        out.printHand(user);
        out.printHand(dealer);

        out.printTurn(user);
        while (user.turn(deck)) {
        }
        dealer.openHoleCard();
        if (user.hand.getTotalWorth() < 21) {
            out.printTurn(dealer);
            while (dealer.turn(deck)) {
            }
        }

        out.printWinner(user, dealer);
        out.printScores(user, dealer);

        out.printHand(user);
        out.printHand(dealer);

        round++;
        dealer.getHand().clear();
        user.getHand().clear();
    }
}
